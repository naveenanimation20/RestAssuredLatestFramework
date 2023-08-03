package com.qa.api.propconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private Properties prop;
	private FileInputStream ip;

	public Properties initProp() {

//		if (prop == null) {
			prop = new Properties();
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}

		return prop;
	}
	
	public String getProperty(String key) {
		initProp();
        return prop.getProperty(key);
    }
	
	public void setProperty(String key, String value) {
		System.out.println("setttt");
		initProp();
        prop.setProperty(key, value);
        System.out.println(prop);
    }

}
