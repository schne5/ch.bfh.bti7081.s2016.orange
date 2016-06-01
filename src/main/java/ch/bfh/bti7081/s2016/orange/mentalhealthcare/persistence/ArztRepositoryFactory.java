package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;


public class ArztRepositoryFactory {
	private static JpaArztRepository repo;

	public static ArztRepository createJpaRepository(){
		if(repo==null){
			repo=new JpaArztRepository();
		}
		return repo;
	}
}
