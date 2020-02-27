package it.dromedian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DicomLib {


	  @Autowired
	    private static   YAMLConfig myConfig;
//YAMLConfig myConfig;

	//org.slf4j.Logger logger;

	 private static final Logger logger=LoggerFactory.getLogger( DicomLib.class);
	 

	  public String serverPort;
	  
	  public String sessionID;

		//private static ArrayList lista_series_uid = new ArrayList();
		
	//private static	List<String> listaUID = new ArrayList();

	public DicomLib(YAMLConfig myConfig)
	{
		this.myConfig = myConfig;
		
		
	}

	private void log(String str)
	{
		//System.out.println(str);
		//logger.trace(str);
		//GlobalVars.LOGGER.trace(str);
		logger.info("["+sessionID+"] "+str);
	}

	private void log_error(String str)
	{
		//System.out.println(str);
		//logger.error(str);
		logger.error("id: "+sessionID+" " +str);
	}

	//public static Hashtable param = new Hashtable();

	public static void main(String[] args) {

		 DicomLib dicomLib = new DicomLib(myConfig);

		    dicomLib.init();

		if (args[0].trim().equals("hl7"))
		{

			//dicomLib.leggiHl7();
		}
		else

	    dicomLib.getFilesByAccessionNumber(args[0]);
	}




	 private static String getPathJar() {
	        try {
	            final URI jarUriPath =
	                    Application.class.getResource(Application.class.getSimpleName() + ".class").toURI();
	            String jarStringPath = jarUriPath.toString().replace("jar:", "");
	            String jarCleanPath  = Paths.get(new URI(jarStringPath)).toString();

	            if (jarCleanPath.toLowerCase().contains(".jar")) {
	                return jarCleanPath.substring(0, jarCleanPath.lastIndexOf(".jar") + 4);
	            } else {
	                return null;
	            }
	        } catch (Exception e) {
	        	System.out.println("Error getting JAR path.");
	            return null;
	        }
	    }

	public  void init() {

		try {

			String pathJar = getPathJar();
			System.out.println("jar path: "+ pathJar);
			System.out.println("file sep: "+File.separator);
			String strPath="";


			if (pathJar!=null && pathJar.length()>0) {
				strPath = pathJar.substring(0, pathJar.lastIndexOf(File.separator));

				System.out.println("path: "+strPath);
			}
			else
			{

				final File f = new File(DicomLib.class.getProtectionDomain().getCodeSource().getLocation().getPath());

				strPath = f.getPath().replace(File.separator+"target"+File.separator+"classes","");
				System.out.println("path: "+strPath);
			}

			final File f = new File(DicomLib.class.getProtectionDomain().getCodeSource().getLocation().getPath());




		//	leggiParam(strPath);




		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		} finally {

		}

	}

	private List<String> leggiSeriesUID(String pathFile) {
		
		
		log ("inizio List<String> leggiSeriesUID");
		
		List<String> lista = new ArrayList();
		
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			fis = new FileInputStream(pathFile);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();

				if (line.toLowerCase().indexOf("0020,000e") >= 0) {
					int inizio = line.indexOf("[");
					int fine = line.indexOf("]");

					String sUID = line.substring(inizio + 1, fine);

					log("seriesUID: " + sUID);

					lista.add(sUID);

				}
			}

		}

		catch (Exception ex) {
			// log("errore: "+ex.getMessage());
		}
		finally
		{

			try {
				fis.close();
			} catch (IOException e) {

			}
			
			log("size: " + lista.size());
			
			log ("fine List<String> leggiSeriesUID");
			
			
			return lista;

		}


	}
	
	private List<String> leggiStudyUID(String pathFile) {
		
	
		log ("inizio List<String> leggiStudyUID");
		
		List<String> lista = new ArrayList();
		
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			fis = new FileInputStream(pathFile);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while (line != null) {
			
				line = reader.readLine();

				if (line.toLowerCase().indexOf("0020,000d") >= 0) {
					
					log ("line: "+line);
					
					int inizio = line.indexOf("[");
					int fine = line.indexOf("]");

					String sUID = line.substring(inizio + 1, fine);
					
				
					
					
					if (!lista.contains(sUID))
					{
						log("studyUID: " + sUID);

						lista.add(sUID);
					}

				}
			}

		}

		catch (Exception ex) {
			log("errore: " + ex.getMessage() + " " + ex.getCause().getMessage());
		
		}
		finally
		{

			try {
				fis.close();
			} catch (IOException e) {

			
				
			}
		
			log("size: " + lista.size());
			
			
			
			log ("fine List<String> leggiStudyUID");
			
			return lista;

		}
		


	}

	/*private  void leggiParam(String pathFile) {
/
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {

			System.out.println(pathFile + File.separator+"parametri.config");

			fis = new FileInputStream(pathFile + File.separator+"/parametri.config");
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();

				if (line==null || line.length()<2)
					continue;

				String[] arr = new String[2];

						arr[0]=line.substring(0,line.indexOf("="));
						arr[1]=line.substring(line.indexOf("=")+1);


				if (arr.length > 1) {
					GlobalVars.param.put(arr[0].toString().toLowerCase().trim(), arr[1].toString().trim());

				//	System.out.println(arr[0].toString().toLowerCase().trim() + "=" + arr[1].toString().trim());
				} else

				{

					GlobalVars.param.put(arr[0].toString().toLowerCase().trim(), "");

					//System.out.println(arr[0].toString().toLowerCase().trim() + "=" + "");
				}

			}

		}

		catch (Exception ex) {
			ex.printStackTrace();

			log("errore: " + ex.getMessage());
		}

	}*/


/*	public static String[] getFiles(String ddir)
	{

		 String[] pathnames;

	        // Creates a new File instance by converting the given pathname string
	        // into an abstract pathname
	        File f = new File(ddir);

	        // Populates the array with names of files and directories
	        pathnames = f.list();

	        // For each pathname in the pathnames array
	        for (String pathname : pathnames) {
	            // Print the names of files and directories
	            System.out.println(pathname);
	        }

	        return f.list();
	}
*/

	public  ArrayList getFiles(String ddir)
	{



	        // Creates a new File instance by converting the given pathname string
	        // into an abstract pathname
	        File f = new File(ddir);

	        // Populates the array with names of files and directories
	        String[] pathnames = f.list();

	        ArrayList lista = new ArrayList();

	        // For each pathname in the pathnames array
	        for (String pathname : pathnames) {

	        	if (pathname.toLowerCase().indexOf(".dcm")>=0 ||
	        	    pathname.toLowerCase().indexOf(".pdf")>=0 )
	        	{
	        		System.out.println(pathname);

	        	    lista.add(pathname);
	        	}

	        }

	        return  lista;
	}


	public boolean existAccessionNumber(String accessionNumber)
	{



		String pathRes1="";

		try
		{

		

		log("inizio boolean existAccessionNumber(String accessionNumber)");

		//log_error("PROVA ");

		int countFiles = 0;


		String pathAccession =myConfig.getDicom_storage();
		// + "/" + accessionNumber;

	     pathRes1 = pathAccession + "/" + accessionNumber + ".log";

		if (new File(pathRes1).exists()) {
			new File(pathRes1).delete();
		}

		String cmdFind = myConfig.getGdcm_path()+ "gdcmscu --find  --series --aetitle "+myConfig.getAetitle()+" --call "+myConfig.getAe_call()+" "
				+myConfig.getAe_host() + " " + myConfig.getAe_port() + " --studyroot --key 08,50," + accessionNumber
				+ " -L " + pathRes1;

		//new File(pathAccession).mkdir();

				if (esegui(myConfig.getCmd_os() + " " + cmdFind)) {

					List<String> lista_series_uid = leggiSeriesUID(pathRes1);

						if (lista_series_uid.size() > 0) {
							      return true;

							}

							}
		}

		catch(Exception ex)
		{
			log_error(ex.getMessage()+" ");
		}
		finally
		{
			if (new File(pathRes1).exists()) {
				new File(pathRes1).delete();
			}
			log("fine boolean existAccessionNumber(String accessionNumber)");

		}


		return false;

		}





	public JAccession getFilesByAccessionNumber(String accessionNumber) {


		try {

			log("inizio ArrayList getFilesByAccessionNumber(String accessionNumber)");

			int countFiles = 0;

	

			String pathAccession =myConfig.getDicom_storage() + "/" + accessionNumber;

			String pathRes1 = pathAccession + "/" + accessionNumber + ".log";

			if (new File(pathRes1).exists()) {
				new File(pathRes1).delete();
			}


			String cmdFind = myConfig. getGdcm_path() + "gdcmscu --find  --series --aetitle "+myConfig.getAetitle()+ " --call  "+myConfig.getAe_call()+
					" "
					+myConfig.getAe_host() + " " + myConfig.getAe_port() + " --studyroot --key 08,50," + accessionNumber
					+ " -L " + pathRes1;



			new File(pathAccession).mkdir();

			if (esegui(myConfig.getCmd_os() + " " + cmdFind)) {

				List<String> lista_series_uid= leggiSeriesUID(pathRes1);

				if (lista_series_uid.size() >= 0) {

					for (int i = 0; i < lista_series_uid.size(); i++) {

						String series_uid = lista_series_uid.get(i).toString();

			
						
						String cmdMove = myConfig.getGdcm_path()
								+ "gdcmscu --move --study --aetitle "+myConfig.getAetitle()+" --call "+myConfig.getAe_call()+"  " +

myConfig.getAe_host() + " " +  myConfig.getAe_port() + " --studyroot  --key 20,0e,"
								+ lista_series_uid.get(i) + " "

								+ " --port-scp "+myConfig.getPort_scp()+" --output " + pathAccession + " -D -L " + pathAccession + "/"
								+ series_uid + ".log";

						// log(cmdMove);

						if (esegui(cmdMove)) {
							countFiles++;

						}

					}
				}

			ArrayList ffiles =	getFiles(pathAccession);

			for (int i=0; i<ffiles.size(); i++)
			{
				log("file: "+ffiles.get(i));

			}


			if (ffiles.size()>0)
			{
				log("files: "+ffiles.size());
				log("sleep");
				Thread.sleep(3000);

				String fileZip = accessionNumber+".tar.gz";
				String fileZipFull = myConfig.getDicom_storage()+"/"+ fileZip;

			    String cmdZip ="tar -cvzf "+ fileZipFull  +" "+pathAccession+"/*.dcm";


			    log("cmd: "+cmdZip);

			    FolderZip.zipFolder(pathAccession, fileZipFull);


				//if (esegui(cmdZip))

				{



				//String ip =	java.net.InetAddress.getLocalHost().getHostAddress();

				String urlServer = "http://"+ myConfig.getIp() +":"+serverPort+"/dirzip";


					//calcolare la dimensione per capure se il file è stato creato

					File file = new File (fileZipFull);

					if (!file.exists())
					{
						new JAccession(accessionNumber,"file non creato","",0);
					}


					//puliamo la cartella
					 log("cancella "+pathAccession);
					 deleteDirectory(new File(pathAccession));


					return new JAccession(accessionNumber, urlServer+"/"+fileZip,"tar.gz", file.length());

				}


			}
			else
			{
				// new NotFound();



				return new JAccession(accessionNumber,"404","",0);
			}
				/* manca verifica file crc */

			}

		} catch (Exception ex) {

			log("errore: " + ex.getMessage() + " " + ex.getCause().getMessage());

		} finally {
			log("fine ArrayList getFilesByAccessionNumber(String accessionNumber)");

		}

		/*
		 * leggere nome dei files riferiti a countfiles
		 * */


		return new JAccession(accessionNumber,"404","",0);
	}
	
	

	public boolean existPatientID(String patientID,String anumber)
	{



		String pathRes1="";

		try
		{

	

		log("inizio boolean existPatientID(String patientID,String anumber)");

		//log_error("PROVA ");

		int countFiles = 0;

	

		String pathAccession =myConfig.getDicom_storage();
		// + "/" + accessionNumber;

	     pathRes1 = pathAccession + "/" + patientID + ".log";

		if (new File(pathRes1).exists()) {
			new File(pathRes1).delete();
		}

		String cmdFind = myConfig.getGdcm_path()+ "gdcmscu --find  --study --aetitle "+myConfig.getAetitle()+" --call "+myConfig.getAe_call()+" "
				+myConfig.getAe_host() + " " + myConfig.getAe_port() + " --patientroot   --key 20,0d --key 10,20," + patientID;
		
		
		if (anumber.length()>1)
		{
			cmdFind +=" --key 8,50," + anumber;
			
		}
			
			cmdFind += " -L " + pathRes1;

		//new File(pathAccession).mkdir();

				if (esegui(myConfig.getCmd_os() + " " + cmdFind)) {

					List<String> listaUID =leggiStudyUID(pathRes1);
					

						if (listaUID.size() > 0) {
							      return true;

							}

							}
		}

		catch(Exception ex)
		{
			log("errore: " + ex.getMessage() + " " + ex.getCause().getMessage());
		}
		finally
		{
			if (new File(pathRes1).exists()) {
				new File(pathRes1).delete();
			}
			log("fine boolean existPatientID(String patientID,String anumber)");

		}


		return false;

		}

	
	public JAccession getFilesByPatientID(String patientID,String anumber) {

//gdcmscu --find  --study --aetitle TOTEM  172.16.100.27 4100 --patientroot --key 10,20,"*"
		
		
		
		try {

			log("inizio ArrayList getFilesByPatientID(String patientID) ");

			int countFiles = 0;

	

			String pathAccession =myConfig.getDicom_storage() + "/" + patientID+"_"+anumber;

			String pathRes1 = pathAccession + "/" + patientID + ".log";

			if (new File(pathRes1).exists()) {
				new File(pathRes1).delete();
			}





			String cmdFind = myConfig.getGdcm_path()+ "gdcmscu --find  --study --aetitle "+myConfig.getAetitle()+" --call "+myConfig.getAe_call()+" "
					+myConfig.getAe_host() + " " + myConfig.getAe_port() + " --patientroot --key 20,0d --key 10,20," + patientID;
			
			
			
			if (anumber.length()>1)
			{
				cmdFind +=" --key 8,50," + anumber;
				
			}
				
				cmdFind += " -L " + pathRes1;



			new File(pathAccession).mkdir();

			if (esegui(myConfig.getCmd_os() + " " + cmdFind)) {

			
				Thread.sleep(100);
			   
				List<String> listaUID =leggiStudyUID(pathRes1);
				
			   
			   
				if (listaUID.size() >= 0) {

					for (int i = 0; i < listaUID.size(); i++) {

						String uid = listaUID.get(i).toString();

						String cmdMove = myConfig.getGdcm_path()
								+ "gdcmscu --move --study --aetitle "+myConfig.getAetitle()+" --call "+myConfig.getAe_call()+"  " +

myConfig.getAe_host() + " " +  myConfig.getAe_port() + " --studyroot  --key 20,0d,"
								+ listaUID.get(i) + " "

								+ " --port-scp "+myConfig.getPort_scp()+" --output " + pathAccession + " -D -L " + pathAccession + "/"
								+ uid + ".log";


						// log(cmdMove);

						if (esegui(cmdMove)) {
							countFiles++;

						}

					}
				}

			ArrayList ffiles =	getFiles(pathAccession);

			for (int i=0; i<ffiles.size(); i++)
			{
				log("file: "+ffiles.get(i));

			}


			if (ffiles.size()>0)
			{
				log("files: "+ffiles.size());
				log("sleep");
				Thread.sleep(3000);

				String fileZip = patientID+"_"+anumber+".tar.gz";
				String fileZipFull = myConfig.getDicom_storage()+"/"+ fileZip;

			    String cmdZip ="tar -cvzf "+ fileZipFull  +" "+pathAccession+"/*.dcm";


			    log("cmd: "+cmdZip);

			    FolderZip.zipFolder(pathAccession, fileZipFull);


				//if (esegui(cmdZip))

				{



				//String ip =	java.net.InetAddress.getLocalHost().getHostAddress();

				String urlServer = "http://"+ myConfig.getIp() +":"+serverPort+"/dirzip";


					//calcolare la dimensione per capure se il file è stato creato

					File file = new File (fileZipFull);

					if (!file.exists())
					{
						new JAccession(patientID,"file non creato","",0);
					}


					//puliamo la cartella
					 log("cancella "+pathAccession);
					 deleteDirectory(new File(pathAccession));


					return new JAccession(patientID, urlServer+"/"+fileZip,"tar.gz", file.length());

				}


			}
			else
			{
				// new NotFound();



				return new JAccession(patientID,"404","",0);
			}
				/* manca verifica file crc */

			}

		} catch (Exception ex) {

			log("errore: " + ex.getMessage() + " " + ex.getCause().getMessage());

		} finally {
			
			//log ("size: "+	listaUID.size());
			
			log("fine ArrayList getFilesByPatientID(String patientID) ");

		}

		/*
		 * leggere nome dei files riferiti a countfiles
		 * */


		return new JAccession(patientID,"404","",0);
	}

	boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}

	/*public  void log(String msg) {
		try

		{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String date = sdf.format(new Date());

			SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

			String date2 = dtf.format(new Date());

			String mlog = date2 + " " + msg;

			System.out.println(mlog);

			FileWriter fileWriter = new FileWriter(
					myConfig.getDicom_storage()+ "/dicom_util" + date + ".log", true); // Set true for append
																									// mode
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(mlog); // New line
			printWriter.close();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
*/
	private  boolean esegui(String cmd) {

		try {

			// -- Linux --

			log("inizio esegui");

			log(cmd);

			// Runtime.getRuntime().exec(cmd);

			Process process = Runtime.getRuntime().exec(cmd);

			// int exitVal = process.waitFor();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				// System.out.println(output);
				return true;

			} else {
				// abnormal...
			}
			return true;

		} catch (Exception e) {

			e.printStackTrace();
			log("errore: " + e.getMessage() + " " + e.getStackTrace().toString());
			e.printStackTrace();

			return false;
		} finally {
			log("fine esegui");
		}

	}
	/*
	 *
	 * 				/*	msg ="TVNIfF5+XCZ8RVNURU5TQVZOQXxFU1RFTlNBVk5BfEVMQ09fQVNMM3xFTENPX0FTTDN8MjAxOTA5\r\n" +
							"MTkxNTA1NDh8fE1ETV5UMDN8MjAxOTA5MjUxNDAyMTQyNTc0OHxQfDIuNQ1QSUR8fHwzNTk3ODE4\r\n" +
							"Xl5eRUxDT19BU0wzXlBJflBOQ1BMTDQ1QTYwRjIwNVNeXl5OTklUQX4yNTU2OTk3Xl5eWE1QSXx8\r\n" +
							"UElOQ09eUEFMTEF8fDE5NDUwMTIwfEZ8fHxeXl5HRV5eXkh+Xl5HRU5PVkFeR0VeMTYxMDBeXkxe\r\n" +
							"XjAxMDAyNX5eXl5eXl5OXl4wMTUxNDZ8fHxeXl5mYWJyaXppby52YWxsaWVyaUBlbGNvLml0fHx8\r\n" +
							"fFBOQ1BMTDQ1QTYwRjIwNVN8fHx8fDAxNTE0Nk1JTEFOTw1QVjF8fE98fHx8fHx8fHx8fHx8fHx8\r\n" +
							"fFJBRDUzMTI1NzBeXl5FTENPX0FTTDMNT1JDfHw1OTgyODMzXkVMQ09fQVNMM3w2MjU4NjA3XkVM\r\n" +
							"Q09fQVNMM3xSQUQxMTc5NTc5MF5FTENPX0FTTDN8fHxeXl4yMDE5MDkxOTE0MzgNT0JSfHw1OTgy\r\n" +
							"ODMzXkVMQ09fQVNMM3w2MjU4NjA3XkVMQ09fQVNMM3xSWDE1OV5SWCBQSUVERSBEWHx8fHx8fHx8\r\n" +
							"fFRPVEVNXjExNzk1NzkzfHx8fHw1OTgyODMzfHx8UkFEMS1BUkVBUlh8fHxDUnx8fHx8fHxESUNP\r\n" +
							"TV4xLjIuODI2LjAuMS4zNjgwMDQzLjIuOTcuMTk1MjkuMjE1NzMuMTk1OTgxMTI1NS4xOTA5MTkx\r\n" +
							"NTAwNTk0MzEwfHx8fHx8Ng1UWEF8fHxwN218fHwyMDE5MDkxOTE1MDAwMHwyMDE5MDkyNTE0MDIx\r\n" +
							"NHx8QkVSVEFNSU5PXkJFUlRBTUlOT15HSU9WQU5OSV5eXl5eXkNGfHx8UkFEQDUzMTI1NzBAMF5F\r\n" +
							"TENPX0FTTDN8fFJBRDUzMTI1NzBeRUxDT19BU0wzfHx8TEF8fHx8fEJFUlRBTUlOT15CRVJUQU1J\r\n" +
							"Tk9eR0lPVkFOTkleXl5eXl5DRl5eXl5eXjIwMTkwOTE5MTUwMDAw";
	public  void leggiHl7() {


		Connection connect = null;
		 Statement statement = null;
	 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;

		 List<String> squery = new ArrayList<String>();

		try {

			log("inizio leggiHl7");

			int countFiles=0;

			//connect = DriverManager.getConnection(
				//	"jdbc:mysql://172.18.0.13:3306/scripta?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false",

			//"progenius", "Test2019");


			//private static String dbUrl = "'jdbc:mysql://127.0.01:3306/mirth','mirth','Mirth.2019'";



			connect = DriverManager.getConnection(GlobalVars.param.get("db_url").toString(),GlobalVars.param.get("db_user").toString(),GlobalVars. param.get("db_pwd").toString());

			String sql = "select * from MSG_HL7 WHERE  (processato is null) or (processato<>'1')";

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			resultSet = statement.executeQuery(sql);

			//resultSet.last();

			//log("record trovati:" + resultSet.getRow());

			//resultSet.beforeFirst();

			int count = 1;

			//if (resultSet.getRow() > 0)

				while (resultSet.next()) {

					log(count + " record");

					String id = resultSet.getString("ID");
					String msg = resultSet.getString("MSG");





					byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(msg);

					//String msg_decode = decode(msg);
					String msg_decode = new String(decodedBytes);

					log(msg_decode);

					ArrayList<String> listaAcc = new ArrayList<String>();

					// String msg64
					// ="TVNIfF5+XCZ8RVNURU5TQVZOQXxFU1RFTlNBVk5BfEVMQ09fQVNMM3xFTENPX0FTTDN8MjAxOTA5MTkxNTA1NDh8fE1ETV5UMDN8MjAxOTA5MjUxNDAyMTQyNTc0OHxQfDIuNQ0KUElEfHx8MzU5NzgxOF5eXkVMQ09fQVNMM15QSX5QTkNQTEw0NUE2MEYyMDVTXl5eTk5JVEF+MjU1Njk5N15eXlhNUEl8fFBJTkNPXlBBTExBfHwxOTQ1MDEyMHxGfHx8Xl5eR0VeXl5Ifl5eR0VOT1ZBXkdFXjE2MTAwXl5MXl4wMTAwMjV+Xl5eXl5eTl5eMDE1MTQ2fHx8Xl5eZmFicml6aW8udmFsbGllcmlAZWxjby5pdHx8fHxQTkNQTEw0NUE2MEYyMDVTfHx8fHwwMTUxNDZNSUxBTk8NClBWMXx8T3x8fHx8fHx8fHx8fHx8fHx8UkFENTMxMjU3MF5eXkVMQ09fQVNMMw0KT1JDfHw1OTgyODMzXkVMQ09fQVNMM3w2MjU4NjA3XkVMQ09fQVNMM3xSQUQxMTc5NTc5MF5FTENPX0FTTDN8fHxeXl4yMDE5MDkxOTE0MzgNCk9CUnx8NTk4MjgzM15FTENPX0FTTDN8NjI1ODYwN15FTENPX0FTTDN8UlgxNTleUlggUElFREUgRFh8fHx8fHx8fHxUT1RFTV4xMTc5NTc5M3x8fHx8NTk4MjgzM3x8fFJBRDEtQVJFQVJYfHx8Q1J8fHx8fHx8RElDT01eMS4yLjgyNi4wLjEuMzY4MDA0My4yLjk3LjE5NTI5LjIxNTczLjE5NTk4MTEyNTUuMTkwOTE5MTUwMDU5NDMxMHx8fHx8fDYNClRYQXx8fHA3bXx8fDIwMTkwOTE5MTUwMDAwfDIwMTkwOTI1MTQwMjE0fHxCRVJUQU1JTk9eQkVSVEFNSU5PXkdJT1ZBTk5JXl5eXl5eQ0Z8fHxSQURANTMxMjU3MEAwXkVMQ09fQVNMM3x8UkFENTMxMjU3MF5FTENPX0FTTDN8fHxMQXx8fHx8QkVSVEFNSU5PXkJFUlRBTUlOT15HSU9WQU5OSV5eXl5eXkNGXl5eXl5eMjAxOTA5MTkxNTAwMDANCg==";

					// String msg_decode = decode(msg64);

					String[] righehl7 = msg_decode.split("\r\n");

					for (String riga : righehl7) {

						if (riga.indexOf("OBR") >= 0) {

							String[] cols = riga.replace("|", "ÿ").split("ÿ");

							log("accession: " + cols[18]);

							listaAcc.add(cols[18]);

						}

					}


					int verifica=0;

					for (String acc : listaAcc) {

					 JAccession res =	getFilesByAccessionNumber(acc);

					 if (res.size>0)
					   verifica++;
					 //ffiles crc su files

					}

					//log(msg_decode);

					if (verifica==listaAcc.size() && listaAcc.size()>0)
					{

					String sql2 = "UPDATE MSG_HL7 SET PROCESSATO='1', DICOMDIR='' WHERE ID=" + id;

					log(sql2);

					squery.add(sql2);


					count++;
					}else

					{

						log("accession non verificato");
					}

				}

				//fare qui le update

				for (String sql2 : squery)
				{
				   statement = connect.createStatement();
				   statement.executeUpdate(sql2);
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {


			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}

			log("fine leggiHl7");
		}

	}
*/

}
