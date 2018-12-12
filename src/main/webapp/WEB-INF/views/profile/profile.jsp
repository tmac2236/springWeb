<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="zh-TW">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge" />
<title>首頁</title>


<link rel="stylesheet" href="app/plugin/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="app/plugin/bootstrap/css/bootstrap-theme.css" />
<link rel="stylesheet" href="app/css/main/main.css" />
<link rel="stylesheet" href="plugin/jQuery/jquery-ui-1.12.1/jquery-ui.min.css" />

<script src="plugin/jQuery/js/jquery-3.3.1.js"></script>
<script src="plugin/jQuery/js/jquery-3.3.1.min.js"></script>
<script src="plugin/jQuery/js/jquery.tmpl.js"></script>
<script src="plugin/jQuery/js/jquery.tmpl.min.js"></script>

<script src="plugin/jQuery/js/jquery-ui.min.js"></script>
<script src="plugin/jQuery/js/jquery-ui.js"></script>
<script src="plugin/jQuery/migrate/jquery-migrate-3.0.0.min.js"></script>
<script src="plugin/jQuery/migrate/jquery-migrate-3.0.0.js"></script>


<script src="plugin/jsencrypt/jsencrypt.min.js"></script>
<script src="js/config/purl.js"></script>
<script src="js/config/app.common.js"></script>
<script src="js/config/ProjConfig.js"></script>

<style>
.rTable { display: table; }
.rTableRow { display: table-row; }
.rTableHeading { display: table-header-group; }
.rTableBody { display: table-row-group; }
.rTableFoot { display: table-footer-group; }
.rTableCell, .rTableHead { display: table-cell; }

.tr_class {
    font-weight: bold;
}

<!--Table加線-->
.rTable {
    display: table;
    width: 100%;
}
.rTableRow {
    display: table-row;
}
.rTableHeading {
    display: table-header-group;
    background-color: #ddd;
}
.rTableCell, .rTableHead {
    display: table-cell;
    padding: 3px 10px;
    border: 1px solid #999999;
}
.rTableHeading {
    display: table-header-group;
    background-color: #ddd;
    font-weight: bold;
}
.rTableFoot {
    display: table-footer-group;
    font-weight: bold;
    background-color: #ddd;
}
.rTableBody {
    display: table-row-group;
}
</style>
</head>
<c:set var="context" value="${pageContext.request.contextPath}" />
<script id="addUserTmpl" type="text/x-jquery-tmpl">
<div class="rTableRow">
      <div class="rTableCell"><input type="text"      size="10" value="{{= account}}"></div>
      <div class="rTableCell"><input type="password"  size="10" value=""></div>
      <div class="rTableCell"><input type="text"      size="10" value="{{= name}}"></div>
      <div class="rTableCell"><input type="text"      size="10" value="{{= phone}}"></div>
      <div class="rTableCell"><input type="text"      size="10" value="{{= address}}"></div>
      <div class="rTableCell"><input type="text"      size="10" value="{{= mail}}"></div>
      <div class="rTableCell">
      <select>
           <option {{if role == 1}} {{= selected="selected"}} {{/if}} value="1" >訪客</option>
           <option {{if role == 2}} {{= selected="selected"}} {{/if}} value="2" >使用者</option>
           <option {{if role == 3}} {{= selected="selected"}} {{/if}} value="3" >管理者</option>
      </select>
      </div>
      <div class="rTableCell" style="display:none;" ><input type="hidden" size="10" value="{{= userId}}"></div>
      <div class="rTableCell" ><button onclick="submitUserModify(this)">修改</button></div>
      <div class="rTableCell"><button onclick="deleteUser('{{= userId}}',this)">刪除</button></div>
</div>

</script>

<body style="">
	<div id="main-frame">
		<div id="header">
            <div style="position: absolute; right: 6px; bottom: 6px;">
                <span style="font-size: 25px; font-family: 微軟正黑體;" id="accountTitle">${sessionScope.User.name}</span> <img src="images/config/icon-user.png" id="modifyProfileBTN" class="header-icon">
                <!-- 只有管理者可以新增刪除user -->
                <c:if test="${sessionScope.User.role == '3'}">
                    <img src="images/config/setting.png" onclick="location.href='${context}/profile';" id="" class="header-icon">
                </c:if>
                                <img style="cursor: pointer; height: 40px; width: 90px;" src="images/config/index.png" onclick="location.href='${context}/main';" class="header-icon">
                <img style="cursor: pointer; height: 40px; width: 90px;" src="images/config/logout.png" onclick="location.href='${context}/logout';" id="deviceRemove" class="header-icon">
            </div>
		</div>
		<div id="all-view" style="background-color: powderblue;">
			<div>
			 <img src="images/config/add.png" id="addUserBTN" height="35" width="35">
			 <span style="font-size: 22px; font-family: 微軟正黑體;" >帳號管理</span>
			</div>
			<div class="rTable" id="topUserDiv">
				<div class="rTableRow">
					<div class="rTableHead">
						<span class="tr_class">帳號</span>
					</div>
					<div class="rTableHead">
						<span class="tr_class">密碼</span>
					</div>
                    <div class="rTableHead">
                        <span class="tr_class">暱稱</span>
                    </div>
                    <div class="rTableHead">
                        <span class="tr_class">電話</span>
                    </div>
                    <div class="rTableHead">
                        <span class="tr_class">地址</span>
                    </div>                         
                    <div class="rTableHead">
                        <span class="tr_class">信箱</span>
                    </div>                                   
                    <div class="rTableHead">
                        <span class="tr_class">權限</span>
                    </div>
                    <div class="rTableHead"><%-- 修改 --%>
                        <span class="tr_class"></span>
                    </div>
                    <div class="rTableHead"><%-- 刪除 --%>
                        <span class="tr_class"></span>
                    </div>                                         					
				</div>
				<c:forEach items="${userList}" var="ay">
				<c:if test="${ay.account != 'admin'}">
				<div class="rTableRow">
					<div class="rTableCell"><input type="text"      size="10" value='${ay.account}'></div>
					<div class="rTableCell"><input type="password"  size="10" value=''></div>
                    <div class="rTableCell"><input type="text"      size="10" value='${ay.name}'></div>
                    <div class="rTableCell"><input type="text"      size="10" value='${ay.phone}'></div>
                    <div class="rTableCell"><input type="text"      size="10" value='${ay.address}'></div>
                    <div class="rTableCell"><input type="text"      size="10" value='${ay.mail}'></div>
                    <%--<div class="rTableCell"><input type="text"      size="10" value='${ay.role}'></div> --%>
                    <div class="rTableCell">
                        <select>
                            <option value="1" <c:if test="${ay.role == '1'}">selected</c:if> >訪客</option>
                            <option value="2" <c:if test="${ay.role == '2'}">selected</c:if> >使用者</option>
                            <option value="3" <c:if test="${ay.role == '3'}">selected</c:if> >管理者</option>
                        </select>
                    </div>
                    <div class="rTableCell" style="display:none;" ><input type="hidden" size="10" value='${ay.userId}'></div>
                    <div class="rTableCell" ><button onclick='submitUserModify(this)'>修改</button></div>
                    <div class="rTableCell"><button onclick="deleteUser('${ay.userId}',this)">刪除</button></div>
				</div>
				</c:if>
				</c:forEach>

			</div>
			<footer id="mainFooter">
				<div id="separate" style="width: 100%; height: 5px;"></div>
				<div style="margin-top: 5px;">
					最佳瀏覽環境：Chrome瀏覽器 | 解析度:1366 x 768以上
				</div>
			</footer>
		</div>

	</div>

</body>
<script>

//新增一個使用者(組合字串)
    function preSubmitAddUser (){
        var jsonStr = {};
            jsonStr.userId = '';
            jsonStr.account = $('#add_account').val();
                            var add_password = $('#add_password').val();
                            if(add_password==''){
                                alert("密碼欄位是空的");
                                return;
                            }
                var encrypt = new JSEncrypt({
                    "default_key_size" : 4096
                });
                    encrypt.setPublicKey(PROJ.publicKey);
                var encrypted = "";
                if (add_password) {
                    encrypted = encrypt.encrypt(add_password);
                }
            jsonStr.password = encrypted;
            
            jsonStr.name = $('#add_name').val();
            jsonStr.phone = $('#add_phone').val();
            jsonStr.address = $('#add_address').val();
            jsonStr.mail = $('#add_mail').val();
            jsonStr.role = $('#add_role').val();
            
            console.log(jsonStr);
            if(checkUserValid(jsonStr)){
                submitAddUser(jsonStr);
            }
    }
//新增一個使用者(Submit)
    function submitAddUser(jsonStr) {
        console.log(jsonStr);
        $.ajax({
            type : 'POST',
            contentType : "application/json",
            data : JSON.stringify(jsonStr),
            url : '${context}/user/add'
        }).done(function(res) {
            $('#addUser').dialog('close');
            if(res.message) {
                alert(res.message);
            }
            else if (res.status && res.data) {
                $('#addUserTmpl').tmpl(res.data).appendTo('#topUserDiv');
                alert("新增完成。");
                console.log(res.data);
            } else {
                alert("新增完成。");
                console.log(res.data);
            }
        }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
            alert("ajax error。");
            console.log(XMLHttpRequest);
            console.log(textStatus);
            console.log(errorThrown);
        });

    }

//刪除一個使用者
    function deleteUser (userId,element){
        
        console.log("ready to delete : "+ userId);
     
        $.ajax({
            url : '${context}/user/delete',
            type : 'POST',
            data: {userId: userId}
        }).done(function(res) {
            //delete front-end div
            $(element).parent().parent().empty();
            if (res.status && res.data) {
                alert("刪除完成。");
                console.log(res.data);
            } else {
                alert("刪除完成。");
                console.log(res.data);
            }
            //把整個欄位刪除
            $(this).parent().parent().empty();
        }).fail(function() {
            alert("ajax error。");
        });  
     
    }


//修改其他User資料(組合字串)
    function submitUserModify(element){
        var jsonStr = {};
        $(element).parent().siblings().each(function(i){
            //console.log($(this).find(">:first-child").val());
            var cellVal =($(this).find(">:first-child").val());
            //console.log(typeof i === 'number');
            switch(i){
                case 0 :
                    jsonStr.account = cellVal;break;
                case 1 :
                        var encrypt = new JSEncrypt({
                            "default_key_size" : 4096
                            });
                            encrypt.setPublicKey(PROJ.publicKey);
                            var encrypted = "";
                            if (cellVal) {
                            encrypted = encrypt.encrypt(cellVal);
                        }
                    jsonStr.password = encrypted;break;
                case 2 :
                    jsonStr.name = cellVal;break;
                case 3 :
                    jsonStr.phone = cellVal;break;
                case 4 :
                    jsonStr.address = cellVal;break;
                case 5 :
                    jsonStr.mail = cellVal;break;
                case 6 :
                    cellVal =($(this).find(">:first-child option:selected").val());
                    jsonStr.role = cellVal;break;
                case 7 :
                   jsonStr.userId = cellVal;break;                    
                default :
                     console.log('index is '+ i +' do nothing ~');
            }
        });
                console.log(jsonStr);
        if(checkUserValid(jsonStr)){
           submitProfile(jsonStr);
        };
                
    }

//修改個人資料
    function submitModify() {
        var crudePassword = $('#password').val();
        var encrypt = new JSEncrypt({
            "default_key_size" : 4096
        });
        encrypt.setPublicKey(PROJ.publicKey);
        var encrypted = "";
        if (crudePassword) {
            encrypted = encrypt.encrypt(crudePassword);
        }
        var jsonStr = {};
            jsonStr.userId = '${sessionScope.User.userId}' ;
            jsonStr.account = '${sessionScope.User.account}' ;
            jsonStr.password = encrypted ;
            jsonStr.name = $('#name').val() ;
            jsonStr.phone = $('#phone').val() ;
            jsonStr.address = $('#address').val() ;
            jsonStr.mail = $('#mail').val() ;        
        if(checkUserValid(jsonStr)){
            submitProfile(jsonStr);
        }
    }

// Update user Profile
// Param : UserDto
    function submitProfile(jsonStr) {
        console.log(jsonStr);
        $.ajax({
            type : 'POST',
            async : false,
            contentType : "application/json",
            data : JSON.stringify(jsonStr),
            url : '${context}/user/edit'
        }).done(function(res) {
            if(res.message) {
                alert(res.message);
            }else if (res.status && res.data) {

                //console.log(res.data.userId);
                //console.log(res.data.phone);
                //修改自己的帳號才需要改Title
                if('${sessionScope.User.userId}' == res.data.userId){
                    $("#accountTitle").text(res.data.name);
                }
                alert("修改完成。");
                console.log(res.data);
            } else {
                alert("修改完成。");
                console.log(res.data);
            }
        }).fail(function() {
            alert("ajax error。");
        });

    }

    $(function() {
        //使用者新增
        $("#addUser").dialog({
            autoOpen : false,
            show : {},
            hide : {}
        });
        $("#addUserBTN").click(function() {
            $("#addUser").dialog("open");
        });
        
        
        //個人資料修改
        $("#modifyProfile").dialog({
            autoOpen : false,
            show : {},
            hide : {}
        });
        $("#modifyProfileBTN").click(function() {
            $("#modifyProfile").dialog("open");
        });
        
        
    });
    
    function checkUserValid(jsonStr){
        for(var k in jsonStr){
           if(jsonStr[k].length === 0 && k !=="password" && k !=="userId"){
                alert(k+"欄位是空的!");
                return false;
           }
        }
        return true;
    }
    
</script>
<%-- 使用者新增 --%>
<div id="addUser" title="使用者新增" style="display: none;">
    <div style="text-align: center">
        <label><h4>帳號</h4></label> <input type="text"     id="add_account" size="20" value=''> </br>
        <label><h4>密碼</h4></label> <input type="password" id="add_password" size="20" value=''> </br>
        <label><h4>姓名</h4></label> <input type="text"     id="add_name" size="20" value=''> </br>
        <label><h4>電話</h4></label> <input type="text"     id="add_phone" size="20" value=''> </br>
        <label><h4>地址</h4></label> <input type="text"     id="add_address" size="20" value=''> </br>
        <label><h4>信箱</h4></label> <input type="text"     id="add_mail" size="20" value=''> </br>
        <label><h4>權限</h4></label>
                        <select id ='add_role'>
                            <option value="1">訪客</option>
                            <option value="2">使用者</option>
                            <option value="3">管理者</option>
                        </select> </br>
        <button onclick='preSubmitAddUser()'>送出修改</button>
    </div>
</div>

<%-- 個人資料修改 --%>
<div id="modifyProfile" title="個人資料修改" style="display: none;">
    <div style="text-align: center">
        <label><h4>密碼</h4></label> <input type="password" id="password" size="20" value=''> </br>
        <label><h4>姓名</h4></label> <input type="text" id="name" size="20" value='${sessionScope.User.name}'> </br>
        <label><h4>電話</h4></label> <input type="text" id="phone" size="20" value='${sessionScope.User.phone}'> </br>
        <label><h4>地址</h4></label> <input type="text" id="address" size="20" value='${sessionScope.User.address}'> </br>
        <label><h4>信箱</h4></label> <input type="text" id="mail" size="20" value='${sessionScope.User.mail}'> </br>
        <button onclick='submitModify()'>送出修改</button>
    </div>
</div>
</html>
