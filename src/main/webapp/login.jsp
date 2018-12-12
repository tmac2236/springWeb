<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
<script type="text/javascript">
    ;
    if (top !== window) {
        top.location.replace(location.href);
    }
</script>
<meta charset="UTF-8">
<title>首頁</title>
<link rel="stylesheet" type="text/css" href="app/css/login/login.css" />
<script src="plugin/LABJS/LAB.js"></script>
<script src="plugin/jQuery/js/jquery-3.3.1.js"></script>
<script src="plugin/jQuery/js/jquery-3.3.1.min.js"></script>
<script src="plugin/jQuery/migrate/jquery-migrate-3.0.0.min.js"></script>
<script src="plugin/jQuery/migrate/jquery-migrate-3.0.0.js"></script>

<script src="plugin/jsencrypt/jsencrypt.min.js"></script>
</head>
<body>
	<div id="background"></div>
	<div id="loginContent">
		<div class="sysTitle">
			<img src="images/config/background-title.png">
		</div>
		<div class="sysImage">
			<img src="images/config/background-title-icon.png">
		</div>
		<form id="form1" class="box login BlackTie" action="login" method="post">
			<fieldset class="boxBody">
				<div>
					<img src="images/config/icon-user.png"> <input id="account" name="account" maxlength="15" type="text" tabindex="1" placeholder="帳號">
				</div>
				<div>
					<img src="images/config/icon-key.png"> <input id="password" name="password" maxlength="15" type="password" tabindex="2" maxlength="15" autocomplete="off" placeholder="密碼">
				</div>
			</fieldset>
			<footer id="loginFooter">
				<label class="msg"><c:if test="${msg != null}">${msg}</c:if></label> <input type="submit" class="ui-button ui-widget ui-state-default ui-corner-all btnLogin" value="登  入" tabindex="3">
				<div class="clearFloat"></div>
			</footer>
		</form>
	</div>
	<form id="form2" action="login" method="post">
		<input id="jsEncrypt" name="jsEncrypt" type="hidden" value="">
	</form>
	<footer id="mainFooter">
		<div id="separate" style="width: 100%; height: 5px;"></div>
		<div style="margin-top: 5px;">
			最佳瀏覽環境：Chrome瀏覽器 | 解析度:1366 x 768以上
		</div>
	</footer>
	<script type="text/javascript">
        $LAB.setGlobalDefaults({
            "CacheBust" : true
        });
        $LAB.script("js/config/ProjConfig.js").wait(function() {
            $("#form1").submit(function(e) {
                e.preventDefault();
                var account = $.trim($("#account").val());
                var password = $.trim($("#password").val());
                if (account == "" || password == "") {
                    $(".msg").text("帳號密碼資訊不得為空");
                } else {
                    var text = account + " " + password;
                    if (PROJ.publicKey) {
                        var encrypt = new JSEncrypt({
                            "default_key_size" : 4096
                        });
                        encrypt.setPublicKey(PROJ.publicKey);
                        var encrypted = encrypt.encrypt(text);
                        $("#jsEncrypt").val(encrypted);
                    } else {
                        $("#jsEncrypt").val(text);
                    }
                    $("#form2").submit();
                }
            });

            var len = PROJ.loginBackgrounds.length;
            if (len > 0) {
                var random = new Date().getTime() % len;
                $("#background").css("background-image", "url('" + PROJ.loginBackgrounds[random] + "')");
            }
            $("#account").focus();
        });
    </script>

</body>
</html>