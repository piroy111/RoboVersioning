package step01_objects.version_file_real;

import java.nio.file.Path;
import java.nio.file.Paths;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import staticdata.RVStaticConst;
import step01_objects.version_folder_real.RVVersionFolderReal;

public class RVVersionFileReal {

	public RVVersionFileReal(String _sNameFile, RVVersionFolderReal _sRVVersionFolderReal) {
		pNameFile = _sNameFile;
		pRVVersionFolderReal = _sRVVersionFolderReal;
		/*
		 * 
		 */
		intiate();
	}
	
	/*
	 * Data
	 */
	private String pNameFile;
	private RVVersionFolderReal pRVVersionFolderReal;
	private Path pPath;
	private String pPrefix;
	private int pDate;
	
	/**
	 * 
	 */
	private void intiate() {
		pPath = Paths.get(pRVVersionFolderReal.getpFolder() + pNameFile);
		/*
		 * Prefix (which also determines the nature of the file)
		 */
		if (pNameFile.startsWith(RVStaticConst.getNAME_VERSION())) {
			pPrefix = RVStaticConst.getNAME_VERSION();
		} else if (pNameFile.startsWith(RVStaticConst.getNAME_ABSENT())) {
			pPrefix = RVStaticConst.getNAME_ABSENT();
		} else {
			BasicPrintMsg.error("I dont recognize the following file"
					+ "\nName= '" + pNameFile + "'"
					+ "\nFolder= '" + pRVVersionFolderReal.getpFolder() + "'");
		}
		/*
		 * Date of the file (read in the name of the file)
		 */
		pDate = BasicString.getInt(pNameFile.substring(pPrefix.length(), pPrefix.length() + 8));
	}
	
	/**
	 * 
	 * @return
	 */
	public final boolean getpIsAbsent() {
		return pPrefix.equals(RVStaticConst.getNAME_ABSENT());
	}
	
	/**
	 * 
	 * @return
	 */
	public final boolean getpIsPresentAndValid() {
		return pPrefix.equals(RVStaticConst.getNAME_VERSION());
	}
	
	/*
	 * Getters & Setters
	 */
	public final String getpNameFile() {
		return pNameFile;
	}
	public final RVVersionFolderReal getpRVVersionFolderReal() {
		return pRVVersionFolderReal;
	}
	public final Path getpPath() {
		return pPath;
	}
	public final String getpPrefix() {
		return pPrefix;
	}
	public final int getpDate() {
		return pDate;
	}
	
	
}
