package ar.com.tdm.codeDemostration.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tdm.codeDemostration.daos.codeDemostrationDao;

@Service
public class codeDemostrationServiceImpl implements codeDemostrationService {

	private final Logger log = LoggerFactory.getLogger(codeDemostrationServiceImpl.class);

	@Autowired
	codeDemostrationDao dao;

	@Override
	public String servicioEjemplo(String variable) throws Exception {
		
		return this.dao.servicioEjemplo(variable);
	}




}
