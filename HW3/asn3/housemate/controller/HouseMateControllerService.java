/**
 * HouseMateController Service is a public facing API for the controller service
 * Its a singleton.
 *
 *
 */


public class HouseMateControllerService {

	private static final HouseMateControllerService instance = new HouseMateControllerService();

	
	/**
	 * This constructor is private and is a singleton.
	 *
	 */
	 private HouseMateControllerService() {}

	/**
	 * The implementation of the singleton instanace
	 * @return the singleton instance of HouseMateControllerService.
	 *
 	 */
	public static HouseMateControllerService getInstance() {
		return instance;
	}

	/**
	 * This is a notify method that the HouseMateModelService uses
	 * to sent to HouseMateController 
	 *
	 */
	public void notify(String authToken, String identifier, String statusName, String statusValue) {

	}

	/**
	 * This method implements the creation of the commands once the stimulus has been
	 * tokenized into the notifier and the commands have been correctly chosen from our
	 * command factory
	 */
	public void createCommand() {

	}

	/**
	 * This method implements execution of the commands once they have been created 
 	 * to be performed on the HouseMateModelService that changes the Model Services state
	 */
	public void executeCommand() {
	}

}
