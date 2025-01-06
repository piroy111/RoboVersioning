package step02_findingmaindrive;

import basicmethods.BasicPrintMsg;
import step08_launchme.RVManager;

public class RVDriveDetector {

	public RVDriveDetector(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pRVDetectDrive = new RVDetectDrive();
		pRVCreateDrive = new RVCreateDrive(this);
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	private RVDetectDrive pRVDetectDrive;
	private RVCreateDrive pRVCreateDrive;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displaySuperTitle(this, "Detect and create drives");
		pRVDetectDrive.run();
		pRVCreateDrive.run();
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}
	public final RVDetectDrive getpRVDetectDrive() {
		return pRVDetectDrive;
	}
	public final RVCreateDrive getpRVCreateDrive() {
		return pRVCreateDrive;
	}
	
}
