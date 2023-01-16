package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String jwt;					//is the JWT
    private String mensaje;				//show a message
    private Boolean state;				//show if the validations are OK or not
    private Boolean ok;					//show if the service works or not
    private Boolean expired;			//show if the pas is expired or not
    private int intentosRestantes = 3; 	//max attempts
}
