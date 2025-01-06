package step04_dump.actions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.BasicTime;
import basicmethods.ReadFile;
import basicmethods.ReadFile.comReadFile;
import staticdata.RVStaticConst;
import staticdata.RVStaticConst.action;
import staticdata.RVStaticNameFile;
import step01_objects.source_drives.RVSourceDrive;
import step01_objects.version_folder.RVVersionFolder;
import step08_launchme.RVManager;

public class RVActionManager {

	public RVActionManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * Initiate
		 */
		pMapKeyToRVAction = new HashMap<>();
	}

	/*
	 * Data
	 */
	private RVManager pRVManager;
	private Map<String, RVAction> pMapKeyToRVAction;

	/**
	 * Read existing file and create the actions
	 */
	public final void readExistingFile() {
		if (RVStaticConst.getIS_REVERSE_ENGENEER()) {
			return;
		}
		BasicPrintMsg.displaySuperTitle(this, "Read file of actions");
		/*
		 * Loop on the version drives
		 */
		for (RVSourceDrive lRVSourceDrive : pRVManager.getpRVSourceDriveManager().getpTreeMapNameToRVSourceDrive().values()) {
			/*
			 * Initiate
			 */
			String lNameFile = RVStaticNameFile.getACTIONS();
			Path lPath = Paths.get(lRVSourceDrive.getpNamePlusZZ() + lNameFile);
			List<Integer> lListDateKnown = new ArrayList<>();
			/*
			 * Communication
			 */
			BasicPrintMsg.display(this, "Source drive= '" + lRVSourceDrive.getpNamePlusZZ() + "'");
			/*
			 * Case the file exist --> we read the file and fill the map
			 */
			int lNbRVActionCreated = pMapKeyToRVAction.size();
			if (BasicFichiersNioRaw.getIsAlreadyExist(lPath)) {
				ReadFile lReadFile = new ReadFile(lPath, comReadFile.FULL_COM);
				for (List<String> lLine : lReadFile.getmContentList()) {
					/*
					 * Load line
					 */
					int lIdx = -1;
					int lDate = BasicString.getInt(lLine.get(++lIdx));
					String lTimeStr = lLine.get(++lIdx);
					String lFolderName = lLine.get(++lIdx);
					String lFileName = lLine.get(++lIdx);
					action lAction = action.valueOf(lLine.get(++lIdx));
					/*
					 * Create RVAction and store it
					 */
					if (lDate >= RVStaticConst.getDATE_MAX_TO_KEEP_PAST_ACTIONS()) {
						getpOrCreateRVAction(lDate, lTimeStr, lFolderName, lFileName, lAction);
						/*
						 * Communication
						 */
						if (!lListDateKnown.contains(lDate)) {
							BasicPrintMsg.display(this, "Date with actions= " + lDate);
							lListDateKnown.add(lDate);
						}
					}
				}
			} else {
				BasicPrintMsg.display(this, "No file found");
			}
			BasicPrintMsg.display(this, "Nb RVActions created from this file= " + (pMapKeyToRVAction.size() - lNbRVActionCreated));
			BasicPrintMsg.display(this, null);		
		}
	}

	/**
	 * Classic get or create
	 * @param _sRVVersionDrive
	 * @return
	 */
	public RVAction getpOrCreateRVAction(int _sDate, String _sTimeStr, String _sDirName, String _sFileName, action _sAction) {
		String lKey = RVAction.getKey(_sDate, _sTimeStr, _sDirName, _sFileName, _sAction);
		RVAction lRVAction = pMapKeyToRVAction.get(lKey);
		if (lRVAction == null) {
			lRVAction = new RVAction(_sDate, _sTimeStr, _sDirName, _sFileName, _sAction);
			pMapKeyToRVAction.put(lKey, lRVAction);
		}		
		return lRVAction;
	}

	/**
	 * 
	 * @param _sRVVersionFile
	 * @param _sAction
	 */
	public final void createNewRVAction(RVVersionFolder _sRVVersionFolder, String _sFileName, action _sAction) {
		String lFolderName = _sRVVersionFolder.getpDir();
		createNewRVAction(lFolderName, _sFileName, _sAction);
	}
	
	/**
	 * 
	 * @param _sRVVersionFile
	 * @param _sAction
	 */
	public final void createNewRVAction(String _sDir, String _sFileName, action _sAction) {
		int lDate = BasicDateInt.getmToday();
		String lTimeStr = BasicTime.getHeureTexteHHMMSSFromLong(BasicTime.getmNow());
		getpOrCreateRVAction(lDate, lTimeStr, _sDir, _sFileName, _sAction);
	}

	/**
	 * Write all the actions in the file
	 */
	public final void writeFileOfActionsPerformed() {
		BasicPrintMsg.displaySuperTitle(this, "Write file of actions performed by the program");
		/*
		 * Initiate common variables to all drives
		 */
		String lHeader = "Date,Time,Folder,File,Action";
		String lNameFile = RVStaticNameFile.getACTIONS();
		List<String> lListLineToWrite = new ArrayList<>();
		/*
		 * Build file content 
		 */
		List<RVAction> lListRVAction = new ArrayList<>(pMapKeyToRVAction.values());
		Collections.sort(lListRVAction);
		for (RVAction lRVAction : lListRVAction) {
			String lLine = lRVAction.getpDate()
					+ "," + lRVAction.getpTimeStr()
					+ "," + "\"" + lRVAction.getpDirName() + "\""
					+ "," + "\"" + lRVAction.getpFileName() + "\""
					+ "," + lRVAction.getpAction();
			lListLineToWrite.add(lLine);
		}
		/*
		 * Write file actions
		 */
		for (RVSourceDrive lRVSourceDrive : pRVManager.getpRVSourceDriveManager().getpTreeMapNameToRVSourceDrive().values()) {
			BasicFichiers.writeFile(this, lRVSourceDrive.getpNamePlusZZ(), lNameFile, lHeader, lListLineToWrite);
		}
		BasicPrintMsg.display(this, lListRVAction.size() + " actions written in all files");
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}



}
