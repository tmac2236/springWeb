var Properties = (
    function() {
        var pub = {
                createContent : function() {
                    
                    var tabs = $('<div>');
                    tabs.attr('class', 'prefs-tabs-container');
                    
                    var ul = $('<ul>');
                    ul.attr('id', 'prefs-tabs');
                    
                    //加入頁籤
                    var titles = ['設備顯示設定', '設備狀態設定'];
                    for(var i = 0; i < titles.length;i++) {
                        var li = $('<li>');
                        li.attr('data-tab', '#prefs-tabs-' + i)
                        
                        if(i == 0) {
                            li.attr('class', 'prefs-tabs-current');
                        }
                        
                        var a = $('<a>');
                        a.attr('style','font-size: 14px; font-weight: bold');
                        a.text(titles[i]);
                        
                        li.append(a);
                        ul.append(li);
                    }
                    
                    tabs.append(ul);

                    
                    tabs.find('li').click(function(){
                        var tab_id = $(this).attr('data-tab');

                        $('#prefs-tabs li').removeClass('prefs-tabs-current');
                        $('.prefs-tabs-content').removeClass('prefs-tabs-current');

                        $(this).addClass('prefs-tabs-current');
                        $(tab_id).addClass('prefs-tabs-current');
                        
                    });
                    
                    var body = $(".propertiesTabDialog");
                    
//                  if (body.length > 0) {
//                      body.empty().remove();
//                      return;
//                  }
                    
                    body = $('#tmpl-properties-tabs').tmpl();
                    
                    body.append(tabs);

                    body.dialog({
                        title: '偏好設定',
                        resizable:false,
                        modal : true,
                        width : 330,
                        height: 330,
                        position : {
                            at : "center top",
                            my : "center top+50"
                        },
                        dialogClass : "propertiesTabDialog",
                        create: function(event, ui) {
                            var widget = $(this).dialog("widget");
                            $(".ui-dialog-titlebar .ui-button-icon-primary", widget).remove();
                            $(".ui-dialog-titlebar .ui-button-text", widget).text("X");
                        },
                        close : function() {
                            $(this).dialog('destroy').remove();
                        },
                        open : function(event, ui) {
                            $(".propertiesTabDialog .ui-dialog-title");
                        }
                    });
                    
                },  
        };
        
        return pub;
    }
)();