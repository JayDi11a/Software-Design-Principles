package cscie97.asn3.housemate.model;

import java.util.Map;
import cscie97.asn3.housemate.model.exception.InvalidCommandException;

/**
 * RangeOptions is a behavior for the StatusOptions. It allows options to be 
 * configured to allow for a range of values. 
 * For example, if a min is set to 0, and max is set to 100, any time a value is set it, will be constrained to that range.
 * Example command: set options house1:kitchen1:tv1:channel type range min 0 max 100 incrementor UP decrementor DOWN minlabel START maxlabel END 
 *
 */
public class RangeOptions implements StatusOptionsBehavior {

	private int minValue;
	private int maxValue;
	private String incrementor = null;
	private String decrementor = null;
	private String minlabel = null;
	private String maxlabel = null;
	
	
	/** 
	 * Constructor.
	 * @param params a map with keys min and max. Optional keys are incrementor, decrementor, minlabel, maxlabel
	 * @throws InvalidCommandException if there is no min or max.
	 */
	public RangeOptions(Map<String, String> params) throws InvalidCommandException {
		if(params.get("min") == null || params.get("max") == null){
			throw new InvalidCommandException(0, "Set options for ranges must include min and max.", "");  
		}
		minValue = Integer.parseInt(params.get("min"));
		maxValue = Integer.parseInt(params.get("max"));
		incrementor = params.get("incrementor");
		decrementor = params.get("decrementor");
		minlabel = params.get("minlabel");
		maxlabel = params.get("maxlabel");
	}


	/** 
	 * Sets a value, within the constraints set in the option. Keywords can be used as well. For example, if the incrementor is set to UP, and UP is the value, the value is increased.
	 * @param newValue the new value to set.
	 * @param oldValue the previous value.
	 * @return a string of the actual set value.
	 */
	public String setValue(String newValue, String oldValue) {
		int iValue = Integer.parseInt(oldValue);
		if(newValue.equals(incrementor)){
			iValue = iValue + 1;
		}else if(newValue.equals(decrementor)){
			iValue = iValue - 1;
		}else if(newValue.equals(minlabel)){
			iValue = minValue;
		}else if(newValue.equals(maxlabel)){
			iValue = maxValue;
		}else{
			iValue = Integer.parseInt(newValue);
		}
			
		iValue = Math.min(iValue, maxValue);
		iValue = Math.max(iValue, minValue);
		
		return Integer.toString(iValue);
	}
	
	
}
