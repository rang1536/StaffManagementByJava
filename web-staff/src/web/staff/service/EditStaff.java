package web.staff.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.staff.domain.Religion;
import web.staff.domain.School;
import web.staff.domain.Skill;
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
		List<School> schoolList = new ArrayList<School>();
		List<Skill> skillList = new ArrayList<Skill>();
		List<Religion> religionList = new ArrayList<Religion>();
		
		schoolList = staffDao.getSchool();
		skillList = staffDao.getSkill();
		religionList = staffDao.getReligion();
		staff = staffDao.getStaffByNo(no);
				
		//조회값중 주민번호 나눔.
		String snf = staff.getSn().substring(0, 6);
		String snl = staff.getSn().substring(6);
				
		// 조회결과 담아서 포워드한다.
		request.setAttribute("snf", snf);
		request.setAttribute("snl", snl);
		request.setAttribute("staff", staff);
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("skillList", skillList);
		request.setAttribute("religionList", religionList);
		
		request.getRequestDispatcher("/editStaff.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("edit POST!!");
		request.setCharacterEncoding("UTF-8");
		
		//폼 입력값들을 받는다
		int no = Integer.parseInt(request.getParameter("no"));
		String name = request.getParameter("name");
		String sn = request.getParameter("snf")+request.getParameter("snl");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String graduateDay = request.getParameter("graduateDay");
		String[] skillNo = request.getParameterValues("skillNo");
						
		//도메인 객체에 값을 세팅한다
		Staff staff = new Staff();
		staff.setNo(no);
		staff.setName(name);
		staff.setSn(sn);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
		staff.setGraduateDay(graduateDay);
		//System.out.println(staff);
		
		//수정처리하는 메서드를 호출한다
		int result = staffDao.editStaff(staff, skillNo);
		/*if(result != 0){
			System.out.println("성공");
		};*/
		
		response.sendRedirect(request.getContextPath()+"/staffHome.jsp");
	}

}
