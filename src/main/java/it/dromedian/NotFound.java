package it.dromedian;


import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)

public class NotFound extends Exception {
    public NotFound() {
    }
}



