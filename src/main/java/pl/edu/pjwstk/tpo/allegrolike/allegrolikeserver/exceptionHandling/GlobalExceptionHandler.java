package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.badrequest.BadRequestException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.ForbiddenException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex) {
        log.info(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException(ForbiddenException ex) {
        log.info(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException ex) {
        log.info(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public String handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.info(ex.getMessage());
        return "You do not have permission to access this resource";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleNotSupportedHttpMethodException(HttpRequestMethodNotSupportedException ex) {
        log.info(ex.getMessage());
        return "This request method is not supported on this path";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleServerException(Exception ex) {
        log.info(ex.getMessage());
        ex.printStackTrace();
        return "Internal server error occurred, please try again later";
    }

}
