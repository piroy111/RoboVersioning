package step01_objects.version_drive;

import java.util.TreeMap;

import step08_launchme.RVManager;

public class RVVersionDriveManager {

	public RVVersionDriveManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pTreeMapNameToRVVersionDrive = new TreeMap<>();
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	private TreeMap<String, RVVersionDrive> pTreeMapNameToRVVersionDrive;
	
	/**
	 * Classic
	 * @param _sName
	 * @return
	 */
	public final RVVersionDrive getpOrCreateRVVersionDrive(String _sName) {
		RVVersionDrive lRVVersionDrive = pTreeMapNameToRVVersionDrive.get(_sName);
		if (lRVVersionDrive == null) {
			lRVVersionDrive = new RVVersionDrive(_sName, this);
			pTreeMapNameToRVVersionDrive.put(_sName, lRVVersionDrive);
		}
		return lRVVersionDrive;
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}
	public final TreeMap<String, RVVersionDrive> getpTreeMapNameToRVVersionDrive() {
		return pTreeMapNameToRVVersionDrive;
	}
}
