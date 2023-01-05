package ar.com.tdm.codeDemostration.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class codeDemostrationDaoImpl implements codeDemostrationDao {

	private final Logger log = LoggerFactory.getLogger(codeDemostrationDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String servicioEjemplo(String variable) throws Exception{
		return variable;
	}

}
