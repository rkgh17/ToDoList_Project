//package com.hjh.todolist.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//	
//	@ExceptionHandler(DuplicateUserException.class)
//	@ResponseBody
//	public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex){
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//	}
//
//}
