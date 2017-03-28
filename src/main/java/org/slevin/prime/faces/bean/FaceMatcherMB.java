package org.slevin.prime.faces.bean;
 
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slevin.common.FaceMatcherPerson;
import org.slevin.dao.FaceMatcherPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component(value="faceMatcherMB")
@ViewScoped
public class FaceMatcherMB {
	
	@Autowired
	FaceMatcherPersonDao faceMatcherPersonService;
    
	List<FaceMatcherPerson> list = new ArrayList<FaceMatcherPerson>();
	
	//EmotionImage selectedImage;
	
	String faceCount;
	
	String contempt;
	String surprise;
	String happiness;
	String neutral;
	String sadness;
	String disgust;
	String anger;
	String fear;
	
    private String filename;
    
    String personNane;
    private UploadedFile file;    
    String customerNumber;;
    String customerImage;
    String benzerlikOrani;
    
    BufferedImage bImageFromConvert;
    private StreamedContent myImage;
    
    
    @PostConstruct
    public void init() throws Exception{
    	//list = faceMatcherPersonService.findAll();
    }
    
    public void capture() throws IOException{
//    	Webcam webcam = Webcam.getDefault();
//    	webcam.open();
//    	File file  = new File("c:\\capture\\hello-world.png");
//    	ImageIO.write(webcam.getImage(), "PNG", file);
//    	System.out.println("bitti");
    }
    
    public void refresh() throws Exception{
    	list.clear();
    	list.addAll(faceMatcherPersonService.findAll());
    }
     
    public void deleteAll() throws Exception{
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			FaceMatcherPerson emotionImage = (FaceMatcherPerson) iterator.next();
			faceMatcherPersonService.remove(emotionImage.getId());
		}
    	list.clear();
    	
    }
    
    
    
    
    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);
         
        return String.valueOf(i);
    }
 
    public String getFilename() {
        return filename;
    }
    
    public static String getURLWithContextPath() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    	}
    
    
    public void insertNew(){
    	
    	
    }
    
    public void upload() throws Exception{
    	System.out.println("updaload");
    	file.getFileName();
    	
    	FaceMatcherPerson faceMatcherPerson  = new FaceMatcherPerson();
    	faceMatcherPerson.setName(personNane);
    	
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    	String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
                File.separator + "images" + File.separator + "photocam" + File.separator + personNane + ".jpeg";
    	
    	InputStream in = new ByteArrayInputStream(file.getContents());
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "jpg",new File(newFileName));
    	
    	faceMatcherPerson.setPath(newFileName);
    	faceMatcherPersonService.persist(faceMatcherPerson);
    	
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tamamandi","Tamamlandi"));
    	refresh();
    }
    
    public void handleFileUpload(FileUploadEvent event) throws Exception {
    	
    	
    }
     
    public void oncapture(CaptureEvent captureEvent) throws Exception {
    	
    	try {
    		if(customerNumber==null || customerNumber.equals("")){
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Müşteri numarası giriniz", "Müşteri numarası giriniz"));
    			return;
    		}
    		
		
    		
    		FaceMatcherPerson faceMatcherPerson = faceMatcherPersonService.findById(new Long(customerNumber));
    		if(faceMatcherPerson==null){
    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Müşteri Bulunamadı", "Müşteri Bulunamadı"));
    			return;
    		}
    		
    		customerImage = faceMatcherPerson.getName();
    		File file = new File(faceMatcherPerson.getPath());
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[]= new byte[(int)file.length()];
			fin.read(fileContent);
			fin.close();
			
			byte[] data = captureEvent.getData();
			
			//benzerlikOrani = "100";
			//benzerlikOrani =AyonixUtil.compare(fileContent, data);
			//benzerlikOrani =FaceUtil.verify(fileContent, data);
			
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			 StreamedContent   myImageasd = new DefaultStreamedContent(bis, "image/png");
			 setMyImage(myImageasd);
			
			 //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Benzerlik orani="+result,result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(),e.getMessage()));
		}
    	
        
        
    }
    
    


	public String getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(String faceCount) {
		this.faceCount = faceCount;
	}

	

	public String getContempt() {
		return contempt;
	}

	public void setContempt(String contempt) {
		this.contempt = contempt;
	}

	public String getSurprise() {
		return surprise;
	}

	public void setSurprise(String surprise) {
		this.surprise = surprise;
	}

	public String getHappiness() {
		return happiness;
	}

	public void setHappiness(String happiness) {
		this.happiness = happiness;
	}

	public String getNeutral() {
		return neutral;
	}

	public void setNeutral(String neutral) {
		this.neutral = neutral;
	}

	public String getSadness() {
		return sadness;
	}

	public void setSadness(String sadness) {
		this.sadness = sadness;
	}

	public String getDisgust() {
		return disgust;
	}

	public void setDisgust(String disgust) {
		this.disgust = disgust;
	}

	public String getAnger() {
		return anger;
	}

	public void setAnger(String anger) {
		this.anger = anger;
	}

	public String getFear() {
		return fear;
	}

	public void setFear(String fear) {
		this.fear = fear;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPersonNane() {
		return personNane;
	}

	public void setPersonNane(String personNane) {
		this.personNane = personNane;
	}

	public List<FaceMatcherPerson> getList() {
		return list;
	}

	public void setList(List<FaceMatcherPerson> list) {
		this.list = list;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public BufferedImage getbImageFromConvert() {
		return bImageFromConvert;
	}

	public void setbImageFromConvert(BufferedImage bImageFromConvert) {
		this.bImageFromConvert = bImageFromConvert;
	}

	public StreamedContent getMyImage() {
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public String getCustomerImage() {
		return customerImage;
	}

	public void setCustomerImage(String customerImage) {
		this.customerImage = customerImage;
	}

	public String getBenzerlikOrani() {
		return benzerlikOrani;
	}

	public void setBenzerlikOrani(String benzerlikOrani) {
		this.benzerlikOrani = benzerlikOrani;
	}
}