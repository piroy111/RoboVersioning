package step01_objects.source_files;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import basicmethods.BasicFichiersNio;
import step01_objects.version_drive.RVVersionDrive;
import step01_objects.version_folder.RVVersionFolder;

public class RVSourceFile {

	protected RVSourceFile(Path _sPath, RVSourceFileManager _sRVSourceFileManager) {
		pPath = _sPath;
		pRVSourceFileManager = _sRVSourceFileManager;
	}
	
	/*
	 * Data
	 */
	private Path pPath;
	private RVSourceFileManager pRVSourceFileManager;
	private Map<RVVersionDrive, RVVersionFolder> pMapRVVersionDriveToRVVersionFolder;
	private long pTimeStamp;
	
	/**
	 * 
	 */
	public final void initiate() {
		try {
			pTimeStamp = BasicFichiersNio.getLastModifiedTime(pPath);
		} catch (IOException lException) {
			lException.printStackTrace();
		}
		pMapRVVersionDriveToRVVersionFolder = new HashMap<>();
		for (RVVersionDrive lRVVersionDrive : pRVSourceFileManager.getpRVManager().getpRVVersionDriveManager().getpTreeMapNameToRVVersionDrive().values()) {
			pMapRVVersionDriveToRVVersionFolder.put(lRVVersionDrive, new RVVersionFolder(lRVVersionDrive, this));
		}
	}
	
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return pPath.getFileName().toString();
	}
	
	/*
	 * Getters & Setters
	 */
	public final Path getpPath() {
		return pPath;
	}
	public final RVSourceFileManager getpRVSourceFileManager() {
		return pRVSourceFileManager;
	}
	public final Map<RVVersionDrive, RVVersionFolder> getpMapRVVersionDriveToRVVersionFolder() {
		return pMapRVVersionDriveToRVVersionFolder;
	}
	public final long getpTimeStamp() {
		return pTimeStamp;
	}
	
	
	
	
	
}
