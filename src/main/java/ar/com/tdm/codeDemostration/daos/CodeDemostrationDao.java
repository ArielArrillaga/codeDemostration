package ar.com.tdm.codeDemostration.daos;

import java.util.Date;

public interface CodeDemostrationDao {

	public String servicioEjemplo(String variable) throws Exception;
	public byte[] getPass(String userName, String nickName) throws Exception;
	public int getRetryCount(String userName, String nickName) throws Exception;
	public int updateRetryCount(String userName, String nickName, int counts) throws Exception;
	public int existNickName(String nickName) throws Exception;
	public String getGenero(String nickName) throws Exception;
	public int updateLastLogin(String userName, String nickName, String status) throws Exception ;
	public Date getLastUpdate(String userName, String nickName) throws Exception;
}
