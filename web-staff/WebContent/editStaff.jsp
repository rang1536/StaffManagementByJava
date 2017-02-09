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
	<script
	src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
	integrity="sha256-/SIrNqv8h6QGKDuNoLGA4iret+kyesCkHGzVUUV0shc="
	crossorigin="anonymous">
	</script>
	<script>
		$(document).on('click','#deleteBtn',function(){
			$('#editForm').attr({action:"<c:url value='/deleteStaff'/>",method:"post"}).submit();
		});
	</script>
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
						<form action = "<c:url value='/editStaff'/>" method="post" id="editForm" >
							<input type="hidden" name="no" value="${staff.no}" />
							<table border="1">
								<tr>
									<th>이름</th>
									<th>
										<input type="text" name="name" id="name" value="${staff.name}" />
									</th>
									<th>주민번호</th>
									<th>
										<input type="text" name="snf" value="${snf}" />-<input type="password" name="snl" value="${snl}" />
									</th>
									<th>종교</th>
									<th>
										
										<select name="religion" id = "rel">
											<c:forEach  var="i" items="${religionList}">
												<option value="${i.no}">${i.name}</option>
											</c:forEach>
											<script>
												$('#rel').val('${staff.religionNo}').attr('selected','selected');
											</script>
										</select>
										
									</th>
								</tr>
								<tr>
									<td>학력</td>
									<td>
										<c:forEach var="i" items="${schoolList}">
											<input type="radio" name="schoolNo" value="${i.no}"/>${i.graduate}
										</c:forEach>
										<script>
											$('input:radio[name="schoolNo"]:radio[value="${staff.schoolNo}"]').prop("checked",true);
										</script>
									</td>
									<td>기술</td>
									<td colspan="3">
										<c:forEach var="i" items="${skillList}">
											<input type="checkbox" id ="skillNo" name="skillNo" value="${i.no}"/>${i.name}
										</c:forEach>
										<c:forEach var="j" items="${staff.skillList}">
										  	<script>
												$('input:checkbox[value="${j.no}"]').prop("checked",true);
											</script>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td>졸업일</td>
									<td colspan="5">
										<input type="date" name="graduateDay" value="${staff.graduateDay}" />
									</td>
								</tr>
								<tr>
									<td  colspan = "6">
										<input type="submit" value="수정" />
										<input type="button" value="삭제" id="deleteBtn" />
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