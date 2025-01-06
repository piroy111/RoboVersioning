package staticdata;

public class RVStaticConst {

	/*
	 * Headers
	 */
	private static String HEADER_CONF_INSTRUCTIONS = "Files to copy (if '*' then we will copy all the files and sub files; if '*.*' then we will copy all the files)";
	private static String HEADER_DATE_LAST_RUN = "Date at which we ran the program the last time (used so that we run the program only once a day)";
	/*
	 * ENUM
	 */
	public static enum action {DELETED_FROM_VERSION, FAIL_TO_DELETE, COPIED_TO_VERSION, NOTIFIED_ABSENT, IGNORED, CREATE_FOLDER_IN_VERSION}
	/*
	 * MODE SELECTION
	 */
	private static boolean IS_DEBUG_AND_DONT_DO_ACTIONS = false;
	private static boolean IS_DEBUG_AND_SKIP_DATE_CHECK = true;
	private static boolean IS_REVERSE_ENGENEER;
	private static boolean IS_FULL_COM = false;
	/*
	 * CONST
	 */
	private static int DATE_MAX_TO_KEEP_PAST_ACTIONS;
	private static int DATE_MAX_TO_ALLOW_DELETION;
	private static String CHARACTER_FORBIDDEN_COPY = "~";	
	private static String NAME_VERSION = "Version_";
	private static String NAME_ABSENT = "Deleted_";
	private static String DRIVE_TO_REVERSE;
	
	/*
	 * Getters & Setters
	 */
	public static final String getHEADER_CONF_INSTRUCTIONS() {
		return HEADER_CONF_INSTRUCTIONS;
	}
	public static final String getHEADER_DATE_LAST_RUN() {
		return HEADER_DATE_LAST_RUN;
	}
	public static final String getCHARACTER_FORBIDDEN_COPY() {
		return CHARACTER_FORBIDDEN_COPY;
	}
	public static final boolean getIS_DEBUG_AND_DONT_DO_ACTIONS() {
		return IS_DEBUG_AND_DONT_DO_ACTIONS;
	}
	public static final boolean getIS_FULL_COM() {
		return IS_FULL_COM;
	}
	public static final String getNAME_VERSION() {
		return NAME_VERSION;
	}
	public static final String getNAME_ABSENT() {
		return NAME_ABSENT;
	}
	public static final int getDATE_MAX_TO_KEEP_PAST_ACTIONS() {
		return DATE_MAX_TO_KEEP_PAST_ACTIONS;
	}
	public static final void setDATE_MAX_TO_KEEP_PAST_ACTIONS(int _sDATE_MAX_TO_KEEP_PAST_ACTIONS) {
		DATE_MAX_TO_KEEP_PAST_ACTIONS = _sDATE_MAX_TO_KEEP_PAST_ACTIONS;
	}
	public static final int getDATE_MAX_TO_ALLOW_DELETION() {
		return DATE_MAX_TO_ALLOW_DELETION;
	}
	public static final void setDATE_MAX_TO_ALLOW_DELETION(int _sDATE_MAX_TO_ALLOW_DELETION) {
		DATE_MAX_TO_ALLOW_DELETION = _sDATE_MAX_TO_ALLOW_DELETION;
	}
	public static final boolean getIS_REVERSE_ENGENEER() {
		return IS_REVERSE_ENGENEER;
	}
	public static final String getDRIVE_TO_REVERSE() {
		return DRIVE_TO_REVERSE;
	}
	public static final void setDRIVE_TO_REVERSE(String _sDRIVE_TO_REVERSE) {
		DRIVE_TO_REVERSE = _sDRIVE_TO_REVERSE;
	}
	public static final void setIS_REVERSE_ENGENEER(boolean _sIS_REVERSE_ENGENEER) {
		IS_REVERSE_ENGENEER = _sIS_REVERSE_ENGENEER;
	}
	public static final boolean getIS_DEBUG_AND_SKIP_DATE_CHECK() {
		return IS_DEBUG_AND_SKIP_DATE_CHECK;
	}
	
}
