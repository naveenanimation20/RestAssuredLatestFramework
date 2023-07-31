package com.qa.api.propconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Properties prop;
	private static FileInputStream ip;

	public static Properties initProp() {

		if (prop == null) {
			prop = new Properties();
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return prop;
	}
	
	public static String getProperty(String key) {
		initProp();
        return prop.getProperty(key);
    }
	
	public static void setProperty(String key, String value) {
		initProp();
        prop.setProperty(key, value);
        System.out.println(prop);
    }

}
