package test.java.com.mediastep.autotools;

public interface IGuiAutoTool {

	void winWait(String title, String text, int timeout);

	void winActivate(String title);

	void ControlSetText(String title, String text, String control, String filePath);

	void controlClick(String title, String text, String controlID);

	boolean winExists(String title);

	String winGetHandle(String title);

	void send(String keys, boolean isRaw);

	void mouseWheel(String direction, int clicks);

	void winWait(String title);

	void winMove(String title, String text, int x, int y, int width, int height);

	void winClose(String title);

	String winGetTitle(String title);
	
	void releaseRenewNetwork(String status);
	
	
}
