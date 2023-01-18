package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDataResponse {
	//this is just a example
	private String mensaje;
	private String nombre;
	private String apellido;
	private boolean ok;
}
