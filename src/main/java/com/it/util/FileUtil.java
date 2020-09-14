package com.it.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * @ClassName: FileUtil  
 * @Description: TODO  文件上传工具类
 * @author Administrator  
 * @date 2019年11月2日  
 *
 */
public class FileUtil {
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
