package step05_copynewversions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import staticdata.RVStaticConst;
import staticdata.RVStaticConst.action;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_folder_real.RVVersionFolderReal;
import step08_launchme.RVManager;

public class RVEndFilesPublicator {

	public RVEndFilesPublicator(RVManager _sRVManager) {
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
		BasicPrintMsg.displaySuperTitle(this, "Notify the files in the version which are no longer present in the source");
		/*
		 * Loop on the version drives
		 */
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().keySet()) {
			BasicPrintMsg.display(this, "Checking " + lRVVersionDrive.getpNamePlusZZ());
			int lNbNotification = 0;
			Map<Path, RVVersionFolderReal> lMapPathToRVVersionFolderReal = pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().get(lRVVersionDrive);
			/*
			 * Loop on all the real folders version
			 */
			for (RVVersionFolderReal lRVVersionFolderReal : lMapPathToRVVersionFolderReal.values()) {
				/*
				 * If the RVVersionFolder exists, then we skip and we do nothing, because it means that the source file exists 
				 */
				if (lRVVersionDrive.getpMapPathToRVVersionFolder().containsKey(lRVVersionFolderReal.getpPath())) {
					continue;
				}
				/*
				 * If the latest file is already a file absent, then we don't write a new file
				 */
				if (lRVVersionFolderReal.getpRVVersionFileRealLatest() != null && lRVVersionFolderReal.getpRVVersionFileRealLatest().getpIsAbsent()) {
					continue;
				}
				/*
				 * Write a new file absent
				 */
				String lNameSourceFile = lRVVersionFolderReal.getpNameSupposedSourceFile();
				String lNameFile = RVStaticConst.getNAME_ABSENT() + BasicDateInt.getmToday() + "_" + lNameSourceFile;
				if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
					BasicFichiers.writeFile(lRVVersionFolderReal.getpFolder(), lNameFile, null, null);
				}
				/*
				 * Communication
				 */
				lNbNotification++;
				pRVManager.getpRVActionManager().createNewRVAction(lRVVersionFolderReal.getpFolder(), lNameFile, action.NOTIFIED_ABSENT);
				if (RVStaticConst.getIS_FULL_COM()) {
					BasicPrintMsg.display(this, "Notification of source file absent for the real version file= '" + lNameFile + "'");
				}
			}
			BasicPrintMsg.display(this, "Number of notifications of absence of source files= " + lNbNotification);
			BasicPrintMsg.display(this, null);
		}

	}

	
	/**
	 * 
	 */
	public final void run_old() {
		BasicPrintMsg.displaySuperTitle(this, "Notify the files in the version which are no longer present in the source");
		/*
		 * Loop on the version drives
		 */
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionDriveManager().getpTreeMapNameToRVVersionDrive().values()) {
			String lDir = lRVVersionDrive.getpNamePlusZZ();
			BasicPrintMsg.display(this, "Checking " + lRVVersionDrive.getpNamePlusZZ());
			int lNbNotification = 0;
			/*
			 * Get the list of all final folders in the version drive
			 * Each folder is supposed to match an existing source file
			 */
			List<Path> lListPathSubFolders = BasicFichiersNio.getListAllSubFolders(Paths.get(lDir));
			/*
			 * Loop over the list of all final folders and check if there is a RVVersionFolder which exists
			 * RVVersionFolders are created from the RVSourceFile, so if they exist, it means that the file source exists
			 */
			for (Path lPath : lListPathSubFolders) {
				/*
				 * If the RVVersionFolder exists, then we skip and we do nothing
				 */
				if (lRVVersionDrive.getpMapPathToRVVersionFolder().containsKey(lPath)) {
					continue;
				}
				/*
				 * If the last file is already a file notifying the absence of the source file, then we skip and we do nothing
				 */
				List<String> lListFileNames = BasicFichiersNio.getListFilesAndDirectoriesInDirectory(lPath);
				int lDateMaxAbsent = -1;
				int lDateMaxRealFile = -1;
				for (String lFileName : lListFileNames) {
					if (lFileName.startsWith(RVStaticConst.getNAME_ABSENT())) {
						int lDate = BasicString.getInt(lFileName.substring(RVStaticConst.getNAME_ABSENT().length(), RVStaticConst.getNAME_ABSENT().length() + 8));
						if (lDate > lDateMaxAbsent) {
							lDateMaxAbsent = lDate;
						}
					} else if (lFileName.startsWith(RVStaticConst.getNAME_VERSION())) {
						int lDate = BasicString.getInt(lFileName.substring(RVStaticConst.getNAME_VERSION().length(), RVStaticConst.getNAME_VERSION().length() + 8));
						if (lDate > lDateMaxRealFile) {
							lDateMaxRealFile = -1;
						}
					} else {
						BasicPrintMsg.error("I dont recognize the name of the file");
					}
				}
				if (lDateMaxAbsent > lDateMaxRealFile) {
					continue;
				}
				/*
				 * Otherwise we create a file to notify that the source file no longer exists
				 */
				String lNameSourceFile = lPath.getFileName().toString();
				String lNameFile = RVStaticConst.getNAME_ABSENT() + BasicDateInt.getmToday() + "_" + lNameSourceFile;
				if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
					BasicFichiers.writeFile(lPath.toString(), lNameFile, null, null);
				}
				/*
				 * Communication
				 */
				lNbNotification++;
				String lFolder = BasicFichiersNio.getNamePlusPath(lPath);
				pRVManager.getpRVActionManager().createNewRVAction(lFolder, lNameFile, action.NOTIFIED_ABSENT);
			}
			BasicPrintMsg.display(this, "Number of notifications= " + lNbNotification);
			BasicPrintMsg.display(this, null);
		}

	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}

}
