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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
                        <center><h1>STAFF LIST</h1></center>
						<div class="container">
							<table class="table table-striped">
								<thead>
							      <tr>
							        <th>NO</th>
							        <th>NAME</th>
							        <th>GRADUATE</th>
							        <th>GRADUATEDAY</th>
							        <th>RELIGION</th>
							        <th>SKILL</th>
							      </tr>
							    </thead>
								<tbody>
								  <c:forEach var="i" items="${staffList}">
								    <tr>
									  <td>${i.no}</td>
									  <td><a href="<c:url value='/editStaff?no=${i.no}'/>">${i.name}</a></td>
									  <td>${i.schoolGraduate}</td>
									  <td>${i.graduateDay}</td>
									  <td>${i.religionName}</td>
									  <td>
									  	<c:forEach var="j" items="${i.skillList}">
									  		${j.name}
									  	</c:forEach>
									  </td>
									</tr>
								  </c:forEach>
								</tbody>
							</table>
						</div>
	                </div>
                </div>
            </div>
            <div align = "center">
            		<c:if test="${nowPage>1}">
						<a href = "<c:url value='/listStaff?nowPage=${nowPage-1}'/>">
						<span class="glyphicon glyphicon-hand-left" aria-hidden="true"></span>이전
						</a>
					</c:if>
					<c:if test="${nowPage < lastPage}">
						<a href = "<c:url value='/listStaff?nowPage=${nowPage+1}'/>">
						<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>다음
						</a>
					</c:if>
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