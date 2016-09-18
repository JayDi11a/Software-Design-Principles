package cscie97.asn3.housemate.model;

import cscie97.asn3.housemate.model.exception.InvalidCommandException;


/**
 * The interface for all behaviors for Status options.
 *
 */
public interface StatusOptionsBehavior {

	public String setValue(String newValue, String oldValue) throws InvalidCommandException;

}
