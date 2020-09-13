package examples.testingil.characterization;

public class TestLogger implements Logger {
	
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public void append(char key, String display) {
		append("Pressed " + key + ", Display shows: " + display + "\n");
	}

	@Override
	public void append(String string) {
		sb.append(string + "\n");
	}

	@Override
	public String getAll() {
		return sb.toString();
	}
}
