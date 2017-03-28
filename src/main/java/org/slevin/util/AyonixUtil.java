package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class AyonixUtil {

	static String key="d80b29ccc96546b2a333edd5641188b6";
	static String keyTagging = "b8a644dd5dc54109b8832fdef2013a8a";
	static String server="localhost";
	//static String url ="http://"+"ec2-52-59-37-65.eu-central-1.compute.amazonaws.com"+":8080/ayonix/rest/hello";
	static String url ="http://"+"localhost"+":8080/primefaces-spring-jpa-tutorial/rest/hello";
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\Administrator\\Desktop\\okan.jpg");
		//file = new File("c:\\ayonixsdk\\steve jobs_77.jpg.jpg");
		FileInputStream fin = new FileInputStream(file);
		byte fileContent[]= new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
        
        File file6 = new File("C:\\Users\\Administrator\\Desktop\\ramazan.PNG");
		//file = new File("c:\\ayonixsdk\\steve jobs_77.jpg.jpg");
		FileInputStream fin6 = new FileInputStream(file6);
		byte fileContent6[]= new byte[(int)file6.length()];
        fin6.read(fileContent6);
        fin6.close();
       
//        File file2 = new File("C:\\Users\\ETR00529\\Desktop\\aliaydin.jpg");
////        file2 = new File("E:\\feretdb\\thumbnails_features_deduped_sample\\thumbnails_features_deduped_sample\\aaron carter\\0.jpg");
//		FileInputStream fin2 = new FileInputStream(file2);
//		byte fileContent2[]= new byte[(int)file2.length()];
//        fin2.read(fileContent2);
//        fin2.close();
//        
//        File file3 = new File("C:\\Users\\ETR00529\\Desktop\\erhan.PNG");
//        file3 = new File("E:\\ayonix\\salih\\2130837583-1160422452.jpg");
//		FileInputStream fin3 = new FileInputStream(file3);
//		byte fileContent3[]= new byte[(int)file3.length()];
//        fin3.read(fileContent3);
//        fin3.close();
//        
        File file4 = new File("C:\\Users\\Administrator\\Desktop\\Lee.jpg");
        //file4 = new File("E:\\ayonix\\salih\\20160728_093253_test.jpg");
		FileInputStream fin4 = new FileInputStream(file4);
		byte fileContent4[]= new byte[(int)file4.length()];
        fin4.read(fileContent4);
        fin4.close();
        
        Date date1 = new Date();
        System.out.println("client start="+date1);
        // String result = enroll("okan","erdogan",fileContent,"1234");
         String result = compateByPassword("1234",fileContent6);
//      String  result = search(fileContent);
       System.out.println("result "+result);
//        compate(fileContent4, fileContent4);
      //compateByStatic(fileContent4, fileContent4);
        //compateById("6", fileContent);
        Date date2 = new Date();
        System.out.println("client end="+date2);
       // ApiResult a =parseResponse(result);
        
       
        
       // String result=searchPerson(fileContent);
        //String result = deletePerson("10019");
        //System.out.println(result);
	
//        String result = compare(fileContent,fileContent);
//        System.out.println(result);
	
//        String result = compateById("17", fileContent);;
        System.out.println((date2.getTime()-date1.getTime()));
	}
	
	 public static String enroll(String name,String surname ,byte[] data,String password) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://"+server+":8080/primefaces-spring-jpa-tutorial/rest/hello/enroll");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);

	            
	            ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            fileBody.getContentLength();
	            
	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	            StringBody surnameBody = new StringBody(surname,ContentType.TEXT_PLAIN);
//	            StringBody fileNameBody = new StringBody(name+"_"+surname,ContentType.TEXT_PLAIN);
	            StringBody passwordBody = new StringBody(password,ContentType.TEXT_PLAIN);

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("image", fileBody)
	                    .addPart("name", surnameBody)
	                    .addPart("surname", nameBody)
	                    .addPart("password", passwordBody)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//                String result = (String)obj.get("result");	
//                if(result.equals("OK")){
//                	String userId = (String)obj.get("userid");
//                	return userId;
//                }else
//                	throw new Exception();
	            }
	          
	          
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	           
	        }
	        
	        return "";
	    }
	

	 
	 
	 
	 
	 
	 public static String search(byte[] data) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder(url+"/search");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            fileBody.getContentLength();
	            
//	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
//	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
//	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
//	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("image", fileBody)
	                   
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	           HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//                  String result = (String)obj.get("result");	
//                  if(result.equals("OK")){
//                  	String userId = (String)obj.get("userid");
//                  	return userId;
//                  }else
//                  	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }
	
	 
	 public static String search2(byte[] data) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder(url+"/searchByParalel2");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            fileBody.getContentLength();
	            
//	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
//	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
//	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
//	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("image", fileBody)
	                   
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            Date d1= new Date();
	            HttpResponse response = httpclient.execute(request);
	            Date d2= new Date();
	            System.out.println("sure:="+(d2.getTime()-d1.getTime())); 
	           HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//               String result = (String)obj.get("result");	
//               if(result.equals("OK")){
//               	String userId = (String)obj.get("userid");
//               	return userId;
//               }else
//               	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }
	
	 
	 
	 
	 
	 
	 
	 public static String compate(byte[] data1,byte[] data2) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://ec2-52-29-227-237.eu-central-1.compute.amazonaws.com:8080/primefaces-spring-jpa-tutorial/rest/hello/compare");
	           // URIBuilder builder = new URIBuilder("http://ec2-52-59-37-65.eu-central-1.compute.amazonaws.com:8080/ayonix/rest/hello/compare");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody1 = new ByteArrayBody(data1,"aa");
	            ByteArrayBody fileBody2 = new ByteArrayBody(data2,"aabb");
	            //fileBody.getContentLength();
	            
//	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
//	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
//	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
//	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("image1", fileBody1)
	                    .addPart("image2", fileBody2)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            Date d1= new Date();
	            HttpResponse response = httpclient.execute(request);
	            Date d2= new Date();
	            System.out.println("sure:="+(d2.getTime()-d1.getTime())); 
	            HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//                  String result = (String)obj.get("result");	
//                  if(result.equals("OK")){
//                  	String userId = (String)obj.get("userid");
//                  	return userId;
//                  }else
//                  	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }

	 public static String compateByStatic(byte[] data1,byte[] data2) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://ec2-52-59-37-65.eu-central-1.compute.amazonaws.com:8080/primefaces-spring-jpa-tutorial/rest/hello/compareByStatic");
	           // URIBuilder builder = new URIBuilder("http://ec2-52-59-37-65.eu-central-1.compute.amazonaws.com:8080/ayonix/rest/hello/compare");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody1 = new ByteArrayBody(data1,"aa");
	            ByteArrayBody fileBody2 = new ByteArrayBody(data2,"aabb");
	            //fileBody.getContentLength();
	            
//	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
//	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
//	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
//	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("image1", fileBody1)
	                    .addPart("image2", fileBody2)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            Date d1= new Date();
	            HttpResponse response = httpclient.execute(request);
	            Date d2= new Date();
	            System.out.println("sure:="+(d2.getTime()-d1.getTime())); 
	            HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//               String result = (String)obj.get("result");	
//               if(result.equals("OK")){
//               	String userId = (String)obj.get("userid");
//               	return userId;
//               }else
//               	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }

	 
	 public static String compateById(String id,byte[] data2) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://localhost:8080/primefaces-spring-jpa-tutorial/rest/hello/comparebyid");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            //ByteArrayBody fileBody1 = new ByteArrayBody(data1,"aa");
	            ByteArrayBody fileBody2 = new ByteArrayBody(data2,"aabb");
	            //fileBody.getContentLength();
	            
//	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
//	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
//	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
//	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
	            StringBody idBody = new StringBody(id,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("id", idBody)
	                    .addPart("image2", fileBody2)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	           HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//               String result = (String)obj.get("result");	
//               if(result.equals("OK")){
//               	String userId = (String)obj.get("userid");
//               	return userId;
//               }else
//               	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }

	 
	 
	 
	 
	 
	 
	 
	 public static String compateByPassword(String password,byte[] data2) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://localhost:8080/primefaces-spring-jpa-tutorial/rest/hello/comparebypasword");
	            
	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
           
	           
	            ByteArrayBody fileBody2 = new ByteArrayBody(data2,"aabb");
	            //fileBody.getContentLength();
            StringBody idBody = new StringBody(password,ContentType.TEXT_PLAIN);
	           

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("password", idBody)
	                    .addPart("image2", fileBody2)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	           HttpEntity entity = response.getEntity();
int statusCode = response.getStatusLine().getStatusCode();
	          System.out.println(statusCode); 
	          
	          if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
//	            	JSONParser parser = new JSONParser();
//	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
//            String result = (String)obj.get("result");	
//            if(result.equals("OK")){
//            	String userId = (String)obj.get("userid");
//            	return userId;
//            }else
//            	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }
	 
	 
}
