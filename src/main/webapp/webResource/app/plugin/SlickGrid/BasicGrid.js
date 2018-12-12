/* eslint-env browser, jquery */
/* eslint no-unused-vars: 0 */
/* global  Slick*/

function BasicGrid(container, columns, gridOptions, fnOptions) {
	// SlickGrid options:
	var defaultGridOpts = {
		enableCellNavigation : true,
		showCellSelection : false,
		enableColumnReorder : true,
		forceFitColumns : true
	};
	var _gridOpts = gridOptions ? $.extend(true, defaultGridOpts, gridOptions) : defaultGridOpts;

	// 自訂功能選項
	var defaultFnOpts = {
		autoResizeAllColumns: false,		// scroll時自動調整欄寬
		click: function (e, rowData) { },	// 外部自訂click callback
		dblClick: function (e, rowData) { }, // 外部自訂double click callback
		dblClickDuration: 250				// 預設:若兩次click在250ms內, 視為dblClick
	};
	var _fnOpts = fnOptions ? $.extend(true, defaultFnOpts, fnOptions) : defaultFnOpts;
	
	$(container).css({
		"display" : "flex",
		"flex" : "1 1 auto",
		"flex-direction" : "column",
		"align-items" : "stretch",
		"width" : "100%",
		"height" : "100%"
	});
	
	// init...
	this._renderTimer = null;
	this._viewPortTimer = null;
	this._fnOpts = _fnOpts;
	this._dataView = new Slick.Data.DataView();
	this._grid = new Slick.Grid($(container), this._dataView, columns, _gridOpts);
	this._resizePlugin = new Slick.AutoColumnSize();
	this._init();
}

/** 以底線開頭的method外部不需呼叫 */
BasicGrid.prototype._resizeAllColumns = function() {
	var me = this;
	me._grid.resizeCanvas();
	me._resizePlugin.resizeAllColumns();
	if (!me._fnOpts.showCellSelection) {
		me._grid.resetActiveCell();
	}
};

/** 以底線開頭的method外部不需呼叫 */
BasicGrid.prototype._lazyRender = function() {
	var me = this;
	clearInterval(me._renderTimer);
	me._renderTimer = setInterval(function() {
		var $Container = $(me._grid.getContainerNode());
		if ($Container.length > 0) {
			if ($Container.is(':visible')) {
				clearInterval(me._renderTimer);
				me._resizeAllColumns();
			}
		} else {
			clearInterval(me._renderTimer);
		}
	}, 100);
};

/** 以底線開頭的method外部不需呼叫 */
BasicGrid.prototype._render = function() {
	var me = this;
	var $Container = $(me.getContainerNode());
	if ($Container.length > 0) {
		if ($Container.is(':visible')) {
			me._resizeAllColumns();
		}
		else{
			me._lazyRender();
		}
	}
};

/** 以底線開頭的method外部不需呼叫 */
BasicGrid.prototype._init = function() {
	var me = this;
	var fnOpts = this._fnOpts;
	var grid = this._grid;
	var dataView = this._dataView;
	var resizePlugin = this._resizePlugin;
	var clickCounts = 0;
	var clickTimer = null;
	var clickLock = false;

	// 註冊grid外掛
	grid.registerPlugin(resizePlugin);
	grid.setSelectionModel(new Slick.RowSelectionModel());
	
	/**
	 * 註冊grid事件
	 */
	grid.onClick.subscribe(function(e, args) {
		if (!clickLock) {
			clickCounts++;
			var data = grid.getData();
			if (Slick.Data && data instanceof Slick.Data.DataView) {
				data = data.getItems();
			}
			var rowData = data[args.row];

			// fix double click bug
			if (clickCounts === 1) {
				clickTimer = setTimeout(function() {
					clickLock = true;
					if (fnOpts.click) {
						fnOpts.click(e, rowData);
					}
					clickCounts = 0;
					clickLock = false;
				}, fnOpts.dblClickDuration);
			} else {
				clickLock = true;
				clearTimeout(clickTimer);
				if (fnOpts.dblClick) {
					fnOpts.dblClick(e, rowData);
				}
				setTimeout(function() {
					clickCounts = 0;
					clickLock = false;
				}, fnOpts.dblClickDuration);
			}
		}
	});

	grid.onViewportChanged.subscribe(function(e, args) {
		clearTimeout(me._viewPortTimer);
		me._viewPortTimer = setTimeout(function(){
			var fnOpts = me._fnOpts;
			if(fnOpts.autoResizeAllColumns){
				me._resizePlugin.resizeAllColumns();
			}
			if (!fnOpts.showCellSelection) {
				me._grid.resetActiveCell();
			}
		}, 100);
	});
	

	/**
	 * 註冊dataView事件
	 */
	dataView.onRowCountChanged.subscribe(function(e, args) {
		grid.updateRowCount();
		me._render();
	});
	dataView.onRowsChanged.subscribe(function(e, args) {
		grid.invalidateRows(args.rows);
		me._render();
	});
	
	me._render();
	
};

/** 輸入資料(array), render畫面
 * 
 * 如果資料的key不是"id", 要指定idProperty才可正確更新
 * 例如: 
 * var columns = [ {
 *		id : "sequenceNo",  // 不是"id"
 *		name : "事件編號",
 *		field : "sequenceNo"
 *	},
 *	....
 *
 *  setData(data, "sequenceNo")
 */
BasicGrid.prototype.setData = function(data, idProperty) {
	var dataView = this._dataView;
	dataView.beginUpdate();
	dataView.setItems(data, idProperty);
	dataView.endUpdate();
	this._render();
};

/**
 * 取回原始資料(rowData array)
 */
BasicGrid.prototype.getData = function() {
	return this._dataView.getItems();
};

/** 輸入ID, 取回rowData */
BasicGrid.prototype.getDataById = function(id) {
	return this._dataView.getItemById(id);
};

/** 輸入ID, 取回index */
BasicGrid.prototype.getIdxById = function(id) {
	return this._dataView.getIdxById(id);
};

/** 輸入index, 取回rowData */
BasicGrid.prototype.getDataByIdx = function(index) {
	return this._dataView.getItemByIdx(index);
};

/** 輸入index, 更新該row畫面 */
BasicGrid.prototype.updateRow = function(index) {
	this._grid.updateRow(index);
};

/** 輸入rowIndex、cellIndex, 更新指定cell畫面(會同步到grid editor) */
BasicGrid.prototype.updateCell = function(rowIndex, cellIndex) {
	this._grid.updateCell(rowIndex, cellIndex);
};

/** 輸入rowData, 更新該row畫面 */
BasicGrid.prototype.renderRow = function(rowData) {
	var index = this.getIdxById(rowData["id"]);
	this._grid.updateRow(index);
};

/** 整個grid畫面更新 */
BasicGrid.prototype.render = function(rowData) {
	this._grid.render();
};

/** 取得Slick.Grid */
BasicGrid.prototype.getGrid = function() {
	return this._grid;
};

/** 取得Slick.Data.DataView */
BasicGrid.prototype.getDataView = function() {
	return this._dataView;
};

/** destroy */
BasicGrid.prototype.destroy = function() {
	this._fnOpts = null;
	this._dataView = null;	
	this._resizePlugin = null;
	this._grid.destroy();
	this._grid = null;
};


// ====================================================================
// UI操作
// ====================================================================

/** 選取row */
BasicGrid.prototype.setSelectedRows = function(rowIndexArray) {
	this._grid.setSelectedRows(rowIndexArray);
};

/** 將scrollbar滾到看的到row */
BasicGrid.prototype.scrollRowIntoView = function(rowIndex, doPaging) {
	this._grid.scrollRowIntoView(rowIndex, doPaging);
};

/** row click, 並將畫面定位到該row */
BasicGrid.prototype.triggerClick = function(rowIndex, doPaging) {
	this.setSelectedRows([rowIndex]);
	this.scrollRowIntoView(rowIndex, doPaging);
	this._grid.onClick.notify({	"row" : rowIndex});
};

/** 調整Grid內容來填滿空間
 *
 * 如果子系統的視窗縮放時會動態調整Grid容器的寬高 <br>
 * 通常需要動態調整Grid內容來填滿空間,不然畫面可能有點醜 <br>
 * 解決方案: <br>
 * $(window).resize(function() { 
 *     grid.resizeCanvas(); 
 * });
 */
BasicGrid.prototype.resizeCanvas = function() {
	this._render();
};

/** 取得Gird容器, 再依需求用jQuery操作
 * 
 * Example: 取得第一個row <br>
 * $(grid.getContainerNode()).find(".slick-row:eq(0)")
 */
BasicGrid.prototype.getContainerNode = function() {
	return this._grid.getContainerNode();
};
