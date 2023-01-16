package ar.com.tdm.codeDemostration.services;

import ar.com.tdm.codeDemostration.entitys.GeneroResponse;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;

public interface codeDemostrationService {

	public String servicioEjemplo(String variable) throws Exception;
	public LoginResponse login(String userName, String password64, String nickName) throws Exception;
	public GeneroResponse getGenero(String nickName) throws Exception;
	public ResponseBoolean existNickName(String nickName) throws Exception;
	
}
