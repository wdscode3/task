package org.wds.taskmanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERRO_CAPTURADO = "Erro capturado pelo ExceptionHandler: {}";

	@ExceptionHandler(Exception.class)
	public Map<String, Object> exception(Exception ex) {
		log.error(ERRO_CAPTURADO, ex.getMessage(), ex);
		return Map.of("tile", "Erro", "datail", ex.getMessage());
	}

}