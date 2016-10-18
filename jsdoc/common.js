/**
 * 
 * 从cookie中获取数据
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月16日 下午7:14:06
 * 
 * @param id 一般为用户的id
 * @param type 缓存的类型
 * @return 一个数组，其中的cookie元素K-V在数组中以#分隔
 * 
 */
function getXCookie(id, type) {
	//得到本域下的所有cookie  -- "id_type_num_title, hwt_history_1_ab=828; hwt_history_2_cd=lisi"
	var cookies = document.cookie;  
	if (cookies.length == 0) { 
		return "";
	}
	
	var keyPrefix = id + "_" + type; // 区分用户和缓存的数据类型
    var arrCookie = cookies.split(";");
    var returnArray = new Array();

    for(var i = 0, len = arrCookie.length; i < len; i++) {
    	var ac = arrCookie[i].split("=");
    	var acKey = ac[0];
    	acKey = $.trim(acKey);
    	if(acKey.indexOf(keyPrefix) != -1) {
    		acKey = acKey.substr(keyPrefix.length + 1);
    		
    		if (acKey != "currentIndex") {
    			acKey = acKey.substr(acKey.indexOf("_") + 1);
    			var acValue = unescape(ac[1]);
    			returnArray.push(acKey + "#" + acValue);    			
    		}
         }
    }

    return returnArray;
}

/**
 * 
 * 从cookie中获取数据，根据指定的name来获取
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月16日 下午7:14:06
 * 
 * @param c_name cookie的名字
 * @return 一个值
 * 
 */
function getXCookie2(c_name) {
	if (document.cookie.length>0) {
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1) {
			c_start=c_start + c_name.length+1; 
			c_end=document.cookie.indexOf(";",c_start);
			if (c_end==-1){
				c_end=document.cookie.length;
			}
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	
	return "";
}

/**
* 
* 向cookie中缓存数据
*
* @author <a href="mailto:wentian.he@qq.com">hewentian</a>
* @date 2016年5月16日 下午7:22:06
* 
* @param id 一般为用户的id
* @param type 缓存的类型
* @param c_name 要缓存的key
* @param value 要缓存的value
* @param expiredays 该cookie的有效时间，为日, 可为空，为空则不设置时间限制
* @param maxSize 该type的cookie最多可以存多少个, 整型值
* 
*/
function setXCookie(id, type, c_name, value, expiredays, maxSize) {
	var cookies = document.cookie;
	var keyPrefix = id + "_" + type;
	
	// 如果是历史搜索，则要去重
	if (type == SEARCH_HISTORY) {
		if (isXCookieContains(c_name)) {
			return;
		}
	}
	
	var currentIndex = 0; // 当前类型的下标数，从0开始，这里假设为0
	var currentIndexStr = keyPrefix + "_currentIndex=";
	var cStart = cookies.indexOf(currentIndexStr);
	var cEnd = 0;
	if (cStart != -1) {
		cStart = cStart + currentIndexStr.length;
		cEnd = cookies.indexOf(";", cStart);
		if (cEnd == -1){
			cEnd = cookies.length;
		}
		
		currentIndex = parseInt(cookies.substring(cStart, cEnd));
		currentIndex += 1;
	}
	
	if (type == SEARCH_HISTORY && currentIndex == maxSize) { // 历史记录最多存maxSize个
		currentIndex = 0;
	}
	
	// 删除之前的
	deleteXCookieHistory(keyPrefix + "_" + currentIndex);
	var cookieString = keyPrefix + "_" + currentIndex + "_" + c_name + "=" + escape(value);
	if (expiredays != null) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		cookieString += "; expires="+exdate.toGMTString();
	}
	
	document.cookie = cookieString + "; path=/";
			
	// 设置当前值
	document.cookie = currentIndexStr + currentIndex + "; path=/";
}

/**
* 
* 向cookie中缓存数据
*
* @author <a href="mailto:wentian.he@qq.com">hewentian</a>
* @date 2016年5月16日 下午7:22:06
* 
* @param c_name 要缓存的key
* @param value 要缓存的value
* @param expiredays 该cookie的有效时间，为日, 可为空，为空则不设置时间限制
* 
*/
function setXCookie2(c_name, value, expiredays) {
	var cookieString = c_name+ "=" +escape(value);
	
	if (expiredays != null) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		cookieString += "; expires="+exdate.toGMTString();
	}
	document.cookie = cookieString + "; path=/";
}

/**
 * 
 * 判断cookie中是否含有这个name
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月21日 下午2:14:06
 * 
 * @param cname cookie的名字
 * @return true/false
 * 
 */
function isXCookieContains(cname) {
	var cookies = document.cookie;  
	if (cookies.length == 0) { 
		return false;
	}
	
	var keyPrefix = getXCookie2("USER_ID") + "_" + SEARCH_HISTORY; // 区分用户和缓存的数据类型
    var arrCookie = cookies.split(";");

    for(var i = 0, len = arrCookie.length; i < len; i++) {
    	var ac = arrCookie[i].split("=");
    	var acKey = ac[0];
    	acKey = $.trim(acKey);
    	if(acKey.indexOf(keyPrefix) != -1) {
    		acKey = acKey.substr(keyPrefix.length + 1);
    		
    		if (acKey != "currentIndex") {
    			acKey = acKey.substr(acKey.indexOf("_") + 1);
    			
    			if (acKey == cname) {
    				return true;
    			}
    		}
         }
    }

    return false;
}

/**
 * 
 * 删除cookie中指定前缀的cookie
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月21日 下午2:14:06
 * 
 * @param cnamePrefix cookie的名字前缀
 * @return true/false
 * 
 */
function deleteXCookieHistory(cnamePrefix) {
	var cookies = document.cookie;  
	if (cookies.length == 0) { 
		return false;
	}
	
    var arrCookie = cookies.split(";");

    for(var i = 0, len = arrCookie.length; i < len; i++) {
    	var ac = arrCookie[i].split("=");
    	var acKey = ac[0];
    	acKey = $.trim(acKey);
    	if(acKey.indexOf(cnamePrefix) != -1) {
    		var date = new Date(); 
            date.setTime(date.getTime() - 24 * 60 * 60 * 1000); 
            var cookStr  = arrCookie[i] + "; expires=" + date.toGMTString(); 
            
    	    document.cookie = cookStr + "; path=/";
    	    return true;
         }
    }

    return false;
}

//var myDate = new Date();
//myDate.getYear();        //获取当前年份(2位)
//myDate.getFullYear();    //获取完整的年份(4位,1970-????)
//myDate.getMonth();       //获取当前月份(0-11,0代表1月)
//myDate.getDate();        //获取当前日(1-31)
//myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
//myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
//myDate.getHours();       //获取当前小时数(0-23)
//myDate.getMinutes();     //获取当前分钟数(0-59)
//myDate.getSeconds();     //获取当前秒数(0-59)
//myDate.getMilliseconds();    //获取当前毫秒数(0-999)
//myDate.toLocaleDateString();     //获取当前日期
//var mytime=myDate.toLocaleTimeString();     //获取当前时间
//myDate.toLocaleString( );        //获取日期与时间

/**
 * 格式化日期
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月18日 下午3:15:26
 * 
 * @param formatStr 格式化字符串，用法如下, new Date().Format('yyyy-MM-dd HH:mm:ss')
 */
Date.prototype.Format = function(formatStr) {
    var str = formatStr;   
    var Week = ['日','一','二','三','四','五','六'];  
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    var month = this.getMonth() + 1;
    str=str.replace(/MM/,month>9?month.toString():'0' + month);   
    str=str.replace(/M/g,month);   
  
    str=str.replace(/w|W/g,Week[this.getDay()]);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
}

/**
* 
* 获取传进来的很长的URL串中的域名部分
*
* @author <a href="mailto:wentian.he@qq.com">hewentian</a>
* @date 2016年5月21日 下午12:25:10
* 
* @param url 要截取的URL
* 
*/
function getUrlDomain(url) {
	var startIndex = 0;
	if (url.startsWith("http://")) {
		startIndex = 7
	} else if (url.startsWith("https://")) {
		startIndex = 8
	}
	
	var endIndex = url.indexOf("/", startIndex);
	url = url.substr(0, endIndex);
	
	return url;
}

/**
 * 产生随机码
 */
function validateNum() {
	// [0-9a-z]
	var numCharArray = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	var validNum = '';
	for (var i = 0; i < 8; i++) {
		var index = Math.floor(Math.random() * numCharArray.length);
		validNum += numCharArray[index];
	}
				
	var validNum2 = prompt('请输入以下验证码: ' + validNum);
	if (validNum != validNum2) {
		alert('验证码错误.')
		return;
	}
}