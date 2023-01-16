package ar.com.tdm.codeDemostration.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ar.com.tdm.codeDemostration.entitys.GeneroResponse;
import ar.com.tdm.codeDemostration.entitys.LoginRequest;
import ar.com.tdm.codeDemostration.entitys.LoginResponse;
import ar.com.tdm.codeDemostration.entitys.ResponseBoolean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "codeDemostration")
public interface codeDemostrationController {


	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info"),
			@ApiResponse(code = 400, message = "bad request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Tech Error")

	})
	@ApiOperation(value = "")
	public ResponseEntity<String> servicioEjemplo(String variable) throws Exception;
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info"),
			@ApiResponse(code = 400, message = "bad request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Tech Error")

	})
	@ApiOperation(value = "login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest variable) throws Exception;
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info"),
			@ApiResponse(code = 400, message = "bad request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Tech Error")

	})
	@ApiOperation(value = "existNickName")
	public ResponseEntity<ResponseBoolean> existNickName(@RequestParam String nickName) throws Exception;

	 @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info"),
	            @ApiResponse(code = 400, message = "bad request"),
	            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
	            @ApiResponse(code = 500, message = "Tech Error")

	    })
    @ApiOperation(value = "getGenero")
    public ResponseEntity<GeneroResponse> getGenero(@RequestParam String dniONick) throws Exception ;
}
