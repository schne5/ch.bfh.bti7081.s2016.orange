package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;


public class DoctorRepositoryFactory {
	private static JpaDoctorRepository repo;

	public static DoctorRepository createJpaRepository(){
		if(repo==null){
			repo=new JpaDoctorRepository();
		}
		return repo;
	}
}
