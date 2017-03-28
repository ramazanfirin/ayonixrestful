package org.slevin.tests;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slevin.dao.FaceMatcherPersonDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;




@ContextConfiguration(locations = { "classpath*:META-INF/spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class persistenceTestsAyonix implements ApplicationContextAware {

	@Context
	private ServletContext context=null; 
	
	@Autowired
    private ApplicationContext applicationContext;

	
	
	
	
	@Test
	public void testDaoImpl() throws Exception {
		File file = new File("C:\\Users\\ETR00529\\Desktop\\ramazan.png");
		FileInputStream fin = new FileInputStream(file);
		byte fileContent[]= new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
        
        applicationContext.getBeanDefinitionCount();
		
//        ServletContext  servletContext =(ServletContext) context;
//    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//		FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
		
		System.out.println(applicationContext.getBeanDefinitionCount());
	}
	

	
	public void setApplicationContext(ApplicationContext context)
            throws BeansException
    {
		//context.get
		
		
		GenericApplicationContext context2 = (GenericApplicationContext)context;
		final String[] beanNames = context2.getBeanDefinitionNames();
		System.out.println("bean names:");
		for (final String beanName : beanNames) { 
		    System.out.println("\tbean name:" + beanName);
		}   // for
		System.out.println("=======================");
		
//		FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBeanDefinitionNames()getBean("faceMatcherPersonDao");
		System.out.println("sdfsdf");
    }
}
