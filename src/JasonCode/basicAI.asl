// Agent ant in project ants

/* Initial goals */
!check_percepts.

/* Plans */
		
/**
 * Chooses a random location where to crawl and crawls there
 */	
+!crawl_random
	: true
	<-
	actions.getRandomDirection(X, Y);
	crawl(X, Y);
	-going_to_resource(_,_).
	
/**
 * Crawls to specific location
 * @param X Location coordinate x
 * @param Y Location coordinate y
 */	
+!crawl_to(X,Y)
	: true
	<-
	crawl(X,Y).
	
/**
 * Waits after perceptual update
 */
+!wait
	: update_rate(Rate)
	<-
	.wait(Rate).

/**
 * If there is no update_rate belief due to belief error, this gets new rate
 */
+!wait
	: true
	<-
	actions.getUpdateRate(Rate);
	+update_rate(Rate);
	.wait(Rate).
	
/**
 * I or someone else has killed enemy I was attacking or he got away
 * stop attacking
 */
+!check_percepts
	: attacking(Enemy)
	& not enemy(_,_,Enemy)
	<-
	-attacking(_);
	stop_attack;
	!!finished;
	!!check_percepts.
	
/**
 * Periodically checks percepts and if there is enemy, choose what to do
 */
+!check_percepts
	: enemy(_,_,_)
	<-
	update_percepts;
	!!enemy;
	!wait;
	!!check_percepts.
	
/**
 * I've reached destination, invokes !!finished and updates percepts
 */
+!check_percepts
	: .my_name(Me)
	& actions.finishedMovement(Me)
	<-
	update_percepts;
	-going_to_resource(_,_);
	-going_home;
	!!finished;
	!wait;
	!!check_percepts.
	
/**
 * I've collected resource, go home
 */
+!check_percepts
	: collected(_)
	& not enemy(_,_,_)
	<-
	update_percepts;
	!!go_home;
	!wait;
	!!check_percepts.
	   
/**
 * Cyclically updates percepts
 * this is necessary because precepts can only be updated within reasoning cycle
 * therefore I can't push them from environment
 */
+!check_percepts
	: true
	<-
	update_percepts;
	!wait;
	!!check_percepts.
	
+resource(X,Y,N,Resource)[source(percept)]
	: resource(X,Y,N,Resource)[source(self)]
	<-
	true.
	
/**
 * Rediscovery of resource to where I am heading
 */
+resource(X,Y,N,Resource)[source(percept)]
	: not collected(_)
	& going_to_resource(X,Y)
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource).
	
/**
 * Some new resource were found and I'm heading to collect other one,
 * this decides which one would be best to collect
 */
+resource(X,Y,N,Resource)[source(percept)]
	: not collected(_)
	& not enemy(_,_,_)
	& going_to_resource(AX,AY)
	& home(HX,HY)
	& actions.isCloser(AX,AY,X,Y,HX,HY)
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource);
	!!go_to_resource(X,Y).
	
/**
 * Found new resource and if I'm not heading towards any other resource
 * and I can carry more, go to resource and collect it
 */
+resource(X,Y,N,Resource)[source(percept)]
	: not collected(_)
	& not enemy(_,_,_)
	& not going_to_resource(_,_)
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource);
	!!go_to_resource(X,Y).

/**
 * Resource was found or rediscovered. Note its position and amount
 */
+resource(X,Y,N,Resource)[source(percept)]
	: true
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource).
	
/**
 * Goes to resource location
 */
+!go_to_resource(X,Y)
	: not enemy(_,_,_)
	<-
	+going_to_resource(X,Y);
	!!crawl_to(X,Y).
	
/**
 * Enemy is nearby, do nothing
 */
+!go_to_resource(_,_)
	:true
	<-
	true.
	
/**
 * Attempts to collect resource from location where agent is (check is done by environment)
 */
+!collect_resource(X,Y,Resource)
	: not enemy(_,_,_)
	<-
	-going_to_resource(_,_);
	collect(Resource);
	-resource(X,Y,_,Resource)[source(self)].
	
/**
 * There is agent nearby or resource was depleted 
 */
-!collect_resource(_,_,_)
	: true
	<-
	-going_to_resource(_,_);
	update_percepts;
	!!finished.

/**
 * Reached home with resources, unloads everything
 */
+!finished
	: collected(_)
	& pos(X, Y)
	& home(X,Y)
	<-
	unload.	

/**
 * Finished collecting resources, go home
 */	
+!finished
	: collected(_)
	<-
	!!go_home.
	
/**
 * Reached destination of resource, collect it
 */
+!finished
	: pos(X,Y)
	& resource(X,Y,_,Resource)[source(percept)]
	& not collected(_)
	<- 
	!!collect_resource(X,Y,Resource).

/**
 * Reached destination but resource was already collected by someone else
 */
+!finished
	: pos(X,Y)
	& resource(X,Y,_,_)
	& not resource(X,Y,_,_)[source(percept)]
	<-
	-resource(X,Y,_,_);
	!!finished.

/**
 * Chooses closest resource
 */
+!finished 
	: resource(_,_,_,_)
	<-
	.findall(resource(X,Y,_,_),resource(X,Y,_,_),ListOfResources);
	!create_resource_list(ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,_,_)));
	!!go_to_resource(ResourceX,ResourceY).
	
/**
 * Reached destination but hadn't found anything in process, move to random
 * location
 */
+!finished
	: not collected(_)
	& not enemy(_,_,_)
	<-
	!!crawl_random.
	
/**
 * There is probably enemy nearby, I should not do any action
 */
+!finished
	: enemy(_,_,_)
	<-
	!!enemy.
	
/**
 * Fail event for finished, should never be triggered though
 */
-!finished
	: true
	<-
	true.
	
/**
 * End of iteration
 */
+!create_resource_list([],[]).

/**
 * Calculates distance from agents position to resource and reconstructsts list
 */
+!create_resource_list([resource(ResourceX,ResourceY,_,_)|Body],[distance(D,resource(ResourceX,ResourceY,_,_))|ResultedBody]) 
	: pos(X,Y)
	<-
	actions.calculateDistance(X,Y,ResourceX,ResourceY,D);
	!create_resource_list(Body,ResultedBody).
     
 /**
  * Skips unknown head (should not happen though)
  */
+!create_resource_list([_|Body],ResultedBody) 
	:true
	<-
	!create_resource_list(Body,ResultedBody).
	
/**
 * Calculation may fail from time to time
 */
-!create_resource_list(_,_)
	: true
	<-
	true.
	
/**
 * Agent goes home
 */
+!go_home
	: home(X,Y)
	<-
	+going_home;
	!!crawl_to(X,Y).
	
/**
 * Sometimes it randomly fails because of problem with beliefs...
 */
+!go_home
	: .my_name(Me)
	<-
	actions.getHome(Me,X,Y);
	+going_home;
	!!crawl_to(X,Y).
	
/**
 * If I'm already attacking agent, but I can't win anymore, run away
 */
+!enemy
	: .count(enemy(_,_,_,_), 1)
	& .count(friend(_,_,_), 0)
	& attacking(Enemy)
	& .my_name(Me)
	& not actions.isWeaker(Enemy, Me)
	<-
	-attacking(_);
	stop_attack;
	actions.getEscapeDirection(Me,Enemy,Ex,Ey);
	!crawl_to(Ex,Ey).
	
/**
 * Agent found new enemy which can be defeated, attack
 */
+!enemy
	: .count(enemy(_,_,_), 1)
	& .count(friend(_,_,_), 0)
	& .my_name(Me)
	& enemy(_,_,Enemy)
	& actions.isWeaker(Enemy, Me)
	<-
	+attacking(Enemy);
	attack(Enemy).
	
/**
 * There is more friends (including me) then enemies nearby
 */
+!enemy
	: attacking(_)
	& .count(enemy(_,_,_), Enemies)
	& .count(friend(_,_,_), Friends)
	& Enemies <= Friends
	<-
	true.
	
/**
 * There is more friends (including me) then enemies nearby
 */
+!enemy
	: attacking(Enemy)
	& .count(enemy(_,_,_), Enemies)
	& .count(friend(_,_,_), Friends)
	& Enemies + 1 > Friends
	& .my_name(Me)
	<-
	stop_attack;
	-attacking(_);
	actions.getEscapeDirection(Me,Enemy,Ex,Ey);
	!crawl_to(Ex,Ey).
	
/**
 * There is more friends (including me) then enemies nearby
 */
+!enemy
	: not attacking(_)
	& .count(enemy(_,_,_), Enemies)
	& .count(friend(_,_,_), Friends)
	& Enemies <= Friends
	& enemy(_,_,Enemy)
	<-
	+attacking(Enemy);
	attack(Enemy).
	
/**
 * Agent is weaker and should run from enemy
 */
+!enemy
	: not attacking(_)
	& enemy(_,_,Enemy)
	& .my_name(Me)
	<-
	-attacking(_);
	stop_attack;
	actions.getEscapeDirection(Me,Enemy,Ex,Ey);
	!crawl_to(Ex,Ey).

/**
 * Some other possibility that is not included in previous plans
 */	
+!enemy
	: true
	<-
	true.
	
