package cscie97.asn3.housemate.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cscie97.asn3.housemate.model.exception.HouseMateModelException;
import cscie97.asn3.housemate.model.exception.InvalidCommandException;


/**
 * CommandImporter is used to execute string commands against the HouseModelService.
 * A file can be supplied and each line of the file will be executed.
 * Each command is split into tokens, and then the appropriate call is executed.
 */
public class CommandImporter {

	// Singleton houseMateModelService
	private static final HouseMateModelService houseMateModelService = HouseMateModelService.getInstance();
	
	/**
	 * Executes a string command against the HouseMateModelService API.
	 * @param command The command to execute.
	 * @throws HouseMateModelException on error of processing command.
	 */
	public void executeCommand(String command) throws HouseMateModelException {
		String[] tokens = getTokensFromLine(command);
    	
        /** Tokens is null on empty lines. Skip and continue processing. Also skip commented out lines. */
        if(tokens == null || tokens.length == 0 || tokens[0].charAt(0) == '#'){
        	return;
        }
        
        try{
        	System.out.println(command);
        	parseCommandTokens(tokens);
        }catch(InvalidCommandException e){
        	 /** Since this is a single command, set the line number to 0. It will be updated later if needed. */
        	throw new InvalidCommandException(0, e.getDescription() , command);
        }
	}

	/**
	 * Attempts to read each line of the provided filename and execute the command.
	 * Empty lines and commented lines are skipped. 
	 * @param filename the filename containing the line-separated triples to import.
	 * @throws HouseMateModelException on error processing a file or line
	 */
	public void importFile(String filename) throws HouseMateModelException
	{
		InputStream fis = null;
		BufferedReader br = null;
		String line = null;
		int lineNumber = 0;
		try {
			
		    fis = new FileInputStream(filename);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    br = new BufferedReader(isr);
		    
		    while ((line = br.readLine()) != null) {
		    	lineNumber++;
		    	
		    	try{
		    		executeCommand(line);
		    	}catch(HouseMateModelException e){
					 /** Update the line number with the lineNmuber from the file. */
		    		HouseMateModelException ex = new HouseMateModelException(lineNumber, e.getDescription() , line);
					System.out.println("ERROR:" + ex.toString());
					//throw ex;
				}
		    }
		    
		}catch(IOException e){
			throw new InvalidCommandException(0, e.getMessage() , filename);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				throw new InvalidCommandException(0, ex.getMessage() , filename);
			}
		}

	}
	
	/**
	 * Converts a line into tokens.
	 * @param line the line contents to be scrubbed, validated, and tokenized.
	 * @return an array of strings that have been trimmed and validated. if null, then nothing needs to be processed.
	 */
	private String[] getTokensFromLine(String line){
		line = line.trim();
		List<String> list = new ArrayList<String>();
		// Reference: http://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
		while (m.find()){    
			list.add(m.group(1).replace("\"", "")); // remove the quotes from the string.
		}
		
		String[] stringList = new String[list.size()];
		stringList = list.toArray(stringList);
		
		return stringList;
    }
	
	
	/**
	 * Calls the appropriate method based off the first token.
	 * @param tokens The tokens of the command.
	 * @throws HouseMateModelException when the first token is not a valid command.
	 */
	private void parseCommandTokens(String[] tokens) throws HouseMateModelException {
		switch(tokens[0].toLowerCase()){
			case "define": parseDefineCommand(tokens); return;
			case "add":    parseAddCommand(tokens);    return;
			case "set":    parseSetCommand(tokens);    return;
			case "show":   parseShowCommand(tokens);   return;
			case "query":  parseQueryCommand(tokens);   return;
		}
		throw new InvalidCommandException(0, "Command must start with define|add|set|show.", tokens[0]);
	}

	/**
	 * Processes the query command, which is used to do raw queries against the knowledge graph.
	 * @param tokens The tokens of the command. 
	 * @throws HouseMateModelException when the format is invalid or the query in invalid.
	 */
	private void parseQueryCommand(String[] tokens)throws HouseMateModelException {
		if(tokens.length == 4){
			houseMateModelService.query(null, tokens[1] + " " + tokens[2] + " " + tokens[3]);
			return;
		}
		throw new InvalidCommandException(0, "Invalid format for \"query\" command", null);
	}

	/**
	 * Processes the show command. The API expects just a single identifier,
	 * so some of the identifiers are concatenated to be the globally unique identifier.
	 * @param tokens The tokens of the command. 
	 * @throws HouseMateModelException when the format is invalid or the query in invalid.
	 */
	private void parseShowCommand(String[] tokens) throws HouseMateModelException {
		/** Show the entire config. */
		if(tokens.length == 2 && tokens[1].equals("configuration")){
			houseMateModelService.showConfiguration(null, null);
			return;
		}
		
		/** Show the config for an appliance. */
		if(tokens.length == 3){
			houseMateModelService.showConfiguration(null, tokens[2]);
			return;
		}
		
		/** Show the config for a house or room. */
		if(tokens.length == 4 && tokens[1].equals("configuration")){
			houseMateModelService.showConfiguration(null, tokens[3]);
			return;
		}
		
		/** Show the config for a status. */
		if(tokens.length == 5 ){
			houseMateModelService.showConfiguration(null, tokens[2] + ":" + tokens[4]);
			return;
		}
		
		throw new InvalidCommandException(0, "Invalid format for \"show\" command", null);
	}

	/**
	 * Processes the set command. 
	 * @param tokens The tokens of the command. 
	 * @throws HouseMateModelException when the format is invalid or the query in invalid.
	 */
	private void parseSetCommand(String[] tokens) throws HouseMateModelException {
		/** If the second token is options, then set the options. This allows to configure options for appliances. */
		if(tokens.length > 2 && tokens[1].toLowerCase().equals("options")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if(	!params.containsKey("type") ){
				throw new InvalidCommandException(0, "status and value must be set.", null);
			}
			houseMateModelService.setStatusOptions(null,  params.get("options"), params);
			return;
		}
		else if(tokens.length == 7){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if(!params.containsKey("appliance") && !params.containsKey("sensor") && !params.containsKey("occupant")){
				throw new InvalidCommandException(0, "only appliance, sensor, or occupant can be set.", null);
			}
			/** Every set command needs at least status and value. */
			if(	!params.containsKey("status") || 
				!params.containsKey("value")){
				throw new InvalidCommandException(0, "status and value must be set.", null);
			}

			/** Find the correct key for the identifier. */
			String identifier = null;
			if(params.containsKey("appliance")){
				identifier = params.get("appliance");
			}
			if(params.containsKey("sensor")){
				identifier = params.get("sensor");
			}
			if(params.containsKey("occupant")){
				identifier = params.get("occupant");
			}

			houseMateModelService.setStatus(null, identifier, params.get("status"), params.get("value"));
			return;
		}
		throw new InvalidCommandException(0, "invalid number of arguments for \"set\" command", null);
	}

	/**
	 * Processes the add command. Used to add occupants to a house.
	 * @param tokens The tokens of the command. 
	 * @throws HouseMateModelException when the format is invalid or the query in invalid.
	 */
	private void parseAddCommand(String[] tokens) throws HouseMateModelException {
		if(tokens.length == 5 && tokens[1].equals("occupant")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("occupant") || 
				!params.containsKey("to_house")){
				throw new InvalidCommandException(0, "add must include occupant and to_house", null);
			}
			/** No relation set, default to resident. */
			houseMateModelService.addOccupant(null, params.get("occupant"), params.get("to_house"), "resident");
		}
		
		if(tokens.length == 7 && tokens[1].equals("occupant")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("occupant") || 
				!params.containsKey("to_house") ||
				!params.containsKey("relation")){
				throw new InvalidCommandException(0, "add must include occupant, to_house and relation", null);
			}
			houseMateModelService.addOccupant(null, params.get("occupant"), params.get("to_house"), params.get("relation").toLowerCase());
		}
		
	}

	/**
	 * Processes the define command. Used to create houses, rooms, appliances, sensors, and occupants.
	 * @param tokens The tokens of the command. 
	 * @throws HouseMateModelException when the format is invalid or the query in invalid.
	 */
	private void parseDefineCommand(String[] tokens) throws HouseMateModelException {
		/** Define a house */
		if(tokens.length == 3 && tokens[1].equals("house")){
			houseMateModelService.createHouse(null, tokens[2]);
			return;
		}
		
		/** Define a room */
		if(tokens.length == 9 && tokens[1].equals("room")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("room") || 
				!params.containsKey("floor") || 
				!params.containsKey("type") || 
				!params.containsKey("house")){
				throw new InvalidCommandException(0, "define room must include room, floor, type, house", null); 
			}
			houseMateModelService.createRoom(null, params.get("room"), Integer.parseInt(params.get("floor")), params.get("type"),  params.get("house"));
			return;
		}
		
		/** Define a sensor */
		if(tokens.length == 7 && tokens[1].equals("sensor")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("sensor") || 
				!params.containsKey("type") || 
				!params.containsKey("room")){
				throw new InvalidCommandException(0, "define sensor must include room, sensor, and type", null);
			}
			houseMateModelService.createSensor(null, params.get("sensor"), params.get("type"),  params.get("room"));
			return;
		}
		
		/** Define an appliance */
		if(tokens.length == 7 && tokens[1].equals("appliance")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("appliance") || 
				!params.containsKey("type") || 
				!params.containsKey("room")){
				throw new InvalidCommandException(0, "define appliance must include room, sensor, and type", null);
			}
			houseMateModelService.createAppliance(null, params.get("appliance"), params.get("type"),  params.get("room"));
			return;
		}
		
		/** Define an occupant */
		if(tokens.length == 5 && tokens[1].equals("occupant")){
			Map<String, String> params = getKeyValueMap(tokens, 1);
			if( !params.containsKey("occupant") || 
				!params.containsKey("type")){
				throw new InvalidCommandException(0, "define appliance must include room, sensor, and type", null);
			}
			houseMateModelService.createOccupant(null, params.get("occupant"), params.get("type"));
			return;
		}

	}
	
	/**
	 * Returns a key value map from an array of tokens, starting at a given index.
	 * @param tokens The tokens to convert into the map.
	 * @param startIndex The stating point where the key value pairs begin.
	 * @return
	 */
	private Map<String, String> getKeyValueMap(String[] tokens, int startIndex){
		Map<String, String> params = new HashMap<String, String>();
		for(int i = startIndex; i < tokens.length - 1; i++){ // Subtract 1 to end on an earlier pair
			params.put(tokens[i], tokens[i + 1]);
			i++;
		}
		return params;
	}
	
}
