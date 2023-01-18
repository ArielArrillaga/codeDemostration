package ar.com.tdm.codeDemostration.services;

import ar.com.tdm.codeDemostration.entitys.Response;
import ar.com.tdm.codeDemostration.entitys.SendEmailAdjuntos;

public interface CodeDemostrationEmailService {
	public Response sendEmailAdjunto(SendEmailAdjuntos request);
}
