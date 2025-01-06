package step01_objects.source_files;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import step08_launchme.RVManager;

public class RVSourceFileManager {

	public RVSourceFileManager(RVManager _sRVManager) {
		pRVManager = _sRVManager;
		/*
		 * 
		 */
		pMapPathToRVSourceFile = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private RVManager pRVManager;
	private Map<Path, RVSourceFile> pMapPathToRVSourceFile;
	
	/**
	 * Classic
	 * @param _sName
	 * @return
	 */
	public final RVSourceFile getpOrCreateRVSourceFile(Path _sPath) {
		RVSourceFile lRVSourceFile = pMapPathToRVSourceFile.get(_sPath);
		if (lRVSourceFile == null) {
			lRVSourceFile = new RVSourceFile(_sPath, this);
			pMapPathToRVSourceFile.put(_sPath, lRVSourceFile);
		}
		return lRVSourceFile;
	}

	/*
	 * Getters & Setters
	 */
	public final RVManager getpRVManager() {
		return pRVManager;
	}
	public final Map<Path, RVSourceFile> getpMapPathToRVSourceFile() {
		return pMapPathToRVSourceFile;
	}
	
}
