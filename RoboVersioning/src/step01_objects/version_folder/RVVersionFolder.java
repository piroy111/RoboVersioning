package step01_objects.version_folder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import staticdata.RVStaticConst.action;
import step01_objects.source_files.RVSourceFile;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_file.RVVersionFile;

public class RVVersionFolder {

	public RVVersionFolder(RVVersionDrive _sRVVersionDrive, RVSourceFile _sRVSourceFile) {
		pRVVersionDrive = _sRVVersionDrive;
		pRVSourceFile = _sRVSourceFile;
	}
	
	/*
	 * Data
	 */
	private RVVersionDrive pRVVersionDrive;
	private RVSourceFile pRVSourceFile;
	private TreeMap<Long, RVVersionFile> pTreeMapTimeStampToRVVersionFile;
	private TreeMap<Long, RVVersionFile> pTreeMapTimeStampToRVVersionFileNotDeleted;
	private String pDir;
	private RVVersionFile pRVVersionFileLast;
	private RVVersionFile pRVVersionFileLastNotDeleted;
	private Path pPath;
	
	/**
	 * Get or create the directory
	 */
	public final int initiateOrCreateDirectory() {
		int lNbCreation = 0;
		String lDirPlusNameFileSource = BasicFichiersNio.getNamePlusPath(pRVSourceFile.getpPath());
		String[] lArray = lDirPlusNameFileSource.split("/", 2);
		pDir = pRVVersionDrive.getpDir() + lArray[1] + "/";
		if (!BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(pDir))) {
			if (!RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
				BasicFichiers.getOrCreateDirectory(pDir);
			}
			/*
			 * Communication
			 */			
			BasicPrintMsg.display(this, "Created new folder= '" + pDir + "'");
			pRVVersionDrive.getpRVVersionDriveManager().getpRVManager().getpRVActionManager().createNewRVAction(this, "", action.CREATE_FOLDER_IN_VERSION);
			lNbCreation = 1;
		}
		/*
		 * 
		 */
		pPath = Paths.get(pDir);
		pRVVersionDrive.declareNewRVVersionFolder(this);
		/*
		 * 
		 */
		return lNbCreation;
	}

	/**
	 * 
	 */
	public final void retrieveAllVersionFiles() {
		/*
		 * Initiate
		 */
		pTreeMapTimeStampToRVVersionFile = new TreeMap<>();
		pTreeMapTimeStampToRVVersionFileNotDeleted = new TreeMap<>();
		/*
		 * Read all the files
		 */
		List<String> lListNameFiles = BasicFichiersNio.getListFilesAndDirectoriesInDirectory(pDir);
		for (String lNameFile : lListNameFiles) {
			RVVersionFile lRVVersionFile = new RVVersionFile(this, lNameFile);
			pTreeMapTimeStampToRVVersionFile.put(lRVVersionFile.getpTimeStamp(), lRVVersionFile);
			if (!lRVVersionFile.getpIsDeleted()) {
				pTreeMapTimeStampToRVVersionFileNotDeleted.put(lRVVersionFile.getpTimeStamp(), lRVVersionFile);
			}
		}
		/*
		 * Get the last file
		 */
		if (pTreeMapTimeStampToRVVersionFile.size() > 0) {
			pRVVersionFileLast = pTreeMapTimeStampToRVVersionFile.lastEntry().getValue();
		}
		if (pTreeMapTimeStampToRVVersionFileNotDeleted.size() > 0) {
			pRVVersionFileLastNotDeleted = pTreeMapTimeStampToRVVersionFileNotDeleted.lastEntry().getValue();
		}
	}

	/*
	 * Getters & Setters
	 */
	public final RVVersionDrive getpRVVersionDrive() {
		return pRVVersionDrive;
	}
	public final RVSourceFile getpRVSourceFile() {
		return pRVSourceFile;
	}
	public final TreeMap<Long, RVVersionFile> getpTreeMapTimeStampToRVVersionFile() {
		return pTreeMapTimeStampToRVVersionFile;
	}
	public final String getpDir() {
		return pDir;
	}
	public final RVVersionFile getpRVVersionFileLast() {
		return pRVVersionFileLast;
	}
	public final TreeMap<Long, RVVersionFile> getpTreeMapTimeStampToRVVersionFileNotDeleted() {
		return pTreeMapTimeStampToRVVersionFileNotDeleted;
	}
	public final RVVersionFile getpRVVersionFileLastNotDeleted() {
		return pRVVersionFileLastNotDeleted;
	}
	public final Path getpPath() {
		return pPath;
	}
}
