function pageInit() {
	$.log.setLogLevel("debug");
	// init project
	if (typeof PROJ.init === "function") {
		PROJ.init();
	}
	
	// TODO 等資料真的載完才移除loading
	setTimeout(function(){
		$("#loading").remove();
	}, 500);
	
}
