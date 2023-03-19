package cn.com.util;

import java.io.*;

/**
 * @description:
 * @author: Count
 * @time: 2022/2/5
 */
public class ResourcesUtils {

    public static File getResource(String path, String prefix, String suffix) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        InputStream is = null;
        try {
            File f = File.createTempFile(prefix, suffix);
            f.deleteOnExit();
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int length;
            byte[] bytes = new byte[1024];
            is = ResourcesUtils.class.getResourceAsStream(path);
//            bis = new BufferedInputStream(ResourcesUtils.class.getResourceAsStream(path));
            while ((length = is.read(bytes)) > 0) {
                bos.write(bytes, 0, length);
                bos.flush();
            }
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

}
