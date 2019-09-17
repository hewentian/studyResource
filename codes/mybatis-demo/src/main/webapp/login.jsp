<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value='/res/login/styles.css'/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        function checkLogin() {
            var name = document.getElementById("name").value;
            var password = document.getElementById("password").value;
            return name != null && name != "" && password != null && password != "";
        }
        //隐藏 信息
        function hiddenMessage() {
        	document.getElementById("message").innerHTML="";
        }
    </script>
</head>
<body id="login">
<div id="wrappertop">
    <p style="color: red; font-size: 13px;" id="message">${message}</p>
</div>
<div id="wrapper">
    <div id="content">
        <div id="darkbanner" class="banner320">
            <h2>Login</h2>
        </div>
        <div id="darkbannerwrap"></div>
        <form name="form1" method="post" action="<c:url value='/login/index.do'/>" onsubmit="return checkLogin()">
            <fieldset class="form">
                <p>
                    <label for="name">Name:</label>
                    <input name="name" id="name" type="text" value="scott" onclick="hiddenMessage();">
                </p>
                <p>
                    <label for="password">Password:</label>
                    <input name="password" id="password" type="password" onclick="hiddenMessage();" value="tiger">
                </p>
                <button type="submit" class="positive" name="Submit">
                    <img src="<c:url value='/res/login/key.png' />" alt="Announcement">Login
                </button>
                <ul id="forgottenpassword">
                    <li class="boldtext">|</li>
                    <li><a href="#">Forgotten it?</a></li>
                </ul>
            </fieldset>
        </form>
    </div>
</div>

<div id="wrapperbottom_branding">
    <div id="wrapperbottom_branding_text"><a href="#" style="text-decoration:none">Powered By Share Suite</a>.</div>
</div>
</body>
</html>