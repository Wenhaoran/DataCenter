package cn.digitalpublishing.util.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
	
	public static boolean testFTPConnect(String hostname,String username, String password){
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 是否成功登录FTP服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean uploadFile(String hostname, int port,String username, String password, String pathname, String fileName, InputStream inputStream) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 是否成功登录FTP服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}

			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.makeDirectory(pathname);
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 上传文件（可对文件进行重命名）
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器后的文件名称
	 * @param originfilename
	 *            待上传文件的名称（绝对地址）
	 * @return
	 */
	public static boolean uploadFileFromProduction(String hostname, int port,
			String username, String password, String pathname, String filename,
			String originfilename) {
		boolean flag = false;
		try {
			InputStream inputStream = new FileInputStream(new File(
					originfilename));
			flag = uploadFile(hostname, port, username, password, pathname,
					filename, inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 上传文件（不可以进行文件的重命名操作）
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param originfilename
	 *            待上传文件的名称（绝对地址）
	 * @return
	 */
	public static boolean uploadFileFromProduction(String hostname, int port,
			String username, String password, String pathname,
			String originfilename) {
		boolean flag = false;
		try {
			String fileName = new File(originfilename).getName();
			InputStream inputStream = new FileInputStream(new File(
					originfilename));
			flag = uploadFile(hostname, port, username, password, pathname,
					fileName, inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器保存目录
	 * @param filename
	 *            要删除的文件名称
	 * @return
	 */
	public static boolean deleteFile(String hostname, int port,
			String username, String password, String pathname, String filename) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			// 登录FTP服务器
			ftpClient.login(username, password);
			// 验证FTP服务器是否登录成功
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {
				}
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 * 
	 * @param hostname
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口号
	 * @param username
	 *            FTP登录帐号
	 * @param password
	 *            FTP登录密码
	 * @param pathname
	 *            FTP服务器文件目录
	 * @param filename
	 *            文件名称
	 * @param localpath
	 *            下载后的文件路径
	 * @return
	 */
	public static boolean downloadFile(String hostname, int port,String username, String password, String pathname, String filename,String localpath){
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = login(hostname,0,username,password);
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {
					File localFile = new File(localpath + "/" + file.getName());
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return false;
	}
	
	public static List<File> getFileList(String hostname, int port,String username, String password , String pathname)throws Exception{
		List<File> fileList = new ArrayList<File>();
		FTPClient ftpClient = login(hostname,0,username,password);
		//ftp://192.168.0.109       /import/excel/upload_two/path/		\\import\\excel\\upload_two\\path\\
		ftpClient.changeWorkingDirectory(pathname);
		FTPFile[] ftpFiles = ftpClient.listFiles();
		for(FTPFile ftpFile : ftpFiles){
			File file = new File(ftpFile.getName());
			OutputStream os = new FileOutputStream(file);
			ftpClient.retrieveFile(ftpFile.getName(), os);
			os.close();
			fileList.add(file);
		}
		ftpClient.logout();
		return fileList;
	}

	public static FTPClient login(String hostname, int port, String username, String password) throws Exception{
		FTPClient ftpClient = new FTPClient();
		
		if(port != 0){
			ftpClient.connect(hostname, port);
		}else{
			ftpClient.connect(hostname);
		}
		// 登录FTP服务器
		ftpClient.login(username, password);
		
		ftpClient.enterLocalPassiveMode(); //加这一句，更改连接模式
		int replyCode = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			throw new Exception("FTP登录失败");
		}
		
		return ftpClient;
	}

	public static void remove(String address, int i, String username, String password, String fileName,String pathOld,String pathNew)throws Exception {
		FTPClient ftpClient = login(address,0,username,password);
		
		InputStream is = null;  
		ftpClient.changeWorkingDirectory(pathOld); 
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
		 is = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"), "iso-8859-1"));  
         // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题  
         ftpClient.getReply();  
         if (is != null) {  
             ftpClient.changeWorkingDirectory(pathNew);  
             ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), is);  
         }  
         
		/*ftpClient.setBufferSize(1024); 
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		ftpClient.retrieveFile(pathOld+fileName, fos);
		ByteArrayInputStream in=new ByteArrayInputStream(fos.toByteArray());
		ftpClient.storeFile(pathNew+fileName, in);
		fos.close();
		in.close();*/
        
		ftpClient.dele(pathOld+fileName);
		
		is.close();
		ftpClient.logout();
	}
	
}
