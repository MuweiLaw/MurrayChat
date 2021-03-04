import com.alibaba.fastjson.JSON;
import com.murray.entity.LastLoginInfo;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Murray Law
 * @describe
 * @createTime 2020/12/29
 */
public class TestIo {
    public static void main(String[] args) {
        writeLastLoginInfo(new LastLoginInfo());
    }
    private static void writeLastLoginInfo(LastLoginInfo loginInfo) {
        byte[] sourceByte = JSON.toJSONBytes(loginInfo);

        if(null != sourceByte){

            try {

                File file = new File(System.getProperty("user.dir") + "\\setting\\lastLogin"); //文件路径（路径+文件名）

                if (!file.exists()) { //文件不存在则创建文件，先创建目录

                    File dir = new File(file.getParent());

                    dir.mkdirs();

                    file.createNewFile();

                }

                FileOutputStream outStream = new FileOutputStream(file); //文件输出流用于将数据写入文件

                outStream.write(sourceByte);

                outStream.close(); //关闭文件输出流

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
    }

}
