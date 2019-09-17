<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<title>总管理后台</title>

<link href="<c:url value='/res/dwz/themes/default/style.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='/res/dwz/themes/css/core.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='/res/dwz/themes/css/print.css'/>" rel="stylesheet" type="text/css" media="print"/>
<link href="<c:url value='/res/dwz/uploadify/css/uploadify.css'/>" rel="stylesheet" type="text/css" media="screen"/>

<!-- if is IE -->
<%-- <link href="<c:url value='/res/dwz/themes/css/ieHack.css'/>" rel="stylesheet" type="text/css" media="screen"/>
  --%>
<script src="<c:url value='/res/dwz/js/speedup.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/js/jquery-2.1.4.js'/>" type="text/javascript"></script>

<script src="<c:url value='/res/dwz/js/jquery.cookie.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/js/jquery.validate.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/js/jquery.bgiframe.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/xheditor/xheditor-1.2.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/xheditor/xheditor_lang/zh-cn.js'/>" type="text/javascript"></script>
<script src="<c:url value='/res/dwz/uploadify/scripts/jquery.uploadify.min.js'/>" type="text/javascript"></script>

<script src="<c:url value='/res/dwz/bin/dwz.min.js'/>" type="text/javascript"></script>

<script src="<c:url value='/res/dwz/js/dwz.regional.zh.js'/>" type="text/javascript"></script>

<%-- <c:if test="${'zh-TW'== languageType}">
	<script src="<c:url value='/res/dwz/js/dwz.regional.zh_TW.js'/>" type="text/javascript"></script>
</c:if> --%>


<script type="text/javascript">
$(function(){
	var languageType="${languageType}";
	var dwzurl="<c:url value='/res/dwz/dwz.frag.xml'/>";
	if("zh-CN"==languageType){
		dwzurl="<c:url value='/res/dwz/dwz.frag_zh_CN.xml'/>";
	}else if("en-US"==languageType){
		dwzurl="<c:url value='/res/dwz/dwz.frag_en_US.xml'/>";
	}else if("zh-TW"==languageType){
		dwzurl="<c:url value='/res/dwz/dwz.frag_zh_TW.xml'/>";
	}
	DWZ.init(dwzurl, {
		//loginTitle:"登录",	// 弹出登录对话框
		loginUrl:"<c:url value='/login.do' />",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301, forbidden:403}, //【可选】 403权限
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, 
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<c:url value='/res/dwz/themes'/>"});
		}
	});
	DWZ.ajaxDone =function (json){
		if(json.statusCode == DWZ.statusCode.error) {
			if(json.message && alertMsg) alertMsg.error(json.message);
		} else if (json.statusCode == DWZ.statusCode.timeout) {
			if(alertMsg) alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:DWZ.loadLogin});
			else DWZ.loadLogin();
		}else if (json.statusCode == DWZ.statusCode.forbidden) {  //添加状态
	        if(alertMsg) alertMsg.info(json.message || DWZ.msg("forbidden"), {okCall:function(){
	         //navTab.closeCurrentTab();
	        }});  
	        else navTab.closeCurrentTab();  
    	} else {
			if(json.message && alertMsg) alertMsg.correct(json.message);
		};
	};
	
	$.fn.extend({
		/**
		 * @param {Object} op: {type:GET/POST, url:ajax请求地址, data:ajax请求参数列表, callback:回调函数 }
		 */
		ajaxUrl: function(op){
			var $this = $(this);
			$this.trigger(DWZ.eventType.pageClear);
			$.ajax({
				type: op.type || 'GET',
				url: op.url,
				data: op.data,
				cache: false,
				success: function(response){
					var json = DWZ.jsonEval(response);
					if (json.statusCode==DWZ.statusCode.error){
						if (json.message) alertMsg.error(json.message);
					} else {
						$this.html(response).initUI();
						if ($.isFunction(op.callback)) op.callback(response);
					}
					
					if (json.statusCode==DWZ.statusCode.timeout){
						if ($.pdialog) $.pdialog.checkTimeout();
						if (navTab) navTab.checkTimeout();
	
						alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
							DWZ.loadLogin();
						}});
					} 
					
					if (json.statusCode==DWZ.statusCode.forbidden){
						// 关闭当前Tab  
						var isdialig=(op.callback+"").indexOf("dialog"); 
						if(isdialig==-1)
							 navTab.closeCurrentTab(); 
						else	
							$.pdialog.closeCurrent();

					    alertMsg.info(json.message || DWZ.msg("forbidden"), {okCall:function(){
					        if ($.pdialog) $.pdialog.checkTimeout();  
					        if (navTab) navTab.checkTimeout();  
					    }});
					}  
				},
				error: DWZ.ajaxError,
				statusCode: {
					503: function(xhr, ajaxOptions, thrownError) {
						alert(DWZ.msg("statusCode_503") || thrownError);
					}
				}
			});
		}});
});
</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo"  href="javascript:void(0)">标志</a>
				<ul class="nav">
					<li><a href="javascript:void(0)">您好，${loginUser.name}</a></li>
					<li><a href="<c:url value='/user/toUpdatepassword.do'/>" target="dialog" mask=true rel="updatePassword" title="修改密码" width=500 height=300>修改密码</a></li>
					<li><a href="<c:url value='/login/logout.do' />">退出</a></li>
				</ul>
                <ul class="themeList" id="themeList">
                    <li theme="default"><div>蓝色</div></li>
                    <li theme="green"><div>绿色</div></li>
                    <li theme="purple"><div>紫色</div></li>
                    <li theme="silver"><div>银色</div></li>
                    <li theme="azure"><div class="selected">天蓝</div></li>
                </ul>
			</div>
			<!-- navMenu -->
		</div>
		<!-- 导航栏的开始 -->
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>菜单</h2><div>收缩</div></div>
				<div class="accordion" fillspace="sidebar">
						<div class="accordionHeader">
                            <h2><span>Folder</span>学生信息</h2>
                        </div>
                        <div class="accordionContent">
                            <ul class="tree treeFolder">
                                <li><a href="<c:url value='/studentController/list.do'/>" target="navTab" rel="studentList" title="学生列表">学生列表</a></li>
                                
                            </ul>
                        </div>
				 
				
					<
				</div>
			</div>
		</div>
		<!-- 导航栏的结束 -->
		
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:void(0);"><span><span class="home_icon">主页</span></span></a></li><!--homePage  -->
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:void(0);">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
                        </div>
                    </div>
                </div>
            </div>
        </div>

	    <div id="footer">Copyright &copy; 2010 <a href="#" target="navTab" external="true"  rel="externald">baidu</a> 京ICP备05019125号-10</div>
    </div>
</body>
</html>