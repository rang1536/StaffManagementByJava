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


@WebServlet("/addStaff")
public class AddStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
    StaffDao staffDao;  
    public AddStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 get!!");
		//학력 종교 기술 조회후 리턴받을 리스트타입 변수 선언
		List<School> schoolList = new ArrayList<School>();
		List<Skill> skillList = new ArrayList<Skill>();
		List<Religion> religionList = new ArrayList<Religion>();
		
		//StaffDao 객체생성
		staffDao = new StaffDao();
		
		// 학력 종교 기술 조회하는 메서드호출 후 리퀘스트 속성으로 세팅
		schoolList = staffDao.getSchool();
		skillList = staffDao.getSkill();
		religionList = staffDao.getReligion();
		
		//System.out.println(schoolList.size());
		
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("skillList", skillList);
		request.setAttribute("religionList", religionList);
		
		// 입력폼으로 조회값을 가지고 포워딩한다.
		request.getRequestDispatcher("/addStaff.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 add POST!!");
		request.setCharacterEncoding("UTF-8");
				
		// 폼 입력값들을 받는다.
		String name = request.getParameter("name");
		String sn = request.getParameter("snf")+request.getParameter("snl");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String[] skillNo = request.getParameterValues("skillNo");
		String graduateDay = request.getParameter("graduateDay");
		
		//도메인 객체에 값을 세팅한다
		Staff staff = new Staff();
		staff.setName(name);
		staff.setSn(sn);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
		staff.setGraduateDay(graduateDay);
		
		// DAO의 직원등록하는 메서드를 호출한다.
		int result = staffDao.addStaff(staff, skillNo);
		
		//System.out.println("입력결과 : "+result);
		//직원정보 등록후 홈화면으로 리다이렉트한다.
		response.sendRedirect(request.getContextPath()+"/staffHome.jsp");
		
	}

}
