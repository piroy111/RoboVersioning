package staticdata;

import java.nio.file.Path;
import java.nio.file.Paths;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicString;
import basicmethods.ReadFile;
import basicmethods.ReadFile.comReadFile;
import step01_objects.source_drives.RVSourceDrive;
import step08_launchme.RVManager;

public class RVStaticManager {

	public RVStaticManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	
	/**
	 * Load the CONF file
	 */
	public final void run() {
		for (RVSourceDrive lRVSourceDrive : pRVManager.getpRVSourceDriveManager().getpListRVSourceDriveToDo()) {
			/*
			 * Load the CONF copy
			 */
			String lNameFileConf = RVStaticNameFile.getCONF_COPY();
			Path lPathConf = Paths.get(lRVSourceDrive.getpNamePlusZZ() + lNameFileConf);
			if (BasicFichiersNioRaw.getIsAlreadyExist(lPathConf)) {
				ReadFile lReadFile = new ReadFile(lPathConf, comReadFile.FULL_COM);
				/*
				 * Load
				 */
				int lIdx = -1;
				int lNbDaysMinDelete = BasicString.getInt(lReadFile.getmContentList().get(++lIdx).get(1));
				int lNbDaysMinKeepHistory = BasicString.getInt(lReadFile.getmContentList().get(++lIdx).get(1));
				/*
				 * Read file and fill it in static variables
				 */
				int lDateMaxDelete = BasicDateInt.getmPlusDay(BasicDateInt.getmToday(), -lNbDaysMinDelete);
				RVStaticConst.setDATE_MAX_TO_ALLOW_DELETION(lDateMaxDelete);
				int lDateMaxHistory = BasicDateInt.getmPlusDay(BasicDateInt.getmToday(), -lNbDaysMinKeepHistory);
				RVStaticConst.setDATE_MAX_TO_KEEP_PAST_ACTIONS(lDateMaxHistory);
			}
			/*
			 * Load the CONF reverse-engineer
			 */
			lNameFileConf = RVStaticNameFile.getCONF_REVERSE();
			lPathConf = Paths.get(lRVSourceDrive.getpNamePlusZZReverse() + lNameFileConf);
			if (BasicFichiersNioRaw.getIsAlreadyExist(lPathConf)) {
				ReadFile lReadFile = new ReadFile(lPathConf, comReadFile.FULL_COM);
				/*
				 * Load
				 */
				int lIdx = -1;
				String lDriveToReverse = lReadFile.getmContentList().get(++lIdx).get(1);
				/*
				 * Read file and fill it in static variables
				 */
				RVStaticConst.setDRIVE_TO_REVERSE(lDriveToReverse);
			}
		}
	}
	
}
