package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;



import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;


public interface DoctorRepository {
	public Doctor findDoctorInfo(String username, String password);
	
	public Doctor get(int arztId);
}
