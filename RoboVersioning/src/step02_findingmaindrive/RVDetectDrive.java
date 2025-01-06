package step02_findingmaindrive;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import basicmethods.BasicFichiers;
import basicmethods.BasicFichiersNio;
import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import staticdata.RVStaticDir;

class RVDetectDrive {

	/*
	 * Data
	 */
	private List<String> pListDriveSource;
	private List<String> pListDriveTarget;
	
	/**
	 * 
	 */
	public final void run() {
		/*
		 * Detect, identify all existing drives
		 */
		BasicPrintMsg.displayTitle(this, "Simple detection of all existing drives");
		Map<String, String> lMapDriveToType = BasicFichiers.getDrivesValid();
		for (String lDrive : lMapDriveToType.keySet()) {
			BasicPrintMsg.display(this, "Drive= '" + lDrive + "'"
					+ "; Type= '" + lMapDriveToType.get(lDrive) + "'");
		}
		/*
		 * Detect the sources
		 */
		BasicPrintMsg.displayTitle(this, "Detection of the source drives (from which we will copy)");
		pListDriveSource = new ArrayList<>();
		for (String lDrive : lMapDriveToType.keySet()) {
			String lTypeStr = lMapDriveToType.get(lDrive);
			if (lTypeStr.equals("Local Disk") || lTypeStr.equals("USB Drive")) {
				String lFolderSource = lDrive + RVStaticDir.getMAIN_COPY();
				if (BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(lFolderSource))) {
					BasicPrintMsg.display(this, "Drive source detected= '" + lDrive + "'"
							+ " because the folder exists '" + lFolderSource + "'");
					pListDriveSource.add(lDrive);
				} 
				/*
				 * Special case for google drive --> we must detect the directory at the second level
				 */
				else {
					List<String> lListSubDir = BasicFichiersNio.getListFilesAndDirectoriesInDirectory(lDrive);
					for (String lSubDir : lListSubDir) {
						String lDir = BasicFichiers.getDirectoryNameCorrect(lDrive + lSubDir);
						lFolderSource = lDir + RVStaticDir.getMAIN_COPY();
						if (BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(lFolderSource))) {
							BasicPrintMsg.display(this, "Drive source detected= '" + lDrive + "'"
									+ " because the folder exists '" + lFolderSource + "'");
							pListDriveSource.add(lDir);
						}
					}
				}
			}
		}
		/*
		 * Detect the targets (where we will copy)
		 */
		BasicPrintMsg.displayTitle(this, "Detection of the target drives (to which we will copy)");
		pListDriveTarget = new ArrayList<>();
		for (String lDrive : lMapDriveToType.keySet()) {
			String lTypeStr = lMapDriveToType.get(lDrive);
			if (lTypeStr.equals("Local Disk") || lTypeStr.equals("USB Drive")) {
				String lFolderTarget = lDrive + RVStaticDir.getTARGET();
				if (BasicFichiersNioRaw.getIsAlreadyExist(Paths.get(lFolderTarget))) {
					BasicPrintMsg.display(this, "Drive target detected= '" + lDrive + "'"
							+ " because the folder exists '" + lFolderTarget + "'");
					pListDriveTarget.add(lDrive);
				} 
			}
		}
	}


	/*
	 * Getters & Setters
	 */
	public final List<String> getpListDriveSource() {
		return pListDriveSource;
	}
	public final List<String> getpListDriveTarget() {
		return pListDriveTarget;
	}
	
}
