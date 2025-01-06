package staticdata;

public class RVStaticNameFile {

	
	private static String LIST_FOLDERS_TO_SAVE = "List_folders_to_save.csv";
	private static String LAST_DATE_RUN = "Last_date_the_program_was_run.csv";
	private static String ACTIONS = "Actions_performed_by_program.csv";
	private static String CONF_COPY = "Conf_copy.csv";
	private static String CONF_REVERSE = "Conf_reverse_engineer.csv";
	
	/*
	 * Getters & Setters
	 */
	public static final String getLIST_FOLDERS_TO_SAVE() {
		return LIST_FOLDERS_TO_SAVE;
	}
	public static final String getLAST_DATE_RUN() {
		return LAST_DATE_RUN;
	}
	public static final String getACTIONS() {
		return ACTIONS;
	}
	public static final String getCONF_COPY() {
		return CONF_COPY;
	}
	public static final String getCONF_REVERSE() {
		return CONF_REVERSE;
	}
	
	
	
}
