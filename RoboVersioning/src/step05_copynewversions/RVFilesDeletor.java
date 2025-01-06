package step05_copynewversions;

import java.nio.file.Path;
import java.util.Map;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import staticdata.RVStaticConst.action;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_file_real.RVVersionFileReal;
import step01_objects.version_folder_real.RVVersionFolderReal;
import step08_launchme.RVManager;

public class RVFilesDeletor {

	public RVFilesDeletor(RVManager _sRVManager) {
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
		BasicPrintMsg.displaySuperTitle(this, "Delete the files in the version which are no longer present in the source");
		BasicPrintMsg.display(this, "The program will delete a file only if the last version file is a notification file saying that the source file is no longer there ('"
				+ RVStaticConst.getNAME_ABSENT()
				+ "') and if the date of this notification file is before " 
				+ RVStaticConst.getDATE_MAX_TO_ALLOW_DELETION()); 
		/*
		 * Loop on the version drives
		 */
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().keySet()) {
			BasicPrintMsg.display(this, "Checking " + lRVVersionDrive.getpNamePlusZZ());
			int lNbNotificationDelete = 0;
			int lNbNotificationFailed = 0;
			Map<Path, RVVersionFolderReal> lMapPathToRVVersionFolderReal = pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().get(lRVVersionDrive);
			/*
			 * Loop on all the real folders version
			 */
			for (RVVersionFolderReal lRVVersionFolderReal : lMapPathToRVVersionFolderReal.values()) {
				if (lRVVersionFolderReal.getpRVVersionFileRealLatest() != null && lRVVersionFolderReal.getpRVVersionFileRealLatest().getpIsAbsent()) {
					int lDateDelete = lRVVersionFolderReal.getpRVVersionFileRealLatest().getpDate();
					if (lDateDelete <= RVStaticConst.getDATE_MAX_TO_ALLOW_DELETION()) {
						/*
						 * Loop on all the files real inside the folder
						 */
						boolean lIsSuccessForAllFiles = true;
						for (RVVersionFileReal lRVVersionFileReal : lRVVersionFolderReal.getpTreeMapDateToRVVersionFileAll().values()) {
							/*
							 * Delete file
							 */
							boolean lIsSuccessForFile = false;
							if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
								lIsSuccessForFile = BasicFichiersNio.deleteFileIfExists(lRVVersionFileReal.getpPath().toString());
								lIsSuccessForFile = true;
							}
							/*
							 * Communication
							 */
							if (lIsSuccessForFile) {
								pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolderReal.getpFolder(), lRVVersionFileReal.getpNameFile(), action.DELETED_FROM_VERSION);
								lNbNotificationDelete++;
							} else {
								pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolderReal.getpFolder(), lRVVersionFileReal.getpNameFile(), action.FAIL_TO_DELETE);
								lNbNotificationFailed++;
								lIsSuccessForAllFiles = false;
							}
						}
						/*
						 * Delete the folder
						 */
						if (lIsSuccessForAllFiles) {
							boolean lIsSuccessForFolder = false;
							if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
								lIsSuccessForFolder = BasicFichiersNio.deleteFileIfExists(lRVVersionFolderReal.getpPath().toString());
							}
							if (lIsSuccessForFolder) {
								pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolderReal.getpFolder(), "", action.DELETED_FROM_VERSION);
								lNbNotificationDelete++;
							} else {
								pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolderReal.getpFolder(), "", action.FAIL_TO_DELETE);
								lNbNotificationFailed++;
							}
						}
					}
				}
			}
			/*
			 * Communication
			 */
			BasicPrintMsg.display(this, "Nb of files actually deleted= " + lNbNotificationDelete);
			BasicPrintMsg.display(this, "Nb of files failed to delete= " + lNbNotificationFailed);
		}
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}

}
