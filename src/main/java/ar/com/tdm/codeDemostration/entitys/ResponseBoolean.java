package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString


public class ResponseBoolean {
	//esta respuesta se utiliza en los servicios que requieren validacion
	private String mensaje;
	
	//state sera true si se aprobo la validacion y false si no aprobo
	private boolean state;
	
	//ok sera true si el servicio anduvo bien y false si por algun motivo no logro hacer las peticiones necesarias
	private boolean ok;
}
