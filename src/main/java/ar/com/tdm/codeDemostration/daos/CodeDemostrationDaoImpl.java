package ar.com.tdm.codeDemostration.daos;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CodeDemostrationDaoImpl implements CodeDemostrationDao {

	private final Logger log = LoggerFactory.getLogger(CodeDemostrationDaoImpl.class);

	@Autowired
	@Qualifier("DB1")
	private JdbcTemplate db1JdbcTemplate;
	
	@Autowired
	@Qualifier("DB2")
	private JdbcTemplate db2JdbcTemplate;

	@Override
	public String servicioEjemplo(String variable) throws Exception{
		return variable;
	}
	
	////////////////////////////IMPORTANT/////////////////////////////////////////
	//all queries are illustrative, the database and the tables don't exist, its just a sample.

	@Override
	public byte[] getPass(String userName, String nickName) throws Exception {
		
		log.info("codeDemostrationDaoImpl: getPass: userName: "+ userName+", nickName: "+ nickName);
		
		String query = "SELECT [PASSWORD] FROM [table1] WHERE [USERNAME] LIKE ? and [NICKNAME] LIKE ?";
		
		log.info("codeDemostrationDaoImpl: getPass: query: "+ query);
		byte[] bitesPassword = null
				;
		try {
			bitesPassword = db1JdbcTemplate.queryForObject(query,  new Object[] {userName, nickName}, byte[].class );
			log.info("codeDemostrationDaoImpl: getPass: respuesta: "+  new String(bitesPassword, StandardCharsets.UTF_8));
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: getPass: error: no se encontraron datos");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: getPass: error: Algo salio mal, no se obtuvo la contraseña. Motivo: " + e);
		}
		return bitesPassword;
	}
	
	@Override
	public int getRetryCount(String userName, String nickName) throws Exception {
		log.info("codeDemostrationDaoImpl: getRetryCount: userName: "+ userName+", nickName: "+ nickName);
		
		String query = "SELECT [RETRY_COUNT] FROM [table1] WHERE [USERNAME] LIKE ? and [NICKNAME] LIKE ?";
		
		log.info("codeDemostrationDaoImpl: getRetryCount: query: "+ query);
		int retryCounts = 0;
		try {
			retryCounts = db1JdbcTemplate.queryForObject(query,  new Object[] {userName, nickName}, Integer.class );
			log.info("codeDemostrationDaoImpl: getRetryCount: respuesta: "+  retryCounts);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: getRetryCount: error: no se encontraron datos");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: getRetryCount: error: Algo salio mal, no se obtuvo el numero de reintentos de inicio de sesion. Motivo: " + e);
		}
		return retryCounts;
	}
	
	@Override
	public int updateRetryCount(String userName, String nickName, int counts) throws Exception {
		log.info("codeDemostrationDaoImpl: updateRetryCount: inicio: counts = "+counts);
		
		String query = "UPDATE [table1] SET [RETRY_COUNT] = ? WHERE [USERNAME] LIKE ? and [NICKNAME] LIKE ?";
		
		log.info("codeDemostrationDaoImpl: updateRetryCount: query: "+ query);
		Integer rows = 0;
		try {
			rows = db1JdbcTemplate.update(query,  new Object[] {counts, userName, nickName});
			log.info("codeDemostrationDaoImpl: updateRetryCount: respuesta: "+  rows);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: updateRetryCount: error: no se pudo actualizar");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: updateRetryCount: error: Algo salio mal, no se actualizo retryCount. Motivo: " + e);
		}
		return rows;
	}
	
	@Override
	public int existNickName(String nickName) throws Exception {
		String query = "SELECT count(NICKNAME_USR) from [table1] where NICKNAME_USR = ?";
		log.info("codeDemostrationDaoImpl: existNickName: query: " + query);
		
		try {
			
			int cant = db1JdbcTemplate.queryForObject(query, new Object[] {nickName}, Integer.class );
			log.info("codeDemostrationDaoImpl: existNickName: Cantidad de registros con el nickname "+nickName+" = " + cant);

			return cant;
		}
		catch(Exception e) {
			log.info("codeDemostrationDaoImpl: existNickName: error: Algo salio mal, falló la consulta. Motivo: " + e);
			return -1;
		}
	}
	
	@Override
	public String getGenero(String nickName) throws Exception {
		
		log.info("codeDemostrationDaoImpl: getGenero: nickName: "+ nickName);
		
		String query = "SELECT [GENERO] FROM [table1] WHERE [NICKNAME] LIKE ?";
		
		log.info("codeDemostrationDaoImpl: getGenero: query: "+ query);
		String genero = "";
		try {
			genero = db1JdbcTemplate.queryForObject(query,  new Object[] { nickName}, String.class );
			log.info("codeDemostrationDaoImpl: getGenero: respuesta: "+  genero);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: getGenero: error: no se encontraron datos");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: getGenero: error: Algo salio mal, no se obtuvo el genero. Motivo: " + e);
		}
		return genero;
	}
	
	@Override
	public Date getLastUpdate(String userName, String nickName) throws Exception {
		log.info("codeDemostrationDaoImpl: getLastUpdate: userName: "+ userName+", nickName: "+ nickName);
		
		String query = "SELECT [UPDATED_PASSWORD] FROM [table1] WHERE [USERNAME] LIKE ? and [NICKNAME] LIKE ?";
		
		log.info("codeDemostrationDaoImpl: getLastUpdate: query: "+ query);
		Date date = new Date();
		try {
			date = db1JdbcTemplate.queryForObject(query,  new Object[] {userName, nickName}, Date.class );
			log.info("codeDemostrationDaoImpl: getLastUpdate: respuesta: "+  date);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: getLastUpdate: error: no se encontraron datos");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: getLastUpdate: error: Algo salio mal, no se obtuvo la fecha de la ultima modificacion de la contraseña. Motivo: " + e);
		}
		return date;
	}

	@Override
	public int updateLastLogin(String userName, String nickName, String status) throws Exception {
		log.info("codeDemostrationDaoImpl: updateLastLoginFail: inicio");
		
		String query = "UPDATE [table1] SET [LAST_LOGIN_"+status.toUpperCase()+"] = ? WHERE [USERNAME] LIKE ? and [NICKNAME] LIKE ?"; //i use the same query to update two columns LAST_LOGIN_FAIL and LAST_LOGIN_OK. 
																																	//  i haven't injection dependency risk because the status is information that i send from the service.
		log.info("codeDemostrationDaoImpl: updateLastLoginFail: query: "+ query);
		Integer rows = 0;
		try {
			rows = db1JdbcTemplate.update(query,  new Object[] {new Date(), userName, nickName});
			log.info("codeDemostrationDaoImpl: updateLastLoginFail: respuesta: "+  rows);
		}
		catch (EmptyResultDataAccessException e) {
			log.error("codeDemostrationDaoImpl: updateLastLoginFail: error: no se pudo actualizar");
		} 
		catch(Exception e) {
			log.error("codeDemostrationDaoImpl: updateLastLoginFail: error: Algo salio mal, no se actualizo la fecha del ultimo login fallido. Motivo: " + e);
		}
		return rows;
	}
}
