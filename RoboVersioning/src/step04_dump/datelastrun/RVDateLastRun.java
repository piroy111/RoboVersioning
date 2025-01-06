package step04_dump.datelastrun;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.ReadFile;
import basicmethods.ReadFile.comReadFile;
import staticdata.RVStaticConst;
import staticdata.RVStaticNameFile;
import step01_objects.source_drives.RVSourceDrive;
import step08_launchme.RVManager;

public class RVDateLastRun {

	public RVDateLastRun(RVManager _sRVManager) {
		pRVManager = _sRVManager;
	}

	/*
	 * Data
	 */
	private RVManager pRVManager;

	/**
	 * We will run the program only once per day, because it is largely enough
	 */
	public final int getpAndComputeDateLastRun(RVSourceDrive _sRVSourceDrive) {
		String lNameFile = RVStaticNameFile.getLAST_DATE_RUN();
		Path lPath = Paths.get(_sRVSourceDrive.getpNamePlusZZ() + lNameFile);
		/*
		 * Case the file exist --> we read the file and set the date last run in the RVSourceDrive
		 */
		if (BasicFichiersNioRaw.getIsAlreadyExist(lPath)) {
			ReadFile lReadFile = new ReadFile(lPath, comReadFile.FULL_COM);
			return BasicString.getInt(lReadFile.getmContent().get(0));
		}
		/*
		 * Case there is no date yet
		 */
		else {
			return -1;
		}
	}

	/**
	 * 
	 */
	public final void writeDateLastRun() {
		BasicPrintMsg.displaySuperTitle(this, "Write the last time we ran the program");
		if (RVStaticConst.getIS_DEBUG_AND_DONT_DO_ACTIONS()) {
			BasicPrintMsg.display(this, "Not written because in debug mode");
		} else {
			for (RVSourceDrive lRVSourceDrive : pRVManager.getpRVSourceDriveManager().getpTreeMapNameToRVSourceDrive().values()) {
				String lNameFile = RVStaticNameFile.getLAST_DATE_RUN();
				String lHeader = RVStaticConst.getHEADER_DATE_LAST_RUN();
				List<String> lListLineToWrite = new ArrayList<>();
				lListLineToWrite.add("" + BasicDateInt.getmToday());
				BasicFichiers.writeFile(this, lRVSourceDrive.getpNamePlusZZ(), lNameFile, lHeader, lListLineToWrite);
			}
		}
	}

}
