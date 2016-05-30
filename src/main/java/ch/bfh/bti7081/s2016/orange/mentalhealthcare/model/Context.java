package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

public class Context {
	private State state;

	   public Context(){
	      state = null;
	   }

	   public void setState(State state){
	      this.state = state;		
	   }

	   public State getState(){
	      return state;
	   }
}
