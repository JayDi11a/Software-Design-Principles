package cscie97.asn4.housemate.model;

import cscie97.asn4.housemate.exception.InvalidCommandException;


/**
 * The interface for all behaviors for Status options.
 *
 */
public interface StatusOptionsBehavior {

	public String setValue(String newValue, String oldValue) throws InvalidCommandException;

}
