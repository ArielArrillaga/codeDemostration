package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SendEmailAdjuntos {
    private SendEmailRequest data;
    private String urlAdjunto;
    private String adjuntoNameAndExtension; //ejemplo "contrato.pdf", "foto.jpg"
    
}