package com.hewentian.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <b>DFA</b> 是
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2019-03-26 14:56:16
 * @since JDK 1.8
 */
public class DFA {
    public static final int MIN_MATCH_TYPE = 1;      //最小匹配规则
    public static final int MAX_MATCH_TYPE = 2;      //最大匹配规则

    /**
     * 敏感词树，构建一个DFA算法模型
     */
    private Map<String, String> sensitiveWordTree;

    /**
     * 敏感词树：
     * *
     * 中 = {
     * * * isEnd = 0
     * * * 华 = {
     * * * * isEnd = 1
     * * * * 民 = {
     * * * * * isEnd = 0
     * * * * * 国 = {
     * * * * * * isEnd = 1
     * * * * * }
     * * * * }
     * * * * 帝  = {
     * * * * * isEnd = 0
     * * * * * 国 = {
     * * * * * * isEnd = 1
     * * * * * }
     * * * * }
     * * * }
     * }
     * *
     * 三 = {
     * * * isEnd = 0
     * * * 民 = {
     * * * * isEnd = 0
     * * * * 主 = {
     * * * * * isEnd = 0
     * * * * * 义 = {
     * * * * * * isEnd = 1
     * * * * * }
     * * * * }
     * * * }
     * }
     *
     * @param words 敏感词库
     */
    public void init(Set<String> words) {
        sensitiveWordTree = new HashMap(words.size());
        Map nowTree = null;

        for (String word : words) {
            nowTree = sensitiveWordTree;

            for (int i = 0; i < word.length(); i++) {
                char wordChar = word.charAt(i);
                Object wordTree = nowTree.get(wordChar);

                if (wordTree != null) {
                    nowTree = (Map) wordTree;
                } else {
                    Map<String, String> newWordTree = new HashMap<String, String>();
                    newWordTree.put("isEnd", "0");
                    nowTree.put(wordChar, newWordTree);
                    nowTree = newWordTree;
                }

                if (i == word.length() - 1) {
                    nowTree.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则
     * @return 若包含返回true，否则返回false
     */
    public boolean isContaintSensitiveWord(String txt, int matchType) {
        boolean flag = false;

        for (int i = 0; i < txt.length(); i++) {
            int length = checkSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
            if (length > 0) {    //大于0存在，返回true
                flag = true;
                break;
            }
        }

        return flag;
    }

    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWords = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {
            int length = checkSensitiveWord(txt, i, matchType);
            if (length > 0) {
                sensitiveWords.add(txt.substring(i, i + length));
                i = i + length - 1;    // 减1, 因为for会自增
            }
        }

        return sensitiveWords;
    }

    private int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;    // 敏感词结束标识位
        int matchWords = 0;     // 匹配词的个数，默认为0
        Map nowTree = sensitiveWordTree;

        for (int i = beginIndex; i < txt.length(); i++) {
            char word = txt.charAt(i);
            nowTree = (Map) nowTree.get(word);

            if (null == nowTree) {     // 不存在，返回
                break;
            }

            // 存在，匹配词的个数加1
            matchWords++;

            if ("1".equals(nowTree.get("isEnd"))) {
                flag = true;
                if (MIN_MATCH_TYPE == matchType) {
                    break;
                }
            }
        }

        if (matchWords < 2 || !flag) {
            matchWords = 0;
        }

        return matchWords;
    }

    public static void main(String[] args) {
        Set<String> keywords = new HashSet<>();
        keywords.add("对象");
        keywords.add("语言");
        keywords.add("静态");
        keywords.add("程序员");

        DFA dfa = new DFA();
        dfa.init(keywords);

        String string = "Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，" +
                "因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，" +
                "允许程序员以优雅的思维方式进行复杂的编程。Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、" +
                "多线程、动态性等特点。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等";

        long beginTime = System.currentTimeMillis();
        Set<String> set = dfa.getSensitiveWord(string, MIN_MATCH_TYPE);
        long endTime = System.currentTimeMillis();

        System.out.println("包含敏感词的个数为: " + set.size() + ", 包含: " + set);
        System.out.println("耗时：" + (endTime - beginTime));
    }
}
