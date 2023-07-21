package controller;

import java.io.IOException;

import Entity.DeptPair;
import Services.DeptNumService;
import Services.NumDeptMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/show-departement-2")
public class ShowDept2 extends HttpServlet {
  private DeptNumService deptNumMapper = new NumDeptMapper();
  
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  
	  
	  
    String deptNum = request.getParameter("dept-num");
    if (deptNum == null) {
      deptNum = "";
    }
    
    
    deptNum = deptNum.trim();
    String deptName = deptNumMapper.findDept(deptNum);
    DeptPair deptInfo = new DeptPair(deptNum, deptName);
    HttpSession session = request.getSession();
    
    
    
    session.setAttribute("deptInfo2", deptInfo);
    
    String address;
    if (deptNum.isEmpty()) {
      address = "/WEB-INF/results/missing-num.jsp";
    } else if (deptName != null) {
      address = "/WEB-INF/results/show-departement-2.jsp";
    } else {
      address = "/WEB-INF/results/unknown-num-2.jsp";
    }

//    response.sendRedirect(address);
    RequestDispatcher dispatcher =
      request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
