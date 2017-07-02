package com.design;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;

public class SqliteTool {
	// 此工具事为了将指定目录下的excel文件，输出到一个sqlite库中
	public static void main(String args[]) throws SQLException, IOException {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(Runtime.getRuntime()
				.availableProcessors() * 3, Runtime.getRuntime()
				.availableProcessors() * 3, 5, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());
		File file = new File("data_excel\\excel_files_for_server.txt");
		Statement stat = null;
		Connection c = null;
		BufferedReader bf = null;
		try {
			String path = file.getAbsolutePath();
			// System.out.println(path);
			String path1 = path.split("GameConfig")[0];
			path1 += args[0];
			/* "Tools\\GameServer\\build\\config\\system_data" */
			// System.out.println(path1);
			// 注册数据库连接
			System.out.println(path1);
			Class.forName("org.sqlite.JDBC");
			// 获得数据库连接
			c = DriverManager.getConnection("jdbc:sqlite:" + path1);
			c.setAutoCommit(false);
			stat = c.createStatement();

			// 接受参数为excel的目录、输出sqlite文件名字
			FileReader fr = new FileReader(file);
			bf = new BufferedReader(fr);
			String head = "data_excel\\";
			// File[] files = file.listFiles();
			ArrayList<Workbook> list = new ArrayList<>();
			String p = null;
			while ((p = bf.readLine()) != null) {
				if (p.equals(""))
					continue;
				System.out.println(p);

				pool.execute(new MyTask(list, head + p, stat));

			}
			System.out.println("==================等待插入完成===================");

			pool.shutdown();// 只是不能再提交新任务，等待执行的任务不受影响

			try {
				boolean loop = true;
				do { // 等待所有任务完成
					loop = !pool.awaitTermination(2, TimeUnit.SECONDS); // 阻塞，直到线程池里所有任务结束
				} while (loop);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// readwb.getCreationHelper()
			// readwb.setForceFormulaRecalculation(true);
			// Sheet的下标是从0开始

			c.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stat.close();
			c.close();
			bf.close();
			System.out.println("插入完成");
			System.in.read();
		}

	}

}