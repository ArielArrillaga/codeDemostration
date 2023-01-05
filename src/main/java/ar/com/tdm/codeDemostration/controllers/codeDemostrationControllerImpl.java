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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
