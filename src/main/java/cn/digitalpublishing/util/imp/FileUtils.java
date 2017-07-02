package cn.digitalpublishing.util.imp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
 
/**
 * 文件操作工具类 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * 
 */
public class FileUtils  {
 
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
 
    public static void remove(String fileName,String pathName)throws Exception{
    	copyFile(fileName,pathName);
    	delFile(fileName);
    }
 
    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     */
    public static boolean copyFile(String srcFileName, String descFileName) throws Exception{
        return FileUtils.copyFileCover(srcFileName, descFileName, false);
    }
 
    /**
     * 复制单个文件
     */
    public static boolean copyFileCover(String srcFileName, String descFileName, boolean coverlay)throws Exception {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            logger.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            logger.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
            return false;
        }
        File descFile = new File(descFileName);
        if (!descFile.exists()) {
           throw new Exception("文件夹不存在");
        }
        if(descFile.isDirectory()){
        	descFile = new File(descFileName+"\\"+srcFile.getName());
        }
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                logger.debug("目标文件已存在，准备删除!");
                if (!FileUtils.delFile(descFileName)) {
                    logger.debug("删除目标文件 " + descFileName + " 失败!");
                    return false;
                }
            } else {
                logger.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                logger.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    logger.debug("创建目标文件所在的目录失败!");
                    return false;
                }
            }
        }
 
        // 准备复制文件
        // 读取的位数
        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            logger.debug("复制单个文件 " + srcFileName + " 到" + descFileName + "成功!");
            return true;
        } catch (Exception e) {
            logger.debug("复制文件失败：" + e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    oute.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) throws Exception {
        return FileUtils.copyDirectoryCover(srcDirName, descDirName, false);
    }
 
    /**
     * 复制整个目录的内容
     */
    public static boolean copyDirectoryCover(String srcDirName, String descDirName, boolean coverlay) throws Exception{
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            logger.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            logger.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                logger.debug("目标目录已存在，准备删除!");
                if (!FileUtils.delFile(descDirNames)) {
                    logger.debug("删除目录 " + descDirNames + " 失败!");
                    return false;
                }
            } else {
                logger.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
                return false;
            }
        } else {
            // 创建目标目录
            logger.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                logger.debug("创建目标目录失败!");
                return false;
            }
 
        }
 
        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则直接复制
            if (files[i].isFile()) {
                flag = FileUtils.copyFile(files[i].getAbsolutePath(), descDirName + files[i].getName());
                // 如果拷贝文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，则继续复制目录
            if (files[i].isDirectory()) {
                flag = FileUtils.copyDirectory(files[i].getAbsolutePath(),
                        descDirName + files[i].getName());
                // 如果拷贝目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }
 
        if (!flag) {
            logger.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
            return false;
        }
        logger.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
        return true;
 
    }
 
    /**
     * 删除文件，可以删除单个文件或文件夹
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            logger.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtils.deleteFile(fileName);
            } else {
                return FileUtils.deleteDirectory(fileName);
            }
        }
    }
     
    /**
     * 删除单个文件
     * @param file 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        if(null == file) {
            logger.error("输入File对象为null，无法删除。") ; 
            return false ;
        }
         
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                logger.debug("删除单个文件 ，成功["+file+"]");
                return true;
            } else {
                logger.error("删除单个文件，失败["+file+"]");
                return false;
            }
        } else {
            logger.error("文件删除失败，文件不存在！["+file+"]");
            return true;
        }
    }
 
    /**
     * 删除单个文件
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if(file.delete()) {
                logger.debug("删除单个文件 ，成功["+fileName+"]");
                return true;
            } else {
                logger.error("删除单个文件，失败["+fileName+"]");
                return false;
            }
        } else {
            logger.error("文件删除失败，文件不存在！["+fileName+"]");
            return true;
        }
    }
 
    /**
     * 
     * 删除目录及目录下的文件
     * 
     * @param dirName
     *            被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            logger.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i].getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }
 
        if (!flag) {
            logger.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            logger.debug("删除目录，成功["+dirName+"]");
            return true;
        } else {
            logger.debug("删除目录，失败["+dirName+"]");
            return false;
        }
 
    }
 
    /**
     * 创建单个文件
     * 
     * @param descFileName
     *            文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            logger.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            logger.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                logger.debug("创建文件所在的目录失败!");
                return false;
            }
        }
 
        // 创建文件
        try {
            if (file.createNewFile()) {
                logger.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                logger.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(descFileName + " 文件创建失败!");
            return false;
        }
 
    }
 
    /**
     * 创建目录
     * 
     * @param descDirName
     *            目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            logger.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            logger.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }
    }
 
    /**
     * 将内容写入文件
     * 
     * @param content
     * @param filePath
     */
    public static void writeFile(String content, String filePath) {
        try {
            if (FileUtils.createFile(filePath)) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.close();
                fileWriter.close();
            } else {
                logger.debug("生成失败，文件已存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 批量修改文件名称工具 <b>function:</b> 将指定目录下的文件的type类型的文件，进行重命名，命名后的文件将去掉type
     * <p>
     * example: 如果type = html； index.html.html -> index.html
     * </p>
     * <p>
     * example: 如果type = zh_CN； index.html.zh_CN -> index.html
     * </p>
     * <p>
     * batchRename("F:\\server\\chat-tomcat-7.0.32\\webapps\\jwchat", "zh_CN");
     * </p>
     * 
     * @author hoojo
     * @createDate 2012-5-16 下午02:16:48
     * @param path
     * @param type
     * @throws Exception
     */
    public static void batchRename(String path, String type) throws Exception {
        if (path == null || "".equals(path)) {
            throw new Exception("请输入要批量修改文件的目录！");
        }
        File dir = new File(path);
        File[] list = dir.listFiles();
        for (File file : list) {
            String name = file.getName();
            String[] s = name.split("\\.");
            if (s.length == 3 && type.equals(s[2])) {
                System.out.println(s[0] + "--" + s[1] + "--" + s[2]);
                file.renameTo(new File(path + "/" + s[0] + "." + s[1]));
            }
        }
    }
 
    public static void WriteJSON(String outPath, Object obj) {
        WriteJSON(outPath, null, obj);
    }
 
    public static void WriteJSON(String outPath, String perfix, Object obj) {
        try {
            String json = JSON.toJSONStringWithDateFormat(obj,
                    "yyyy-MM-dd HH:mm:ss");
            OutputStreamWriter out = new OutputStreamWriter(
                    new FileOutputStream(outPath), "UTF-8");
            out.write((null != perfix ? perfix : "") + json);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("生成JSON文件失败，原因：" + e.getMessage());
        }
        logger.debug("生成JSON文件-->" + outPath);
    }
 
    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * 
     * @param fileName
     *            文件的名
     */
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
            showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，rn这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
                if (((char) tempchar) != 'r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != 'r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == 'r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 随机读取文件内容
     * 
     * @param fileName
     *            文件名
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }
 
    /**
     * 显示输入流中还剩的字节数
     * 
     * @param in
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 修改文件名
     * 
     * @param filePath
     *            需改名的文件
     * @param definedName
     *            自定义名称
     * @return
     */
    public static boolean rename(String filePath, String definedName) {
        File file = new File(filePath);
        if (file.exists()) {
            String fileExt = file.getName()
                    .substring(file.getName().lastIndexOf(".") + 1)
                    .toLowerCase();
 
            String rename = file.getParent() + "/" + definedName + "."
                    + fileExt;
 
            if (file.renameTo(new File(rename))) {
                logger.debug("修改文件名成功：{} TO {}", file.getAbsoluteFile(), rename);
                return true;
            } else {
                logger.debug("修改文件名失败，该文件已存在：{} TO {}", file.getAbsoluteFile(),
                        rename);
                return false;
            }
        } else {
            logger.debug("该文件不存在：{}", file.getAbsoluteFile());
            return false;
        }
    }
     
    /**
     * 获取文件扩展名
     * 
     * @param filename
     * @return
     */
    public static String getExtend(String filename) {
        return getExtend(filename, "");
    }
 
    /**
     * 获取文件扩展名
     * 
     * @param filename
     * @return
     */
    public static String getExtend(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
 
            if ((i > 0) && (i < (filename.length() - 1))) {
                return (filename.substring(i+1)).toLowerCase();
            }
        }
        return defExt.toLowerCase();
    }
 
    /**
     * 获取文件名称[不含后缀名]
     * 
     * @param
     * @return String
     */
    public static String getFilePrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
    }
     
    /**
     * 获取文件名称[不含后缀名]
     * 不去掉文件目录的空格
     * @param
     * @return String
     */
    public static String getFilePrefix2(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }
 
    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    
    public static List<File> getExcelFileList(String path)throws Exception{
    	File srcDir = new File(path);
    	// 判断源目录是否存在
    	if (!srcDir.exists()) {
    		throw new Exception("复制目录失败，源目录 " + path + " 不存在!");
    	}
    	// 判断源目录是否是目录
    	else if (!srcDir.isDirectory()) {
    		throw new Exception("复制目录失败，" + path + " 不是一个目录!");
    	}

    	// 列出源目录下的所有文件名和子目录名
    	File[] files = srcDir.listFiles();
    	List<File> fileList = new ArrayList<File>();
    	for(File file: files){
    		String fileName = file.getName();
    		String suffixName = getExtend(fileName);
    		if(suffixName.equals("xls")||suffixName.equals("xlsx")){
    			fileList.add(file);
    		}
    	}
    	return fileList;
    }
    
    public static List<File> getTextFileList(String path)throws Exception{
    	File srcDir = new File(path);
    	// 判断源目录是否存在
    	if (!srcDir.exists()) {
    		throw new Exception("源目录 " + path + " 不存在!");
    	}
    	// 判断源目录是否是目录
    	else if (!srcDir.isDirectory()) {
    		throw new Exception("" + path + " 不是一个目录!");
    	}

    	// 列出源目录下的所有文件名和子目录名
    	File[] files = srcDir.listFiles();
    	List<File> fileList = new ArrayList<File>();
    	for(File file: files){
    		String fileName = file.getName();
    		String suffixName = getExtend(fileName);
    		if(suffixName.equals("txt")){
    			fileList.add(file);
    		}
    	}
    	return fileList;
    }
     
    
    public static void main(String[] args) {
        //System.out.println(formetFileSize(755996)); ;
        String s = "D:\\work\\app_server\\tomcat-7.0.55\\temp\\backup\\" ;
        String t = "D:\\work\\app_server\\tomcat-7.0.55\\webapps\\rosense\\attached\\ueditor\\" ;
        String t1 = "D:\\work\\app_server\\tomcat-7.0.55\\webapps\\rosense\\attached\\emp_touxiang\\" ;
        try {
            org.apache.commons.io.FileUtils.copyDirectoryToDirectory(new File(t),new File(s));
            org.apache.commons.io.FileUtils.copyDirectoryToDirectory(new File(t1),new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}