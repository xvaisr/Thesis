/**
 * Waits some time after each perceptual update
 */
+!wait
	: update_rate(Rate)
	<-
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
	