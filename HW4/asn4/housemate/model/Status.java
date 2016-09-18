package cscie97.asn4.housemate.model;

import java.util.Map;

import cscie97.asn4.knowledge.engine.KnowledgeGraph;
import cscie97.asn4.knowledge.engine.Node;
import cscie97.asn4.housemate.exception.InvalidCommandException;

/**
 * Status follows the Strategy pattern for the option behaviors.
 * This allows for Statuses to have different validation for their
 * values. The behaviors can be configured via the set options command.
 *
 */
public class Status extends HouseMateNode {

	StatusOptionsBehavior optionsBehavior = null;
	private String value;
	
	/**
	 * Constructs the status.
	 * @param identifierString the identifier of the status
	 * @param identifier the friendly name of the status.
	 * @param value the original value of the status.
	 */
	public Status(String identifierString, String identifier, String value) {
		super(identifierString, identifier);
		this.value = value;
	}

	/**
	 * Gets the value of the status.
	 * @return the value of the status.
	 */
	public String getValue() {
		return value;
	}

	
	/**
	 * Sets the value of the status. If a behavior is set, it is used to set the value.
	 * @param newValue The new value to set
	 * @throws InvalidCommandException if the value in invalid.
	 */
	public void setValue(String newValue) throws InvalidCommandException {
		String valueToSave = null;
		if(this.optionsBehavior == null){
			valueToSave = newValue;
		}else{
			valueToSave = this.optionsBehavior.setValue(newValue, value); // The current value is needed for incrementing and decrementing.
		}
		KnowledgeGraph.getInstance().deleteTriple(this, "has_value", new Node(value));
		value = valueToSave;
		KnowledgeGraph.getInstance().importTriple(this, "has_value", new Node(value));
	}

	/**
	 * Sets which option behavior to use for this Status.
	 * @param params the params to be used with the behavior.
	 * @throws InvalidCommandException if the behavior can not be properly constructed.
	 */
	public void setOptions(Map<String, String> params) throws InvalidCommandException {
		if(params.get("type").equals("range")){
			optionsBehavior = new RangeOptions(params);
		}
		if(params.get("type").equals("enum")){
			optionsBehavior = new EnumOptions(params);
		}
		
	}

}
