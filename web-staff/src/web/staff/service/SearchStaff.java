package web.staff.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


@WebServlet("/searchStaff")
public class SearchStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StaffDao staffDao;
	
    public SearchStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("h2 GET Search!!");
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
		request.getRequestDispatcher("/searchStaff.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("staff���� ��ȸȭ�� post��û");
		// ���ڵ� ����.
		request.setCharacterEncoding("UTF-8");
		
		//������ �Է��� ������ �޾Ƽ� ������ �Ҵ��Ѵ�.
		String name = request.getParameter("name");
		String[] gender = request.getParameterValues("gender");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String[] skillNo = request.getParameterValues("skillNo");
		String gdStart = request.getParameter("gdStart");
		String gdEnd = request.getParameter("gdEnd");
		
		Staff staff = new Staff();
		
		//�Է¹��� ������ StaffŸ�� ������ �����Ѵ�. ��ų�� �ֹι�ȣ�� ��ȸ ���� ���ϵǸ� ���ϴ°ɷ� ��. �������� �Ű������� �����Ѱ��ش�.(������.)
		staff.setName(name);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
				
		// ���ǿ� �����ϴ� row�� ��ȸ�ϴ� ������ �����Ѵ�.
		staffDao = new StaffDao();
		List<Staff> staffList = new ArrayList<Staff>();
		staffList = staffDao.searchStaff(gdStart, gdEnd, staff);
		
		//���������� ��ų���� ��ġ���� Ȯ�� �� ��ġ�ϴ� Staff�� ���� �迭�� �����
		List<Staff> list = new ArrayList<Staff>();
		
		// ������ �Էµ� ��ų���� ArrayList���·� �ٲ��Ŀ� for������ int������ �ٲ㼭 ��ȸ�� ��ų��� ���Ҽ� �ֵ��� �����Ѵ�.
		ArrayList<String> no = new ArrayList<String>(Arrays.asList(skillNo));
		ArrayList<Integer> skillList = new ArrayList<Integer>();
		
		for(int i=0 ; i<no.size(); i++){
			skillList.add(Integer.parseInt(no.get(i)));
		}
		
		//��ȸ������� ��ų��ȣ�� �̾Ƽ� ���� �迭�� �����
		ArrayList<Integer> skNo = new ArrayList<Integer>();

		//���� ���� �������� ���þ������� �б� 
		if(gender.length == 1){
			for(int i=0; i < staffList.size(); i++){
				if(gender[0].equals((staffList.get(i).getSn()).substring(6, 7))){
					staff = new Staff();
					staff = staffDao.getStaffByNo(staffList.get(i).getNo());
		//for���� ���� ��ȸ�� ����� ��ų��ȣ�� �߷��� skNo������ ��´�.
					for(int j=0; j<staff.getSkillList().size(); j++){
						skNo.add(staff.getSkillList().get(j).getNo());
					}
		// ������ �Է¹��� ���� ���� �迭�� ��ȸ������� ��ų��ȣ�� ���� �迭��  ���� ���Ͽ� ��ġ�ϸ� ���� ����� ���� list������ ��ȸ�� staff������ ����ش�
					if(skillList.equals(skNo)){
						list.add(staff);
					}
				}
			}
		}else{
			for(int i=0; i < staffList.size(); i++){
				staff = new Staff();
				staff = staffDao.getStaffByNo(staffList.get(i).getNo());
				
				for(int j=0; j<staff.getSkillList().size(); j++){
					skNo.add(staff.getSkillList().get(j).getNo());
				}
				if(skillList.equals(skNo)){
					list.add(staff);
				}
			}
		}
		request.setAttribute("staffList", list);
		request.getRequestDispatcher("listStaff.jsp").forward(request, response);
	}

}
