package org.slevin.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;

import org.slevin.common.FaceMatcherPerson;

import ayonix.AynxFace;
import ayonix.AynxImage;
import ayonix.FaceID;

public class AyonixStaticUtil {

	public static byte[] createAfid(FaceID sdk,byte[] data) throws Exception{
		 AynxImage img = sdk.LoadImage(data);
		 AynxFace[] faces = sdk.DetectFaces(img);
		 
		 if(faces.length==0){
			 throw new Exception("no face detected");
		 }
		 
		 if(faces.length>1){
			 throw new Exception("more than 1 face detected");
		 }
		 
		 sdk.PreprocessFace(faces[0]);
		 byte[] afidi = sdk.CreateAfid(faces[0]);
		 return afidi;
	}
	
	
	public static SearchResultDTO compare(FaceID sdk,byte[] data, byte[] data2) throws Exception {
		Date t0 = new Date();
		System.out.println("method start="+t0);
		byte[] query = createAfid(sdk,data);
		byte[] query2 = createAfid(sdk,data2);
		
		Date t1 = new Date();
		Vector<byte[]> afidList  = new Vector<byte[]>();
		afidList.add(query2);
		
		float[] scores = new float[1];
       int[] indexes = new int[1];
       Date t2 = new Date();
       sdk.MatchAfids(query, afidList, scores, indexes);
       float score  = scores[0];
       Date t3 = new Date();
       SearchResultDTO result = new SearchResultDTO();
       result.setScore(score);
Date t4 = new Date();
       
       System.out.println("method end="+t4);
       System.out.println("total ="+(t4.getTime()-t0.getTime())+",t2 ="+(t2.getTime()-t1.getTime())+",t3="+(t3.getTime()-t2.getTime())+",t4="+(t4.getTime()-t3.getTime()));
       
       return result;
	}

	

	
}
