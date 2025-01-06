package step01_objects.version_folder_real;

import java.nio.file.Path;
import java.util.List;
import java.util.TreeMap;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import step01_objects.version_file_real.RVVersionFileReal;
import step01_objects.version_folder.RVVersionFolder;

public class RVVersionFolderReal {

	protected RVVersionFolderReal(Path _sPath, RVVersionFolderRealManager _sRVVersionFolderRealManager) {
		pPath = _sPath;
		pRVVersionFolderRealManager = _sRVVersionFolderRealManager;
		/*
		 * 
		 */
		initiate();
	}
	
	/*
	 * Data
	 */
	private Path pPath;
	private String pFolder;
	private String pNameSupposedSourceFile;
	private RVVersionFolderRealManager pRVVersionFolderRealManager;
	private RVVersionFolder pRVVersionFolder; // RVVersionFolder is the wished folder from the RVSourceFiles.
	private TreeMap<Integer, RVVersionFileReal> pTreeMapDateToRVVersionFileValid;
	private TreeMap<Integer, RVVersionFileReal> pTreeMapDateToRVVersionFileEnd;
	private TreeMap<Integer, RVVersionFileReal> pTreeMapDateToRVVersionFileAll;
	private RVVersionFileReal pRVVersionFileRealLatest;

	
	protected final void initiate() {
		/*
		 * Initiate
		 */
		pTreeMapDateToRVVersionFileValid = new TreeMap<>();
		pTreeMapDateToRVVersionFileEnd = new TreeMap<>();
		pTreeMapDateToRVVersionFileAll = new TreeMap<>();
		List<String> lListNameRealFiles = BasicFichiersNio.getListFilesAndDirectoriesInDirectory(pPath);
		pFolder = BasicFichiersNio.getNamePlusPath(pPath);
		/*
		 * Loop on all the files of this folder and fill the treeMaps of the files
		 */
		for (String lFileName : lListNameRealFiles) {
			RVVersionFileReal lRVVersionFileReal = new RVVersionFileReal(lFileName, this);
			if (lRVVersionFileReal.getpIsAbsent()) {
				pTreeMapDateToRVVersionFileEnd.put(lRVVersionFileReal.getpDate(), lRVVersionFileReal);
			} else if (lRVVersionFileReal.getpIsPresentAndValid()) {
				pTreeMapDateToRVVersionFileValid.put(lRVVersionFileReal.getpDate(), lRVVersionFileReal);
			} else {
				BasicPrintMsg.errorCodeLogic();
			}
			pTreeMapDateToRVVersionFileAll.put(lRVVersionFileReal.getpDate(), lRVVersionFileReal);
		}
		/*
		 * 
		 */
		if (pTreeMapDateToRVVersionFileAll.size() > 0) {
			pRVVersionFileRealLatest = pTreeMapDateToRVVersionFileAll.lastEntry().getValue();
		}
		/*
		 * 
		 */
		pNameSupposedSourceFile = pPath.getFileName().toString();
	}
	
	
	/*
	 * Getters & Setters
	 */
	public final Path getpPath() {
		return pPath;
	}
	public final RVVersionFolderRealManager getpRVVersionFolderRealManager() {
		return pRVVersionFolderRealManager;
	}
	public final RVVersionFolder getpRVVersionFolder() {
		return pRVVersionFolder;
	}
	public final void setpRVVersionFolder(RVVersionFolder _sPRVVersionFolder) {
		pRVVersionFolder = _sPRVVersionFolder;
	}
	public final String getpFolder() {
		return pFolder;
	}
	public final TreeMap<Integer, RVVersionFileReal> getpTreeMapDateToRVVersionFileValid() {
		return pTreeMapDateToRVVersionFileValid;
	}
	public final TreeMap<Integer, RVVersionFileReal> getpTreeMapDateToRVVersionFileEnd() {
		return pTreeMapDateToRVVersionFileEnd;
	}
	public final TreeMap<Integer, RVVersionFileReal> getpTreeMapDateToRVVersionFileAll() {
		return pTreeMapDateToRVVersionFileAll;
	}
	public final RVVersionFileReal getpRVVersionFileRealLatest() {
		return pRVVersionFileRealLatest;
	}
	public final String getpNameSupposedSourceFile() {
		return pNameSupposedSourceFile;
	}
	
}
