<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script type="text/javascript">  
	var test = '${error}';
	if (test != "") {
		alert(test);
	}
</script> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>config sql connection</title>
</head>
<body>


    <form action="${pageContext.request.contextPath}/index/login.do" method="post">
        <table align="center">

            <tr>
                <td colspan="3" align="center"><font color="#ff0000" size="5sp">user login</font></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><input style="width: 150px;"  type="text" name="url"></td>
                <td><font size="1sp">in form of (URL:port/databaseName)</font></td>
            </tr>
            <tr>
                <td>User:</td>
                <td><input style="width: 150px;" type="text" name="user"></td>
                <td></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input style="width: 150px;" type="password" name="password"></td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3"><input type="submit" value="Connnect"></td>            
            </tr>   
        </table>
    </form>
</body>
</html>