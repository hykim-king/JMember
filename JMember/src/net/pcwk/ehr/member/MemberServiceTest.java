package net.pcwk.ehr.member;

import java.util.List;

import net.pcwk.ehr.cmn.DTO;

public class MemberServiceTest {

	MemberService service;
	MemberDTO member01;
	
	public MemberServiceTest() {
		service = new MemberService();
		member01 = new MemberDTO();
		member01.setMemberId("user01");
		member01.setPassword("1234");
		member01.setTeamId(3);
	}
	
	public void doRetrieve() {
		List<MemberDTO> list =service.doRetrieve(member01);
		for(MemberDTO vo :list) {
			System.out.println(vo);
		}
	}
	public void login() {
		int flag = service.loginCheck(member01);
		
		if(10 == flag) {
			System.out.println("ID를 확인 하세요.");
		}else if(20 == flag) {
			System.out.println("비번을 확인 하세요.");
		}else if(30 == flag) {
			System.out.println("id와 비번이 일치 합니다.");
			
			MemberDTO outVO=service.doSelectOne(member01);
			System.out.println(outVO);
		}
	}
	
	
	public static void main(String[] args) {
		MemberServiceTest service=new MemberServiceTest();
		//service.login();
		service.doRetrieve();
	}

}
