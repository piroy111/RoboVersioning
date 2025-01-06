package step01_objects.source_drives;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicTime;
import basicmethods.ReadFile;
import basicmethods.ReadFile.comReadFile;
import staticdata.RVStaticConst;
import staticdata.RVStaticDir;
import staticdata.RVStaticNameFile;

public class RVSourceDrive {

	protected RVSourceDrive(String _sName, RVSourceDriveManager _sRVSourceDriveManager) {
		pName = _sName;
		pRVSourceDriveManager = _sRVSourceDriveManager;
	}
	
	/*
	 * Data
	 */
	private String pName;
	private String pNamePlusZZ;
	private String pNamePlusZZReverse;
	private String pNamePlusZZReverseOutput;
	private RVSourceDriveManager pRVSourceDriveManager;
	private Path pPathDirConf;
	private ReadFile pReadFileInstruction;
	private int pDateLastRun;

	/**
	 * 
	 */
	public final void initiate() {
		/*
		 * get or create path of instructions
		 */
		pNamePlusZZ = pName + RVStaticDir.getMAIN_COPY();
		pNamePlusZZReverse = pName + RVStaticDir.getREVERSE();
		pNamePlusZZReverseOutput = pName + RVStaticDir.getREVERSE_OUTPUT();
		pPathDirConf = Paths.get(pNamePlusZZ);
		BasicFichiersNio.createDirectory(pPathDirConf);
		/*
		 *  We create a file instructions if it is not here
		 */
		String lNameConf = RVStaticNameFile.getLIST_FOLDERS_TO_SAVE();
		Path lPathFileConf = Paths.get(pNamePlusZZ + lNameConf);
		if (!BasicFichiersNioRaw.getIsAlreadyExist(lPathFileConf)) {
			String lHeader = RVStaticConst.getHEADER_CONF_INSTRUCTIONS();
			BasicFichiers.writeFile(pNamePlusZZ, lNameConf, lHeader, new ArrayList<>());
			BasicTime.sleep(50);
		}
		/*
		 * Read the file of instructions
		 */
		pReadFileInstruction = new ReadFile(lPathFileConf, comReadFile.FULL_COM);
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
	/**
	 * Contains already 'ZZ_ROBO_VERSIONING_SOURCE'
	 * @return
	 */
	public final String getpName() {
		return pName;
	}
	public final RVSourceDriveManager getpRVSourceDriveManager() {
		return pRVSourceDriveManager;
	}
	public final Path getpPathDirConf() {
		return pPathDirConf;
	}
	public final ReadFile getpReadFileInstruction() {
		return pReadFileInstruction;
	}
	public final String getpNamePlusZZ() {
		return pNamePlusZZ;
	}
	public final int getpDateLastRun() {
		return pDateLastRun;
	}
	public final void setpDateLastRun(int _sPDateLastRun) {
		pDateLastRun = _sPDateLastRun;
	}
	public final String getpNamePlusZZReverse() {
		return pNamePlusZZReverse;
	}
	public final String getpNamePlusZZReverseOutput() {
		return pNamePlusZZReverseOutput;
	}
}
