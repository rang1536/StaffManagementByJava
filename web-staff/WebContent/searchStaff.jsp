<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<title>Simple Sidebar - Start Bootstrap Template</title>
	
	<!-- Bootstrap Core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="css/simple-sidebar.css" rel="stylesheet">
	
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>
 <div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="#">
                        HOME
                    </a>
                </li>
                <li>
                    <a href="<c:url value='/addStaff'/>">직원등록</a>
                </li>
                <li>
                    <a href="<c:url value='/listStaff'></c:url>">직원목록</a>
                </li>
                <li>
                    <a href="<c:url value='/searchStaff'></c:url>">직원검색</a>
                </li>
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1>STAFF 조회</h1>
					<div>
						<form action = "<c:url value='/searchStaff'/>" method="post">
							<table border="1">
								<tr>
									<th>이름</th>
									<th>
										<input type="text" name="name" id="name" />
									</th>
									<th>성별</th>
									<th>
										<input type="radio" name="gender" value="1"/>남
										<input type="radio" name="gender" value="2"/>여
									</th>
									<th>종교</th>
									<th>
										
										<select name="religion">
											<c:forEach  var="i" items="${religionList}">
												<option value="${i.no}">${i.name}</option>
										
											</c:forEach>
										</select>
										
									</th>
								</tr>
								<tr>
									<td>학력</td>
									<td>
										<c:forEach var="i" items="${schoolList}">
											<input type="radio" name="schoolNo" value="${i.no}"/>${i.graduate}
										</c:forEach>
									</td>
									<td>기술</td>
									<td colspan="3">
										<c:forEach var="i" items="${skillList}">
											<input type="checkbox" name="skillNo" value="${i.no}"/>${i.name}
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td>졸업일</td>
									<td colspan="5">
										<input type="date" name="gdStart" />--<input type="date" name="gdEnd" />
									</td>
								</tr>
								<tr>
									<td  colspan = "6">
										<input type="submit" value="등록" id="submit" />
										<input type="reset" value="다시작성" />
									</td>
								</tr>
							</table>
						</form>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-content-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Menu Toggle Script -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    </script>

</body>
</html>