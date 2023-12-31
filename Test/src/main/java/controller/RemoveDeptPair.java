package controller;

import java.io.IOException;
import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import Entity.DeptPair;
import Services.DeptNumService;
import Services.NumDeptMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveDeptPair
 */
@WebServlet("/RemoveDeptPair")
public class RemoveDeptPair extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RemoveDeptPair() {
        // TODO Auto-generated constructor stub
    }

  	private DeptNumService deptNumMapper = new NumDeptMapper();
    
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           // retrieving dept-num name value send by POST form request
           String deptNum = request.getParameter("dept-num");
           
           // retrieving deptName from deptArray
           NumDeptMapper deptMapper = new NumDeptMapper();
           String associatedDeptName = deptMapper.findDept(deptNum);
           System.out.println("deptMapperResult = " + associatedDeptName);
           
           // setting deptPair values and passing data to result.jsp view template
           System.out.println("Num de département = " + deptNum);
           DeptPair deptPair = new DeptPair(deptNum, associatedDeptName);
           request.setAttribute("key", deptPair);
           
           // managing errors
           String address = "";
           if (deptNum == "") {
               address = "/WEB-INF/results/missing-num.jsp";
           } else if (associatedDeptName != null) {
               address = "/WEB-INF/results/show-departement-1.jsp";
           } else {
               address = "/WEB-INF/results/unknown-num-1.jsp";
           }
           
           request.getRequestDispatcher(address).forward(request, response);
       }
   	
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   	  String deptNumString = request.getParameter("dept-num");
      String deptName = request.getParameter("dept-nom");

      EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
      EntityManager em = emf.createEntityManager();
      EntityTransaction txn = em.getTransaction();

      try {
          txn.begin();

          // Recherche de l'entité à supprimer dans la base de données en utilisant le nom et le numéro
          javax.persistence.Query query = em.createQuery("SELECT d FROM DeptPair d WHERE d.deptNum = :deptNum AND d.deptName = :deptName");
          query.setParameter("deptNum", deptNumString);
          query.setParameter("deptName", deptName);
          List<DeptPair> deptPairs = query.getResultList();

          if (!deptPairs.isEmpty()) {
              // Suppression de l'entité en utilisant remove() pour chaque élément trouvé (il peut y en avoir plusieurs avec le même nom et numéro)
              for (DeptPair deptPair : deptPairs) {
                  em.remove(deptPair);
              }

              System.out.println("Data removed from DB");
              System.out.println("Num de département = " + deptNumString);
              System.out.println("Nom de département = " + deptName);
          } else {
              System.out.println("Data not found in the DB. Nothing to remove.");
              System.out.println("Num de département = " + deptNumString);
              System.out.println("Nom de département = " + deptName);
          }

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

}}
