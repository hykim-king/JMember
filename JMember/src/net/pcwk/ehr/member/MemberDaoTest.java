package net.pcwk.ehr.member;

import java.sql.Connection;
import java.sql.SQLException;

import net.pcwk.ehr.cmn.PLog;

public class MemberDaoTest implements PLog {
    
	MemberDao dao;
	MemberDTO member01;
	
	public MemberDaoTest() {
		dao = new MemberDao();
		member01 = new MemberDTO();
		member01.setMemberId("james01");
		member01.setPassword("4321");
	}
	
	public void getConnection() {
			Connection conn = dao.getConnection();
			log.debug("conn:"+conn);
	}
	
	public void login() {
		int flag =dao.idCheck(member01);
		log.debug("flag:{}",flag);
		
		if(flag==1) {
			int passCnt = dao.passCheck(member01);
			
			if(passCnt ==1 ) {
				MemberDTO outVO = dao.doSelectOne(member01);
				log.debug("MemberDTO:{}",outVO);
			}
		}else {
			flag = 2;//ID오류
		}
	}
	
	
	public void passCheck() {
		int flag =dao.passCheck(member01);
		log.debug("flag:{}",flag);
	}
	

	public void idCheck() {
		int flag =dao.idCheck(member01);
		log.debug("flag:{}",flag);
	}
	
	public void doSelectOne() {
		MemberDTO outVO = dao.doSelectOne(member01);
		log.debug("MemberDTO:{}",outVO);
	}
	
	public static void main(String[] args) {
		MemberDaoTest memberDaoTest=new MemberDaoTest();
		//memberDaoTest.getConnection();
		//memberDaoTest.doSelectOne();
		//memberDaoTest.idCheck();
		//memberDaoTest.passCheck();
		
		memberDaoTest.login();

	}

}
