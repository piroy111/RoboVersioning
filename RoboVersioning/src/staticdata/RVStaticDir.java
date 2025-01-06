package staticdata;

public class RVStaticDir {


	private static String MAIN = "ZZ_ROBO_VERSIONING_SOURCE/";
	private static String MAIN_COPY = MAIN + "Launch_copies_versioning/";
	private static String REVERSE = MAIN + "Launch_reverse_engineering/";
	private static String REVERSE_OUTPUT = MAIN + "Output_reverse_engineering/";
	private static String TARGET = "ZZ_ROBO_VERSIONING_COPIES/";	
	
	
	/*
	 * Getters & Setters
	 */
	public static final String getMAIN_COPY() {
		return MAIN_COPY;
	}
	public static final String getTARGET() {
		return TARGET;
	}
	public static final String getREVERSE() {
		return REVERSE;
	}
	public static final String getREVERSE_OUTPUT() {
		return REVERSE_OUTPUT;
	}


	
	
	
}
