package net.pcwk.ehr.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.pcwk.ehr.cmn.DTO;
import net.pcwk.ehr.cmn.WorkDiv;

public class MemberDao implements WorkDiv<MemberDTO> {

	public MemberDao() {

	}

	public Connection getConnection() {
		final String DB_URL = "jdbc:oracle:thin:@192.168.100.30:1522:xe";
		final String DB_USER = "scott";
		final String DB_PASSWORD = "pcwk";

		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("1.conn:" + conn);

		return conn;

	}

	public int updateLoginCount(MemberDTO param) {
		int flag = 0;
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" UPDATE member                            \n");
		sb.append(" SET                                      \n");
		sb.append("      login_count = NVL(login_count,0)+1  \n");
		sb.append(" WHERE                                    \n");
		sb.append("         member_id = ?                    \n");

		log.debug(String.format("1.param:%s", param));
		log.debug(String.format("2.sql:\n%s", sb.toString()));
		PreparedStatement pstmt = null;

		try {
			log.debug(String.format("3.conn:%s", conn));
			pstmt = conn.prepareStatement(sb.toString());
			log.debug(String.format("3.1pstmt:%s", pstmt));

			pstmt.setString(1, param.getMemberId());

			flag = pstmt.executeUpdate();
		} catch (SQLException e) {
			log.debug(String.format("4.SQLException:%s", e.getMessage()));
		} finally {


			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
		log.debug(String.format("5. flag:%d", flag));
		
		return flag;		
	}
	
	
	
	@Override
	public List<MemberDTO> doRetrieve(DTO search) {
		
		
		MemberDTO outVO = (MemberDTO)search;
		List<MemberDTO> list =new ArrayList<MemberDTO>();
		
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT                                                      \n");
		sb.append("     M.MEMBER_ID,         -- 회원 ID                          \n");
		sb.append("     M.MEMBER_NAME,       -- 사용자명                           \n");
		sb.append("     M.PASSWORD,          -- 비밀번호                           \n");
		sb.append("     M.EMAIL,             -- 이메일                            \n");
		sb.append("     M.BIRTHDAY,          -- 생년월일                           \n");
		sb.append("     M.CELLPHONE,         -- 핸드폰                            \n");
		sb.append("     M.LOGIN_COUNT,       -- 로그인 횟수                         \n");
		sb.append("     T.TEAM_NAME,         -- 팀명                             \n");
		sb.append("     R.ROLE_NAME,         -- 권한명                            \n");
		sb.append("     TO_CHAR( M.REG_DT,'yyyy/mm/dd hh24:mi:ss ff') REG_DT    \n");
		sb.append(" FROM                                                        \n");
		sb.append("     MEMBER M                                                \n");
		sb.append(" JOIN                                                        \n");
		sb.append("     TEAM T ON M.TEAM_ID = T.TEAM_ID                         \n");
		sb.append(" LEFT JOIN                                                   \n");
		sb.append("     USER_ROLE UR ON M.MEMBER_ID = UR.MEMBER_ID              \n");
		sb.append(" LEFT JOIN                                                   \n");
		sb.append("     ROLE R ON UR.ROLE_ID = R.ROLE_ID                        \n");
		sb.append(" WHERE M.TEAM_ID = ?                                       \n");

		log.debug(String.format("1.param:%s", outVO));
		log.debug(String.format("2.sql:\n%s", sb.toString()));

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			log.debug(String.format("3.conn:%s", conn));
			pstmt = conn.prepareStatement(sb.toString());
			log.debug(String.format("3.1pstmt:%s", pstmt));

			pstmt.setInt(1, outVO.getTeamId());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO vo = new MemberDTO();
				vo.setMemberId(rs.getString("MEMBER_ID"));
				vo.setMemberName(rs.getString("MEMBER_NAME"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setEmail(rs.getString("EMAIL"));

				vo.setBirthday(rs.getString("BIRTHDAY"));
				vo.setCellphone(rs.getString("CELLPHONE"));

				vo.setLoginCount(rs.getInt("LOGIN_COUNT"));

				vo.setTeamName(rs.getString("TEAM_NAME"));
				vo.setRoleName(rs.getString("ROLE_NAME"));
				vo.setRegDt(rs.getString("REG_DT"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			log.debug(String.format("4.SQLException:%s", e.getMessage()));
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
	
	public int passCheck(MemberDTO param) {
		int flag = 0;
		MemberDTO outVO = null;
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT COUNT(*) cnt           \n");
		sb.append(" FROM                          \n");
		sb.append("     member                    \n");
		sb.append(" WHERE member_id = ?           \n");
		sb.append(" And   PASSWORD  = ?           \n");

		log.debug(String.format("1.param:%s", param));
		log.debug(String.format("2.sql:\n%s", sb.toString()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			log.debug(String.format("3.conn:%s", conn));
			pstmt = conn.prepareStatement(sb.toString());
			log.debug(String.format("3.1pstmt:%s", pstmt));

			pstmt.setString(1, param.getMemberId());
			pstmt.setString(2, param.getPassword());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			log.debug(String.format("4.SQLException:%s", e.getMessage()));
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
		log.debug(String.format("5. flag:%d", flag));
		
		return flag;
	}
	

	public int idCheck(MemberDTO param) {
		int flag = 0;
		MemberDTO outVO = null;
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT COUNT(*) cnt           \n");
		sb.append(" FROM                          \n");
		sb.append("     member                    \n");
		sb.append(" WHERE member_id = ?           \n");

		log.debug(String.format("1.param:%s", param));
		log.debug(String.format("2.sql:\n%s", sb.toString()));
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			log.debug(String.format("3.conn:%s", conn));
			pstmt = conn.prepareStatement(sb.toString());
			log.debug(String.format("3.1pstmt:%s", pstmt));

			pstmt.setString(1, param.getMemberId());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			log.debug(String.format("4.SQLException:%s", e.getMessage()));
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
		log.debug(String.format("5. flag:%d", flag));
		
		return flag;
	}

	@Override
	public int doSave(MemberDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(MemberDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(MemberDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberDTO doSelectOne(MemberDTO param) {
		MemberDTO outVO = null;
		// 1. DB연결을 위한 Connection
		// 2. SQL을 담은 PreparedStatement,Statement를 생성
		// 3. PreparedStatement를 실행한다.
		// 4. 실행결과 받기 ResultSet 받아서 저장
		// 5. Connection,PreparedStatement,ResultSet의 자원 반납.
		// 6. JDBC API에 대한 예외 처리
		Connection conn = getConnection();
		StringBuilder sb = new StringBuilder(200);
		sb.append(" SELECT                                                      \n");
		sb.append("     M.MEMBER_ID,         -- 회원 ID                          \n");
		sb.append("     M.MEMBER_NAME,       -- 사용자명                           \n");
		sb.append("     M.PASSWORD,          -- 비밀번호                           \n");
		sb.append("     M.EMAIL,             -- 이메일                            \n");
		sb.append("     M.BIRTHDAY,          -- 생년월일                           \n");
		sb.append("     M.CELLPHONE,         -- 핸드폰                            \n");
		sb.append("     M.LOGIN_COUNT,       -- 로그인 횟수                         \n");
		sb.append("     T.TEAM_NAME,         -- 팀명                             \n");
		sb.append("     R.ROLE_NAME,         -- 권한명                            \n");
		sb.append("     TO_CHAR( M.REG_DT,'yyyy/mm/dd hh24:mi:ss ff') REG_DT    \n");
		sb.append(" FROM                                                        \n");
		sb.append("     MEMBER M                                                \n");
		sb.append(" JOIN                                                        \n");
		sb.append("     TEAM T ON M.TEAM_ID = T.TEAM_ID                         \n");
		sb.append(" LEFT JOIN                                                   \n");
		sb.append("     USER_ROLE UR ON M.MEMBER_ID = UR.MEMBER_ID              \n");
		sb.append(" LEFT JOIN                                                   \n");
		sb.append("     ROLE R ON UR.ROLE_ID = R.ROLE_ID                        \n");
		sb.append(" WHERE M.MEMBER_ID = ?                                       \n");

		log.debug(String.format("1.param:%s", param));
		log.debug(String.format("2.sql:\n%s", sb.toString()));

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			log.debug(String.format("3.conn:%s", conn));
			pstmt = conn.prepareStatement(sb.toString());
			log.debug(String.format("3.1pstmt:%s", pstmt));

			pstmt.setString(1, param.getMemberId());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				outVO = new MemberDTO();
				outVO.setMemberId(rs.getString("MEMBER_ID"));
				outVO.setMemberName(rs.getString("MEMBER_NAME"));
				outVO.setPassword(rs.getString("PASSWORD"));
				outVO.setEmail(rs.getString("EMAIL"));

				outVO.setBirthday(rs.getString("BIRTHDAY"));
				outVO.setCellphone(rs.getString("CELLPHONE"));

				outVO.setLoginCount(rs.getInt("LOGIN_COUNT"));

				outVO.setTeamName(rs.getString("TEAM_NAME"));
				outVO.setRoleName(rs.getString("ROLE_NAME"));
				outVO.setRegDt(rs.getString("REG_DT"));
			}
		} catch (SQLException e) {
			log.debug(String.format("4.SQLException:%s", e.getMessage()));
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return outVO;
	}

}
