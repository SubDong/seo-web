package com.groot.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by subDong on 2014/12/26.
 */
public class KeywordUtils {
    private static Set<String> tkd = new HashSet<>();


    public static Set<String> executeTkd(Set<Document> doc) {
        Set<String> tkdPY = new HashSet<>();
        for (Document d:doc){
            tkdPY = PageUtils.getTargetKeywords(d);
            tkd.addAll(tkdPY);
        }
        return tkd;
    }

    public static String getPinYin(String chinese) {
        StringBuffer sb = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static Set<String> getPinyinTKD(Set<String> set) {
        Set<String> tkd = new HashSet<>();
        set.parallelStream().forEach(s -> {
            tkd.add(getPinYin(s));
        });
        return tkd;
    }
}
