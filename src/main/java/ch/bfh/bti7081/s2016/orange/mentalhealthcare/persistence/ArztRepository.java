package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;



import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;


public interface ArztRepository {
	public Arzt findArztInfo(String username, String password);
	public Arzt get(int arztId);

}
