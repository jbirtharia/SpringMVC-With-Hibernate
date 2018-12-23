<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Registration Form</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="authheader.jsp" %>

		<div class="well lead">User Registration Form</div>
	 	<form:form method="POST" modelAttribute="employee" class="form-horizontal">
			<form:input type="hidden" path="empid" id="empid" readonly="readonly"/>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="name">Name</label>
					<div class="col-md-7">
						<form:input type="text" path="name" id="name" class="form-control input-sm" readonly="readonly"/>
						<div class="has-error">
							<form:errors path="name" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="username">User Name</label>
					<div class="col-md-7">
						<form:input type="text" path="username" id="username" class="form-control input-sm" readonly="readonly"/>
						<div class="has-error">
							<form:errors path="username" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
	
	<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="pass">Password</label>
					<div class="col-md-7">
						<form:input type="text" path="pass" id="pass" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="pass" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${mssg != null}">
								<div class="alert alert-danger" >
									<p>${mssg}.</p>
								</div>
							</c:if>
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="confirmPass">Confirm Password</label>
					<div class="col-md-7">
						<form:input type="text" path="confirmPass" id="confirmPass" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="confirmPass" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="phoneno">Phone Number</label>
					<div class="col-md-7">
								<form:input type="text" path="phoneno" id="phoneno" class="form-control input-sm" />
								<div class="has-error" >
									<form:errors path="phoneno" class="help-inline"/>
								</div>
						<br><input type="button" value="Send OTP" class="btn btn-primary btn-sm" onclick="message()">
						<br><br>Enter otp <input type="text" id="otp">
						<input type="button" value="Verify OTP" class="btn btn-primary btn-sm" onclick="validate()">
						<div id="mydiv"></div>
						<c:if test="${otp != null}">
								<div class="alert alert-danger" >
									<p>${otp}.</p>
								</div>
							</c:if>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="sal">Salary</label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="sal" id="sal" class="form-control input-sm" />
							</c:when>
							<c:otherwise>
								<form:input type="text" path="sal" id="sal" class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="sal" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			
			
	 		<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="role">Role</label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:select path="role" id="role" class="form-control input-sm">
									<form:option value="ADMIN" label="ADMIN"/>
									<form:option value="USER" label="USER"/>
									<form:option value="DBA" label="DBA" />
								</form:select>

							</c:when>
							<c:otherwise>
							<form:select path="role" id="role" class="form-control input-sm" >
									<form:option value="ADMIN" label="ADMIN"/>
									<form:option value="USER" label="USER"/>
									<form:option value="DBA" label="DBA" />
							</form:select>
								<div class="has-error">
									<form:errors path="role" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
	 
	
	
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>

<script type="text/javascript">
	
	function message() {

		var div = "";

		var id = $("#phoneno").val();
		$
				.ajax({
					type : "GET",
					url : getContextPath() + "/message",
					data : "id=" + id,
					success : function(response) {
						if (response == "true") {

							div += "<p>Message has been sent</p>";
							$("#mydiv").append(div);
						} else {
							div += "<p>Message has not been sent. Please try again latter</p>";
							$("#mydiv").html(div);
						}
					},
					error : function(e) {
						div += "<p>Message has not been sent. Please try again latter</p>";
						$("#mydiv").html(div);

					}
				});

	}

	function validate() {

		var div = "";

		var id = $("#otp").val();
		$
				.ajax({
					type : "GET",
					url : getContextPath() + "/validate",
					data : "id=" + id,
					success : function(response) {

						if (response == "true") {
							div += "<p>OTP is Verified</p>";
							$("#mydiv").html(div);

						} else {
							div += "<p>OTP is not correct. Please try again latter</p>";
							$("#mydiv").html(div);
						}

					}
				});

	}

	function getContextPath() {
		return window.location.pathname.substring(0, window.location.pathname
				.indexOf("/", 2));
	}
</script>

</html>