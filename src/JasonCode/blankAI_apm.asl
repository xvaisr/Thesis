!check_percepts.

/**
 * Waits some time after each perceptual update
 */
+!wait
	: update_rate(Rate)
	<-
	.wait(Rate);
	actions.addReasoningAction.

/**
 * If there is no update_rate belief due to belief error, this gets new rate
 */
+!wait
	: true
	<-
	actions.getUpdateRate(Rate);
	+update_rate(Rate);
	.wait(Rate);
	actions.addReasoningAction.
	
/**
 * Cyclically updates percepts
 */
+!check_percepts
	: true
	<-
	update_percepts;
	!wait;
	!!check_percepts;
	actions.addReasoningAction.
	
+enemy(X, Y, Enemy)[source(percept)]
	: true
	<-
	.print("Enemy: ", Enemy, " at  x: ", X, " y: ", Y).
	
+friend(X, Y, Friend)[source(percept)]
	: true
	<-
	.print("Friend: ", Friend, " at  x: ", X, " y: ", Y).
	
+resource(X,Y,N,Resource)[source(percept)]
	: true
	<-
	.print("Resource field: ", Resource, " at  x: ", X, " y: ", Y, " with ", N, " resources").
	
	