package com.hewentian.util;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * <p>
 * <b>JsUtil</b> 是 执行javascript的工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月9日 下午3:04:55
 * @since JDK 1.7
 * 
 */
public class JsUtil {
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

	/** engine */
	private static ScriptEngine scriptEngine = null;

	/** 存放预编译的脚本 */
	private static Map<String, CompiledScript> compiledScriptMap = new HashMap<String, CompiledScript>();

	static {
		try {
			getScriptEngine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取engine, 相当于初始化的作用
	 * 
	 * @date 2016年9月9日 下午5:19:51
	 * @return
	 * @throws Exception
	 */
	public static ScriptEngine getScriptEngine() throws Exception {
		if (null == scriptEngine) {
			scriptEngine = scriptEngineManager.getEngineByName("js");
		}

		if (null == scriptEngine) {
			List<ScriptEngineFactory> scriptEngineFactories = scriptEngineManager.getEngineFactories();
			if (null != scriptEngineFactories && !scriptEngineFactories.isEmpty()) {
				for (ScriptEngineFactory sef : scriptEngineFactories) {
					scriptEngine = sef.getScriptEngine();
					break;
				}
			}
		}

		if (null == scriptEngine) {
			throw new Exception("java version not support javascript.");
		}

		return scriptEngine;
	}

	/**
	 * 初始化指定的脚本
	 * 
	 * @date 2016年9月9日 下午5:20:48
	 * @param script
	 *            要执行的脚本
	 */
	public static void initScript(String script) {
		try {
			scriptEngine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取compilable
	 * 
	 * @date 2016年9月9日 下午5:21:10
	 * @return
	 */
	public static Compilable getCompilable() {
		if (scriptEngine instanceof Compilable) {
			return (Compilable) scriptEngine;
		} else {
			return null;
		}
	}

	/**
	 * 增加一个预编译的脚本
	 * 
	 * @date 2016年9月9日 下午5:21:25
	 * @param key
	 *            用于指定脚本的key
	 * @param scriptOrPath
	 *            是脚本还是文件路径
	 * @param isFile
	 *            是否为文件
	 */
	public static void addCompile(String key, String scriptOrPath, boolean isFile) {
		try {
			if (isFile) {
				CompiledScript cs = getCompilable().compile(new FileReader(scriptOrPath));
				compiledScriptMap.put(key, cs);
			} else {
				CompiledScript cs = getCompilable().compile(scriptOrPath);
				compiledScriptMap.put(key, cs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行脚本
	 * 
	 * @date 2016年9月9日 下午5:22:33
	 * @param scriptOrPath
	 *            是脚本还是文件路径
	 * @param bindings
	 *            参数, 可为空
	 * @param isFile
	 *            是否为文件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T execOne(String scriptOrPath, Bindings bindings, boolean isFile) {
		Object o = null;

		try {
			if (isFile) {
				if (null == bindings) {
					o = scriptEngine.eval(new FileReader(scriptOrPath));
				} else {
					o = scriptEngine.eval(new FileReader(scriptOrPath), bindings);
				}
			} else {
				if (null == bindings) {
					o = scriptEngine.eval(scriptOrPath);
				} else {
					o = scriptEngine.eval(scriptOrPath, bindings);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == o) {
			return null;
		}
		return (T) o;
	}

	/**
	 * 执行指定的预编译脚本
	 * 
	 * @date 2016年9月9日 下午5:23:06
	 * @param key
	 *            用于指定脚本的key
	 * @param bindings
	 *            参数, 可为空
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T execCompiledScript(String key, Bindings bindings) {
		Object o = null;
		CompiledScript cs = compiledScriptMap.get(key);
		if (null == cs) {
			return null;
		}

		try {
			if (null == bindings) {
				o = cs.eval();
			} else {
				o = cs.eval(bindings);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == o) {
			return null;
		}
		return (T) o;
	}
}