package step02_findingmaindrive;

import basicmethods.BasicDateInt;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticConst;
import step01_objects.source_drives.RVSourceDrive;
import step01_objects.source_drives.RVSourceDriveManager;
import step01_objects.version_drive.RVVersionDrive;

class RVCreateDrive {

	protected RVCreateDrive(RVDriveDetector _sRVDriveDetector) {
		pRVDriveDetector = _sRVDriveDetector;
	}
	
	/*
	 * Data
	 */
	private RVDriveDetector pRVDriveDetector;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Create objects drives");
		/*
		 * Source drives
		 */
		RVSourceDriveManager lRVSourceDriveManager = pRVDriveDetector.getpRVManager().getpRVSourceDriveManager();
		for (String lSourceDriveStr : pRVDriveDetector.getpRVDetectDrive().getpListDriveSource()) {
			RVSourceDrive lRVSourceDrive = lRVSourceDriveManager.getpOrCreateRVSourceDrive(lSourceDriveStr);
			/*
			 * Read file CONF of instructions
			 */
			lRVSourceDrive.initiate();
			/*
			 * Check if the drive should be done today
			 */
			int lLastDateRun = pRVDriveDetector.getpRVManager().getpRVDateLastRun().getpAndComputeDateLastRun(lRVSourceDrive);
			if (lLastDateRun < BasicDateInt.getmToday()) {
				lRVSourceDriveManager.declareNewRVSourceDriveToDo(lRVSourceDrive);
				BasicPrintMsg.display(this, "Activate " + RVSourceDrive.class.getSimpleName() + " '" + lRVSourceDrive.getpName() + "'"
						+ " because last run is out of date; last run= " + lLastDateRun);
			} else if (RVStaticConst.getIS_REVERSE_ENGENEER()) {
				lRVSourceDriveManager.declareNewRVSourceDriveToDo(lRVSourceDrive);
				BasicPrintMsg.display(this, "Activate " + RVSourceDrive.class.getSimpleName() + " '" + lRVSourceDrive.getpName() + "'"
						+ " because the program reverse engineer was activated; last run= " + lLastDateRun);
			} else if (RVStaticConst.getIS_DEBUG_AND_SKIP_DATE_CHECK()) {
				lRVSourceDriveManager.declareNewRVSourceDriveToDo(lRVSourceDrive);
				BasicPrintMsg.display(this, "Activate " + RVSourceDrive.class.getSimpleName() + " '" + lRVSourceDrive.getpName() + "'"
						+ " because we deactivated the check on last date run");
			} else {
				BasicPrintMsg.display(this, "End the program because it was already run today; last run= " + lLastDateRun);
				System.exit(0);
			}
		}
		BasicPrintMsg.display(this, "--> " + pRVDriveDetector.getpRVDetectDrive().getpListDriveSource().size()
				+ " " + RVSourceDrive.class.getSimpleName() + " created");
		BasicPrintMsg.display(this, "--> " + lRVSourceDriveManager.getpListRVSourceDriveToDo().size()
				+ " " + RVSourceDrive.class.getSimpleName() + " activated and to be done by the program");
		/*
		 * Version drives
		 */
		for (String lVersionDriveStr : pRVDriveDetector.getpRVDetectDrive().getpListDriveTarget()) {
			RVVersionDrive lRVVersionDrive = pRVDriveDetector.getpRVManager().getpRVVersionDriveManager().getpOrCreateRVVersionDrive(lVersionDriveStr);
			/*
			 * 
			 */
			lRVVersionDrive.initiate();
		}
		BasicPrintMsg.display(this, "--> " + pRVDriveDetector.getpRVDetectDrive().getpListDriveTarget().size()
				+ " " + RVVersionDrive.class.getSimpleName() + " created");
	}
	
}
