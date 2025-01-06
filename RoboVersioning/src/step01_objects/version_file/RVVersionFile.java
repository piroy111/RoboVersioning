package step01_objects.version_file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import basicmethods.BasicFichiersNio;
import staticdata.RVStaticConst;
import step01_objects.version_folder.RVVersionFolder;

public class RVVersionFile {

	public RVVersionFile(RVVersionFolder _sRVVersionFolder, String _sNameFile) {
		pRVVersionFolder = _sRVVersionFolder;
		pNameFile = _sNameFile;
		/*
		 * 
		 */
		pPath = Paths.get(pRVVersionFolder.getpDir() + pNameFile);
		try {
			pTimeStamp = BasicFichiersNio.getLastModifiedTime(pPath);
		} catch (IOException lException) {
			lException.printStackTrace();
		}
	}

	/*
	 * Data
	 */
	private RVVersionFolder pRVVersionFolder;
	private String pNameFile;
	private Path pPath;
	private long pTimeStamp;

	/**
	 * Classic
	 */
	public String toString() {
		return "pTimeStamp= " + pTimeStamp
				+ "; NameFile= '" + pPath.toString() + "'";
	}

	/**
	 * 
	 * @return
	 */
	public final boolean getpIsDeleted() {
		return pNameFile.startsWith(RVStaticConst.getNAME_ABSENT());
	}

	/**
	 * 
	 * @return
	 */
	public final boolean getpIsNormalFile() {
		return pNameFile.startsWith(RVStaticConst.getNAME_VERSION());
	}

	/*
	 * Getters & Setters
	 */
	public final RVVersionFolder getpRVVersionFolder() {
		return pRVVersionFolder;
	}
	public final String getpNameFile() {
		return pNameFile;
	}
	public final Path getpPath() {
		return pPath;
	}
	public final long getpTimeStamp() {
		return pTimeStamp;
	}
	
	
}
