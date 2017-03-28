package org.slevin.rest;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slevin.common.FaceMatcherPerson;
import org.slevin.dao.FaceMatcherPersonDao;
import org.slevin.dao.FaceMatcherPersonFullDataDao;
import org.slevin.rest.dto.SearchDTO;
import org.slevin.util.AyonixStaticUtil;
import org.slevin.util.ResultDto;
import org.slevin.util.SearchResultDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.jersey.multipart.FormDataParam;

import ayonix.FaceID;
 
@Path("/hello")
public class AyonixServicece {
 
	@Context
	private ServletContext context=null; 
	
	@Autowired
	FaceMatcherPersonDao faceMatcherPersonService;
	
	@Autowired
	FaceMatcherPersonFullDataDao faceMatcherPersonFullDataService;
	
	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		
		return Response.status(200).entity(output).build();
 
	}
	
	
	@POST
	@Path("/enroll") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public  ResultDto uploadFile(@FormDataParam("name") String name,
							   @FormDataParam("surname") String surname,
							   @FormDataParam("password") String password,
							   @FormDataParam("image") InputStream is) {
 
		
		ResultDto resultDto = new ResultDto();
		try {
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
	    	
			
			byte[] bytes = getBytes(is);
			int a = bytes.length;
			Random r = new Random();
			int b  =  r.nextInt((100000 - 0) + 1) + 0;
			faceMatcherPersonService.enroll(name, surname, bytes, String.valueOf(b)+".jpg",password);
			
			resultDto.setResult(true);
			resultDto.setMessage("success");;
			
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setResult(true);
			resultDto.setMessage(e.getMessage());;
			return resultDto;
			
		}
 
		
 
	}
	
	@POST
	@Path("/search") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO search(@FormDataParam("image") InputStream is) {
 
		
 
		try {
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
	    	
			
			byte[] bytes = getBytes(is);
			SearchResultDTO resultDto= faceMatcherPersonService.search(bytes);
			//AyonixStaticUtil.
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setId(resultDto.getPerson().getId());
			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			searchDTO.setPath(resultDto.getPerson().getPath());
			
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}
	
	
	@POST
	@Path("/searchByParalel") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO searchByParalel(@FormDataParam("image") InputStream is) {
 
		
 
		try {
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
	    	
			
			byte[] bytes = getBytes(is);
			SearchResultDTO resultDto= faceMatcherPersonService.search2(bytes);
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setId(resultDto.getPerson().getId());
			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			searchDTO.setPath(resultDto.getPerson().getPath());
			
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}
	

	@POST
	@Path("/searchByParalel2") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO searchByParalel2(@FormDataParam("image") InputStream is) {
 
		
 
		try {
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    	FaceMatcherPersonFullDataDao faceMatcherPersonFullDataService= (FaceMatcherPersonFullDataDao)context.getBean("faceMatcherPersonFullDataService");
	    	
			
			byte[] bytes = getBytes(is);
			SearchResultDTO resultDto= faceMatcherPersonFullDataService.search2(bytes);
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setId(resultDto.getPerson().getId());
			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			searchDTO.setPath(resultDto.getPerson().getPath());
			
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}
	
	
	
	
	@POST
	@Path("/compare") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO compare(@FormDataParam("image1") InputStream is1,
							 @FormDataParam("image2") InputStream is2) {
 
		
 
		try {
			Date t0 = new Date();
			System.out.println("rest t0="+t0);
			if(faceMatcherPersonService == null){
				ServletContext  servletContext =(ServletContext) context;
				BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
				faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
				System.out.println("bean set edildi");
			}
			
			byte[] bytes1 = getBytes(is1);
			byte[] bytes2 = getBytes(is2);
			Date t1 = new Date();
			System.out.println("rest t1="+t1+" duration="+(t1.getTime()-t0.getTime()));
			
			SearchResultDTO resultDto= faceMatcherPersonService.compare(bytes1, bytes2);
			SearchDTO searchDTO = new SearchDTO();
//			searchDTO.setId(resultDto.getPerson().getId());
//			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			
			Date t2 = new Date();
			System.out.println("rest t2="+t2);
			System.out.println("rest duration = "+(t2.getTime()-t0.getTime()));
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}
	
	
	
	@POST
	@Path("/compareByStatic") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO compareByStatic(@FormDataParam("image1") InputStream is1,
							 @FormDataParam("image2") InputStream is2) {
 
		
 
		try {
			Date t0 = new Date();
			System.out.println("rest t0="+t0);
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
	    	
			
			byte[] bytes1 = getBytes(is1);
			byte[] bytes2 = getBytes(is2);
			//bytes2.length
			Date t1 = new Date();
			System.out.println("rest t1="+t1+" duration="+(t1.getTime()-t0.getTime()));
			//FaceID sdk = faceMatcherPersonService.getSdk();
			FaceID sdk = (FaceID)servletContext.getAttribute("sdk");
			Date sdkDate = new Date();
			System.out.println("sdk = duration="+(sdkDate.getTime()-t1.getTime()));
			SearchResultDTO resultDto= AyonixStaticUtil.compare(sdk,bytes1, bytes2);
			SearchDTO searchDTO = new SearchDTO();
//			searchDTO.setId(resultDto.getPerson().getId());
//			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			Date t2 = new Date();
			System.out.println("rest t2="+t2);
			System.out.println("rest duration = "+(t2.getTime()-t0.getTime()));
			
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}

	
	@POST
	@Path("/comparebyid") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO compare(@FormDataParam("id") String id,
							 @FormDataParam("image2") InputStream is2) {
		try {
			Date t0 = new Date();
			System.out.println("rest comparebyid t0="+t0);
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
			Date business1 = new Date();
			byte[] bytes1 = getBytes(is2);
			
			SearchResultDTO resultDto= faceMatcherPersonService.compare(id, bytes1);
			SearchDTO searchDTO = new SearchDTO();
//			searchDTO.setId(resultDto.getPerson().getId());
//			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			
			Date t2 = new Date();
			System.out.println("rest comparebyid t2="+t2);
			System.out.println("rest comparebyid duration = "+(t2.getTime()-t0.getTime()));
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore(e.getMessage());
			return searchDTO;
			
		}
 
		
 
	}
	
	
	@POST
	@Path("/comparebypasword") 
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public SearchDTO compareByPassword(@FormDataParam("password") String password,
							 @FormDataParam("image2") InputStream is2) {
		try {
			Date t0 = new Date();
			System.out.println("rest comparebyid t0="+t0);
			ServletContext  servletContext =(ServletContext) context;
	    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");
			Date business1 = new Date();
			byte[] bytes1 = getBytes(is2);
			
			SearchResultDTO resultDto= faceMatcherPersonService.compareByPassword(password, bytes1);
			SearchDTO searchDTO = new SearchDTO();
//			searchDTO.setId(resultDto.getPerson().getId());
//			searchDTO.setName(resultDto.getPerson().getName());
			searchDTO.setScore(String.valueOf(resultDto.getScore()));
			searchDTO.setName(resultDto.getPerson().getName());
			
			Date t2 = new Date();
			System.out.println("rest comparebyid t2="+t2);
			System.out.println("rest comparebyid duration = "+(t2.getTime()-t0.getTime()));
			return searchDTO;
		} catch (Exception e) {
			e.printStackTrace();
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setScore("0");
			return searchDTO;
			
		}
 
		
 
	}
	
	
	@GET
	@Path("/listUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FaceMatcherPerson> listUsers() throws Exception {
		
		ServletContext  servletContext =(ServletContext) context;
    	BeanFactory context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		FaceMatcherPersonDao faceMatcherPersonService= (FaceMatcherPersonDao)context.getBean("faceMatcherPersonService");

		List<FaceMatcherPerson> list = faceMatcherPersonService.findAll();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			FaceMatcherPerson faceMatcherPerson = (FaceMatcherPerson) iterator
					.next();
			
			faceMatcherPerson.setAfid(null);
			faceMatcherPerson.setPath("http://ec2-52-29-192-232.eu-central-1.compute.amazonaws.com:8080/images"+faceMatcherPerson.getPath().replace("c:\\ayonixsdk\\", "/"));
		}

		return list;
	}
	
	@GET
	@Path("/test")
	public Response getUsers(
		@QueryParam("id") int id) {
System.out.println("getUsers is called, id : " + id);
		return Response
		   .status(200)
		   .entity("getUsers is called, id : " + id).build();

	}
	
	
	public byte[] getBytes(InputStream is) throws IOException{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		buffer.flush();

		return buffer.toByteArray();
	}
 
}