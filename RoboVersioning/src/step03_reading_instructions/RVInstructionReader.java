package step03_reading_instructions;

import java.nio.file.Path;
import java.util.List;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import step01_objects.source_drives.RVSourceDrive;
import step01_objects.source_files.RVSourceFile;
import step08_launchme.RVManager;

class RVInstructionReader {

	
	protected RVInstructionReader(RVReadInstructionsFromConfFileManager _sRVReadInstructionsFromConfFileManager) {
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
		for (RVSourceDrive lRVSourceDrive : lRVManager.getpRVSourceDriveManager().getpListRVSourceDriveToDo()) {
			for (List<String> lLineStr : lRVSourceDrive.getpReadFileInstruction().getmContentList()) {
				/*
				 * Load instruction
				 */
				String lInstruction = lLineStr.get(0);
				if (!lInstruction.contains(":")) {
					lInstruction = lRVSourceDrive.getpName() + lInstruction;
				}
				/*
				 * Communication
				 */
				BasicPrintMsg.displayTitle(this, "Create object files source for " + lInstruction);
				/*
				 * Create all the objects files from the instruction
				 */
				List<Path> lListPathToDo = BasicFichiersNio.getListFilesAndSubFilesFromCode(lInstruction);
				for (Path lPath : lListPathToDo) {
					RVSourceFile lRVSourceFile = lRVManager.getpRVSourceFileManager().getpOrCreateRVSourceFile(lPath);
					lRVSourceFile.initiate();
					if (RVStaticConst.getIS_FULL_COM()) {
						BasicPrintMsg.display(this, "Create " + RVSourceFile.class.getSimpleName() + " '" + lRVSourceFile + "'");
					}
				}
				/*
				 * Communication
				 */
				BasicPrintMsg.display(this, "--> Created " + lListPathToDo.size() + " " + RVSourceFile.class.getSimpleName());
			}
		}
	}
	
}
