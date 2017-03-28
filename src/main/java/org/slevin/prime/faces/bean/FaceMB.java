package org.slevin.prime.faces.bean;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.json.simple.parser.ParseException;
import org.primefaces.component.tabview.TabView;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slevin.common.AyonixPerson;
import org.slevin.common.FaceMatcherPerson;
import org.slevin.dao.AyonixPersonDao;
import org.slevin.dao.FaceMatcherPersonDao;
import org.slevin.util.SearchResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="faceMB")
@ViewScoped
public class FaceMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	//Map<String,String> imageList = new HashMap 
	
	@Autowired
	AyonixPersonDao ayonixPersonService;
	
	@Autowired
	FaceMatcherPersonDao faceMatcherPersonService;
	
	private TabView tabView;

	private UploadedFile uploadfile;
	private UploadedFile searchFile;
	private UploadedFile identityFile;
	List<FaceMatcherPerson> personList;
	//List<PairKey> resultList;
	
	private StreamedContent myImage;
	private StreamedContent myImage2;
	
	SearchResultDTO resultDTO;
	SearchResultDTO resultDTO2;
	
	String name;
	String surname;
	String password;
	
	String userIdForDelete;
	
	private UploadedFile comparefile1;
	private UploadedFile comparefile2;
	

	private StreamedContent compareImage;
	private StreamedContent compareImage2;
	
	private FaceMatcherPerson selectedPerson;
	
	@PostConstruct
	public void init() throws Exception{
		//updateUserList();
	}
	
	public void updateUserList() throws Exception{
		personList = faceMatcherPersonService.findAll();
	}
	
	
	
	public void compare() throws Exception{
		try {
			System.out.println("geldi");
		
			//resultList = AyonixUtil.searchPerson(identityFile.getContents());
			compareImage = new DefaultStreamedContent(new ByteArrayInputStream(comparefile1.getContents()), "image/png");
			compareImage2 = new DefaultStreamedContent(new ByteArrayInputStream(comparefile2.getContents()), "image/png");
			resultDTO2 = faceMatcherPersonService.compare(comparefile1.getContents(),comparefile2.getContents());
			
			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
//			updateUserList();
//			tabView.setActiveIndex(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata="+e.getMessage(), e.getMessage()));
		}
	}
	
	public void test() throws Exception{
		File file = new File("C:\\Users\\ETR00529\\Desktop\\aliaydin.jpg");
		FileInputStream fin = new FileInputStream(file);
		byte fileContent[]= new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
		
		
		SearchResultDTO resultDTO= faceMatcherPersonService.search(fileContent);
		System.out.println(resultDTO.getScore() +" "+ resultDTO.getPerson()+ " "+resultDTO.getPerson().getName());
		}
	
	public void upload() throws ParseException{
		try {
			System.out.println("geldi");
			faceMatcherPersonService.enroll(name, surname, searchFile.getContents(), searchFile.getFileName(),password);;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
			updateUserList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}
	
	public void deletePerson() throws ParseException{
		try {
			System.out.println("geldi");
			

			//List<AyonixPerson> list = ayonixPersonService.findByProperty("userId", userIdForDelete);
//			if(list.size()==0){
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", "user bulunamadı"));
//				return;
//			}
			faceMatcherPersonService.delete(selectedPerson.getId());
			File file = new File(selectedPerson.getPath());
			file.delete();
			//ayonixPersonService.remove(list.get(0).getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
			updateUserList();
			tabView.setActiveIndex(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}

	public void identityPerson() throws ParseException{
		try {
			System.out.println("geldi");
		
			//resultList = AyonixUtil.searchPerson(identityFile.getContents());
			myImage = new DefaultStreamedContent(new ByteArrayInputStream(identityFile.getContents()), "image/png");
        
			resultDTO=faceMatcherPersonService.search(identityFile.getContents());
			myImage2 = new DefaultStreamedContent(new FileInputStream(resultDTO.getPerson().getPath()), "image/png");
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
//			updateUserList();
//			tabView.setActiveIndex(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}


	public UploadedFile getFile() {
		return uploadfile;
	}

	public void setFile(UploadedFile file) {
		this.uploadfile = file;
	}

	

	public UploadedFile getSearchFile() {
		return searchFile;
	}

	public void setSearchFile(UploadedFile searchFile) {
		this.searchFile = searchFile;
	}

	

	
	public StreamedContent getMyImage() {
		//System.out.println(myImage.getStream());
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public UploadedFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(UploadedFile uploadfile) {
		this.uploadfile = uploadfile;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<FaceMatcherPerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<FaceMatcherPerson> personList) {
		this.personList = personList;
	}

	public String getUserIdForDelete() {
		return userIdForDelete;
	}

	public void setUserIdForDelete(String userIdForDelete) {
		this.userIdForDelete = userIdForDelete;
	}

	public UploadedFile getIdentityFile() {
		return identityFile;
	}

	public void setIdentityFile(UploadedFile identityFile) {
		this.identityFile = identityFile;
	}



	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public SearchResultDTO getResultDTO() {
		return resultDTO;
	}

	public void setResultDTO(SearchResultDTO resultDTO) {
		this.resultDTO = resultDTO;
	}

	public StreamedContent getMyImage2() {
		return myImage2;
	}

	public void setMyImage2(StreamedContent myImage2) {
		this.myImage2 = myImage2;
	}

	public UploadedFile getComparefile1() {
		return comparefile1;
	}

	public void setComparefile1(UploadedFile comparefile1) {
		this.comparefile1 = comparefile1;
	}

	public UploadedFile getComparefile2() {
		return comparefile2;
	}

	public void setComparefile2(UploadedFile comparefile2) {
		this.comparefile2 = comparefile2;
	}

	public StreamedContent getCompareImage() {
		return compareImage;
	}

	public void setCompareImage(StreamedContent compareImage) {
		this.compareImage = compareImage;
	}

	public StreamedContent getCompareImage2() {
		return compareImage2;
	}

	public void setCompareImage2(StreamedContent compareImage2) {
		this.compareImage2 = compareImage2;
	}

	public SearchResultDTO getResultDTO2() {
		return resultDTO2;
	}

	public void setResultDTO2(SearchResultDTO resultDTO2) {
		this.resultDTO2 = resultDTO2;
	}

	public FaceMatcherPerson getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(FaceMatcherPerson selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	
	
	

	
}
