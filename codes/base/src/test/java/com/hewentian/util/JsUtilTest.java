package com.hewentian.util;

import java.util.Date;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.SimpleBindings;

public class JsUtilTest {
	public static void main(String[] args) throws Exception {
		String script1 = "var a = 1; a;";
		double res1 = JsUtil.execOne(script1, null, false);
		System.out.println(res1);

		String script2 = "function sum(a, b){ return a + b;} sum(1,1);";
		double res2 = JsUtil.execOne(script2, null, false);
		System.out.println(res2);

		String script3 = "function sum(a, b){ return a + b;}";
		JsUtil.initScript(script3);
		double res3 = JsUtil.execOne("sum(1,2)", null, false);
		System.out.println(res3);

		String script4 = "var c = a + b; c;";
		Bindings param = new SimpleBindings();
		param.put("a", 2);
		param.put("b", 2);
		double res4 = JsUtil.execOne(script4, param, false);
		System.out.println(res4);

		JsUtil.addCompile("s1", script1, false);
		JsUtil.addCompile("s2", script4, false);
		double res5 = JsUtil.execCompiledScript("s1", null);
		System.out.println(res5);

		double res6 = JsUtil.execCompiledScript("s2", param);
		System.out.println(res6);

		// 在JS中调用JAVA的类是这样子的
		String script7 = "var Date = java.util.Date; var date = new Date(); date;";
		Date res7 = JsUtil.execOne(script7, null, false);
		System.out.println(res7);

		String script8 = "d://addAB.js";// 在src/main/resources中有此文件
		double res8 = JsUtil.execOne(script8, null, true);
		System.out.println(res8);

		System.out.println("-------------------- bindings --------------------");
		Bindings bindings = JsUtil.getScriptEngine().getBindings(ScriptContext.ENGINE_SCOPE);
		for (Entry<String, Object> e : bindings.entrySet()) {
			System.out.println(e.getKey() + ", " + e.getValue());
		}
	}
}