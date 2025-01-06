package step04_dump.actions;

import staticdata.RVStaticConst.action;

public class RVAction implements Comparable<RVAction> {

	public RVAction(int _sDate, String _sTimeStr, String _sDirName, String _sFileName, action _sAction) {
		pDate = _sDate;
		pTimeStr = _sTimeStr;
		pFileName = _sFileName;
		pDirName = _sDirName;
		pAction = _sAction;
		/*
		 * 
		 */
		pDirPlusFileName = pDirName + pFileName;
	}
	
	/*
	 * Data
	 */
	private int pDate;
	private String pTimeStr;
	private String pDirName;
	private String pFileName;	
	private action pAction;
	private String pDirPlusFileName;

	/**
	 * Put the oldest first
	 */
	@Override public int compareTo(RVAction _sRVAction) {
		int lCompareDate = Integer.compare(pDate, _sRVAction.pDate);
		if (lCompareDate != 0) {
			return -lCompareDate;
		} else {
			int lCompare = pDirPlusFileName.compareTo(_sRVAction.pDirPlusFileName);
			if (lCompare != 0) {
				return lCompare;
			} else {
				return -pTimeStr.compareTo(_sRVAction.pTimeStr);
			}
		}
	}

	/**
	 * 
	 * @param _sDate
	 * @param _sTimeStr
	 * @param _sDirName
	 * @param _sFileName
	 * @param _sAction
	 * @return
	 */
	public static String getKey(int _sDate, String _sTimeStr, String _sDirName, String _sFileName, action _sAction) {
		return _sDate + ";;" + _sTimeStr + ";;" + removeQuotes(_sDirName) + ";;" + removeQuotes(_sFileName) + ";;" + _sAction;
	}
	
	/**
	 * 
	 * @param _sString
	 * @return
	 */
	private static String removeQuotes(String _sString) {
		if (_sString.startsWith("\"")) {
			return removeQuotes(_sString.substring(1));
		}
		if (_sString.endsWith("\"")) {
			return removeQuotes(_sString.substring(0, _sString.length() - 1));
		}
		return _sString;
	}
	
	/*
	 * Getters & Setters
	 */
	public final int getpDate() {
		return pDate;
	}
	public final String getpTimeStr() {
		return pTimeStr;
	}
	public final String getpFileName() {
		return pFileName;
	}
	public final String getpDirName() {
		return pDirName;
	}
	public final action getpAction() {
		return pAction;
	}
	
	
	
}
