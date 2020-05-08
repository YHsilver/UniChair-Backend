package fudan.se.lab2.exception;

import fudan.se.lab2.exception.ConferencException.IllegalConferenceApplicationException;
import fudan.se.lab2.exception.ConferencException.PaperSubmitOrModifyFailException;
import fudan.se.lab2.exception.GenericException.JsonObjectCreatedException;
import fudan.se.lab2.exception.LoginAndRegisterException.IllegalRegisterRequestException;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 * 这个类用来处理所有异常
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Username '" + ex.getMessage() + "' doesn't exist or password not correct");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UsernameHasBeenRegisteredException.class)
    ResponseEntity<?> handleUsernameHasBeenRegisteredException(UsernameHasBeenRegisteredException ex, WebRequest request) {
        //request.
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    ResponseEntity<?> handlePasswordNotCorrectException(PasswordNotCorrectException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalRegisterRequestException.class)
    ResponseEntity<?> handleIllegalRegisterRequestException(IllegalRegisterRequestException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonObjectCreatedException.class)
    ResponseEntity<?> handleJsonObjectCreatedException(JsonObjectCreatedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalConferenceApplicationException.class)
    ResponseEntity<?> handleIllegalConferenceApplicationException(IllegalConferenceApplicationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaperSubmitOrModifyFailException.class)
    ResponseEntity<?> handlePaperSubmitOrModifyFailException(PaperSubmitOrModifyFailException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}