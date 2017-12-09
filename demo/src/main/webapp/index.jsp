<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>starting page</title>  
      
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="userManage page">  
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">  
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">  
    <script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>  
    <script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script> 
    <script type="text/javascript">  
        var url;  
        function openRecordAddDialog() {  
            $("#dlg").dialog("open").dialog("setTitle", "add user info");  
            url = "${pageContext.request.contextPath}/user/save.do";  
        }  
        function saveRecord() {  
            $("#fm").form("submit", {  
                url : url,  
                onSubmit : function() {  
                    if ($("#roleName").combobox("getValue") == "") {  
                        $.messager.alert("notification", "select user role");  
                        return false;  
                    }  
                    return $(this).form("validate");  
                },  
                success : function(result) {  
                    var result = eval('(' + result + ')');  
                    if (result.success) {  
                        $.messager.alert("notification", "saved!");  
                        resetValue();  
                        $("#dlg").dialog("close");  
                        $("#dg").datagrid("reload");  
                    } else {  
                        $.messager.alert("notification", "fail to save!");  
                        return;  
                    }  
                }  
            });  
        }  
        function searchUser() {  
            $("#dg").datagrid('load', {  
                "userName" : $("#s_userName").val()  
            });  
        }  
        function resetValue() {  
            $("#userName").val("");  
            $("#password").val("");  
            $("#trueName").val("");  
            $("#email").val("");  
            $("#phone").val("");  
            $("#roleName").combobox("setValue", "");  
        }  
        function deleteUser() {  
            var selectedRows = $("#dg").datagrid("getSelections");  
            if (selectedRows.length == 0) {  
                $.messager.alert("notification", "select data to delete");  
                return;  
            }  
            var strIds = [];  
            for ( var i = 0; i < selectedRows.length; i++) {  
                strIds.push(selectedRows[i].id);  
            }  
            var ids = strIds.join(",");  
            $.messager.confirm("notification", "sure to delete data of <font color=red>"  
                    + selectedRows.length + "</font>?", function(r) {  
                if (r) {  
                    $.post("${pageContext.request.contextPath}/user/delete.do", {  
                        ids : ids  
                    }, function(result) {  
                        if (result.success) {  
                            $.messager.alert("notification", "deleted!");  
                            $("#dg").datagrid("reload");  
                        } else {  
                            $.messager.alert("notification", "fail to delete");  
                        }  
                    }, "json");  
                }  
            });  
        }  
        function openUserModifyDialog() {  
            var selectedRows = $("#dg").datagrid("getSelections");  
            if (selectedRows.length != 1) {  
                $.messager.alert("notification", "select data to edit");  
                return;  
            }  
            var row = selectedRows[0];  
            $("#dlg").dialog("open").dialog("setTitle", "edit user nfo");  
            $("#fm").form("load", row);  
            url = "${pageContext.request.contextPath}/user/save.do?id=" + row.id;  
        }  
    </script>  
  </head>  
    
  <body style="margin: 1px">  
    <table id="dg" title="record management" class="easyui-datagrid" fitColumns="true"  
        pagination="true" rownumbers="true"  
        url="${pageContext.request.contextPath}/user/list.do" fit="true"  
        toolbar="#tb">  
        <thead>  
            <tr>  
                <th field="cb" checkbox="true" align="center"></th>  
                <th field="id" width="50" align="center">ID</th>  
                <th field="message" width="50" align="center">Message</th>  
                <th field="isURL" width="50" align="center">isURL</th>  
                <th field="group" width="50" align="center">Group</th> 
            </tr>  
        </thead>  
    </table>  
    <div id="tb">  
        <a href="javascript:openRecordAddDialog()" class="easyui-linkbutton"  
            iconCls="icon-add" plain="true">Add</a> <a  
            href="javascript:openUserModifyDialog()" class="easyui-linkbutton"  
            iconCls="icon-edit" plain="true">Edit</a> <a  
            href="javascript:deleteUser()" class="easyui-linkbutton"  
            iconCls="icon-remove" plain="true">Delete</a>  
        <div>  
             User Name: <input type="text" id="s_userName" size="20"  
                onkeydown="if(event.keyCode == 13)searchUser()" /> <a  
                href="javascript:searchUser()" class="easyui-linkbutton"  
                iconCls="icon-search" plain="true">Query</a>  
        </div>  
        <div id="dlg-buttons">  
            <a href="javascript:saveRecord()" class="easyui-linkbutton"  
                iconCls="icon-ok">Save</a> <a href="javascript:closeUserDialog()"  
                class="easyui-linkbutton" iconCls="icon-cancel">Close</a>  
        </div>  
        <div id="dlg" class="easyui-dialog"  
            style="width: 730px;height:280px;padding:10px 10px;" closed="true"  
            buttons="#dlg-buttons">  
            <form method="post" id="fm">  
                <table cellspacing="8px;">  
                    <tr>  
                        <td>User Name:</td>  
                        <td><input type="text" id="userName" name="userName"  
                            class="easyui-validatebox" required="true" /> <span  
                            style="color: red">*</span>  
                        </td>  
                        <td>  </td>  
                        <td>Password:</td>  
                        <td><input type="password" id="password" name="password"  
                            class="easyui-validatebox" required="true" /> <span  
                            style="color: red">*</span>  
                        </td>  
                    </tr>  
                    <tr>  
                        <td>True Name:</td>  
                        <td><input type="text" id="trueName" name="trueName"  
                            class="easyui-validatebox" required="true" /> <span  
                            style="color: red">*</span>  
                        </td>  
                        <td>  </td>  
                        <td>Email:</td>  
                        <td><input type="text" id="email" name="email"  
                            validType="email" class="easyui-validatebox" required="true" /> <span  
                            style="color: red">*</span>  
                        </td>  
                    </tr>  
                    <tr>  
                        <td>Phone:</td>  
                        <td><input type="text" id="phone" name="phone"  
                            class="easyui-validatebox" required="true" /> <span  
                            style="color: red">*</span>  
                        </td>  
                        <td>  </td>  
                        <td>Role</td>  
                        <td><select name="roleName" class="easyui-combobox"  
                            id="roleName" style="width: 154px;" editable="false"  
                            panelHeight="auto">  
                                <option value="">Choose a Role</option>  
                                <option value="system admin">system admin</option>  
                                <option value="sales">sales</option>  
                                <option value="manager">manager</option>  
                                <option value="officer">officer</option>  
                        </select>  <span style="color: red">*</span>  
                        </td>  
                    </tr>  
                </table>  
            </form>  
        </div>  
    </div>  
  </body>  
</html>  