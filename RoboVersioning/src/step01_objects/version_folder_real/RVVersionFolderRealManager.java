package step01_objects.version_folder_real;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import step01_objects.version_drive.RVVersionDrive;
import step08_launchme.RVManager;

public class RVVersionFolderRealManager {

	public RVVersionFolderRealManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pMapRVVersionDriveToMapPathToRVVersionFolderReal = new HashMap<>();
	}

	/*
	 * Data
	 */
	private RVManager pRVManager;
	private Map<RVVersionDrive, Map<Path, RVVersionFolderReal>> pMapRVVersionDriveToMapPathToRVVersionFolderReal;

	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displaySuperTitle(this, "Identify the real version files and folders");
		/*
		 * Loop on the version drives
		 */
		for (RVVersionDrive lRVVersionDrive : pRVManager.getpRVVersionDriveManager().getpTreeMapNameToRVVersionDrive().values()) {
			String lDir = lRVVersionDrive.getpNamePlusZZ();
			BasicPrintMsg.display(this, "Checking " + lRVVersionDrive.getpNamePlusZZ());
			int lNbFiles = 0;
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
				 * Create the RVVersionFolderReal + read all the files inside it and assign to the type (valid file or notify absent file)
				 */
				RVVersionFolderReal lRVVersionFolderReal = getpOrCreateRVVersionFolderReal(lRVVersionDrive, lPath);
				lNbFiles += lRVVersionFolderReal.getpTreeMapDateToRVVersionFileAll().size();
				/*
				 * Fill with the RVVersionFolder, which is what we want to have according to the RVSourceFolder
				 */
				lRVVersionFolderReal.setpRVVersionFolder(lRVVersionDrive.getpMapPathToRVVersionFolder().get(lPath));
			}
			BasicPrintMsg.display(this, "Number of files= " + lNbFiles);
			BasicPrintMsg.display(this, null);
		}
	}

	/**
	 * 
	 * @param _sPath
	 * @return
	 */
	public final RVVersionFolderReal getpOrCreateRVVersionFolderReal(RVVersionDrive _sRVVersionDrive, Path _sPath) {
		/*
		 * 1st get or create
		 */
		Map<Path, RVVersionFolderReal> lMapPathToRVVersionFolderReal = pMapRVVersionDriveToMapPathToRVVersionFolderReal.get(_sRVVersionDrive);
		if (lMapPathToRVVersionFolderReal == null) {
			lMapPathToRVVersionFolderReal = new HashMap<>();
			pMapRVVersionDriveToMapPathToRVVersionFolderReal.put(_sRVVersionDrive, lMapPathToRVVersionFolderReal);
		}
		/*
		 * 2nd get or create
		 */
		RVVersionFolderReal lRVVersionFolderReal = lMapPathToRVVersionFolderReal.get(_sPath);
		if (lRVVersionFolderReal == null) {
			lRVVersionFolderReal = new RVVersionFolderReal(_sPath, this);
			lMapPathToRVVersionFolderReal.put(_sPath, lRVVersionFolderReal);
		}
		return lRVVersionFolderReal;
	}

	/*
	 * Getters & Setters
	 */
	public final Map<RVVersionDrive, Map<Path, RVVersionFolderReal>> getpMapRVVersionDriveToMapPathToRVVersionFolderReal() {
		return pMapRVVersionDriveToMapPathToRVVersionFolderReal;
	}
	
}
