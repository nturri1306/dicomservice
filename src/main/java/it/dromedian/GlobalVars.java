package it.dromedian;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class GlobalVars {

	//public static Hashtable param = new Hashtable();

	public static String port="8080";
	public static String dicomStorage;

	public static List<String> aext = new ArrayList<String>();

	public YAMLConfig myConfig = new YAMLConfig() ;

	public static YAMLConfig myConfig2 = new YAMLConfig() ;

	public static Logger LOGGER=null;
	
	public static boolean isDebug=false;

}
