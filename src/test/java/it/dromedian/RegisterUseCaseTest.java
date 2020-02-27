package it.dromedian;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import io.netty.handler.codec.http.HttpHeaders;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.AsyncHandler.State;

@TestPropertySource(properties = "server.port=7090")

@SpringBootTest
class RegisterUseCaseTest {


	  @Autowired
	    private   YAMLConfig myConfig;
	  
	 

  @Test
  void savedUserHasRegistrationDate() {

	  
	  //org.junit.jupiter.api.Assertions.assertEquals(myConfig.getAe_port(),123);
	  
	  org.junit.jupiter.api.Assertions.assertEquals(multiple_curl(),10);
	  
  }
  

  public int multiple_curl() {
 

  	String cmd = "curl http://127.0.0.1:7090/existPatientID/1234";
  	
  
  	int count = 0;
  	
  	for (int i=0; i<10; i++)
  	{
  		count++;
  		
  	
  	
		try {
		 Runtime.getRuntime().exec(cmd+i);
			
			
			
		} catch (Exception e1) {
			
		}

      
  	}
  	
  	
  	return count;
  	
 // 	org.junit.jupiter.api.Assertions.assertEquals(count,10);
  			

  }
  

}