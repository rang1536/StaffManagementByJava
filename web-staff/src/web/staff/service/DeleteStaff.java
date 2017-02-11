package web.staff.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.staff.domain.Staff;


@WebServlet("/deleteStaff")
public class DeleteStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public DeleteStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 delete");
		request.setCharacterEncoding("UTF-8");
		
		//팝업창에서 입력받은 생년월일과 staff no값을 받는다
		int no = Integer.parseInt(request.getParameter("staffNum"));
		//System.out.println(no);
		String birth = request.getParameter("birthDay");
		//System.out.println(birth);
		
		// 받은 no값으로 직원정보를 조회한다
		StaffDao staffDao = new StaffDao();
		Staff staff = new Staff();
		staff = staffDao.getStaffByNo(no);
		
		//System.out.println(staff.getSn().substring(0, 6));
		//조회값의 주민번호 앞 6자리와 일치여부를 확인한다.
		int result = 0;
		
		if(birth.equals(staff.getSn().substring(0, 6))){
			result = staffDao.deleteStaff(no);
			//System.out.println("수정처리 : "+result);
			response.sendRedirect(request.getContextPath()+"/staffHome.jsp");
		}else{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('생년월일이 일치하지 않습니다');");
			out.println("history.back();");
			out.println("</script>");
		}
	}

}
