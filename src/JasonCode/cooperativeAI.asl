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
	: actions.getUpdateRate(Rate)
	<-
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
	-requested_help;
	-attacking(_);
	stop_attack;
	!!finished;
	!!check_percepts.
	
/**
 * Periodically checks percepts and if there is enemy, choose what to do
 */
+!check_percepts
	: enemy(_,_,_)
	& requested_help
	<-
	update_percepts;
	-helping(_);
	!!stay_near_enemy;
	!wait;
	!!check_percepts.
	
/**
 * Periodically checks percepts and if there is enemy, choose what to do
 */
+!check_percepts
	: enemy(_,_,_)
	<-
	update_percepts;
	-helping(_);
	!!enemy;
	!wait;
	!!check_percepts.
	
/**
 * 
 */
+!check_percepts
	: helping(Agent)
	& actions.dead(Agent)
	<-
	-helping(Agent);
	!!check_percepts.
	
/**
 * 
 */
+!check_percepts
	: helping(Agent)
	& friend(_,_,Agent)
	<-
	-helping(Agent);
	update_percepts;
	!!check_percepts.
	
	
/**
 * 
 */
+!check_percepts
	: helping(Agent)
	<-
	update_percepts;
	actions.getPosition(Agent,X,Y);
	!!crawl_to(X,Y);
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
	-helping(_);
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
	
/**
 * Fail event handler
 */
-!check_percepts
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
 * Resoulve problem with sending to multiple agents
 */
+resource(X,Y,N,Resource)[source(percept)]
	: N > 1
	& not resource(X,Y,_,_)[source(self)]
	<-
	!inform_agents_about_resource(X,Y);
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource);
	.wait(500);
	!allocate_agents(X,Y,N,Resource).
	
	
+resource(X,Y,N,Resource)[source(percept)]
	: true
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource).
	
/**
 * Some new resource were found and I'm heading to collect other one,
 * this decides which one would be best to collect
 */
+resource(X,Y,_,_)
	: not collected(_)
	& not enemy(_,_,_)
	& going_to_resource(AX,AY)
	& home(HX,HY)
	& actions.isCloser(AX,AY,X,Y,HX,HY)
	<-
	!!go_to_resource(X,Y).
	
/**
 * Found new resource and if I'm not heading towards any other resource
 * and I can carry more, go to resource and collect it
 */
+resource(X,Y,_,_)
	: not collected(_)
	& not enemy(_,_,_)
	& not going_to_resource(_,_)
	<-
	!!go_to_resource(X,Y).
	
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
	-resource(X,Y,_,Resource)[source(self)];
	update_percepts;
	.wait(100);
	!!inform_others(X,Y,Resource).
	
/**
 * There is agent nearby or resource was depleted 
 */
-!collect_resource(_,_,_)
	: true
	<-
	-going_to_resource(_,_);
	update_percepts;
	!!finished.
	
+!inform_others(X,Y,Resource)
	: resource(X,Y,_,Resource)[source(percept)]
	<-
	true.
	
+!inform_others(X,Y,Resource)
	: true
	<-
	actions.teamBroadcast(achieve, remove_resource(X,Y,Resource)).
	

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
	& not helping(_)
	<-
	!!go_home.
	
/**
 * Reached destination of resource, collect it
 */
+!finished
	: pos(X,Y)
	& resource(X,Y,_,Resource)[source(percept)]
	& not collected(_)
	& not helping(_)
	<- 
	!!collect_resource(X,Y,Resource).

/**
 * Reached destination but resource was already collected by someone else
 */
+!finished
	: pos(X,Y)
	& resource(X,Y,_,_)
	& not resource(X,Y,_,Resource)[source(percept)]
	& not helping(_)
	<-
	-resource(X,Y,_,_);
	actions.teamBroadcast(achieve, remove_resource(X,Y,Resource));
	!!finished.
		
/**
 * Chooses closest resource
 */
+!finished 
	: resource(_,_,_,_)
	& not helping(_)
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
	& not helping(_)
	<-
	!!crawl_random.
	
/**
 * There is probably enemy nearby, I should not do any action
 */
+!finished
	: enemy(_,_,_)
	& not helping(_)
	<-
	!!enemy.
	
/**
 * There is probably enemy nearby, I should not do any action
 */
+!finished
	: enemy(_,_,_)
	& not helping(Agent)
	<-
	-helping(Agent);
	.send(Agent, achieve, busy);
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
	!!request_help;
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
	!!request_help;
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
	stop_attack;
	actions.getEscapeDirection(Me,Enemy,Ex,Ey);
	!!request_help;
	!crawl_to(Ex,Ey).

/**
 * Some other possibility that is not included in previous plans
 */	
+!enemy
	: true
	<-
	true.
	
/**
 * Inform all agents about newly discovered resource
 */
+!inform_agents_about_resource(X,Y)
	: true
	<-
	actions.teamBroadcast(achieve,get_distance(X,Y));.
	
/**
 * Respond with distance to location X, Y
 */
+!get_distance(X,Y)[source(Agent)]
	: not enemy(_,_,_)
	& not collected(_)
	& not going_to_resource(_,_)
	& pos(AgX,AgY)
	<-
	actions.calculateDistance(X,Y,AgX,AgY,Distance);
	.send(Agent, achieve, response(X,Y,Distance)).
	
/**
 * Already going to those resources, pick me again so no extra agents are notified
 */
+!get_distance(X,Y)[source(Agent)]
	: not enemy(_,_,_)
	& going_to_resource(X,Y)
	<-
	.send(Agent, achieve, response(X,Y,0)).
	
/**
 * Respond with distance to location X, Y
 */
+!get_distance(X,Y)[source(Agent)]
	: not enemy(_,_,_)
	& not collected(_)	
	& going_to_resource(AX,AY)
	& home(HX,HY)
	& actions.isCloser(AX,AY,X,Y,HX,HY)
	& pos(AgX,AgY)
	<-
	actions.calculateDistance(X,Y,AgX,AgY,Distance);
	.send(Agent, achieve, response(X,Y,Distance)).
	
/**
 * Already carrying something, bid high
 */
+!get_distance(X,Y)[source(Agent)]
	: pos(AgX,AgY)
	<-
	actions.calculateDistance(X,Y,AgX,AgY,Distance);
	.send(Agent, achieve, response(X,Y,10*Distance)).
	
	
/**
 * Fail event for get distance
 */
-!get_distance(_,_)
	: true
	<-
	true.
	
/**
 * Stores information about distance of an agent from resource 
 */
+!response(X,Y,Distance)[source(Agent)]
	: true
	<-
	+friend_bid(Distance,X,Y,Agent).
	
/**
 * 
 */
+!allocate_agents(X,Y,N,Resource)
	: friend_bid(_,X,Y,_)
	& N > 0
	<-
	.findall(friend_bid(Dist,AX,AY,Ag),friend_bid(Dist,AX,AY,Ag),ListOfBids);
	.min(ListOfBids,friend_bid(D,X,Y,Agent));
	.send(Agent, achieve, add_resource(X,Y,N,Resource));
	-friend_bid(D,X,Y,Agent);
	!allocate_agents(X,Y,N-1,Resource).
	
+!allocate_agents(X,Y,_,_)
	: true
	<-
	.abolish(friend_bid(_,X,Y,_)).
	
/**
 * Fail event since sometimes the list is empty and therefore there is no agent to whom we can send allocation request
 */
-!allocate_agents(X,Y,_,_)
	:true
	<-
	.abolish(friend_bid(_,X,Y,_)).

+!add_resource(X,Y,N,Resource)
	: true
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource).
	
-!remove_resource(X,Y,Resource)
	: true
	<-
	-resource(X,Y,_,Resource);
	!!finished.
	
+!request_help
	: not requested_help
	& pos(X,Y)
	<-
	+requested_help;
	actions.teamBroadcast(achieve, need_help(X,Y));
	.wait(500);
	!!check_who_volunteered.
	
+!request_help
	: true
	<-
	true.
	
+!check_who_volunteered
	: .count(enemy(_,_,_), Enemies)
	& .count(offer_to_help(_,_), Friends)
	& Friends+1 >= Enemies
	<-
	!!allocate_help(Enemies).

/**
 * Not enough friends volunteered
 */
+!check_who_volunteered
	: true
	<-
	-requested_help;
	-helping(_).
	
+!allocate_help(N)
	: N > 0
	<-
	.findall(offer_to_help(Distance,Agent),offer_to_help(Distance,Agent),ListOfBids);
	.min(ListOfBids,offer_to_help(D,Ag));
	.send(Ag, achieve, help);
	-offer_to_help(D,Ag);
	!allocate_help(N-1).
	
+!allocate_help(_)
	: true
	<-
	.abolish(offer_to_help(_,_)).
	
-!allocate_help(_)
	: true
	<-
	.abolish(offer_to_help(_,_)).
	
	
/**
 * Respond with distance to location X, Y
 */
+!need_help(_,_)[source(Agent)]
	: helping(Agent)
	<-
	.send(Agent, achieve, response_to_help(0)).
	
/**
 * Respond with distance to location X, Y
 */
+!need_help(X,Y)[source(Agent)]
	: not enemy(_,_,_)
	& not collected(_)	
	& going_to_resource(AX,AY)
	& home(HX,HY)
	& actions.isCloser(AX,AY,X,Y,HX,HY)
	& pos(AgX,AgY)
	<-
	actions.calculateDistance(X,Y,AgX,AgY,Distance);
	.send(Agent, achieve, response_to_help(Distance)).
	
/**
 * Carrying resources, I'm less likely to help
 */
+!need_help(X,Y)[source(Agent)]
	: not enemy(_,_,_)
	& pos(AgX,AgY)
	<-
	actions.calculateDistance(X,Y,AgX,AgY,Distance);
	.send(Agent, achieve, response_to_help(2*Distance)).
	
/**
 * Already attacking some enemy
 */
+!need_help(_,_)
	: true
	<-
	true.

/**
 * Stores response of request for help
 */
+!response_to_help(Distance)[source(Agent)]
	: true
	<-
	+offer_to_help(Distance, Agent).
	
/**
 * Agreed to help to friend who is running away from enemy
 */
+!help[source(Agent)]
	: true
	<-
	+helping(Agent).
	
+!busy
	: true
	<-
	-requested_help.
	
+!busy
	: true
	<-
	-requested_help.
	
-requested_help
	: not enemy(_,_,_)
	<-
	actions.teamBroadcast(achieve, dont_need_help).
	
+!dont_need_help[source(Agent)]
	: true
	<-
	-helping(Agent).
	
/**
 * If I'm already attacking agent, but I can't win anymore, run away
 */
+!stay_near_enemy[source(self)]
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
+!stay_near_enemy[source(self)]
	: .count(enemy(_,_,_), 1)
	& .count(friend(_,_,_), 0)
	& .my_name(Me)
	& enemy(_,_,Enemy)
	& actions.isWeaker(Enemy, Me)
	<-
	-requested_help;
	+attacking(Enemy);
	attack(Enemy).
	
/**
 * There is more friends (including me) then enemies nearby
 */
+!stay_near_enemy[source(self)]
	: attacking(_)
	& .count(enemy(_,_,_), Enemies)
	& .count(friend(_,_,_), Friends)
	& Enemies <= Friends
	<-
	-requested_help;.
	
/**
 * There is more friends (including me) then enemies nearby
 */
+!stay_near_enemy[source(self)]
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
+!stay_near_enemy[source(self)]
	: not attacking(_)
	& .count(enemy(_,_,_), Enemies)
	& .count(friend(_,_,_), Friends)
	& Enemies <= Friends
	& enemy(_,_,Enemy)
	<-
	+attacking(Enemy);
	-requested_help;
	attack(Enemy).
	
/**
 * Agent is weaker and should run from enemy
 */
+!stay_near_enemy[source(self)]
	: not attacking(_)
	& enemy(_,_,Enemy)
	& .my_name(Me)
	<-
	stop_attack;
	actions.getEscapeDirection(Me,Enemy,Ex,Ey);
	!crawl_to(Ex,Ey).
	
/**
 * 
 */
+!stay_near_enemy[source(self)]
	: true
	<-
	-requested_help.

-helping(Agent)
	: not friend(_,_,Agent)
	<-
	!inform_I_will_not_help_anymore(Agent).
	
+!inform_I_will_not_help_anymore(Agent)
	: true
	<-
	.send(Agent, achieve, busy).
	
/**
 * Send failed (probably null pointer exception because Agent was killed)
 */
-!inform_I_will_not_help_anymore(_)
	:true
	<-
	true.