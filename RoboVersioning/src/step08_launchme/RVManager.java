package step08_launchme;

import staticdata.RVStaticConst;
import staticdata.RVStaticManager;
import step01_objects.source_drives.RVSourceDriveManager;
import step01_objects.source_files.RVSourceFileManager;
import step01_objects.version_drive.RVVersionDriveManager;
import step01_objects.version_folder_real.RVVersionFolderRealManager;
import step02_findingmaindrive.RVDriveDetector;
import step03_reading_instructions.RVReadInstructionsFromConfFileManager;
import step04_dump.actions.RVActionManager;
import step04_dump.datelastrun.RVDateLastRun;
import step05_copynewversions.RVCopyManager;
import step05_copynewversions.RVEndFilesPublicator;
import step05_copynewversions.RVFilesDeletor;
import step05_copynewversions.RVReverseEngineer;
import tictoc.BasicTicToc;

public class RVManager {

	public RVManager() {
		pRVDetectDriveManager = new RVDriveDetector(this);
		pRVReadInstructionsFromConfFileManager = new RVReadInstructionsFromConfFileManager(this);
		pRVSourceDriveManager = new RVSourceDriveManager(this);
		pRVVersionDriveManager = new RVVersionDriveManager(this);
		pRVSourceFileManager = new RVSourceFileManager(this);
		pRVCopyManager = new RVCopyManager(this);
		pRVDateLastRun = new RVDateLastRun(this);
		pRVActionManager = new RVActionManager(this);
		pRVEndFilesPublicator = new RVEndFilesPublicator(this);
		pRVWrongFilesDeletor = new RVFilesDeletor(this);
		pRVVersionFolderRealManager = new RVVersionFolderRealManager(this);
		pRVStaticManager = new RVStaticManager(this);
		pRVReverseEngeneer = new RVReverseEngineer(this);
	}

	/*
	 * Data
	 */
	private RVDriveDetector pRVDetectDriveManager;
	private RVReadInstructionsFromConfFileManager pRVReadInstructionsFromConfFileManager;
	private RVSourceDriveManager pRVSourceDriveManager;
	private RVVersionDriveManager pRVVersionDriveManager;
	private RVSourceFileManager pRVSourceFileManager;
	private RVCopyManager pRVCopyManager;
	private RVDateLastRun pRVDateLastRun;
	private RVActionManager pRVActionManager;
	private RVEndFilesPublicator pRVEndFilesPublicator;
	private RVFilesDeletor pRVWrongFilesDeletor;
	private RVVersionFolderRealManager pRVVersionFolderRealManager;
	private RVStaticManager pRVStaticManager;
	private RVReverseEngineer pRVReverseEngeneer;

	/**
	 * 
	 */
	public final void run() {
		/*
		 * Initiate
		 */
		pRVDetectDriveManager.run();
		pRVStaticManager.run();
		if (!RVStaticConst.getIS_REVERSE_ENGENEER()) {
			pRVActionManager.readExistingFile();
		}
		pRVReadInstructionsFromConfFileManager.run();
		pRVVersionFolderRealManager.run();
		/*
		 * Actions: Copy, and notify absent and delete files absent for too long (as defined by the CONF file)
		 */
		if (!RVStaticConst.getIS_REVERSE_ENGENEER()) {
			pRVCopyManager.run();
			pRVEndFilesPublicator.run();
			pRVWrongFilesDeletor.run();
		}
		/*
		 * Reverse engineer
		 */
		else {
			pRVReverseEngeneer.run();
		}
		/*
		 * Display and print results
		 */
		if (!RVStaticConst.getIS_REVERSE_ENGENEER()) {
			pRVActionManager.writeFileOfActionsPerformed();
			pRVDateLastRun.writeDateLastRun();
		}
		BasicTicToc.displayResults();
	}

	/*
	 * Getters & Setters
	 */
	public final RVDriveDetector getpRVDriveDetector() {
		return pRVDetectDriveManager;
	}
	public final RVReadInstructionsFromConfFileManager getpRVReadInstructionsFromConfFileManager() {
		return pRVReadInstructionsFromConfFileManager;
	}
	public final RVSourceDriveManager getpRVSourceDriveManager() {
		return pRVSourceDriveManager;
	}
	public final RVDriveDetector getpRVDetectDriveManager() {
		return pRVDetectDriveManager;
	}
	public final RVVersionDriveManager getpRVVersionDriveManager() {
		return pRVVersionDriveManager;
	}
	public final RVSourceFileManager getpRVSourceFileManager() {
		return pRVSourceFileManager;
	}
	public final RVCopyManager getpRVCopyManager() {
		return pRVCopyManager;
	}
	public final RVDateLastRun getpRVDateLastRun() {
		return pRVDateLastRun;
	}
	public final RVActionManager getpRVActionManager() {
		return pRVActionManager;
	}
	public final RVEndFilesPublicator getpRVEndFilesPublicator() {
		return pRVEndFilesPublicator;
	}
	public final RVFilesDeletor getpRVWrongFilesDeletor() {
		return pRVWrongFilesDeletor;
	}

	public final RVVersionFolderRealManager getpRVVersionFolderRealManager() {
		return pRVVersionFolderRealManager;
	}




}
