<%@ page 
		import="java.util.*,entity.User"
		contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>显示用户列表</h1>
	<%
	List<User> list = (List<User>)request.getAttribute("users");
	for(User u:list){
		System.out.println(u);
	}
	%>
</body>
</html>