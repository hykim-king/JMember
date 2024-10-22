package net.pcwk.ehr.member;

import java.util.List;

import net.pcwk.ehr.cmn.DTO;
import net.pcwk.ehr.cmn.PLog;

public class MemberService implements PLog {

	private MemberDao dao;

	public MemberService() {
		dao = new MemberDao();
				
	}
	
	/**
	 * 회원목록 조회:
	 * 
	 * @param MemberDTO
	 * @return List<MemberDTO>
	 */
	public List<MemberDTO> doRetrieve(DTO search) {
		return dao.doRetrieve(search);
	}
	/**
	 * 10: ID를 확인 하세요.
	 * 20: 비번을 확인 하세요.
	 * 30: ID/비번 일치 : 로그인
	 * @param param
	 * @return int
	 */
	public int loginCheck(MemberDTO param) {
		int flag = 0;
		
		flag =dao.idCheck(param);
		log.debug("flag:{}",flag);
		
		if(flag==1) {
			int passCnt = dao.passCheck(param);
			
			//id와 비번이 일치 합니다.
			if(passCnt == 1 ) {
				flag = 30;
			//비번을 확인 하세요.	
			}else {
				flag = 20;
			}
		//ID를 확인 하세요.	
		}else {
			flag = 10;
		}
		
		return flag;
	}
	
	/**
	 * 회원정보 
	 * @param param
	 * @return MemberDTO
	 */
	public MemberDTO doSelectOne(MemberDTO param) {
		MemberDTO member = dao.doSelectOne(param);
		updateLoginCount(param);
		return member;
	}
	
	private int updateLoginCount(MemberDTO param) {
		return dao.updateLoginCount(param);
	}
	
}
