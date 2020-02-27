package it.dromedian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ctrl shit f

@RestController
public class DicomController {

	@Autowired
	private YAMLConfig myConfig;

	private static final Logger logger = LoggerFactory.getLogger(DicomController.class);

	
	
	@RequestMapping("/hello")
	public String unSaluto() {
		return "ciao-mondo";
	}

	private void log(String str)
	{
		
		logger.info(str);
	}
	
	private void log(String sessionID,String str)
	{
		
		logger.info("["+sessionID+"] "+str);
	}
	
	 @Value("${server.port}")
	  private String serverPort;
	 
	
	  
	 private String generateUID()
	 {
		 UUID uniqueKey = UUID.randomUUID();

	   String[] pp =  uniqueKey.toString().split("-");
		 
	   return pp[pp.length -1];
	 }

	
	@RequestMapping("/getByAccessionNumber/{anumber}")
	public ResponseEntity<JAccession> getByAccessionNumber(@PathVariable("anumber") String anumber) {
		JAccession res = null;

		String sessionID =generateUID();
		
		try

		{
			log( sessionID,"inizio  ResponseEntity<JAccession> getByAccessionNumber");
			log( sessionID,"valore: " + anumber);
			DicomLib dicomLib = new DicomLib(myConfig);
			dicomLib.sessionID = sessionID;

			dicomLib.serverPort = serverPort;

			res = dicomLib.getFilesByAccessionNumber(anumber);
			
			
			log( sessionID,"result: " + res.size);
			
		}

		catch (Exception ex) {

		} finally {
			log( sessionID,"fine ResponseEntity<JAccession> getByAccessionNumber");

			if (res.size < 1) {
				// new NotFound();
				return new ResponseEntity<JAccession>(res, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<JAccession>(res, HttpStatus.OK);

		}

	}

	@RequestMapping("/existAccessionNumber/{anumber}")
	public ResponseEntity<Boolean> existAccessionNumber(@PathVariable("anumber") String anumber) {

		boolean res = false;
		String sessionID =generateUID();

		try

		{

			log( sessionID,"inizio ResponseEntity<Boolean> existAccessionNumber ");
			log( sessionID,"valore: " + anumber);

			DicomLib dicomLib = new DicomLib(myConfig);
			dicomLib.sessionID = sessionID;		

			res = dicomLib.existAccessionNumber(anumber);

			log( sessionID,"result: " + res);
			
		} catch (Exception ex) {

		} finally {

			log( sessionID,"fine ResponseEntity<Boolean> existAccessionNumber ");

			if (!res)
				return new ResponseEntity<Boolean>(res, HttpStatus.NOT_FOUND);

			else
				return new ResponseEntity<Boolean>(res, HttpStatus.OK);

		}

	}
	
	@RequestMapping("/getByPatientID/{patientID}/{anumber}")
	public ResponseEntity<JAccession> getByPatientID(@PathVariable("patientID") String patientID, @PathVariable(required = false) String anumber) {
		JAccession res = null;
		String sessionID =generateUID();
		try

		{
			log( sessionID,"inizio  ResponseEntity<JAccession> getByPatientID");
			log( sessionID,"valore: " + patientID);
			DicomLib dicomLib = new DicomLib(myConfig);
			dicomLib.serverPort = serverPort;
			dicomLib.sessionID = sessionID;
			
			res = dicomLib.getFilesByPatientID(patientID,anumber);
			
			
			log( sessionID,"result: " + res.size);
			
		}

		catch (Exception ex) {

		} finally {
			log( sessionID,"fine ResponseEntity<JAccession> getByPatientID");

			if ( res==null || res.size < 1) {
				// new NotFound();
				return new ResponseEntity<JAccession>(res, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<JAccession>(res, HttpStatus.OK);

		}

	}

	@RequestMapping("/existPatientID/{patientID}/{anumber}")
	public ResponseEntity<Boolean> existPatientID(@PathVariable("patientID") String patientID, @PathVariable(required = false) String anumber  ) {

		boolean res = false;
		String sessionID =generateUID();
		try

		{

			log( sessionID,"inizio ResponseEntity<Boolean> existPatientID");
			log( sessionID,"valore: " + patientID);

			DicomLib dicomLib = new DicomLib(myConfig);
			dicomLib.sessionID = sessionID;
			res = dicomLib. existPatientID( patientID,anumber);

			log( sessionID," result: " + res);
			
		} catch (Exception ex) {

		} finally {

			log( sessionID,"fine ResponseEntity<Boolean> existPatientID ");

			if (!res)
				return new ResponseEntity<Boolean>(res, HttpStatus.NOT_FOUND);

			else
				return new ResponseEntity<Boolean>(res, HttpStatus.OK);

		}

	}



}