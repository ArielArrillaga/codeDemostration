package ar.com.tdm.codeDemostration.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.tdm.codeDemostration.daos.codeDemostrationDao;
import ar.com.tdm.codeDemostration.entitys.GeneroResponse;
import ar.com.tdm.codeDemostration.entitys.JwtResponse;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
public class codeDemostrationServiceImpl implements codeDemostrationService {

	private final Logger log = LoggerFactory.getLogger(codeDemostrationServiceImpl.class);
	
	//with this line i generate a random key with HS512, and the key changes in each instance of the program, 
	//if i restart the server, the key will change
	//i never know what the key is
	private  Key key = MacProvider.generateKey(SignatureAlgorithm.NONE);// here i can choose the signature, in this sample i put "none",
																		// but in the real code i use another

	@Autowired
	codeDemostrationDao dao;

	@Override
	public String servicioEjemplo(String variable) throws Exception {
		
		return this.dao.servicioEjemplo(variable);
	}

	// the login service validates that the password is correct, and that the user hasn't more than 3 failed login Attempts
	//if all is OK so update the login date, reset retryCount, generate and return JWT
	@Override
	public LoginResponse login(String userName, String password64, String nickName) throws Exception { 	//userName is the dni and nickName is a unique alias
																										//to find i need dni + genre, or nickName
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		LoginResponse response = new LoginResponse();
		log.info("codeDemostrationServiceImpl: validate: userName: "+userName); 
		
		nickName=(nickName.toLowerCase());
		
		password64 = this.decode64pass(password64);
		
		String password = this.b64ToString(password64);
		
		try {			
			//retryCount is a login failed counter, if you have 3 failures you will be locked out
			int retryCount = this.dao.getRetryCount(userName, nickName);

			
			if (retryCount>2) { //3 Attempts
				response.setMensaje("Superaste la cantidad de intentos permitidos para ingresar la contraseña");
				log.info("codeDemostrationServiceImpl: validate: Superaste la cantidad de intentos permitidos para ingresar la contraseña ");
				response.setOk(true);
				response.setState(false);
				response.setIntentosRestantes(0);
				response.setExpired(false);
				this.updateLastLogin(userName, nickName, "FAIL");
				return response;
			}
			
			byte[] pass = this.dao.getPass(userName, nickName);  //getRetryCount and getPass can be replaced by a single query that gives me the pass and the retryCount
			
			if (pass == null) {
				response.setMensaje("No se encontro el usuario indicado");
				log.info("codeDemostrationServiceImpl: login: No se encontro al usuario indicado ");
				response.setOk(true);
				response.setState(false);
		        response.setExpired(false);
			}
			else {
				log.info("codeDemostrationServiceImpl: login: Validar contraseña");

				String stringPass = new String(pass, StandardCharsets.UTF_8);
		
				//the password is hashed so i need to use this line to know if the password is correct
				boolean isThePassword = bCryptPasswordEncoder.matches(password, stringPass);
				
				log.info("codeDemostrationServiceImpl: login: isThePassword: "+isThePassword); 
				
				if (isThePassword) {
					
					if (this.isPassExpired(userName, nickName)) {
					
						log.info("codeDemostrationServiceImpl: login: Contraseña vencida ");
						
						response.setExpired(true);
						
					}else {
					    log.info("codeDemostrationServiceImpl: login: Contraseña correcta");
					    response.setExpired(false);
					}

					JwtResponse jwtResponse = this.jwtGeneratorWithNickName(userName, nickName);
					response.setIntentosRestantes(jwtResponse.getIntentosRestantes());
                    response.setMensaje(jwtResponse.getMensaje());
                    response.setOk(jwtResponse.getOk());
                    response.setState(jwtResponse.getState());
                    response.setJwt(jwtResponse.getJwt());

					this.resetRetryCount(userName, nickName); //retryCount = 0
					this.updateLastLogin(userName, nickName, "OK"); //save the date of the login
				}
				else {
					response.setIntentosRestantes(2-retryCount);
					response.setMensaje("Contraseña incorrecta");
					log.info("codeDemostrationServiceImpl: login: Contraseña incorrecta");
					this.updateRetryCount(userName, nickName, 1); //increment retryCount
					this.updateLastLogin(userName, nickName, "FAIL");
			        response.setExpired(false);
					response.setOk(true);
					response.setState(false);
				}
			}
			
		}catch (Exception e) {
			log.error("codeDemostrationServiceImpl: login: ERROR: No se pudo validar: "+e); 
			response.setMensaje("Falló la consulta");
			response.setOk(false);
			response.setState(false);
		}
		return response;
	}
	
    private String decode64pass(String pass64) { 	
    	//the frontend send me the password encrypt with base64 and with some traps for hackers, 
    	//here I dodge the traps and decode the password.
    	//finally i return the pass to later compare it
    	//i can't put the code for obvious reason
        return "passDecode";
    }
    
    private String b64ToString(String b64) {
        byte[] decodedBytes = Base64.getDecoder().decode(b64);
       return new String(decodedBytes);
    }
    
    private void updateLastLogin(String userName, String nickName, String status) throws Exception {
		log.info("codeDemostrationServiceImpl: updateLastLogin: inicio:");
		
		if (status.toUpperCase().equals("OK") || status.toUpperCase().equals("FAIL")) {
			
			if (this.dao.updateLastLogin(userName, nickName, status)>0) {
				log.info("codeDemostrationServiceImpl: updateLastLogin: se actualizo LAST_LOGIN_"+status.toUpperCase()+"_USER ");
			}else{
				log.error("codeDemostrationServiceImpl: updateLastLogin: no se actualizo LAST_LOGIN_"+status.toUpperCase()+"_USER ");
			}			
		}
		else {
			log.error("codeDemostrationServiceImpl: updateLastLogin: no se actualizo LAST_LOGIN_**_USER porque **"+status.toUpperCase()+"** no es un estatus valido (OK o FAIL)");
		}
	}
    
	private boolean isPassExpired(String userName, String nickName) throws Exception {
		Date lastUpdate = this.dao.getLastUpdate( userName, nickName);
		Date today = new Date();

        long diff = today.getTime() - lastUpdate.getTime();
        
        int monthsDiff = (int) ((diff/60000)/43800); //(diff/60000) miliseconds to seconds /43800 seconds to month

        if (monthsDiff>=6) {
        	return true;
        }
		return false;
	}
	
	private JwtResponse jwtGeneratorWithNickName(String userName, String nickName) throws Exception {
		JwtResponse response = new JwtResponse();
		String genero = "";
		log.info("codeDemostrationServiceImpl: tokenGenerator: Comienzo");
		try{
			log.info("codeDemostrationServiceImpl: tokenGenerator: Obtener genero");
			genero = this.getGenero(nickName).getGenero();
			log.info("codeDemostrationServiceImpl: validate: Genero: "+ genero);
			
		}catch (Exception e) {
			log.error("codeDemostrationServiceImpl: tokenGenerator: Obtener genero");
			response.setMensaje("No se pudo obtener el género de: "+ nickName);
			response.setOk(false);
			response.setState(false);
			return response;
		}
		return this.jwtGenerator(userName, genero);
	}
	
	@Override
	public GeneroResponse getGenero(String nickName) throws Exception {
	    GeneroResponse response = new GeneroResponse();
	    String generoStr="";

		    if (!this.existNickName(nickName).isState()) {
		        log.error("codeDemostrationServiceImpl: getGenero: no existe el nickName"); 
	            response.setMensaje("El nombre de usuario indicado no existe");
	            response.setOk(false);
	            return response;
		    }
	        try {
	            log.info("codeDemostrationServiceImpl: getGenero: inicio");
	            generoStr = this.dao.getGenero(nickName);

	            if (generoStr.equals("") || generoStr ==null) {
	                log.error("codeDemostrationServiceImpl: getGenero: genero: "+generoStr);
	                response.setMensaje("Algo salió mal, no pudimos recuperar el genero");
	                response.setOk(false);
	            }else {
	                log.info("codeDemostrationServiceImpl: getGenero: genero: "+generoStr);
	                response.setGenero(generoStr);
	                response.setMensaje("Género recuperado con éxito");
	                response.setOk(true);               
	            }
	            
	        } catch (Exception e) {
	            log.error("codeDemostrationServiceImpl: getGenero: Ocurrio un error al intentar recuperar el genero: "+e); 
	            response.setMensaje("Ocurrió un error al intentar recuperar el genero");
	            response.setOk(false);
	        }
	        return response;
	}
	
	@Override
	public ResponseBoolean existNickName(String nickName) throws Exception {
		ResponseBoolean response = new ResponseBoolean();
		nickName=(nickName.toLowerCase());
		try {
			log.info("codeDemostrationServiceImpl: existNickName: inicio ");
			int cantNickName = this.dao.existNickName(nickName);
			log.info("codeDemostrationServiceImpl: existNickName: nickNames encontrados = "+cantNickName);

			if (cantNickName>0) {
				response.setMensaje("Consulta exitosa");
				response.setOk(true);
				response.setState(true); //the nickName already exist.. can't use it
			}else {
				if (cantNickName==0) {
					response.setMensaje("Consulta exitosa");
					response.setOk(true);
					response.setState(false); //the nickName don't exist.. can use it
				}
				else { // cantNickName = -1
					response.setMensaje("Ocurrió un error al intentar verificar el nombre de usuario");
					response.setOk(false);
					response.setState(true); 
				}
			}
		}
		catch(Exception e){
			log.error("codeDemostrationServiceImpl: existNickName: fallo la consulta al intentar verificar el nickName: "+e); 
			response.setMensaje("Falló la consulta");
			response.setOk(false);
			response.setState(true);
		}
		return response;
	}
	
	private void resetRetryCount(String userName, String nickName) throws Exception {
		this.updateRetryCount(userName, nickName, 0);
	}
	
	private void updateRetryCount(String userName, String nickName, int option) throws Exception{
		
	    //option 1 refer to is necessary increment 1 the retryCount
        //option 0 refer to is necessary reset the retyCount
		
		log.info("codeDemostrationServiceImpl: updateRetryCount: inicio: Opcion: "+option);
		if (option == 1) { 
			int counts = this.dao.getRetryCount(userName, nickName);
			log.info("codeDemostrationServiceImpl: updateRetryCount: retryCount actual: "+counts);
			counts += 1;
			if (this.dao.updateRetryCount(userName, nickName, counts)>0) {
				log.info("codeDemostrationServiceImpl: updateRetryCount: se incremento retryCount");
			}else {
				log.error("codeDemostrationServiceImpl: updateRetryCount: no se incremento retryCount");
			}			
		}else {
			if (option == 0) {
				if (this.dao.updateRetryCount(userName, nickName, option)>0) {
					log.info("codeDemostrationServiceImpl: updateRetryCount: se reseteo retryCount");
				}else{
					log.error("codeDemostrationServiceImpl: updateRetryCount: no se reseteo retryCount");
				}	
			}
			else {
				log.error("codeDemostrationServiceImpl: updateRetryCount: ingrese una opcion valida (1 o 0)");
			}
		}
	}
	
	private JwtResponse jwtGenerator(String userName, String genero) throws Exception {
		JwtResponse response = new JwtResponse();
		log.info("codeDemostrationServiceImpl: tokenGenerator: Comienzo generacion jwt");
		try {
			Claims claims = Jwts.claims();
			claims.put("userName", userName);
			claims.put("genero", genero);
			claims.put("exp", System.currentTimeMillis()+600000);
			claims.put("init", System.currentTimeMillis());
			claims.put("iss", "codeDemostration");
			
			String token = Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.NONE, key)
					.compact();
			
			log.info("codeDemostrationServiceImpl: tokenGenerator: Fin generacion jwt: "+token);
			response.setJwt(token);
			response.setMensaje("Código de seguridad generado con éxito");
			response.setOk(true);
			response.setState(true);
			return response;
			
		}catch (Exception e) {
			log.error("codeDemostrationServiceImpl: tokenGenerator: algo salio mal: " +e);
			response.setMensaje("No se pudo generar el Código de seguridad");
			response.setOk(false);
			response.setState(false);
			return response;
		}
	}
}
