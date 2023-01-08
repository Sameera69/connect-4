/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adversarial;



/**
 * Variant of the search interface. Since players can only control the next
 * move, method <code>makeDecision</code> returns only one action, not a
 * sequence of actions.
 * 
 * @author Ruediger Lunde
 */
public interface AdversarialSearch<STATE, ACTION> {

	/** Returns the action which appears to be the best at the given state. */
	ACTION makeDecision(STATE state , int limit);
	
}