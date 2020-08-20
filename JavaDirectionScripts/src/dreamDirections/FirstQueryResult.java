package dreamDirections;

public class FirstQueryResult {
	  String firstDown = "";
      String label1 = "";
      String secondDown = "";
      String label2 = "";
      String original = "";
      
	public FirstQueryResult(String firstDown, String label1, String secondDown,
			String label2, String original) {
		this.firstDown = firstDown;
		this.label1 = label1;
		this.secondDown = secondDown;
		this.label2 = label2;
		this.original = original;
	}
	
	public FirstQueryResult(String secondDown) {
		this.secondDown = secondDown;
	}

	public String getFirstDown() {
		return firstDown;
	}

	public void setFirstDown(String firstDown) {
		this.firstDown = firstDown;
	}

	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getSecondDown() {
		return secondDown;
	}

	public void setSecondDown(String secondDown) {
		this.secondDown = secondDown;
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
