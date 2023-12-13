package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	public static Properties properties;
	private final static String propertyFilePath = System.getProperty("user.dir")
			+ "/src/test/resources/Config.properties";

	public static void loadConfig() throws Throwable {

		try {
			FileInputStream fis;
			fis = new FileInputStream(propertyFilePath);
			properties = new Properties();
			try {
				properties.load(fis);
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}

	}

	public static String getBrowserType() {
		String browserType = properties.getProperty("browser");
		return browserType;
	}

	public static String getApplicationUrl() {
		String url = properties.getProperty("baseURL");
		return url;
	}
}
