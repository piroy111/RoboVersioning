package step01_objects.version_drive;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import staticdata.RVStaticDir;
import step01_objects.version_folder.RVVersionFolder;

public class RVVersionDrive {

	protected RVVersionDrive(String _sName, RVVersionDriveManager _sRVVersionDriveManager) {
		pName = _sName;
		pRVVersionDriveManager = _sRVVersionDriveManager;
		/*
		 * 
		 */
		pNamePlusZZ = pName + RVStaticDir.getTARGET();
		pMapPathToRVVersionFolder = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private String pName;
	private String pNamePlusZZ;
	private String pDir;
	private RVVersionDriveManager pRVVersionDriveManager;
	private Map<Path, RVVersionFolder> pMapPathToRVVersionFolder;
	
	/**
	 * 
	 */
	public final void initiate() {
		pDir = pName + RVStaticDir.getTARGET();
	}

	/**
	 * 
	 * @param _sRVVersionFolder
	 */
	public final void declareNewRVVersionFolder(RVVersionFolder _sRVVersionFolder) {
		pMapPathToRVVersionFolder.put(_sRVVersionFolder.getpPath(), _sRVVersionFolder);
	}
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return pName;
	}

	/*
	 * Getters & Setters
	 */
	public final String getpName() {
		return pName;
	}
	public final String getpDir() {
		return pDir;
	}
	public final RVVersionDriveManager getpRVVersionDriveManager() {
		return pRVVersionDriveManager;
	}
	public final String getpNamePlusZZ() {
		return pNamePlusZZ;
	}
	public final Map<Path, RVVersionFolder> getpMapPathToRVVersionFolder() {
		return pMapPathToRVVersionFolder;
	}
	
}
