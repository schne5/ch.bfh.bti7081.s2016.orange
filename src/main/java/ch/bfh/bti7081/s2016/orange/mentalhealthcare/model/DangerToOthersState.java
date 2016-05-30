package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

public class DangerToOthersState implements State {

	@Override
	public void changeState(Context context) {
		 context.setState(this);	
	}
	
	public String toString(){
	      return "Danger to others";
	   }

}
