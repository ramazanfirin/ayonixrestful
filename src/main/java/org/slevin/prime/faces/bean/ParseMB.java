package org.slevin.prime.faces.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;

import org.slevin.common.FaceMatcherPerson;
import org.slevin.common.ParseResult;
import org.slevin.dao.FaceMatcherPersonDao;
import org.slevin.dao.ParseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component(value="parseMB")
@ViewScoped
public class ParseMB {

	@Autowired
	ParseDao parseService;
	
	@Autowired
	FaceMatcherPersonDao faceMatcherPersonService;
	
	public void startInsert() throws Exception{
System.out.println("asdasd");
		
		String path  ="E:\\feretdb\\thumbnails_features_deduped_sample\\thumbnails_features_deduped_sample\\";
		File f = new File(path);
		String[] paths = f.list();
		
		for(String aa:paths)
        {
           File file = new File(path+aa);
           if(file.isDirectory()){
        	   if(parseService.findByProperty("name", file.getName()).size()==0){
        		   try {
					parseDirectory(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
               
           }
           //System.out.println(aa);
        }
	}
	
	public void bulkInsert() throws Exception{
		
//		for (int i = 0; i < 100; i++) {
//			startInsert();
//			parseService.removeAll();
//			System.out.println(i+"bitti");
//			
//		}
	for (int i = 0; i < 1; i++) {
		List<FaceMatcherPerson> list =faceMatcherPersonService.findAll();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			FaceMatcherPerson faceMatcherPerson = (FaceMatcherPerson) iterator
					.next();
			faceMatcherPerson.setId(null);
			faceMatcherPersonService.persist(faceMatcherPerson);
		}
	}
	
	}
	
	public void parseDirectory(File directory) throws Exception{
		
		String[] paths = directory.list();
		for(String aa:paths)
        {
           File file = new File(directory.getPath()+"\\"+aa);
           if(file.isDirectory()){
        	   continue;
           }
           
           if(parseService.findByProperty("name", file.getParentFile().getName()+"_"+file.getName()).size()==0){
        	   try {
				parseFile(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
           
          
        }
		parseService.persist(new ParseResult(directory.getName(), "d", true));
		
	}
	
	public void parseFile(File file) throws Exception{
		String parent = file.getParentFile().getName();
		//parent = parent.replace(" ", "_");
		
		BufferedImage originalImage = ImageIO.read(file);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizeImageJpg = resizeImage(originalImage, type);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( resizeImageJpg, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		
        faceMatcherPersonService.enroll(parent, "", imageInByte, parent+"_"+file.getName(),"");
		parseService.persist(new ParseResult(parent+"_"+file.getName(), "f", true));
	}
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(originalImage.getWidth()*2, originalImage.getHeight()*2, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, originalImage.getWidth()*2, originalImage.getHeight()*2, null);
		g.dispose();
			
		return resizedImage;
	    }
}
