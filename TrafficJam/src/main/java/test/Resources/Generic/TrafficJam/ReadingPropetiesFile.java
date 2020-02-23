package test.Resources.Generic.TrafficJam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadingPropetiesFile {

	public static void main(String[] args) throws IOException {


		File f=new File("Environment.properties");
		
		FileInputStream fis=new FileInputStream(f);
		
		Properties prop=new Properties();
		
		prop.load(fis);
		
		
		System.out.println(prop.getProperty("dev_environment"));
		
		
		
		

	}

}
