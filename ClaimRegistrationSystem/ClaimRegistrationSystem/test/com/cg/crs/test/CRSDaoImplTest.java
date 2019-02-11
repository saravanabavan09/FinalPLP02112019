package com.cg.crs.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cg.crs.dao.CRSDao;
import com.cg.crs.dao.implementation.CRSDaoImpl;
import com.cg.crs.exception.CRSException;
import com.cg.crs.model.ClaimCreationEntity;
import com.cg.crs.model.Report;
import com.cg.crs.model.UserRole;

public class CRSDaoImplTest {
CRSDao crsDao;
	@Before
	public void setUp() throws Exception {
		crsDao=new CRSDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		crsDao=null;
	}

	@Test
	public void testProfileCreation() {
		UserRole role=new UserRole();
		role.setUserName("surya");
		role.setPassword("surya123");
		role.setRoleCode("admin");
		int val=0;
		try {
			boolean valid=crsDao.profileCreation(role);
			
			System.out.println(valid);
			if(valid==true)
			{
				val=1;
			}
			
			assertEquals(1, val);
		} catch (CRSException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReportGeneration() {
	List<Report> list = new ArrayList<Report>();
		try {
			list=crsDao.reportGeneration("Agent1");
			
			System.out.println(list.size());
		
		    assertEquals(1, list.size());
		} catch (CRSException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testInsertClaimDetails() {
		ClaimCreationEntity claimcreate=new ClaimCreationEntity();
		claimcreate.setAccidentCity("City");
		claimcreate.setAccidentLocationStreet("Strret");
		claimcreate.setAccidentState("State");
		claimcreate.setAccidentZip(12345l);
		claimcreate.setClaimReason("Reason");
		claimcreate.setClaimType("Type");
		claimcreate.setPolicyNumber(8100001020l);
		int id=0;
		try {
			id=crsDao.insertClaimDetails(claimcreate);
			if(id>0) {
			assertEquals(1, id);
		} 
		}catch (CRSException e) 
		
		{
			e.printStackTrace();
		}
		
		}

	@Test
	public void testViewClaimStatus() {
		List<ClaimCreationEntity> list = new ArrayList<>();
		ClaimCreationEntity entities = new ClaimCreationEntity();
		entities.setAccidentCity("Madurai");
		entities.setAccidentLocationStreet("Mstreet");
		entities.setAccidentState("TamilNadu");
		entities.setAccidentZip(12365l);
		entities.setClaimNumber(1000000001l);
		entities.setClaimReason("Reason");
		entities.setClaimType("Type");
		entities.setPolicyNumber(8100001021l);
		try {
			long claimId = 1000000001;
			list=crsDao.viewClaimStatus(claimId);
			System.out.println(list.size());
		} catch (CRSException e) {
			e.printStackTrace();
		}
		int record=0;
		if(list.size()>0){
			record=1;
		}
		assertEquals(1, record);
	}

	@Test
	public void testViewAllClaim() {
		List<ClaimCreationEntity> list = new ArrayList<>();
		ClaimCreationEntity entry = new ClaimCreationEntity();
		entry.setAccidentCity("Venice");
		entry.setAccidentLocationStreet("Vstreet");
		entry.setAccidentState("Vstate");
		entry.setAccidentZip(13234l);
		entry.setClaimNumber(1000000003l);
		entry.setClaimReason("FireAccident");
		entry.setPolicyNumber(8100001022l);
		try {
			list=crsDao.viewAllClaim();
		} catch (CRSException e) {
			e.printStackTrace();
		}
		
		int record=0;
		if(list.size()>0){
			record=1;
		}
		assertEquals(1, record);
	}

}
