package dreamDirections;

public class FirstQueryUpResult {
	String firstUp = "";
    String label1 = "";
    String secondUp = "";
    String label2 = "";
    String original = "";
    
	public FirstQueryUpResult(String firstUp, String label1, String secondUp,
			String label2, String original) {
		this.firstUp = firstUp;
		this.label1 = label1;
		this.secondUp = secondUp;
		this.label2 = label2;
		this.original = original;
	}
	
	public FirstQueryUpResult(String secondUp) {
		this.secondUp = secondUp;
	}

	public String getFirstUp() {
		return firstUp;
	}

	public void setFirstUp(String firstUp) {
		this.firstUp = firstUp;
	}

	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getSecondUp() {
		return secondUp;
	}

	public void setSecondUp(String secondUp) {
		this.secondUp = secondUp;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
}
