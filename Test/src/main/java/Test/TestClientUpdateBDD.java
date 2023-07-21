package Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import Entity.DeptPair;

public class TestClientUpdateBDD {
	
	   public static void main(String[] args) {
	        DeptPair deptPair = new DeptPair("123", "Test");

	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
	        EntityManager em = emf.createEntityManager();
	        EntityTransaction txn = em.getTransaction();
	        try {
	            txn.begin();
	        
	            em.persist(deptPair);

	            txn.commit();
	        } catch (Exception e) {
	            if (txn != null) {
	                txn.rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            if (em != null) {
	                em.close();
	            }
	            if (emf != null) {
	                emf.close();
	            }
	        }
	    }
}
