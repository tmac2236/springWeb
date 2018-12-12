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
<script src="js/config/markerclusterer.js"></script>
<script src="js/web/component/PropertiesObj.js"></script>

<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0; padding: 0 }
  #map-canvas { height: 100% }
  
.sidebar {
    position: absolute;
    left: 0px;
    top: 50px;
    z-index: 2147483647;
    max-width: 138px;
}

.sidebar .dropdown-menu li a {
    line-height: 30px;
}
</style>
</head>
<c:set var="context" value="${pageContext.request.contextPath}" />
<div id="dialog" title="個人資料修改" style="display: none;">
	<!-- 個人資料設定 -->
	<div style="text-align: center">
		<label><h4>個人資料</h4></label> </br> <label><h4>密碼</h4></label> <input type="password" id="password" size="20" value=''> </br> <label><h4>姓名</h4></label> <input type="text" id="name" size="20" value='${sessionScope.User.name}'> </br> <label><h4>電話</h4></label> <input type="text" id="phone" size="20" value='${sessionScope.User.phone}'> </br> <label><h4>地址</h4></label> <input type="text" id="address" size="20" value='${sessionScope.User.address}'> </br> <label><h4>信箱</h4></label> <input type="text" id="mail" size="20" value='${sessionScope.User.mail}'> </br>
		<button onclick='submitModify()'>送出修改</button>
	</div>
</div>
<body style="">
	<div id="main-frame">
		<div id="header">
			<div style="position: absolute; right: 6px; bottom: 6px;">
				<span style="font-size: 25px; font-family: 微軟正黑體;" id="accountTitle">${sessionScope.User.name}</span> <img src="images/config/icon-user.png" id="OpenDialog" class="header-icon">
				<!-- 只有管理者可以新增刪除user -->
				<c:if test="${User.role == '3'}">
					<img src="images/config/setting.png" onclick="location.href='${context}/profile';" id="" class="header-icon">
				</c:if>
				<img style="cursor: pointer; height: 40px; width: 90px;" src="images/config/index.png" onclick="location.href='${context}/main';" class="header-icon">
				<img style="cursor: pointer; height: 40px; width: 90px;" src="images/config/logout.png" onclick="location.href='${context}/logout';" id="deviceRemove" class="header-icon">
			</div>
		</div>
		<div id="all-view" style="background-color: powderblue;">
		    <div id="sidebar" class="sidebar">
                <div class="btn-group-vertical">
                    <a class="btn btn-primary" onclick="Properties.createContent()">偏好設定</a>
                </div>
            </div>
            <div id="map-canvas"></div>
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
//Global
      var resourceIdList;
      var locations = [
        {lat: 22.616610, lng: 120.314280},
        {lat: 22.614610, lng: 120.334280},
        {lat: 22.615610, lng: 120.354280},
        {lat: 22.619610, lng: 120.354280},
        {lat: 22.625620, lng: 120.324280},
        {lat: 22.615303, lng: 120.313680},
        {lat: 22.614720, lng: 120.331880},
        {lat: 22.617010, lng: 120.355280},
        {lat: 22.619590, lng: 120.340280},
        {lat: 22.628110, lng: 120.319280}
      ] 
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
            
        submitProfile(jsonStr);
    }

    function submitProfile(jsonStr) {

        $.ajax({
            type : 'POST',
            async : false,
            contentType : "application/json",
            data : JSON.stringify(jsonStr),
            url : '${context}/user/edit'
        }).done(function(res) {
            if(res.message){
                alert(res.message);
            }else if (res.status && res.data) {

                //console.log(res.data.userId);
                //console.log(res.data.phone);
                $("#accountTitle").text(res.data.name);
                alert("修改完成。");
                console.log(res.data);
            } else {
                alert("修改完成。");
                console.log(res.data);
            }
        }).fail(function() {
            alert("ajax error。");
            console.log(res.data);
        });

    }
    
       function initMap() {
        var mapOptions = {
          center: { lat: 22.616610, lng: 120.314280},
          zoom: 13
        };
         map = new google.maps.Map(
            document.getElementById('map-canvas'),
            mapOptions);
         

        // Add some markers to the map.
        // Note: The code uses the JavaScript Array.prototype.map() method to
        // create an array of markers based on a given "locations" array.
        // The map() method here has nothing to do with the Google Maps API.
        var markers = locations.map(function(location, i) {
          return new google.maps.Marker({
            position: location,
            icon: {
                url: '${context}/images/mapIcon/'+'avi0'+'.png',
                scaledSize: new google.maps.Size(50, 50)
            }
          });
        });

        // Add a marker clusterer to manage the markers.
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
      }
          
//init
    $(function() {
        var map ;
        initMap();
        resourceIdList = '${sessionScope.resourceIdList}';
        console.log(resourceIdList);
        var myJsonArray = $.parseJSON(resourceIdList);
        //console.log(typeof(myJsonArray));
        
        $("#dialog").dialog({
            autoOpen : false,
            show : {},
            hide : {}
        });

        $("#OpenDialog").click(function() {
            $("#dialog").dialog("open");
        });
    });
</script>
    <script 
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDG7z-kKmUW2jSnik34CpIdLIbk9TyKMu8&libraries=places,geometry,drawing">
    </script>
</html>
