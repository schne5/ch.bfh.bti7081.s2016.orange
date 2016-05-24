package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

public class PatientRepositoryFactory {
	private static JpaPatientRepository repo;
	
	public static PatientRepository createJpaRepository(){
		if(repo ==null){
			repo=new  JpaPatientRepository();
		}
		return repo;
	}
}
