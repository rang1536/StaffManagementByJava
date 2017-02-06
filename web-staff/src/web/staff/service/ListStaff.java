package web.staff.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.staff.domain.Staff;


@WebServlet("/listStaff")
public class ListStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
    StaffDao staffDao;
	
    public ListStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 List Get!!");
		//Dao에서 직원전체 목록 조회하는 메서드 호출 
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		staffDao = new StaffDao();
		staffList = staffDao.getAllStaff();
		System.out.println(staffList.get(0));
		
		request.setAttribute("staffList", staffList);
				
		request.getRequestDispatcher("/listStaff.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
