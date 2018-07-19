package com.test.service.services;


import java.io.IOException;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import com.test.service.model.TestRequest;
import com.test.service.model.TestResponse;


@WebService()
public class TestWS {
	
	
	public TestResponse enviaRespuesta(@WebParam(name="TestRequest") TestRequest testRequest){
		
		
		TestResponse testResponse = new TestResponse();
		
		testResponse.setMensaje("SOAP Mensaje Respuesta " + testRequest.getMensaje() );
		testResponse.setCodigo("SOAP Codigo Respuesta " + testRequest.getCodigo() );
		
		return testResponse;
	} 
	
}
