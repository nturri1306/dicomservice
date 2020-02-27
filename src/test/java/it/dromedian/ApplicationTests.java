package it.dromedian;




import static org.asynchttpclient.Dsl.asyncHttpClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import io.netty.handler.codec.http.HttpHeaders;


public class ApplicationTests {



	public static void main(String[] args) {
		
		multiple_curl();
		
	
	}
	

	public static void multiple_curl() {
		 

	  	String cmd = "curl http://127.0.0.1:7090/existPatientID/1234";
	  	
	  
	  	int count = 0;
	  	
	  	for (int i=0; i<100; i++)
	  	{
	  		count++;
	  		
	  	
	  	
			try {
				
				
				System.out.println(cmd+i+"/0");
			 Runtime.getRuntime().exec(cmd+i+"/0");
				
				
				
			} catch (Exception e1) {
				
			}

	      
	  	}
	 
	 
	}
	   
	}
	

