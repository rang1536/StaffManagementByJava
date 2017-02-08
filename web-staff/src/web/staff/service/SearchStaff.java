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
		request.getRequestDispatcher("/searchStaff.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("staff정보 조회화면 post요청");
		// 인코딩 설정.
		request.setCharacterEncoding("UTF-8");
		
		//폼에서 입력한 값들을 받아서 변수에 할당한다.
		String name = request.getParameter("name");
		String[] gender = request.getParameterValues("gender");
		int religionNo = Integer.parseInt(request.getParameter("religion"));
		int schoolNo = Integer.parseInt(request.getParameter("schoolNo"));
		String[] skillNo = request.getParameterValues("skillNo");
		String gdStart = request.getParameter("gdStart");
		String gdEnd = request.getParameter("gdEnd");
		
		Staff staff = new Staff();
		
		//입력받은 값들을 Staff타입 변수에 세팅한다. 스킬과 주민번호는 조회 값이 리턴되면 비교하는걸로 함. 졸업일은 매개변수로 직접넘겨준다.(범위라서.)
		staff.setName(name);
		staff.setReligionNo(religionNo);
		staff.setSchoolNo(schoolNo);
				
		// 조건에 부합하는 row를 조회하는 쿼리를 실행한다.
		staffDao = new StaffDao();
		List<Staff> staffList = new ArrayList<Staff>();
		staffList = staffDao.searchStaff(gdStart, gdEnd, staff);
		
		//최종적으로 스킬까지 일치여부 확인 후 일치하는 Staff를 담을 배열을 만든다
		List<Staff> list = new ArrayList<Staff>();
		
		// 폼에서 입력된 스킬값을 ArrayList형태로 바꾼후에 for문으로 int형으로 바꿔서 조회된 스킬들과 비교할수 있도록 세팅한다.
		ArrayList<String> no = new ArrayList<String>(Arrays.asList(skillNo));
		ArrayList<Integer> skillList = new ArrayList<Integer>();
		
		for(int i=0 ; i<no.size(); i++){
			skillList.add(Integer.parseInt(no.get(i)));
		}
		
		//조회결과에서 스킬번호만 뽑아서 담을 배열을 만든다
		ArrayList<Integer> skNo = new ArrayList<Integer>();

		//성별 선택 했을때와 선택안했을때 분기 
		if(gender.length == 1){
			for(int i=0; i < staffList.size(); i++){
				if(gender[0].equals((staffList.get(i).getSn()).substring(6, 7))){
					staff = new Staff();
					staff = staffDao.getStaffByNo(staffList.get(i).getNo());
		//for문을 돌려 조회된 결과의 스킬번호만 추려서 skNo변수에 담는다.
					for(int j=0; j<staff.getSkillList().size(); j++){
						skNo.add(staff.getSkillList().get(j).getNo());
					}
		// 폼에서 입력받은 값을 담은 배열과 조회결과에서 스킬번호를 담은 배열간  값을 비교하여 일치하면 최종 결과를 담을 list변수에 조회된 staff정보를 담아준다
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
