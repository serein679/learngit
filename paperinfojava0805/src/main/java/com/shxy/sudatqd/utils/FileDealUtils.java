package com.shxy.sudatqd.utils;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;

/**
 * @author Lenl
 * @version v1.0
 * @create 2022-09-10 8:45
 * @description 提供一些文件处理过程中的工具，如判断文件可用性
 */
public class FileDealUtils {
    /**
     * 根据路径判断为文件是否可用
     *
     * @param path 文件路径
     * @return
     */
    public boolean isFileCanUse(String path) {
        File file = new File(path);
        try {
            Tika tika = new Tika();
            tika.parseToString(file);
            return true;
        } catch (TikaException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
