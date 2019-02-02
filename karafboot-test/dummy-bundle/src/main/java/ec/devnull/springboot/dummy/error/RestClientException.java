package ec.devnull.springboot.dummy.error;


/**
 * Custom Exception for errors like invalid authorization, invalid keys, in general not valid server responses
 *
 * @author Kleber Ayala
 */
public class RestClientException extends RuntimeException {

    private final int status;


    protected RestClientException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int status() {
        return status;
    }


}
