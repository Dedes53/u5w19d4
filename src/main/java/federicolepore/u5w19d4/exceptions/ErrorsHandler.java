package federicolepore.u5w19d4.exceptions;


import federicolepore.u5w19d4.payloads.ErrorsPayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
// GESTORE DELLE ECCEZIONI
// ogni volta che verrà lanciata una eccezione, questa arriverà a questa classe
// qui ci saranno n metodi, ognuno dedicato alla gestione di un tipo specifico di eccezione
public class ErrorsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 400
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorsPayload handleNotFoundEx(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ErrorsPayload handleGenericEx(Exception ex) {
        return new ErrorsPayload("Aiaiai sto giro è colpa mia", LocalDateTime.now());
    }


}
