package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JwtResponse {
	private String jwt;
	private String mensaje;
	private Boolean state;
	private Boolean ok;
	private int intentosRestantes = 3; //numero maximo
}
