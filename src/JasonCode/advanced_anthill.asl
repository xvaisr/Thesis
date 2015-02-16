!check_percepts.

/* Internal actions */
// new_agent
// upgrade_attack
// upgrade_armor
// upgrade_speed

/* Upgrade beliefs */
// armor_upgrade_price(Food, Water)[source(percept)]
// attack_upgrade_price(Food, Water)[source(percept)]
// speed_upgrade_price(Food, Water)[source(percept)]
// new_ant_price(Food, Water)[source(self)]


+!manage_allocations
	: actions.getGameTime(Time)
	& allocated(_,_,_,_,_)
	<-
	.findall(allocated(RX,RY,N,Resource,T),allocated(RX,RY,N,Resource,T),ListOfAllocations);
	!check_allocations(ListOfAllocations,Time).

+!manage_allocations
	: true
	<-
	true.

/**
 * End of iteration
 */
+!check_allocations([],_).

/**
 * Allocation has not been modified within last 30 game time seconds, remove it
 */
+!check_allocations([allocated(RX,RY,N,Resource,T)|Body],Time) 
	: T + 3000 < Time
	<-
	-allocated(RX,RY,N,Resource,T);
	!check_allocations(Body, Time).
	
/**
 * Allocation was modified recently, no need to remove it
 */
+!check_allocations([allocated(_,_,_,_,_)|Body],Time)
	: true
	<-
	!check_allocations(Body, Time).

/**
 * Waits after perceptual update
 */
+!wait
	: update_rate(Rate)
	<-
	.wait(Rate).
	
/**
 * I or someone else has killed enemy I was attacking or he got away
 * stop attacking
 */
+!check_percepts
	: true
	<-
	update_percepts;
	!decide_action;
	!!manage_allocations;
	!wait;
	!!check_percepts.
	
/**
 * Ready to create new agent
 */
+!decide_action
	: food(Food)
	& water(Water)
	& new_ant_price(PriceFood, PriceWater)
	& Food >= PriceFood
	& Water >= PriceWater
	& army(A)
	& attack(Attack)
	& armor(Armor)
	& A < Attack + 4
	& A < Armor + 4
	<-
	new_agent.
	
/**
 * Ready to upgrade Attack
 */
+!decide_action
	: food(Food)
	& water(Water)
	& attack_upgrade_price(PriceFood, PriceWater)
	& Food >= PriceFood
	& Water >= PriceWater
	& attack(Attack)
	& armor(Armor)
	& army(Army)
	& Army > Attack + 2
	& Attack <= Armor
	<-
	upgrade_attack.
	
/**
 * Ready to upgrade Armor
 */
+!decide_action
	: food(Food)
	& water(Water)
	& armor_upgrade_price(PriceFood, PriceWater)
	& Food >= PriceFood
	& Water >= PriceWater
	& armor(Armor)
	& army(Army)
	& Army > Armor + 2
	& speed(Speed)
	& (Armor/100 + 1 <= Speed
	   | (speed_cap(Cap) & Speed >= Cap))
	<-
	upgrade_armor.
	
/**
 * Ready to upgrade speed
 */
+!decide_action
	: food(Food)
	& water(Water)
	& speed_upgrade_price(PriceFood, PriceWater)
	& Food >= PriceFood
	& Water >= PriceWater
	& speed(Speed)
	& speed_cap(Cap)
	& Speed < Cap
	& army(Army)
	& (Speed - 1) * 100 < Army + 2
	<-
	upgrade_speed.

/**
 * Nothing can be upgraded, wait some time
 */
+!decide_action.
	
+!add_resource(X,Y,N,Resource)
	: resource(X,Y,N,Resource)
	<-
	true.
	
+!add_resource(X,Y,N,water)
	: discover_more(_,food)
	<-
	-discover_more(_,_);
	-no_other_resources;
	-resource(X,Y,_,water);
	+resource(X,Y,N,water);
	actions.teamBroadcast(achieve, offer_resource(X,Y,N,water));
	.wait(300);
	!allocate_resource(X,Y,N,water).
	
+!add_resource(X,Y,N,food)
	: discover_more(_,water)
	<-
	-discover_more(_,_);
	-no_other_resources;
	-resource(X,Y,_,food);
	+resource(X,Y,N,food);
	actions.teamBroadcast(achieve, offer_resource(X,Y,N,food));
	.wait(300);
	!allocate_resource(X,Y,N,food).
	
+!add_resource(X,Y,N,Resource)
	: not resource(X,Y,_,Resource)
	& not discover_more(_,_)
	& not no_other_resource
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource);
	actions.teamBroadcast(achieve, offer_resource(X,Y,N,Resource));
	.wait(300);
	!allocate_resource(X,Y,N,Resource).
	
+!add_resource(X,Y,N,Resource)
	: true
	<-
	-resource(X,Y,_,Resource);
	+resource(X,Y,N,Resource).
	
@premove_resource[atomic]
+!remove_resource(X,Y,Resource)
	: true
	<-
	-resource(X,Y,_,Resource);
	-allocated(X,Y,Resource,_,_).
	
+!get_action[source(Agent)]
	: resource(_,_,_,food)
	& food(Food)
	& water(Water)
	& (Food < Water * 2
	  | Food < 10)
	<-
	actions.getPosition(Agent,X,Y);
	.findall(resource(RX,RY,N,food),resource(RX,RY,N,food),ListOfResources);
	!create_resource_list(X,Y, ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,Count,food)));
	!give_him_resource(Agent,ResourceX,ResourceY,Count,food).
	
+!get_action[source(Agent)]
	: resource(_,_,_,food)
	& water(Water)
	& army(Army)
	& attack(Attack)
	& Army < Attack + 3
	& Water > 1
	<-
	actions.getPosition(Agent,X,Y);
	.findall(resource(RX,RY,N,food),resource(RX,RY,N,food),ListOfResources);
	!create_resource_list(X,Y, ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,Count,food)));
	!give_him_resource(Agent,ResourceX,ResourceY,Count,food).
	
+!get_action[source(Agent)]
	: resource(_,_,_,water)
	& food(Food)
	& water(Water)
	& (Water < Food * 2
	  | Water < 10)
	<-
	actions.getPosition(Agent,X,Y);
	.findall(resource(RX,RY,N,water),resource(RX,RY,N,water),ListOfResources);
	!create_resource_list(X,Y, ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,Count,water)));
	!give_him_resource(Agent,ResourceX,ResourceY,Count,water).
	
+!get_action[source(Agent)]
	: resource(_,_,_,_)
	& no_other_resources
	<-
	actions.getPosition(Agent,X,Y);
	.findall(resource(RX,RY,N,Kind),resource(RX,RY,N,Kind),ListOfResources);
	!create_resource_list(X,Y, ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,Count,Resource)));
	!give_him_resource(Agent,ResourceX,ResourceY,Count,Resource).
	
+!get_action[source(Agent)]
	: resource(_,_,_,_)
	& discover_more(D,Res)
	& D > 50
	<-
	-discover_more(_,_);
	+discover_more(1,Res);
	+no_other_resources;
	actions.getPosition(Agent,X,Y);
	.findall(resource(RX,RY,N,Kind),resource(RX,RY,N,Kind),ListOfResources);
	!create_resource_list(X,Y, ListOfResources,ListOfResourcesWithDistance);
	.min(ListOfResourcesWithDistance,distance(_,resource(ResourceX,ResourceY,Count,Resource)));
	!give_him_resource(Agent,ResourceX,ResourceY,Count,Resource).
	
+!get_action[source(Agent)]
	: resource(_,_,_,Resource)
	& discover_more(D,Resource)
	& actions.getRandomDirection(X,Y)
	<-
	-discover_more(_,_);
	+discover_more(D+1,Resource);
	.send(Agent, achieve, go_to(X,Y)).
	
+!get_action[source(Agent)]
	: resource(_,_,_,Resource)
	& actions.getRandomDirection(X,Y)
	<-
	+discover_more(1,Resource);
	.send(Agent, achieve, go_to(X,Y)).
	
-!get_action[source(Agent)]
	: actions.getRandomDirection(X,Y)
	<-
	.send(Agent, achieve, go_to(X,Y)).

@pgive_resource[atomic]
+!give_him_resource(Agent,X,Y,Count,Resource)
	: allocated(X,Y,Resource,AgentCount,_)
	& AgentCount < Count
	<-
	actions.getGameTime(Time);
	-allocated(X,Y,Resource,AgentCount,_);
	+allocated(X,Y,Resource,AgentCount+1, Time);
	.send(Agent, achieve, go_to_resource(X,Y,Count,Resource)).

@pgive_resource2[atomic]
+!give_him_resource(Agent,X,Y,Count,Resource)
	: not allocated(X,Y,Resource,_,_)
	<-
	actions.getGameTime(Time);
	+allocated(X,Y,Resource,1, Time);
	.send(Agent, achieve, go_to_resource(X,Y,Count,Resource)).
	
@punallocate2[atomic]
+!unallocate(X,Y,Resource)
	: allocated(X,Y,Resource,N,_)
	<-
	actions.getGameTime(Time);
	-allocated(X,Y,Resource,N,_);
	+allocated(X,Y,Resource,N-1, Time).
	
+!unallocate(_,_,_)
	: true
	<-
	true.
	
+!collected(_,_,_)
	: true
	<-
	true.
	
/**
 * End of iteration
 */
+!create_resource_list(_,_,[],[]).

/**
 * Calculates distance from agents position to resource and reconstructs list
 */
@pcreate_res_list[atomic]
+!create_resource_list(X,Y,[resource(ResourceX,ResourceY,N,Resource)|Body],[distance(D,resource(ResourceX,ResourceY,N,Resource))|ResultedBody]) 
	: (allocated(ResourceX,ResourceY,Resource, Count, _)
	& Count < N)
	| not allocated(ResourceX,ResourceY,Resource,_,_)
	<-
	actions.calculateDistance(X,Y,ResourceX,ResourceY,D);
	!create_resource_list(X,Y,Body,ResultedBody).
	
+!require_resource(Distance,X,Y,N,Resource)[source(Agent)]
	: true
	<-
	+resource_request(Distance,X,Y,N,Resource,Agent).


@pallocate_resource[atomic]
+!allocate_resource(X,Y,_,Resource)
	: resource(X,Y,N,Resource)
	& resource_request(_,X,Y,_,Resource,_)
	& not allocated(X,Y,Resource,_,_)
	| (allocated(X,Y,Resource,Count,_)
		& Count < N)
	<-
	.findall(resource_request(D,X,Y,C,Resource,Agent),resource_request(D,X,Y,C,Resource,Agent),ListOfResources);
	.min(ListOfResources, resource_request(_,_,_,Num,Resource,Ag));
	-resource_request(_,X,Y,_,Resource,Ag);
	!give_him_resource(Ag,X,Y,Num,Resource);
	!allocate_resource(X,Y,_,Resource).

+!allocate_resource(X,Y,_,Resource)
	: true
	<-
	.abolish(resource_request(_,X,Y,_,Resource,_)).
	
-!allocate_resource(X,Y,_,Resource)
	: true
	<-
	.abolish(resource_request(_,X,Y,_,Resource,_)).
	