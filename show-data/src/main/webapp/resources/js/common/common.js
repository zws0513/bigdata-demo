/**
 *获得根目录
 */
function getRootPath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath); 
	var prePath = strFullPath.substring(0, pos); 
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1); 
	//return (prePath + postPath);
	return '';
}

/**
 * ajax请求方法
 * @param url
 * @param data
 * @param type
 * @param async
 * @param cache
 * @param dataType
 */
function ajaxCommon(url,data,type,async,cache,dataType){
	var resultJson;
	if(!data) data = {};
	if(!type) type = "POST";
	if(!async) async = false;
	if(!cache) cache = false;
	if(!dataType) dataType = "json";
	$.ajax({
        type: type,
        url: url,
        data: data,
        dataType: dataType,
        async: async,
        cache: cache,
        beforeSend: function() {
        	//$(".masker").remove();
        	//$("body").append("<div class='masker'><img src='../../images/loading.png' class='loading'/></div>");
        },
        success: function(result) {
        	//$(".masker").hide();
        	resultJson = result;
        },
        error: function() {
        	//console.log('resultJson Error');
        },
        complete: function() {
        	//$(".masker").hide();
        }
	});
	return resultJson;
}