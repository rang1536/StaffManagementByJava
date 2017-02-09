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
		
		//������ �ѱ� no���� �޾Ƽ� ȸ�������� ��ȸ�� �� ����� ����ȭ������ ������ �Ѵ�
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
				
		//��ȸ���� �ֹι�ȣ ����.
		String snf = staff.getSn().substring(0, 6);
		String snl = staff.getSn().substring(6);
				
		// ��ȸ��� ��Ƽ� �������Ѵ�.
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
		
		//�� �Է°����� �޴´�
		int no = Integer.parseInt(request.getParameter("no"));
		String name = request.getParameter("name");
		String sn = request.getParameter("snf")+request.getParameter("snl");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String graduateDay = request.getParameter("graduateDay");
		String[] skillNo = request.getParameterValues("skillNo");
						
		//������ ��ü�� ���� �����Ѵ�
		Staff staff = new Staff();
		staff.setNo(no);
		staff.setName(name);
		staff.setSn(sn);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
		staff.setGraduateDay(graduateDay);
		//System.out.println(staff);
		
		//����ó���ϴ� �޼��带 ȣ���Ѵ�
		int result = staffDao.editStaff(staff, skillNo);
		/*if(result != 0){
			System.out.println("����");
		};*/
		
		response.sendRedirect(request.getContextPath()+"/staffHome.jsp");
	}

}
