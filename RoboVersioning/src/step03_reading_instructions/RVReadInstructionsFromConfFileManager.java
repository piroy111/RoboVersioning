package step03_reading_instructions;

import basicmethods.BasicPrintMsg;
import step08_launchme.RVManager;

public class RVReadInstructionsFromConfFileManager {

	public RVReadInstructionsFromConfFileManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pRVInstructionReader = new RVInstructionReader(this);
		pRVReadTimeOfVersionFiles = new RVReadTimeOfVersionFiles(this);
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	private RVInstructionReader pRVInstructionReader;
	private RVReadTimeOfVersionFiles pRVReadTimeOfVersionFiles;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displaySuperTitle(this, "Read conf file with instructions on the list of paths to copy");
		/*
		 * Create the RVVersionFolder and create the directory
		 */
		pRVInstructionReader.run();
		/*
		 * Create the directories if they don't exist + load the version files and their date
		 */
		pRVReadTimeOfVersionFiles.run();
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}
	
	
}
