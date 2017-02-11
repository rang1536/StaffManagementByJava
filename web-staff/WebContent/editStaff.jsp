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
	
		$(document).on('click','#editBtn',function(){
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
				$('#editForm').attr({action:"<c:url value='/editStaff'/>",method:"post"}).submit();
			}
		});
		$(document).on('click','#deleteBtn',function(){
			$('#deleteModel').modal()
			var staffNo = $('#staffNo').val().trim();
			console.log(staffNo);
			$('#no').html(staffNo);
			$('#staffNum').val(staffNo);
		});
		$(document).on('click','#delete',function(){
			var birthDay = $('#birthDay').val().trim();
			if(birthDay == ""){
				alert('생년월일 6자리를 입력해주세요');
				return ;
			}else if(parseInt(birthDay.substr(2,2)) > 12 || parseInt(birthDay.substr(2,2)) == 0 ){
				console.log(parseInt($('#birthDay').val().substr(2,2)));
				alert('올바른 월을 입력해주세요');
				return ;
			}else if(parseInt(birthDay.substr(4,2)) > 31 || parseInt(birthDay.substr(4,2)) == 0 ){
				console.log(parseInt($('#birthDay').val().substr(4,2)));
				alert('올바른 일을 입력해주세요');
				return ;
			}else if(!numberCheck(birthDay)){
				alert('주민번호는 숫자만 입력해주세요');
				return;
			}else{
				$('#deleteForm').attr({action:"<c:url value='/deleteStaff'/>",method:"post"}).submit();
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
                        <center><h1>STAFF 수정</h1></center>
					<div class="container">
						<form action = "<c:url value='/editStaff'/>" method="post" id="editForm" >
							<input type="hidden" name="no" id="staffNo" value="${staff.no}" readonly />
							<table class="table table-bordered">
								<tr>
									<th>이름</th>
									<th>
										<input type="text" name="name" id="name" value="${staff.name}" />
									</th>
									<th>주민번호</th>
									<th>
										<input type="text" name="snf" id="snf" value="${snf}" />-<input type="password" id="snl" name="snl" value="${snl}" />
									</th>
									<th>종교</th>
									<th>
										
										<select name="religion" id = "religion">
											<c:forEach  var="i" items="${religionList}">
												<option value="${i.no}">${i.name}</option>
											</c:forEach>
											<script>
												$('#religion').val('${staff.religionNo}').attr('selected','selected');
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
										<input type="date" id = "graduateDay" name="graduateDay" value="${staff.graduateDay}" />
									</td>
								</tr>
								<tr>
									<td  colspan = "6">
										<input type="button" value="수정" id="editBtn" />
										<input type="button" value="삭제" id="deleteBtn" />
									</td>
								</tr>
							</table>
						</form>
						<div class="modal fade" id="deleteModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="exampleModalLabel">New message</h4>
						      </div>
						      <form id="deleteForm">
							      <div class="modal-body">
							       
							          <div class="form-group">
							            <label for="no" class="control-label">Staff No</label>
							            <span id="no"></span>
							            <input type="hidden" id="staffNum" name="staffNum" />
							          </div>
							          <div class="form-group">
							            <label for="birthday" class="control-label">Birthday:</label>
							            <input type="text" class="form-control" name ="birthDay" id="birthDay" placeholder="생년월일을 6자리를 입력하세요  예) 901013">
							          </div>
							       
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							        <button type="button" class="btn btn-primary" id="delete">삭제</button>
							      </div>
						      </form>
						    </div>
						    
						  </div>
						</div>
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