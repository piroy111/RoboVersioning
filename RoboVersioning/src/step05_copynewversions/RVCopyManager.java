package step05_copynewversions;

import java.nio.file.Path;
import java.nio.file.Paths;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import staticdata.RVStaticConst.action;
import step01_objects.source_files.RVSourceFile;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_folder.RVVersionFolder;
import step08_launchme.RVManager;
import tictoc.BasicTicToc;

public class RVCopyManager {

	public RVCopyManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displaySuperTitle(this, "Copy new versions");
		/*
		 * Loop on the version drives
		 */
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionDriveManager().getpTreeMapNameToRVVersionDrive().values()) {
			int lNbCopies = 0;
			int lNbDeleted = 0;
			int lNbFailedToDelete = 0;
			BasicPrintMsg.displayTitle(this, "Treating " + lRVVersionDrive.getpName());
			BasicTicToc.Start(this, lRVVersionDrive.getpName());
			/*
			 * Loop on the Source files, get all the version folders, check if the latest version is out of date, and copy the file if it is the case
			 */
			for (RVSourceFile lRVSourceFile : pRVManager.getpRVSourceFileManager().getpMapPathToRVSourceFile().values()) {
				RVVersionFolder lRVVersionFolder = lRVSourceFile.getpMapRVVersionDriveToRVVersionFolder().get(lRVVersionDrive);
				/*
				 * If the file source has a name not valid (including '~'), then we ignore it, so we will not create a version
				 */
				String lOldNameOfFile = lRVSourceFile.getpPath().getFileName().toString();
				if (lOldNameOfFile.contains(RVStaticConst.getCHARACTER_FORBIDDEN_COPY())) {
					pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolder, lOldNameOfFile, action.IGNORED);
					continue;
				}
				/*
				 * If the last real version file present is a file notifying the absence of the file, then we delete the notification file only if its date is today
				 */
				if (lRVVersionFolder.getpRVVersionFileLast() != null
						&& lRVVersionFolder.getpRVVersionFileLast().getpIsDeleted() 
						&& lRVVersionFolder.getpRVVersionFileLast().getpNameFile().startsWith(RVStaticConst.getNAME_ABSENT())) {
					String lNameOfFileToDelete = lRVVersionFolder.getpRVVersionFileLast().getpNameFile();
					String lDirPlusNameFileToDelete = lRVVersionFolder.getpDir() + lNameOfFileToDelete;						
					boolean lIsDeleted = false;
					if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
						lIsDeleted = BasicFichiersNio.deleteFileIfExists(lDirPlusNameFileToDelete);
					}
					/*
					 * Communication
					 */
					if (lIsDeleted) {
						if (RVStaticConst.getIS_FULL_COM()) {
							BasicPrintMsg.display(this, "Deleted with success '" + lNameOfFileToDelete + "'");
						}
						lNbDeleted++;
					} else {
						if (RVStaticConst.getIS_FULL_COM()) {
							BasicPrintMsg.display(this, "Failed to delete '" + lNameOfFileToDelete + "'");
						}
						lNbFailedToDelete++;
					}
					pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolder, lNameOfFileToDelete, action.DELETED_FROM_VERSION);
				}
				/*
				 * We create a real version file (copy of the source file) if either the real version file does not exists or it is out of date  
				 */
				if (lRVVersionFolder != null 
						&& (lRVVersionFolder.getpRVVersionFileLastNotDeleted() == null
						|| lRVVersionFolder.getpRVVersionFileLastNotDeleted().getpTimeStamp() < lRVSourceFile.getpTimeStamp())) {
					/*
					 * Copy the file
					 */
					String lNewNameOfFile = RVStaticConst.getNAME_VERSION() + BasicDateInt.getmToday() + "_" + lRVSourceFile.getpPath().getFileName().toString();
					Path lPathTarget = Paths.get(lRVVersionFolder.getpDir() + lNewNameOfFile);
					if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
						BasicFichiersNio.copyFile(lRVSourceFile.getpPath(), lPathTarget, false);
					}
					/*
					 * Communication
					 */
					lNbCopies++;
					if (RVStaticConst.getIS_FULL_COM()) {
						BasicPrintMsg.display(this, "Copy new version into '" + lNewNameOfFile + "'");
					}
					pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolder, lNewNameOfFile, action.COPIED_TO_VERSION);
				}
			}
			BasicPrintMsg.display(this, "--> Copied " + lNbCopies + " new versions");
			BasicPrintMsg.display(this, "--> Nb deleted (in case of a end file has the same date as a verion file): " + lNbDeleted);
			BasicPrintMsg.display(this, "--> Nb failed to delete= " + lNbFailedToDelete);
			BasicTicToc.Stop(this, lRVVersionDrive.getpName());
		}
	}
	
}
