package web.staff.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import web.staff.domain.Religion;
import web.staff.domain.School;
import web.staff.domain.Skill;
import web.staff.domain.Staff;

public class StaffDao {
	private final String driverClassName = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/webstaff?useUnicode=true&characterEncoding=UTF-8";
	private final String username="root";
	private final String password = "java0000";
	Connection conn;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	ResultSet rs;
	ResultSet rs2;
	
	int rowCount;
	Staff staff;
	Skill skill;
	School school;
	Religion religion;
	List<Religion> religionList;
	List<School> schoolList;
	List<Skill> skillList;
	List<Staff> staffList;
	
	// no값으로 하나의 회원 조회
	public Staff getStaffByNo(int no){
		try{
			conn = this.getConnection();
			String sql = "SELECT staff.*, religion.name, school.graduate "+
					"FROM staff "+
					"LEFT JOIN religion "+
					"ON staff.religionno = religion.no "+
					"LEFT JOIN school ON staff.schoolno = school.no "+
					"WHERE staff.no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			String sql2 = "SELECT skill.* FROM skill WHERE skill.no IN (SELECT skillno FROM staffskill WHERE staffno = ?)";
			if(rs.next()){
				staff = new Staff();
				int staffNo = rs.getInt("no");
				staff.setNo(staffNo);
				staff.setName(rs.getString("name"));
				staff.setReligionNo(rs.getInt("religionno"));
				staff.setReligionName(rs.getString("religion.name"));
				staff.setSchoolGraduate(rs.getString("school.graduate"));
				staff.setSchoolNo(rs.getInt("schoolno"));
				staff.setGraduateDay(rs.getString("graduateday"));
				staff.setSn(rs.getString("sn"));
				ArrayList<Skill> skillList = new ArrayList<Skill>();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, staffNo);
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					skill = new Skill();
					skill.setNo(rs2.getInt("no"));
					skill.setName(rs2.getString("name"));
					skillList.add(skill);
					//System.out.println("뭐임"+staff);
				}
				System.out.println("스킬사이즈 : "+skillList.size());
				staff.setSkillList(skillList);
				//System.out.println("뭐임"+staff);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return staff;
	}
	
	// 스킬을 제외한 검색조건으로 조회
	public List<Staff> searchStaff(String gdStart, String gdEnd, Staff staff){
		try{
			conn=this.getConnection();
			String sql = "SELECT * "+
					"FROM staff "+
					"WHERE name like ? "+ 
					"and graduateday > ? "+ 
					"and graduateday < ? "+
					"and schoolno = ? "+
					"and religionno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%'+staff.getName()+'%');
			pstmt.setString(2, gdStart);
			pstmt.setString(3, gdEnd);
			pstmt.setInt(4, staff.getSchoolNo());
			pstmt.setInt(5, staff.getReligionNo());
			rs = pstmt.executeQuery();
			staffList = new ArrayList<Staff>();
			while(rs.next()){
				staff = new Staff();
				staff.setNo(rs.getInt("no"));
				staff.setName(rs.getString("name"));
				staff.setGraduateDay(rs.getString("graduateday"));
				staff.setSchoolNo(rs.getInt("schoolno"));
				staff.setReligionNo(rs.getInt("religionno"));
				staff.setSn(rs.getString("sn"));
				staffList.add(staff);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			this.close(conn,pstmt,rs );
		}
		return staffList;
	}
	
	//전체 직원조회 
	public ArrayList<Staff> getAllStaff(){
		//System.out.println("h2!");
		ArrayList<Skill> skillList = null;
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		try{
			conn = this.getConnection();
			String sql = "SELECT staff.*, religion.name, school.graduate "+
					"FROM staff "+
					"LEFT JOIN religion "+
					"ON staff.religionno = religion.no "+
					"LEFT JOIN school ON staff.schoolno = school.no";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String sql2 = "SELECT skill.* FROM skill WHERE skill.no IN (SELECT skillno FROM staffskill WHERE staffno = ?)";
 			//System.out.println(rs.next());
			while(rs.next()){
				staff = new Staff();
				int staffNo = rs.getInt("no");
				staff.setNo(staffNo);
				staff.setName(rs.getString("name"));
				staff.setReligionNo(rs.getInt("religionno"));
				staff.setReligionName(rs.getString("religion.name"));
				staff.setSchoolGraduate(rs.getString("school.graduate"));
				staff.setSchoolNo(rs.getInt("schoolno"));
				staff.setGraduateDay(rs.getString("graduateday"));
				staff.setSn(rs.getString("sn"));
				skillList = new ArrayList<Skill>();
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, staffNo);
				rs2 = pstmt2.executeQuery();
				while(rs2.next()){
					skill = new Skill();
					skill.setNo(rs2.getInt("no"));
					skill.setName(rs2.getString("name"));
					skillList.add(skill);
					//System.out.println("뭐임"+staff);
				}
				//System.out.println("스킬사이즈 : "+skillList.size());
				staff.setSkillList(skillList);
				staffList.add(staff);
				//System.out.println("뭐임"+staff);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return staffList;
	}
	
	/* 직원입력 후 직원기술테이블도 입력처리. 트랜잭션처리.
	 * staff등록하고 getGeneratedKeys통하여 staffskill태이블에 키값세팅하고 입력받은 스킬번호 세팅하여 
	 * 입력처리. ConnectionAPI의 commit(),rollback()을 사용하여 staffskill태이블에 정확히
	 * 스태프별 스킬이 세팅되게 함.
	 * */
	public int addStaff(Staff staff,String[] skillNo){
		try{
			conn=this.getConnection();
			int staffNo;
			conn.setAutoCommit(false);
			String sql = "insert into staff(name,sn,graduateday,schoolno,religionno) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, staff.getName());
			pstmt.setString(2, staff.getSn());
			pstmt.setString(3, staff.getGraduateDay());
			pstmt.setInt(4, staff.getSchoolNo());
			pstmt.setInt(5, staff.getReligionNo());
			rowCount = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				staffNo = rs.getInt(1);
				for(int i=0 ; i < skillNo.length ; i++){
					pstmt = conn.prepareStatement("insert into staffSkill(staffno,skillno) values(?,?)");
					pstmt.setInt(1, staffNo);
					pstmt.setInt(2, Integer.parseInt(skillNo[i]));
					pstmt.executeUpdate();
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{conn.rollback();}catch(Exception ignore){};
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return rowCount;
	}
	
	// 종교조회
	public List<Religion> getReligion(){
		try{
			conn=this.getConnection();
			String sql = "SELECT * FROM religion";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			religionList = new ArrayList<Religion>();
			while(rs.next()){
				religion = new Religion();
				religion.setNo(rs.getInt("no"));
				religion.setName(rs.getString("name"));
				//System.out.println(religion);
				religionList.add(religion);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}finally{
			this.close(conn,pstmt,rs );
		}
		return religionList;
	}
	// 기술조회
	public List<Skill> getSkill(){
		try{
			conn=this.getConnection();
			String sql = "SELECT * FROM skill";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			skillList = new ArrayList<Skill>();
			while(rs.next()){
				skill = new Skill();
				skill.setNo(rs.getInt("no"));
				skill.setName(rs.getString("name"));
				//System.out.println(skill);
				skillList.add(skill);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}finally{
			this.close(conn,pstmt,rs );
		}
		return skillList;
	}
	// 학력조회
	public List<School> getSchool(){
		try{
			conn=this.getConnection();
			String sql = "SELECT * FROM school";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			schoolList = new ArrayList<School>();
			while(rs.next()){
				school = new School();
				school.setNo(rs.getInt("no"));
				school.setGraduate(rs.getString("graduate"));
				//System.out.println(school);
				schoolList.add(school);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}finally{
			this.close(conn,pstmt,rs );
		}
		return schoolList;
	}
	
	// 드라이버 로딩및 커넥션 생성.
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(driverClassName);
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	// 커넥션 종료
	private void close(Connection conn, Statement pstmt, ResultSet rs){
		if(rs != null){
			try{
				rs.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try{
				pstmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try{
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
}
