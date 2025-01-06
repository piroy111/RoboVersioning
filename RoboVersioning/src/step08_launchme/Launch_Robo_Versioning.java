package step08_launchme;

import staticdata.RVStaticConst;

public class Launch_Robo_Versioning {

	public static void main(String[] _sArgs) {
		RVStaticConst.setIS_REVERSE_ENGENEER(false);
		new RVManager().run();
	}
	
}
