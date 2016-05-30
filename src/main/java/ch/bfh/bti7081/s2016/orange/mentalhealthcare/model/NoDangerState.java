package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

public class NoDangerState implements State{

	@Override
	public void changeState(Context context) {
		 context.setState(this);		
	}
	
	public String toString(){
	      return "No Danger";
	   }

}
