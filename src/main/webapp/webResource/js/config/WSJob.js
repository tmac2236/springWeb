/*
 * sample
 * ==========================================================================
 */

(function() {
	
	function wsJob() {
		//var subscribeId = WebSocketModel.subscribe(destination, callback);
		
		//if(!subscribeId){
		//	setTimeout(function(){
		//		wsJob();
		//	}, 500)
		//}
	}
//	wsJob();
	
})();


// 訂閱系統時間
(function() {
	
	function wsJob() {
//		var subscribeId = WebSocketModel.subscribe("/topic/date", function(message) {
//			$.publish('systemDate', message.body);
//		});
//		
//		if(!subscribeId){
//			setTimeout(function(){
//				wsJob();
//			}, 500)
//		}
	}
//	wsJob();
})();

/**
 * Tree - icon chnage
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/treeIconChange", function(message){
			$.publish('treeIconChange', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();

/**
 * Tree - setting chnage
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/treeSettingChange", function(message){
			$.publish('treeSettingChange', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();

/**
 * Tree - groupNode chnage
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/treeGroupNodeChange", function(message){
			$.publish('treeGroupNodeChange', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();

/**
 * Tree - device change
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/treeDeviceChange", function(message){
			var result = JSON.parse(message.body);
			if (result.chgAction=="CFG_ADD"){
				alertify.success("設備新增："+result.deviceDto.equipId +" ["+result.deviceDto.equipName+"]");
			} else if (result.chgAction=="CFG_DELETE"){
				alertify.error("設備刪除："+result.deviceDto.equipId +" ["+result.deviceDto.equipName+"]");
			} else if (result.chgAction=="CFG_UPDATE"){
				alertify.success("設備修改："+result.deviceDto.equipId +" ["+result.deviceDto.equipName+"]");
			}
			$.publish('treeDeviceChange', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();

/**
 * signal - icDynamicMessage
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/icDynamicMessage", function(message){
			$.publish('icDynamicMessage', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();
/**
 * Incident action
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/AllincidentUpdate", function(message){
			$.publish('incident.updateAll', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();
/**
 * Incident GPSignal Group exec msg
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/GPSignalGroupExec", function(message){
			$.publish('GPSignalGroup.download', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();
/**
 * signal - icDynamicExec
 */
(function() {
	function wsJob() {
		var subscribeId = WebSocketModel.subscribe("/topic/icDynamicExec", function(message){
			$.publish('icDynamicExec', message.body);
		});
		
		if(!subscribeId){
			setTimeout(function(){
				wsJob();
			}, 500)
		}
	}
	wsJob();
	
})();

console.log("js/config/WsJob loaded!");