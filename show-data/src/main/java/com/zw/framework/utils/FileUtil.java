package com.zw.framework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件操作工具类
 * @author zhangws
 *
 */
public class FileUtil {
    
    public static String filePath(String path, String folder) {
        if (path.endsWith(File.separator)) {
            return path + folder;
        } else {
            return path + File.separator + folder;
        }
    }

	/**
	 * 读取文件
	 * 
	 * @param path
	 * @return 文件内容
	 * @throws Exception
	 */
	public static byte[] readFile(String path) throws Exception {
		BufferedInputStream dis = new BufferedInputStream(new FileInputStream(
				new File(path)));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c = 0;
		while ((c = dis.read()) != -1) {
			baos.write(c);
		}
		dis.close();
		return baos.toByteArray();
	}

	/**
	 * 保存文件流到磁盘
	 * 
	 * @param path
	 * @param buffer
	 * @return 保存的文件名
	 */
	public static String writeFile(String path, byte[] buffer) {
		String fileName = System.currentTimeMillis() + ".jpg";
		FileOutputStream out = null;
		try {
			File file = new File(path + File.separator + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			out.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
			fileName = null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileName;
	}

	public static void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            InputStream is =  new BufferedInputStream(new FileInputStream(path));  
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
