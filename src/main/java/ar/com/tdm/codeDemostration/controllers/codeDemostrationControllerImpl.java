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
import ar.com.tdm.codeDemostration.entitys.LoginRequest;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;
import ar.com.tdm.codeDemostration.services.codeDemostrationService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Api(value = "codeDemostration", tags = "codeDemostration")
@ControllerAdvice()
@CrossOrigin(allowCredentials = "true", origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET,
		RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.PATCH,
		RequestMethod.HEAD })
public class codeDemostrationControllerImpl implements codeDemostrationController {

	private final Logger log = LoggerFactory.getLogger(codeDemostrationControllerImpl.class);

	@Autowired
	codeDemostrationService service;

	@Override
	@GetMapping("/sample")
	@ResponseBody
	public ResponseEntity<String> servicioEjemplo(String variable) throws Exception {
		
		return new ResponseEntity<String>(this.service.servicioEjemplo(variable), HttpStatus.OK);
	}

	@Override
	@PostMapping("/userLogIn")
	public ResponseEntity<LoginResponse> login( LoginRequest request) throws Exception {
		
	    LoginResponse response = new LoginResponse();
		try {
			log.info("codeDemostrationControllerImpl: login: inicio: userName (sin ceros adelante): "+ request.getUserName()+", nickName: "+ request.getNickName()); 
			response = this.service.login(request.getUserName(), request.getPassword(), request.getNickName());
			log.info("codeDemostrationControllerImpl: login: respuesta: "+response); 
		} catch (Exception e) {
			log.error("codeDemostrationControllerImpl: login: ERROR: " + e);
			return new ResponseEntity<LoginResponse>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}

	@Override
	@GetMapping("/existNickName")
	public ResponseEntity<ResponseBoolean> existNickName(String nickName) throws Exception {
		ResponseBoolean response = new ResponseBoolean();
		try {
			log.info("codeDemostrationControllerImpl: existNickName: inicio: nickName: "+ nickName); 
			response = this.service.existNickName(nickName);
			log.info("codeDemostrationControllerImpl: existNickName: respuesta: "+response); 
		} catch (Exception e) {
			log.error("codeDemostrationControllerImpl: existNickName: ERROR: " + e);
			return new ResponseEntity<ResponseBoolean>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ResponseBoolean>(response, HttpStatus.OK);
	}

	@Override
	@GetMapping("/getGenero")
	public ResponseEntity<GeneroResponse> getGenero(String dniONick) throws Exception {
	        GeneroResponse response = new GeneroResponse();
	        try {
	            log.info("codeDemostrationControllerImpl: getGenero: request: "+dniONick); 
	            response = service.getGenero(dniONick);
	            log.info("codeDemostrationControllerImpl: getGenero: respuesta: "+response); 
	        } catch (Exception e) {
	            log.error("codeDemostrationControllerImpl: getGenero: ERROR: " + e);
	            return new ResponseEntity<GeneroResponse>(response, HttpStatus.BAD_REQUEST);
	        }
	        return new ResponseEntity<GeneroResponse>(response, HttpStatus.OK);
	    }

}
