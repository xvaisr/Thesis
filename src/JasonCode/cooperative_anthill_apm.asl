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

/**
 * Waits after perceptual update
 */
+!wait
	: update_rate(Rate)
	<-
	.wait(Rate);
	actions.addReasoningAction.
	
/**
 * I or someone else has killed enemy I was attacking or he got away
 * stop attacking
 */
/**
 * I or someone else has killed enemy I was attacking or he got away
 * stop attacking
 */
+!check_percepts
	: true
	<-
	update_percepts;
	!decide_action;
	!wait;
	!!check_percepts;
	actions.addReasoningAction.
	
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
	new_agent;
	actions.addReasoningAction.
	
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
	upgrade_attack;
	actions.addReasoningAction.
	
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
	upgrade_armor;
	actions.addReasoningAction.
	
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
	upgrade_speed;
	actions.addReasoningAction.

/**
 * Nothing is upgradable, wait some time
 */
+!decide_action.