<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>records page</title>  
      
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
    var tbk;
    function openRecordAddDialog() {
        $("#dlg").dialog("open").dialog("setTitle", "add user info");
        url = "${pageContext.request.contextPath}/records/save.do";
    }

    function closeEditDialog(){
    	$("#dlg").dialog('close');
        }

    function saveRecord() {        
            $.ajax({
                url: url,
                type: 'post',
                data: $("#fm").serialize()+ "&tbk="+tbk,
                onSubmit: function() {
                    if ($("#messageBox").val() == "") {
                        $.messager.alert("notification", "message cannot be null");
                        return false;
                        }                                       
                    },
                success: function(result) {
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
    };
     
     function showRecord() {
    	tbk = $("#tableKey").val();
        $("#dg").datagrid('load',{"tableKey": tbk});
        
    } 

    function resetValue() {
        $("#message").val("");        
        $("#group").combobox("setValue", "");
    }

    function deleteRecord() {
        var selectedRows = $("#dg").datagrid("getSelections");
        if (selectedRows.length == 0) {
            $.messager.alert("notification", "select data to delete");
            return;
        }
        var strIds = [];
        for (var i = 0; i < selectedRows.length; i++) {
            strIds.push(selectedRows[i].id);
        }
        var ids = strIds.join(",");
        $.messager.confirm("notification",
            "sure to delete data of <font color=red>" + selectedRows.length +"</font>?",
            function(r) {
                if (r) {
                    $.post("${pageContext.request.contextPath}/records/delete.do", {ids: ids, tableKey: tbk}
                    , function(result) {
                        if (result.success) {
                            $.messager
                                .alert("notification", "deleted!");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("notification",
                                "fail to delete");
                        }
                    }, "json");
                }
            });
    }

    function openModifyDialog() {
        var selectedRows = $("#dg").datagrid("getSelections");
        if (selectedRows.length != 1) {
            $.messager.alert("notification", "select data to edit");
            return;
        }
        var row = selectedRows[0];
        $("#dlg").dialog("open").dialog("setTitle", "edit record");
        $("#fm").form("load", row);
        url = "${pageContext.request.contextPath}/records/save.do?id=" + row.id;
    }

    function formatIsURL(value,row)
    {
     //console.log(row);
     if(row.url)         
        return '✔';
     else
        return '❌';
    }
    
    </script>  
  </head>  
    
  <body style="margin: 1px"> 
    <table id="dg" title="records management" class="easyui-datagrid" fitColumns="true"  
        pagination="true" rownumbers="true"  
        url="${pageContext.request.contextPath}/records/list.do" fit="true"  
        toolbar="#tb">
          
        <thead>  
            <tr>  
                <th field="cb" checkbox="true" align="center"></th>  
                <th field="id" width="50" align="center">ID</th>  
                <th field="message" width="50" align="center">Message</th>  
                <th field="isURL" formatter="formatIsURL" width="50" align="center">isURL</th>  
                <th field="group" width="50" align="center">Group</th> 
            </tr>  
        </thead> 
         
    </table> 
    <div id="tb"> 
		
		<a href="javascript:openRecordAddDialog()" class="easyui-linkbutton"
                iconCls="icon-add" plain="true">Add</a> <a
                href="javascript:openModifyDialog()" class="easyui-linkbutton"
                iconCls="icon-edit" plain="true" style="margin-left:20px;">Edit</a> <a
                href="javascript:deleteRecord()" class="easyui-linkbutton"
                iconCls="icon-remove" plain="true" style="margin-left:20px;">Delete</a>  
		
		<div style="float: right;">  
             Table key: <input type="text" id="tableKey" size="20"  
                onkeydown="if(event.keyCode == 13) showRecord()" /> <a  
                href="javascript:showRecord()">Go</a>
        </div> 
        
        <div id="dlg-buttons">  
            <a href="javascript:saveRecord()" class="easyui-linkbutton"  
                iconCls="icon-ok">Save</a> <a href="javascript:closeEditDialog()"  
                class="easyui-linkbutton" iconCls="icon-cancel">Close</a>  
        </div>  
        
        <div id="dlg" class="easyui-dialog"  
            style="width: 730px;height:280px;padding:10px 10px;" closed="true"  
            buttons="#dlg-buttons">  
            <form method="post" id="fm">
				<table cellspacing="8px;">
					<tbody>
						<tr height="50">
							
							<td colspan="2">Message:<input type="text" id="messageBox"
								 name="message" style="width: 300px"
								required="true" /> <span style="color: red;">*</span></td>
						</tr>
						<tr height="50">
						<td>This
                            <select id="isURL" name="URL">
                                <option value="false" selected>is NOT</option>
                                <option value="true">  is</option>
                            </select>a url.<span style="color: red;">*</span>
                            </td>						
						</tr>
						<tr height="50">	
							<td>Group:<select id="group" class="easyui-combobox"
								style="width: 154px;" name="group">
									<option value="">Choose a Group</option>
									<!--  add avaliable groups here>
									<-->
							</select>
							<span style="color: red;">*</span></td>
							
						</tr>
					</tbody>
				</table>
			</form>  
        </div>  
    </div>  
    
    
  </body>  
</html>  