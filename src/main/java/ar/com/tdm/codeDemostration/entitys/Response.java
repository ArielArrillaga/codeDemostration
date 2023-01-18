package ar.com.tdm.codeDemostration.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class Response {
	private String mensaje;
	private Boolean ok;
}