package step05_copynewversions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import staticdata.RVStaticDir;
import staticdata.RVStaticNameFile;
import step01_objects.source_drives.RVSourceDrive;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_file_real.RVVersionFileReal;
import step01_objects.version_folder_real.RVVersionFolderReal;
import step08_launchme.Launch_Robo_Versioning;
import step08_launchme.RVManager;

public class RVReverseEngineer {

	public RVReverseEngineer(RVManager _sRVManager) {
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
		BasicPrintMsg.displaySuperTitle(this, "Copy all the files in the drive where the .exe of the program is present");
		BasicPrintMsg.display(this, "Look for the place where to copy back the real version files");
		/*
		 * Check the drive to reverse-engineer is here
		 */
		String lFolderVersion = RVStaticConst.getDRIVE_TO_REVERSE();
		if (lFolderVersion == null) {
			BasicPrintMsg.error("The drive is missing in the conf file. You should indicate me which drive you want to reverse engineer"
					+ "\nConf file name= '" + RVStaticNameFile.getCONF_REVERSE() + "'");
		}
		if (!lFolderVersion.endsWith(":/")) {
			if (!lFolderVersion.endsWith(":")) {
				lFolderVersion += ":/";
			} else {
				lFolderVersion += "/";
			}
		} else {
			lFolderVersion += ":/";
		}
		lFolderVersion += RVStaticDir.getTARGET();
		if (!BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(lFolderVersion))) {
			BasicPrintMsg.error("The folder version given in the conf file is wrong and does not exist"
					+ "/nDrive given by conf file= '" + lFolderVersion + "'");
		} else {
			BasicPrintMsg.display(this, "I will copy back in the folder version all the files which don't have a notification of absence");
			BasicPrintMsg.display(this, "Folder version which will be reverse engineered= '" + lFolderVersion + "'");
		}
		/*
		 * Find the drive in which there is an executable and in which we will deposit the reverse-engineered version files
		 */
		String lFolderOutput = null;
		String lNameFileJar = Launch_Robo_Versioning.class.getSimpleName() + ".jar";
		for (RVSourceDrive lRVSourceDrive : pRVManager.getpRVSourceDriveManager().getpListRVSourceDriveToDo()) {
			String lNameDirPlusFile = lRVSourceDrive.getpNamePlusZZ() + lNameFileJar;
			if (BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(lNameDirPlusFile))) {
				lFolderOutput = lRVSourceDrive.getpNamePlusZZReverseOutput();
				break;
			}
		}
		if (lFolderOutput == null) {
			BasicPrintMsg.error("I cannot find the folder where the jar is located; expected jar= '" + lNameFileJar);
		} else {
			BasicPrintMsg.display(this, "File jar found --> I will copy the reverse engineered in the same root --> Folder output= '" + lFolderOutput + "'");
		}
		BasicPrintMsg.display(this, null);
		/*
		 * Copy the real version files which have a source file
		 * Loop on all the RVVersionFolderReal. a RVVersionFolderReal contains all the versions files and the notifications file for a same source file
		 */
		int lNbFilesBackCopied = 0;
		int lNbFilesFailedToCopy = 0;
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().keySet()) {
			if (lRVVersionDrive.getpNamePlusZZ().equals(lFolderVersion)) {
				Map<Path, RVVersionFolderReal> lMapPathToRVVersionFolderReal = pRVManager.getpRVVersionFolderRealManager().getpMapRVVersionDriveToMapPathToRVVersionFolderReal().get(lRVVersionDrive);
				for (RVVersionFolderReal lRVVersionFolderReal : lMapPathToRVVersionFolderReal.values()) {
					RVVersionFileReal lRVVersionFileRealLatest = lRVVersionFolderReal.getpRVVersionFileRealLatest();
					if (lRVVersionFileRealLatest != null && lRVVersionFileRealLatest.getpIsPresentAndValid()) {
						/*
						 * Find the name of the source folder
						 */
						String lNameSupposedSourceFile = lRVVersionFolderReal.getpNameSupposedSourceFile();
						int lIdxDrop = lRVVersionFolderReal.getpFolder().length() - lNameSupposedSourceFile.length();
						if (lRVVersionFolderReal.getpFolder().endsWith("/")) {
							lIdxDrop--;
						}
						String lNameSupposedSourceFolder = lRVVersionFolderReal.getpFolder().substring(0, lIdxDrop);
						String lSuffixSupposedSourceFolder = lNameSupposedSourceFolder.substring(3 + RVStaticDir.getTARGET().length());
						/*
						 * Communication
						 */
						if (RVStaticConst.getIS_FULL_COM()) {
							BasicPrintMsg.display(this, lRVVersionFileRealLatest.getpNameFile() + " --> " + lNameSupposedSourceFolder + lNameSupposedSourceFile);
						}
						/*
						 * Copy the file and its folder back
						 */
						Path lPathVersion = lRVVersionFileRealLatest.getpPath();
						Path lPathCopyBack = Paths.get(lFolderOutput + lSuffixSupposedSourceFolder + lNameSupposedSourceFile);
						boolean lIsSuccess = false;
						if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
							lIsSuccess = BasicFichiersNio.copyFile(lPathVersion, lPathCopyBack);
						}
						/*
						 * Communication
						 */
						if (lIsSuccess) {
							lNbFilesBackCopied++;
						} else {
							lNbFilesFailedToCopy++;
						}
					}
				}
				/*
				 * Communication
				 */
				BasicPrintMsg.display(this, "Number of files successfully copied= " + lNbFilesBackCopied);
				BasicPrintMsg.display(this, "Number of files failed to copy= " + lNbFilesFailedToCopy);
			}			
		}
	}
	
}
