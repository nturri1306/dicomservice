package it.dromedian;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	  @Autowired
	    private   YAMLConfig myConfig;
	  
		 @Value("${server.port}")
		  private String serverPort;
	 
	 private static final Logger LOGGER=LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
	//	SpringApplication.run(Application.class, args);
		
		GlobalVars.LOGGER=LOGGER;
		
	
		
		if (args.length>0 && args[0]=="debug")
		{
			  GlobalVars.isDebug = true;
		}
	 
		
		    GlobalVars.aext.add("dcm");
		    GlobalVars.aext.add("pdf");
		    
		  
		    
		   
		SpringApplication app = new SpringApplication(Application.class);
        
		
	//	app.setDefaultProperties(Collections.singletonMap("server.port",GlobalVars.port));

        
        app.run(args);
        
      
        
		
	}
	

	

	
	 public void run(String... args) throws Exception {
		 
		
		 
	       System.out.println("ae host: " + myConfig.getAe_host());
	       System.out.println("serverPort: " +  serverPort);
	       
	  
	  
	     
	    }


	
}
