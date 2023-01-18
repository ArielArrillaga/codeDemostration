package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class SendEmailRequest {
    private String to;
    private String html;
    private String asunto;
    private String host;
    private String from;
    private String port;
}
