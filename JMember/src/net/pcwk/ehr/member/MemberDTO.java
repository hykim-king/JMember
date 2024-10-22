package net.pcwk.ehr.member;

import net.pcwk.ehr.cmn.DTO;

public class MemberDTO extends DTO {

	private String memberId;// 회원ID
	private String memberName;// 사용자명
	private String password;// 비밀번호
	private String email;// 이메일
	
	private String birthday; //생년월일
	private String cellphone;//핸드폰
	
	private int loginCount;// 로그인 횟수
	private int teamId;// 팀_ID
	private String regDt;// 생성일
	
	
	private String teamName;// 팀명
	private String roleName;// 롤명

	public MemberDTO() {

	}



	public MemberDTO(String memberId, String memberName, String password, String email, String birthday,
			String cellphone, int loginCount, int teamId, String regDt, String teamName, String roleName) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.password = password;
		this.email = email;
		this.birthday = birthday;
		this.cellphone = cellphone;
		this.loginCount = loginCount;
		this.teamId = teamId;
		this.regDt = regDt;
		this.teamName = teamName;
		this.roleName = roleName;
	}



	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	
	public String getBirthday() {
		return birthday;
	}



	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}



	public String getCellphone() {
		return cellphone;
	}



	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}



	@Override
	public String toString() {
		return "MemberDTO [memberId=" + memberId + ", memberName=" + memberName + ", password=" + password + ", email="
				+ email + ", birthday=" + birthday + ", cellphone=" + cellphone + ", loginCount=" + loginCount
				+ ", teamId=" + teamId + ", regDt=" + regDt + ", teamName=" + teamName + ", roleName=" + roleName
				+ ", toString()=" + super.toString() + "]";
	}





}
