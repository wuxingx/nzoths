package com.targzon.generator.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties pros = null;
	
	private static final String PROPERTIES = "/jdbc.properties";

	static {
		if (pros == null) {
			load(PROPERTIES);
		}
	}

	public static void load(String fileName) {
		pros = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream(fileName);
		try {
			pros.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getString(String key) {
		return pros.getProperty(key);
	}

	public static Integer getInt(String key) {
		try {
			return Integer.parseInt(pros.getProperty(key));
		} catch (Exception e) {
			return 0;
		}
	}
}