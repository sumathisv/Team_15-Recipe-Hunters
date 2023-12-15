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
	public static String getInputExcel() {
		String url = properties.getProperty("inputExcel");
		return url;
	}
	
	public static String Diebatichref() {
		String url = properties.getProperty("diabeticHref");
		return url;
	}
	
	public static String hypothyrohref() {
		String url = properties.getProperty("hypothyroHref");
		return url;
	}
	public static String hypertensionhref() {
		String url = properties.getProperty("hypertensionHref");
		return url;
	}
	public static String pcoshref() {
		String url = properties.getProperty("pcosHref");
		return url;
	}
}
