package step01_objects.source_drives;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import step08_launchme.RVManager;

public class RVSourceDriveManager {

	public RVSourceDriveManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pTreeMapNameToRVSourceDrive = new TreeMap<>();
		pListRVSourceDriveToDo = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	private TreeMap<String, RVSourceDrive> pTreeMapNameToRVSourceDrive;
	private List<RVSourceDrive> pListRVSourceDriveToDo;
	
	/**
	 * Classic
	 * @param _sName
	 * @return
	 */
	public final RVSourceDrive getpOrCreateRVSourceDrive(String _sName) {
		RVSourceDrive lRVSourceDrive = pTreeMapNameToRVSourceDrive.get(_sName);
		if (lRVSourceDrive == null) {
			lRVSourceDrive = new RVSourceDrive(_sName, this);
			pTreeMapNameToRVSourceDrive.put(_sName, lRVSourceDrive);
		}
		return lRVSourceDrive;
	}

	/**
	 * 
	 * @param _sRVSourceDrive
	 */
	public final void declareNewRVSourceDriveToDo(RVSourceDrive _sRVSourceDrive) {
		pListRVSourceDriveToDo.add(_sRVSourceDrive);
	}
	
	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}
	public final TreeMap<String, RVSourceDrive> getpTreeMapNameToRVSourceDrive() {
		return pTreeMapNameToRVSourceDrive;
	}
	public final List<RVSourceDrive> getpListRVSourceDriveToDo() {
		return pListRVSourceDriveToDo;
	}
	
	
}
