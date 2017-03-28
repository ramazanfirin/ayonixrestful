package org.slevin.dao.service;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.slevin.common.FaceMatcherPerson;
import org.slevin.common.FaceMatcherPersonFullData;
import org.slevin.dao.FaceMatcherPersonDao;
import org.slevin.dao.FaceMatcherPersonFullDataDao;
import org.slevin.util.ForkAyonix;
import org.slevin.util.ForkDto;
import org.slevin.util.SearchResultDTO;
import org.slevin.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ayonix.AynxFace;
import ayonix.AynxImage;
import ayonix.FaceID;


@Component
@Transactional
public class FaceMatcherPersonService extends EntityService<FaceMatcherPerson> implements FaceMatcherPersonDao {

	@Autowired
	private ServletContext context;
	
	@Autowired
	private FaceMatcherPersonFullDataDao faceMatcherPersonFullDataDao;
	
	FaceID sdk;
	String storePath="c:\\ayonixsdk\\";
	
	Vector<byte[]> afidList = new Vector<byte[]>();
	
	HashMap<byte[], String> memory = new HashMap<byte[], String>();
	
	@PostConstruct
	public void init(){
		sdk = new FaceID("C:\\Program Files (x86)\\Ayonix\\FaceID\\data\\engine");
		int[] version = sdk.GetVersion();
        System.out.format("Engine version: %d.%d.%d.%d%n", version[0], version[1], version[2], version[3]);
        
        int nthreads = (int)sdk.GetParam(FaceID.PARAM_NTHREADS);
        System.out.println("NThreads: " + nthreads);
        
        context.setAttribute("sdk", sdk);
	}
	
	@PreDestroy
	public void destroy(){
		sdk.Close();
		System.out.println("sdk kapatıldı");
	}
	
	public byte[] createAfid(byte[] data) throws Exception{
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
	
	public void enroll(String name, String surname, byte[] data,String fileName,String password) throws Exception {

		 byte[] afidi = createAfid(data);
		 
		 String newFileName = storePath+fileName;
		 InputStream in = new ByteArrayInputStream(data);
		 BufferedImage bImageFromConvert = ImageIO.read(in);
		 ImageIO.write(bImageFromConvert, "jpg",new File(newFileName));
		 
		 FaceMatcherPerson faceMatcherPerson = new FaceMatcherPerson();
		 faceMatcherPerson.setName(name+" "+ surname);
		 faceMatcherPerson.setPath(newFileName);
		 faceMatcherPerson.setAfid(afidi);
		 faceMatcherPerson.setPassword(password);
		 
		 persist(faceMatcherPerson);
		
	}

	public SearchResultDTO search(byte[] data) throws Exception{
		Date t0 = new Date();
		byte[] query = createAfid(data);
		Date t1 = new Date();
		// tek yüz için geçerli
		
        
//        if(afidList.size()==0){
        	//afidList = getAllAfids();
        	List<FaceMatcherPerson> list = findAll();
        	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				FaceMatcherPerson faceMatcherPerson = (FaceMatcherPerson) iterator.next();
				memory.put(faceMatcherPerson.getAfid(), faceMatcherPerson.getName()+","+faceMatcherPerson.getPassword());
				afidList.add(faceMatcherPerson.getAfid());
			}
//        }	
        Date t2 = new Date();
        
        float[] scores = new float[afidList.size()];
        int[] indexes = new int[afidList.size()];
        Date t3 = new Date();
        sdk.MatchAfids(query, afidList, scores, indexes);
        Date t4 = new Date();
     
        int index = Util.sort(scores);
        float score  = scores[index];
        byte[] a = afidList.get(index);
        Date t5 = new Date();
        //FaceMatcherPerson person = findByPropertyUnique("afid", a);
        FaceMatcherPerson person = new FaceMatcherPerson();
        //person.setPath(memory.get(a));
        String[] b =  memory.get(a).split(",");
        person.setName(b[0]);
        person.setPassword(b[1]);
        
        Date t6 = new Date();
		
        SearchResultDTO result = new SearchResultDTO();
        result.setPerson(person);
        result.setScore(score);
        
        Date t7 = new Date();
        
        System.out.println("total ="+(t7.getTime()-t0.getTime())+",t2 ="+(t2.getTime()-t1.getTime())+",t3="+(t3.getTime()-t2.getTime())+",t4="+(t4.getTime()-t3.getTime())+",t5="+(t5.getTime()-t4.getTime())+",t6="+(t6.getTime()-t5.getTime())+",t7="+(t7.getTime()-t6.getTime()));
        System.out.println("search method:"+(t7.getTime()-t0.getTime()));
        return result;
	}
	public SearchResultDTO search2(byte[] data) throws Exception{
		Date t0 = new Date();
		byte[] query = createAfid(data);
		Date t1 = new Date();
		// tek yüz için geçerli
		
        
        if(afidList.size()==0){
        	//afidList = getAllAfids();
        	List<FaceMatcherPerson> list = findAll();
        	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				FaceMatcherPerson faceMatcherPerson = (FaceMatcherPerson) iterator.next();
				memory.put(faceMatcherPerson.getAfid(), faceMatcherPerson.getPath());
				afidList.add(faceMatcherPerson.getAfid());
			}
        }	
        Date t2 = new Date();
        
        float[] scores = new float[afidList.size()];
        int[] indexes = new int[afidList.size()];
        Date t3 = new Date();
//        sdk.MatchAfids(query, afidList, scores, indexes);
//        
//     
//        int index = Util.sort(scores);
//        float score  = scores[index];
//        byte[] a = afidList.get(index);
        
        final ForkJoinPool pool = new ForkJoinPool(4);
        final ForkAyonix finder = new ForkAyonix(sdk,query,afidList);
        ForkDto dto = pool.invoke(finder);
        byte[] a = dto.getAfid();
        float score = dto.getScore();
        
        Date t4 = new Date();
        
        Date t5 = new Date();
        //FaceMatcherPerson person = findByPropertyUnique("afid", a);
        FaceMatcherPerson person = new FaceMatcherPerson();
        person.setPath(memory.get(a));
        Date t6 = new Date();
		
        SearchResultDTO result = new SearchResultDTO();
        result.setPerson(person);
        result.setScore(score);
        
        Date t7 = new Date();
        
        System.out.println("total ="+(t7.getTime()-t0.getTime())+",t2 ="+(t2.getTime()-t1.getTime())+",t3="+(t3.getTime()-t2.getTime())+",t4="+(t4.getTime()-t3.getTime())+",t5="+(t5.getTime()-t4.getTime())+",t6="+(t6.getTime()-t5.getTime())+",t7="+(t7.getTime()-t6.getTime()));
        System.out.println("asda");
        return result;
	}
	
	public SearchResultDTO search3(byte[] data) throws Exception{
		Date t0 = new Date();
		byte[] query = createAfid(data);
		Date t1 = new Date();
		// tek yüz için geçerli
		
        
        if(afidList.size()==0){
        	//afidList = getAllAfids();
        	List<FaceMatcherPersonFullData> list = faceMatcherPersonFullDataDao.findAll();
        	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        		FaceMatcherPersonFullData faceMatcherPerson = (FaceMatcherPersonFullData) iterator.next();
				memory.put(faceMatcherPerson.getAfid(), faceMatcherPerson.getPath());
				afidList.add(faceMatcherPerson.getAfid());
			}
        }	
        Date t2 = new Date();
        
        float[] scores = new float[afidList.size()];
        int[] indexes = new int[afidList.size()];
        Date t3 = new Date();
//        sdk.MatchAfids(query, afidList, scores, indexes);
//        
//     
//        int index = Util.sort(scores);
//        float score  = scores[index];
//        byte[] a = afidList.get(index);
        
        final ForkJoinPool pool = new ForkJoinPool(4);
        final ForkAyonix finder = new ForkAyonix(sdk,query,afidList);
        ForkDto dto = pool.invoke(finder);
        byte[] a = dto.getAfid();
        float score = dto.getScore();
        
        Date t4 = new Date();
        
        Date t5 = new Date();
        //FaceMatcherPerson person = findByPropertyUnique("afid", a);
        FaceMatcherPerson person = new FaceMatcherPerson();
        person.setPath(memory.get(a));
        Date t6 = new Date();
		
        SearchResultDTO result = new SearchResultDTO();
        result.setPerson(person);
        result.setScore(score);
        
        Date t7 = new Date();
        
        System.out.println("total ="+(t7.getTime()-t0.getTime())+",t2 ="+(t2.getTime()-t1.getTime())+",t3="+(t3.getTime()-t2.getTime())+",t4="+(t4.getTime()-t3.getTime())+",t5="+(t5.getTime()-t4.getTime())+",t6="+(t6.getTime()-t5.getTime())+",t7="+(t7.getTime()-t6.getTime()));
        System.out.println("asda");
        return result;
	}
	
	
	
	
	
	public FaceMatcherPerson findByPropertyUnique(String property,Object value) throws Exception{
		List<FaceMatcherPerson> a = findByProperty("afid", value);
		if(a.size()>1)
			return a.get(0);
		if(a.size()==1)
			return a.get(0);
		else return null;
	}
	
	public void delete(Long id) throws Exception{
		remove(id);
		
	}

	public SearchResultDTO compare(byte[] data, byte[] data2) throws Exception {
		Date t0 = new Date();
		System.out.println("method start="+t0);
		byte[] query = createAfid(data);
		byte[] query2 = createAfid(data2);
		
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
	
	public SearchResultDTO compare(String id, byte[] data2) throws Exception {
		Date t0 = new Date();;
		System.out.println("method start="+t0);
		
		FaceMatcherPerson person =findById(new Long(id));
		byte[] query = person.getAfid();
		byte[] query2 = createAfid(data2);
		
			
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

	public SearchResultDTO compareByPassword(String password, byte[] data2) throws Exception {
		
		SearchResultDTO result = new SearchResultDTO();
		
		//FaceMatcherPerson person =findById(new Long(id));
		SearchResultDTO dto= search(data2);
		if(dto.getScore()<0.7)
			throw new Exception("no user found");
		
		FaceMatcherPerson person = dto.getPerson();
		dto.setPerson(person);
		
		if(!password.equals(person.getPassword())){
			throw new Exception("password error");
		}
		return dto;
	}
	
	@Override
	public Vector<byte[]> getAllAfids() throws Exception {
		Vector<byte[]> v = new Vector<byte[]>();
		
		List<byte[]> l = getEntityManager().createQuery("Select t.afid from " + getEntityClass().getSimpleName() + " t").getResultList();
		
		//Collections.copy(v,l);
		v.addAll(l);
		
		return v;
	}

	public FaceID getSdk() {
		return sdk;
	}

	public void setSdk(FaceID sdk) {
		this.sdk = sdk;
	}

	
}

