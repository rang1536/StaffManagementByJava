package web.staff.domain;

import java.util.ArrayList;
import java.util.List;

public class Staff {
	private int 	no;
	private String 	name;
	private String 	sn;
	private String 	graduateDay;
	private int 	schoolNo;
	private int 	religionNo;
	private String 	religionName;
	private String 	schoolGraduate;
	private ArrayList<Skill> skillList;
	
	@Override
	public String toString() {
		return "Staff [no=" + no + ", name=" + name + ", sn=" + sn + ", graduateDay=" + graduateDay + ", schoolNo="
				+ schoolNo + ", religionNo=" + religionNo + ", religionName=" + religionName + ", schoolGraduate="
				+ schoolGraduate + ", skillList=" + skillList + "]";
	}
	public String getReligionName() {
		return religionName;
	}
	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}
	public String getSchoolGraduate() {
		return schoolGraduate;
	}
	public void setSchoolGraduate(String schoolGraduate) {
		this.schoolGraduate = schoolGraduate;
	}
	public ArrayList<Skill> getSkillList() {
		return skillList;
	}
	public void setSkillList(ArrayList<Skill> skillList2) {
		this.skillList = skillList2;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getGraduateDay() {
		return graduateDay;
	}
	public void setGraduateDay(String graduateDay) {
		this.graduateDay = graduateDay;
	}
	public int getSchoolNo() {
		return schoolNo;
	}
	public void setSchoolNo(int schoolNo) {
		this.schoolNo = schoolNo;
	}
	public int getReligionNo() {
		return religionNo;
	}
	public void setReligionNo(int religionNo) {
		this.religionNo = religionNo;
	}
		
}
