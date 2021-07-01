package examples.testingil.characterization;

public class TestLogger {
	
	private StringBuilder sb = new StringBuilder();
	
	public void append(char key, String display) {
		append("Pressed " + key + ", Display shows: " + display);
	}

	public void append(String string) {
		sb.append(string + "\n");
	}

	public String getAll() {
		return sb.toString();
	}
}
