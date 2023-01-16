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
    private String jwt;
    private String mensaje;
    private Boolean state;
    private Boolean ok;
    private Boolean expired;
    private int intentosRestantes = 3; //numero maximo
}
