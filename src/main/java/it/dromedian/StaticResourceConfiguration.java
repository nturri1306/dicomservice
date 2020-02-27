package it.dromedian;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	  @Autowired
	    private   YAMLConfig myConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


    	//registry.addResourceHandler("/**").addResourceLocations("file:/c:");

    	/*registry.addResourceHandler("/test/**")
        .addResourceLocations(
                "file:///C:/logs/")
        .setCachePeriod(0);*/

    	//Path path = Paths.get(System.getProperty("user.home"), "dicomstorage/");
    //	String homeDirectory = path.toUri().toString();

    	try

    	{
    		String sDirectory = "file://"+myConfig.getDicom_storage()+"/";




    	System.out.println(sDirectory);


    	 registry
         .addResourceHandler("/dirzip/**")
         .addResourceLocations(sDirectory)
         .setCachePeriod(0);
    	}
    	catch(Exception ex)
    	{


    	}

    }
}