package examples.testingil.characterization;

public class TestLogger {
	
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public String toString() {
		return sb.toString();
	}

	public void append(char key, String display) {
		append("Pressed " + key + ", Display shows: " + display + "\n");
	}

	public void append(String string) {
		sb.append(string);
	}
}
