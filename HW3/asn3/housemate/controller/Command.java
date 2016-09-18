package cscie97.asn3.housemate.controller;


/**
 * The Command class is an interface that is responsible for the implementation of the execution
 * of commands as part of a pattern process
 *
 */

public abstract class Command {
	
	/**
	 * This method implements the execution on a concrete class
	 *
	 */
	public void execute() {

	}

	/**
	 * This method implements the storing of the execution on a concrete class
	 *
	 */
	public void store() {
	
	}

	/**
	 * This method implements the loading of a stored execution of a concrete class
	 * which acts as a receipt of the execution having taken place.
	 */
	public void load() {

	}

}
