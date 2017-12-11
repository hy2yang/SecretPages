<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>config sql connection</title>
</head>
<body>

    <form action="login.do" method="post">
        <table align="center">

            <tr>
                <td colspan="2" align="center"><font color="#ff0000" size="5sp">user login</font></td>
            </tr>
            <tr>
                <td>URL:port/databaseName</td>
                <td><input type="text" name="url"></td>
            </tr>
            <tr>
                <td>user:</td>
                <td><input type="text" name="user"></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td align="center" colspan="2"><input type="submit" value="提交"></td>            
            </tr>   
        </table>
    </form>
</body>
</html>