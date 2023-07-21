package controller;

import java.io.IOException;

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

@WebServlet("/CreateDeptPair")
public class CreateDeptPair extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateDeptPair() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deptNumString = request.getParameter("dept-num");

        // Convertir la valeur de deptNum en Long
        Long deptNum = null;
        try {
            deptNum = Long.parseLong(deptNumString);
        } catch (NumberFormatException e) {
            // Gérer l'erreur de conversion si nécessaire
            // Par exemple, rediriger l'utilisateur vers une page d'erreur
            e.printStackTrace();
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            // Recherche de l'entité à supprimer dans la base de données en utilisant le Long deptNum
            DeptPair deptPair = em.find(DeptPair.class, deptNum);

            if (deptPair != null) {
                // Suppression de l'entité en utilisant remove()
                em.remove(deptPair);

                System.out.println("Data removed from DB");
                System.out.println("Num de département = " + deptNum);
            } else {
                System.out.println("Data not found in the DB. Nothing to remove.");
                System.out.println("Num de département = " + deptNum);
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