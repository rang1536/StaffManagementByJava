package web.staff.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.staff.domain.Staff;


@WebServlet("/editStaff")
public class EditStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
    StaffDao staffDao;
	
    public EditStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 EDIT");
		
		//폼에서 넘긴 no값을 받아서 회원정보를 조회한 후 결과를 수정화면으로 포워드 한다
		int no = Integer.parseInt(request.getParameter("no"));
		//System.out.println(no);
		staffDao = new StaffDao();
		Staff staff = new Staff();
		
		staff = staffDao.getStaffByNo(no);
		
		// 주민번호랑 체크리스트들 세팅 및 가공 해줘야함.ㅠ
		
		// 조회결과 담아서 포워드한다.
		request.setAttribute("staff", staff);
		request.getRequestDispatcher("/editStaff.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
