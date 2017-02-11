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
	
	//staff �� �� ���ϱ�
	public int getStaffTotalCount(){
		try{
			conn=this.getConnection();
			String sql = "SELECT COUNT(*) FROM staff";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				rowCount = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}finally{
			this.close(conn,pstmt,rs );
		}
		return rowCount;
	}
	
	//����ó��
	public int deleteStaff(int no){
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "DELETE FROM staff WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rowCount = pstmt.executeUpdate();
			if(rowCount != 0){
				pstmt = conn.prepareStatement("SELECT * FROM staffskill WHERE staffno = ?");
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();
				if(rs.next()){
					pstmt = conn.prepareStatement("DELETE FROM staffskill WHERE staffno = ?");
					pstmt.setInt(1, no);
					rowCount = pstmt.executeUpdate();
					if(rowCount != 0){
						conn.commit();
					}
				}else{
					conn.commit();
				}
			}
	
		}catch(Exception e){
			try{conn.rollback();}catch(Exception ignore){};
			e.printStackTrace();
			
		}finally{
			this.close(conn,pstmt,rs );
		}
		return rowCount;
	}
	
	//����ó�� (Ʈ�����)
	public int editStaff(Staff staff,String[] skillNo){
		int result2 = 0;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "UPDATE staff SET name=?, sn=?, graduateday=?, schoolno=?, religionno=? WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, staff.getName());
			pstmt.setString(2, staff.getSn());
			pstmt.setString(3, staff.getGraduateDay());
			pstmt.setInt(4, staff.getSchoolNo());
			pstmt.setInt(5, staff.getReligionNo());
			pstmt.setInt(6, staff.getNo());
			rowCount = pstmt.executeUpdate();
			//System.out.println("�������� ? "+rowCount);
			
			// ���� �����ÿ� ������ ��ų�� ����� �Է¹��� ���� �ٽ� �Է����ش�. ������ ��ų�� �����ٸ� ���� ���� �Է¸� �ϰ� ��ų�� �־����� ������ �Է�ó���Ѵ�.
			if(rowCount != 0){
				pstmt = conn.prepareStatement("SELECT * FROM staffskill WHERE staffno = ?");
				pstmt.setInt(1, staff.getNo());
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					pstmt = conn.prepareStatement("DELETE FROM staffskill WHERE staffno=?");
					pstmt.setInt(1, staff.getNo());
					int result1 = pstmt.executeUpdate();
					//System.out.println("�������� ? "+result1);
					if(result1 != 0){
						for(int i=0 ; i < skillNo.length ; i++){
							pstmt = conn.prepareStatement("INSERT INTO staffSkill(staffno,skillno) VALUES(?,?)");
							pstmt.setInt(1, staff.getNo());
							pstmt.setInt(2, Integer.parseInt(skillNo[i]));
							result2 = pstmt.executeUpdate();
							//System.out.println("�Է¼��� ? "+result2);
						}
						if(result2 != 0){
							conn.commit();
						}
					}
				}else{
					for(int i=0 ; i < skillNo.length ; i++){
						pstmt = conn.prepareStatement("INSERT INTO staffSkill(staffno,skillno) VALUES(?,?)");
						pstmt.setInt(1, staff.getNo());
						pstmt.setInt(2, Integer.parseInt(skillNo[i]));
						result2 = pstmt.executeUpdate();
						//System.out.println("�Է¼��� ? "+result2);
					}
					if(result2 != 0){
						conn.commit();
					}
				}
			}
		
		}catch(Exception e){
			try{conn.rollback();}catch(Exception ignore){};
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return result2;
	}
	// no������ �ϳ��� ȸ�� ��ȸ
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
					//System.out.println("����"+staff);
				}
				//System.out.println("��ų������ : "+skillList.size());
				staff.setSkillList(skillList);
				//System.out.println("����"+staff);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return staff;
	}
	
	// ��ų�� ������ �˻��������� ��ȸ
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
	
	//��ü ������ȸ 
	public ArrayList<Staff> getAllStaff(int startRow, int pagePerRow){
		//System.out.println("h2!");
		ArrayList<Skill> skillList = null;
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		try{
			conn = this.getConnection();
			String sql = "SELECT staff.*, religion.name, school.graduate "+
					"FROM staff "+
					"LEFT JOIN religion "+
					"ON staff.religionno = religion.no "+
					"LEFT JOIN school ON staff.schoolno = school.no "+
					"LIMIT ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pagePerRow);
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
					//System.out.println("����"+staff);
				}
				//System.out.println("��ų������ : "+skillList.size());
				staff.setSkillList(skillList);
				staffList.add(staff);
				//System.out.println("����"+staff);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.close(conn,pstmt,rs);
			
		}
		return staffList;
	}
	
	/* �����Է� �� ����������̺� �Է�ó��. Ʈ�����ó��.
	 * staff����ϰ� getGeneratedKeys���Ͽ� staffskill���̺� Ű�������ϰ� �Է¹��� ��ų��ȣ �����Ͽ� 
	 * �Է�ó��. ConnectionAPI�� commit(),rollback()�� ����Ͽ� staffskill���̺� ��Ȯ��
	 * �������� ��ų�� ���õǰ� ��.
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
				if(skillNo != null){
					for(int i=0 ; i < skillNo.length ; i++){
						pstmt = conn.prepareStatement("insert into staffSkill(staffno,skillno) values(?,?)");
						pstmt.setInt(1, staffNo);
						pstmt.setInt(2, Integer.parseInt(skillNo[i]));
						pstmt.executeUpdate();
					}
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
	
	// ������ȸ
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
	// �����ȸ
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
	// �з���ȸ
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
	
	// ����̹� �ε��� Ŀ�ؼ� ����.
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(driverClassName);
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	// Ŀ�ؼ� ����
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
