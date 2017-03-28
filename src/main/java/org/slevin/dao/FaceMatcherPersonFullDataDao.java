package org.slevin.dao;

import java.util.Vector;

import org.slevin.common.FaceMatcherPerson;
import org.slevin.common.FaceMatcherPersonFullData;
import org.slevin.util.SearchResultDTO;

import ayonix.FaceID;

public interface FaceMatcherPersonFullDataDao extends EntityDao<FaceMatcherPersonFullData>{
	public void enroll(String name,String surname,byte[] data,String fileName) throws Exception;
	public SearchResultDTO search(byte[] data) throws Exception;
	public SearchResultDTO search2(byte[] data) throws Exception;
	public void delete(Long id) throws Exception;
	public SearchResultDTO compare(byte[] data,byte[] data2) throws Exception;
	public SearchResultDTO compare(String id,byte[] data2) throws Exception;
	
	public Vector<byte[]> getAllAfids() throws Exception;
	
	public FaceID getSdk();
}
