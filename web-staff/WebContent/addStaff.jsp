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
		function numberCheck(num) { 
			for (i=0; i<num.length; i++) { 
			  var check = num.substr(i, 1); 
			  if (check < "0" || check > "9") return false; 
			} 
			return true; 
		}
		$(document).ready(function(){
			$(document).on('focus','#snf',function(){
				$(document).on('blur','#snf',function(){
					var birthDay = $('#snf').val().trim();
					var mm = parseInt($('#snf').val().trim().substr(2,2));
					console.log(dd);
					var dd = parseInt($('#snf').val().trim().substr(4,2)); 
					if(!numberCheck(birthDay)){
						alert('주민번호는 숫자만 입력해주세요');
						return;
					}else if(birthDay.length != 6){
						alert('주민번호 앞자리는 6자리로 입력하세요');
						return;
					}else if(mm >12 || mm < 1){
						alert('올바른 월을 입력해주세요');
						return ;
					}else if(dd > 31 || dd == 0 ){
						alert('올바른 날짜를 입력해주세요');
						return ;
					}
				});
			});
		
			$(document).on('click','#addBtn',function(){
				if($('#name').val().trim() == ''){
					alert('이름을 입력하세요');
					return;
				}else if($('#religion > option:selected').val() == '::종교::'){
					alert('종교를 골라주세요');
					return;
				}else if($('input:radio[name="schoolNo"]:checked').length < 1){
					alert('학력을 골라주세요');
					return;
				}else if($('input:checkbox[name="skillNo"]:checked').length < 1){
					alert('1가지 이상의 기술을 골라주세요');
					return;
				}else if($('#graduateDay').val() == ""){
					alert('졸업일을 입력해 주세요');
					return;
				}else if($('#snl').val().trim().length != 7){
					alert('주민번호 뒷자리는 7자리로 입력하세요');
					return;
				}else if(!numberCheck($('#snl').val().trim())){
					alert('주민번호는 숫자만 입력해주세요');
					return;
				}else{
					$('#addForm').attr({action:"<c:url value='/addStaff'/>",method:"post"}).submit();
				}
			});
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
                        <center><h1>STAFF 등록</h1></center>
					<div class="container">
						<form action = "<c:url value='/addStaff'/>" method="post" id="addForm" >
							<table class="table table-bordered" >
								<tr>
									<th>이름</th>
									<th>
										<input type="text" name="name" id="name" />
									</th>
									<th>주민번호</th>
									<th>
										<input type="text" name="snf" id="snf" />-<input type="text" name="snl" id="snl" />
									</th>
									<th>종교</th>
									<th>
										
										<select name="religion" id="religion">
											<option>::종교::</option>
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
										<input type="date" name="graduateDay" id="graduateDay" />
									</td>
								</tr>
								<tr>
								
									<td  colspan = "6">
										<input type="button" value="등록" id="addBtn" />
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