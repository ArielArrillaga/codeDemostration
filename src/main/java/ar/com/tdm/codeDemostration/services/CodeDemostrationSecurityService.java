package ar.com.tdm.codeDemostration.services;

import ar.com.tdm.codeDemostration.entitys.GeneroResponse;
import ar.com.tdm.codeDemostration.entitys.IdUser;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;
import ar.com.tdm.codeDemostration.entitys.UserDataResponse;

public interface CodeDemostrationSecurityService {

	public String servicioEjemplo(String variable) throws Exception;
	public LoginResponse login(String userName, String password64, String nickName) throws Exception;
	public GeneroResponse getGenero(String nickName) throws Exception;
	public ResponseBoolean existNickName(String nickName) throws Exception;
	public UserDataResponse getUserData(IdUser request) throws Exception;
}
