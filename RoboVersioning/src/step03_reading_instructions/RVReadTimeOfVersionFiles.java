package step03_reading_instructions;

import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import step01_objects.source_files.RVSourceFile;
import step01_objects.source_files.RVSourceFileManager;
import step01_objects.version_file.RVVersionFile;
import step01_objects.version_folder.RVVersionFolder;
import step08_launchme.RVManager;
import tictoc.BasicTicToc;

class RVReadTimeOfVersionFiles {

	protected RVReadTimeOfVersionFiles(RVReadInstructionsFromConfFileManager _sRVReadInstructionsFromConfFileManager) {
		pRVReadInstructionsFromConfFileManager = _sRVReadInstructionsFromConfFileManager;
	}

	/*
	 * Data
	 */
	private RVReadInstructionsFromConfFileManager pRVReadInstructionsFromConfFileManager;
	
	/**
	 * 
	 */
	public final void run() {
		RVManager lRVManager = pRVReadInstructionsFromConfFileManager.getpRVManager();
		/*
		 * 
		 */
		BasicPrintMsg.displayTitle(this, "Check and create version folders in real");
		BasicTicToc.Start(this, "initiateOrCreateDirectory");
		int lNbCreation = 0;
		RVSourceFileManager lRVSourceFileManager = lRVManager.getpRVSourceFileManager();
		for (RVSourceFile lRVSourceFile : lRVSourceFileManager.getpMapPathToRVSourceFile().values()) {
			for (RVVersionFolder lRVVersionFolder : lRVSourceFile.getpMapRVVersionDriveToRVVersionFolder().values()) {
				lNbCreation += lRVVersionFolder.initiateOrCreateDirectory();
			}
		}
		BasicPrintMsg.display(this, "--> Created " + lNbCreation + " new folders in the version drives");
		BasicTicToc.Stop(this, "initiateOrCreateDirectory");
		/*
		 * 
		 */
		BasicPrintMsg.displayTitle(this, "Load all timestamps of existing versions");
		BasicTicToc.Start(this, "retrieveAllVersionFiles");
		for (RVSourceFile lRVSourceFile : lRVSourceFileManager.getpMapPathToRVSourceFile().values()) {
			for (RVVersionFolder lRVVersionFolder : lRVSourceFile.getpMapRVVersionDriveToRVVersionFolder().values()) {
				lRVVersionFolder.retrieveAllVersionFiles();
			}
		}
		BasicTicToc.Stop(this, "retrieveAllVersionFiles");
		/*
		 * Display
		 */
		BasicTicToc.Start(this, "Display");
		int lNBRetrieve = 0;
		for (RVSourceFile lRVSourceFile : lRVSourceFileManager.getpMapPathToRVSourceFile().values()) {
			if (RVStaticConst.getIS_FULL_COM()) {
				BasicPrintMsg.display(this, RVSourceFile.class.getSimpleName() + " '" + lRVSourceFile.getpPath().toString() + "'");
			}
			for (RVVersionFolder lRVVersionFolder : lRVSourceFile.getpMapRVVersionDriveToRVVersionFolder().values()) {
				for (RVVersionFile lRVVersionFile : lRVVersionFolder.getpTreeMapTimeStampToRVVersionFile().values()) {
					if (RVStaticConst.getIS_FULL_COM()) {
						BasicPrintMsg.display(this, "     retrieved " + RVVersionFile.class.getSimpleName() + " '" + lRVVersionFile + "'");
					}
					lNBRetrieve++;
				}
			}
		}
		BasicPrintMsg.display(this, "--> Treated " + lRVSourceFileManager.getpMapPathToRVSourceFile().size() + " " + RVSourceFile.class.getSimpleName());
		BasicPrintMsg.display(this, "--> Retrieved " + lNBRetrieve + " " + RVVersionFile.class.getSimpleName());
		BasicTicToc.Stop(this, "Display");
	}

	
}
