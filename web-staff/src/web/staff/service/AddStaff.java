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
		//�з� ���� ��� ��ȸ�� ���Ϲ��� ����ƮŸ�� ���� ����
		List<School> schoolList = new ArrayList<School>();
		List<Skill> skillList = new ArrayList<Skill>();
		List<Religion> religionList = new ArrayList<Religion>();
		
		//StaffDao ��ü����
		staffDao = new StaffDao();
		
		// �з� ���� ��� ��ȸ�ϴ� �޼���ȣ�� �� ������Ʈ �Ӽ����� ����
		schoolList = staffDao.getSchool();
		skillList = staffDao.getSkill();
		religionList = staffDao.getReligion();
		
		//System.out.println(schoolList.size());
		
		request.setAttribute("schoolList", schoolList);
		request.setAttribute("skillList", skillList);
		request.setAttribute("religionList", religionList);
		
		// �Է������� ��ȸ���� ������ �������Ѵ�.
		request.getRequestDispatcher("/addStaff.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 add POST!!");
		request.setCharacterEncoding("UTF-8");
				
		// �� �Է°����� �޴´�.
		String name = request.getParameter("name");
		String sn = request.getParameter("snf")+request.getParameter("snl");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String[] skillNo = request.getParameterValues("skillNo");
		String graduateDay = request.getParameter("graduateDay");
		
		//������ ��ü�� ���� �����Ѵ�
		Staff staff = new Staff();
		staff.setName(name);
		staff.setSn(sn);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
		staff.setGraduateDay(graduateDay);
		
		// DAO�� ��������ϴ� �޼��带 ȣ���Ѵ�.
		int result = staffDao.addStaff(staff, skillNo);
		
		//System.out.println("�Է°�� : "+result);
		//�������� ����� Ȩȭ������ �����̷�Ʈ�Ѵ�.
		response.sendRedirect(request.getContextPath()+"/staffHome.jsp");
		
	}

}
