package ar.com.tdm.codeDemostration.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ar.com.tdm.codeDemostration.entitys.Response;
import ar.com.tdm.codeDemostration.entitys.SendEmailAdjuntos;
import ar.com.tdm.codeDemostration.entitys.SendEmailRequest;

@Service
public class CodeDemostrationEmailServiceImpl implements CodeDemostrationEmailService {

	private final Logger log = LoggerFactory.getLogger(CodeDemostrationEmailServiceImpl.class);

	@Override
	public Response sendEmailAdjunto(SendEmailAdjuntos request) { //this service can only send an attached file
	        
    	SendEmailRequest data = request.getData();
        Response response = new Response();
        String to = data.getTo();
        
        Properties emailProperties = System.getProperties();	
        Properties properties = new Properties();
        
        try {
        	//this is another way to get the properties, it's just an example.
            properties = this.readConfigProperties();
        }catch (Exception e) {
            log.error("CodeDemostrationEmailServiceImpl: sendEmail: No se pudo enviar el email: no se encontraron las properties "+e);
            response.setMensaje("Falló el envio del email");
            response.setOk(false);
            return response;
        }
        
        // Setup mail server
        emailProperties.put("mail.smtp.host", data.getHost());
        emailProperties.put("mail.smtp.port", data.getPort());
        //emailProperties.put("mail.smtp.ssl.enable", "false");
        //emailProperties.put("mail.smtp.starttls.enable","true");
        emailProperties.put("mail.smtp.ssl.enable", "false");
        //emailProperties.put("mail.smtp.auth", "true"); //If I have a user and password I must uncomment this line


        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication(EMAIL, PASS);
                return new PasswordAuthentication(null, null);	//in that case i use IP and a port to send emails
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);
        
        try {
            BodyPart cuerpo= new MimeBodyPart();
            cuerpo.setContent(data.getHtml(), "text/html");
            
            MimeMultipart m=new MimeMultipart();
            m.addBodyPart(cuerpo);

            String[] path = request.getUrlAdjunto().split("/"); //this url is like https://www.example.com.ar/pdfs/pdfName.pdf  result: [https:, , www.example.com.ar, pdfs, pdfName.pdf]
            													//this line and the one below only works with contracts, if the url is not for contracts it doesn't work 
            File file = new File("/app/pdf/"+path[4]); //server path where i will save the attachment
            log.info("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: creando pdf en: "+file.getPath());
            
            URL url = new URL(properties.getProperty("URL-pdf")+path[4]); 
            try (InputStream in = url.openStream()) {
                log.info("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: Conexion con url establecida");
                Files.copy(in, Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: Falló el envio del email, no se pudo leer el adjunto: "+e);
                response.setMensaje("Falló el envio del email, no se pudo leer el adjunto");
                response.setOk(false);
                return response;
            }
            BodyPart adjunto= new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(file.getPath())));
            adjunto.setFileName(request.getAdjuntoNameAndExtension()); //example "Contrato.pdf"
            m.addBodyPart(adjunto);

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(data.getFrom()));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field 
            message.setSubject(data.getAsunto());
            
            // Send the actual HTML message.
            message.setContent(m); 

            log.info("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: enviando email");
            // Send message
           Transport.send(message);
           
           file.delete();// delete the file from server directory
           log.info("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: enviado");
           response.setMensaje("Email enviado exitosamente");
           response.setOk(true);
           return response;
        } catch (Exception e) {
            log.error("CodeDemostrationEmailServiceImpl: sendEmailAdjunto: No se pudo enviar el email: "+e);
            response.setMensaje("Falló el envio del email");
            response.setOk(false);
            return response;
        }
    }
	
	private Properties readConfigProperties() throws FileNotFoundException, IOException {
		Properties propiedades = new Properties();
		propiedades.load(new FileReader("/app/codeDemostration/config.properties"));
		return propiedades;
	}

}
