package cscie97.asn3.housemate.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import cscie97.asn3.housemate.model.exception.InvalidCommandException;

/**
 * EnumOptions is a behavior for the StatusOptions. It allows options to be 
 * configured to allow only a certain set of values.
 *
 */
public class EnumOptions implements StatusOptionsBehavior {
	
	ArrayList<String> values = null;
	
	/**
	 * Constructor. Takes a map of params, and sets the values.
	 * @param params Must contain a key values with pipe delimited values.
	 * @throws InvalidCommandException when values is not present in the params.
	 */
	public EnumOptions(Map<String, String> params) throws InvalidCommandException {
		if(params.get("values") == null){
			throw new InvalidCommandException(0, "Set options must include 'values'.", "");  
		}
		values = new ArrayList<String>(Arrays.asList(params.get("values").split("|")));
	}

	@Override
	public String setValue(String newValue, String oldValue) throws InvalidCommandException {
		if(!values.contains(newValue)){
			throw new InvalidCommandException(0, "Invalid option set.", newValue);  
		}
		return newValue;
	}

}
