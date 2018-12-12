/* Common
========================================================================== */
var CommonObj = CommonObj || {};

/** 
 * 網站的基本路徑
 * example:
 * http://yahoo.com.tw/abc/test.jsp
 * http://yahoo.com.tw/images/123.jpg
 * 
 * BASEPATH = http://yahoo.com.tw/
 * 
 * 在css中如果要設定一個圖片路徑, url(../images/test.jpg) 路徑是相對於css檔案位置
 * 在javascript中, ../images/test.jpg 路徑是相對於當前頁面(jsp,html...)檔案位置
 * 為了js元件可以順利的被各個不同頁面引用, 需要串接路徑時, 建議都以CommonObj.BASEPATH開頭
 * 
 */
CommonObj.BASEPATH = (function() {
	var url = purl(location.href);
	var basePath = url.attr("base") + "/" + url.segment(1) + "/";

	return basePath;
})();

/**
 * 判斷是否為IE瀏覽器
 * return 版本號
 * return -1表示不是IE瀏覽器
 * 
 * edge不包含在內, 如果要特別偵測是否為edge應該使用$.browser.edge
 * 參考https://github.com/gabceb/jquery-browser-plugin
 * 
 */
//CommonObj.IE_VER = (function() {
//	return $.browser.msie ? $.browser.versionNumber : -1;
//})();

CommonObj.classExtend = function(p, c) {
	for ( var i in p) {
		if (typeof p[i] === 'object') {
			c[i] = (p[i].constructor === Array) ? [] : {};
			ClassExtend(p[i], c[i]);
		} else {
			c[i] = p[i];
		}
	}
};

CommonObj.ajax = function(ajaxOptions) {
    var options = {
        showLoadig: false,
        ajaxCaller: null,
        disableButton: null,
        url : "",
        data : {},
        type : "post",
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        timeout: 60000,
        cache : false,
        processData : true,
        beforeSend: null,
        error: null,
        success : null,
        complete : null,
        async : true
    };
    $.extend(true, options, ajaxOptions);
    
//	var loader = $('#tmpl-loading').tmpl();//這是共用的,這個沒辦法用loader.remove();會出錯

	return $.ajax({
    	async: options.async,
        url: options.url,
        data: options.data,
        type: options.type,
        dataType: options.dataType,
        contentType: options.contentType,
        timeout: options.timeout,
        cache: options.cache,
        processData: options.processData,
        beforeSend: function(xhr, settings){
            if(options.showLoadig){
//            	$(document.body).append(loader);
               CommonObj.loading.show();
            }
            if(options.disableButton){
                options.disableButton.prop("disabled", true).addClass("ui-state-disabled");
            }
            if (options.beforeSend) {
                options.beforeSend(xhr, settings);
            }
        },
        complete: function(xhr, textStatus){
            if(options.disableButton){
                options.disableButton.prop("disabled", false).removeClass("ui-state-disabled");
            }
            if (options.complete) {
                options.complete(xhr, textStatus);
            }
            if(options.showLoadig){
//	        	loader.remove();
                CommonObj.loading.hide();
            }
        },
        success: function(result, textStatus, xhr) {
            $.log.debug("Ajax Success: " + options.ajaxCaller + "()");
            
            if(result){
                if(result.message && result.message !== ""){
                    $.log.info(result.message);
                }
                
                if(result.action){
                	switch (result.action){
	                    case "logout":
	                        top.location.replace("login.jsp");
	                        break;
	                }
                }
            }
            
            if (options.success) {
                options.success(result, textStatus, xhr);
            }
        },
        error: function(xhr, textStatus) {
            var responseText = xhr.responseText;
            if(responseText && responseText.indexOf("login.jsp") !== -1){
            	top.location.replace("login.jsp");
            }
            
            $.log.error("Ajax Error: " + options.ajaxCaller + "()");
            $.log.error("httpStatus: " + xhr.status);
            $.log.error("textStatus: " + textStatus);
            $.log.debug("responseText:\r\n" + responseText);
            
            if(options.error){
                options.error(xhr, textStatus);
            }
        }
    });
};

CommonObj.loading = (function() {
    var minDealy = 500;
    return {
        show : function() {
            var customId = "CommonObj-loading-show";
            if ($("#" + customId).length > 0){
                return;
            }
            
            window.temp_LoadingStartTime = Date.now();
            $.blockUI({
                message : "<h1><img id='" + customId + "' src='" + CommonObj.BASEPATH + "app/images/misc/loading_circle.gif' /> Loading...</h1>"
            });
        },
        showIconOnly : function() {
            var customId = "CommonObj-loading-showIconOnly";
            if($("#" + customId).length > 0){
                return;
            }
            
            window.temp_LoadingStartTime = Date.now();
            $.blockUI({
                message : "<img id='" + customId + "' src='" + CommonObj.BASEPATH + "app/images/misc/loading_circle.gif' />"
            });
            $(".blockUI").css({"background": "", "border": ""});
        },
        hide : function() {
            var now = Date.now();
            var diff = now - window.temp_LoadingStartTime;
            if (diff < minDealy) {
                setTimeout(function() {
                    $.unblockUI();
                }, minDealy - diff);
            }
            else{
                $.unblockUI();
            }
        }
    };
}());

CommonObj.showMessage = function(message, title, callback) {
	$("<div>").html(message).dialog({
		dialogClass : "sysDialog",
		title : title ? title : "系統訊息",
		modal : true,
		position : {
			my : "center",
			at : "center top+150",
			of : window
		},
		buttons : {
			"確定" : function() {
				$(this).dialog('destroy').remove();
				if(callback){
					callback();
				}
			}
		},
		create: function(event, ui) {
			var widget = $(this).dialog("widget");
			$(".ui-dialog-titlebar .ui-button-icon-primary", widget).remove();
			$(".ui-dialog-titlebar .ui-button-text", widget).text("X");
		},
		close : function() {
			$(this).dialog('destroy').remove();
		}
	});
};

/**
 * 訂閱WebSocket
 */
CommonObj.subscribe = function(subWindow, key, callback){
	var viewId = (new Date()).getTime();
	var mergeKey = key + "." + viewId;
	TOP.$.subscribe(mergeKey, callback);
	$(subWindow).unload(function(){
		CommonObj.unsubscribe(viewId, key);
	});
	
	TOP.$.log.debug("Subscribe:" + mergeKey);
	
	return viewId;
};

/**
 * 取消訂閱WebSocket
 */
CommonObj.unsubscribe = function(viewId, key){
	var mergeKey = key + "." + viewId;
	TOP.$.unsubscribe(mergeKey);
	
	TOP.$.log.debug("Unsubscribe:" + mergeKey);
};

/**
 * 取得共用的DataModel
 */
CommonObj.getDataModel = function(){
	return TOP.DataModel ? TOP.DataModel : null;
};

/* jQuery擴充
========================================================================== */
/** Contains判斷時不區分大小寫 */
jQuery.expr[':'].Contains = function(a, i, m) {
	return jQuery(a).text().toLowerCase().indexOf(m[3].toLowerCase()) >= 0;
};

/** 元素垂直置中 */
$.fn.vAlign = function() {
	return this.each(function(i) {
		var ah = $(this).height();
		var ph = $(this).parent().height();
		var mh = Math.ceil((ph - ah) / 2);
		$(this).css('margin-top', mh);
	});
};

/** 元素禁止選取 */
$.fn.disableSelection = function() {
	return this.attr('unselectable', 'on').css('user-select', 'none').on('selectstart', false);
};
