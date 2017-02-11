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
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		staffDao = new StaffDao();
		
		int nowPage = 1;
		if(request.getParameter("nowPage") != null ){
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		int totalRowCount = staffDao.getStaffTotalCount();
		int pagePerRow = 5;
		int startRow = (nowPage-1)*pagePerRow;
		int lastPage = totalRowCount/pagePerRow;
		if(totalRowCount%pagePerRow !=0){
			lastPage++;
		}
		
		//Dao에서 직원전체 목록 조회하는 메서드 호출 
		staffList = staffDao.getAllStaff(startRow, pagePerRow);
		//System.out.println(staffList.size());
		
		request.setAttribute("staffList", staffList);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startRow", startRow);
		request.setAttribute("nowPage", nowPage);
			
		request.getRequestDispatcher("/listStaff.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
