package web.staff.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		staff = staffDao.getStaffByNo(no);
		
		// �ֹι�ȣ�� üũ����Ʈ�� ���� �� ���� �������.��
		
		// ��ȸ��� ��Ƽ� �������Ѵ�.
		request.setAttribute("staff", staff);
		request.getRequestDispatcher("/editStaff.jsp").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
