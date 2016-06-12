package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

public enum Takings {
	
	PER_DAY("Per Day"),PER_WEEK("Per Week"),PER_MONTH("Per Month");
	private String text="";
	
	Takings(String text){
		this.text=text;
	}
	
	public String getText() {
		return text;
	}

}
