package cn.digitalpublishing.util;

import org.apache.log4j.Logger;

public class Log {
	private static Logger rootlogger = Logger.getRootLogger();

	public static void printInfo(String string) {
		try {
			rootlogger.info("{INFO PRINTLN} -->  " + string + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printError(String string) {
		try {
			rootlogger.error("{ERROR PRINTLN} -->  " + string + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printDebug(String string) {
		try {
			rootlogger.debug("{DEBUG PRINTLN} -->  " + string + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}