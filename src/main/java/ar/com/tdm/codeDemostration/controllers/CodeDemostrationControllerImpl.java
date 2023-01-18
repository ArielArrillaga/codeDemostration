package ar.com.tdm.codeDemostration.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tdm.codeDemostration.entitys.GeneroResponse;
import ar.com.tdm.codeDemostration.entitys.IdUser;
import ar.com.tdm.codeDemostration.entitys.LoginRequest;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.Response;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;
import ar.com.tdm.codeDemostration.entitys.SendEmailAdjuntos;
import ar.com.tdm.codeDemostration.entitys.UserDataResponse;
import ar.com.tdm.codeDemostration.services.CodeDemostrationEmailService;
import ar.com.tdm.codeDemostration.services.CodeDemostrationSecurityService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Api(value = "codeDemostration", tags = "codeDemostration")
@ControllerAdvice()
@CrossOrigin(allowCredentials = "true", origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.PATCH,
		RequestMethod.HEAD })
public class CodeDemostrationControllerImpl implements CodeDemostrationController {

	private final Logger log = LoggerFactory.getLogger(CodeDemostrationControllerImpl.class);

	@Autowired
	CodeDemostrationSecurityService securityService;
	
	@Autowired
	CodeDemostrationEmailService emailService;

	@Override
	@GetMapping("/sample")
	@ResponseBody
	public ResponseEntity<String> servicioEjemplo(String variable) throws Exception {
		
		return new ResponseEntity<String>(this.securityService.servicioEjemplo(variable), HttpStatus.OK);
	}

	@Override
	@PostMapping("/userLogIn")
	public ResponseEntity<LoginResponse> login( LoginRequest request) throws Exception {
		
	    LoginResponse response = new LoginResponse();
		try {
			log.info("codeDemostrationControllerImpl: login: inicio: userName (sin ceros adelante): "+ request.getUserName()+", nickName: "+ request.getNickName()); 
			response = this.securityService.login(request.getUserName(), request.getPassword(), request.getNickName());
			log.info("codeDemostrationControllerImpl: login: respuesta: "+response); 
		} catch (Exception e) {
			log.error("codeDemostrationControllerImpl: login: ERROR: " + e);
			return new ResponseEntity<LoginResponse>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}
	
    @Override
    @PostMapping("/sendEmailAdjunto")
    public ResponseEntity<Response> sendEmailAdjunto(SendEmailAdjuntos request) {
        Response response = new Response();
        try {
            log.info("codeDemostrationControllerImpl: sendEmailAdjunto: request: "+request); 
            response = emailService.sendEmailAdjunto(request);
            log.info("codeDemostrationControllerImpl: sendEmailAdjunto: respuesta: "+response); 
        } catch (Exception e) {
            log.error("codeDemostrationControllerImpl: sendEmailAdjunto: ERROR: " + e);
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<UserDataResponse> getUserData(IdUser request) {
		UserDataResponse response = new UserDataResponse();
	        try {
	            log.info("codeDemostrationControllerImpl: getUserData: request: "+request); 
	            response = securityService.getUserData(request);
	            log.info("codeDemostrationControllerImpl: getUserData: respuesta: "+response); 
	        } catch (Exception e) {
	            log.error("codeDemostrationControllerImpl: getUserData: ERROR: " + e);
	            return new ResponseEntity<UserDataResponse>(response, HttpStatus.BAD_REQUEST);
	        }
	        return new ResponseEntity<UserDataResponse>(response, HttpStatus.OK);
	}
}
