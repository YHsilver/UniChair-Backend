package fudan.se.lab2.exception;

public class JsonObjectCreatedException extends RuntimeException {
    public JsonObjectCreatedException(Object object){
        super("Json object for object '" + object.toString() + "' created failed");
    }
}
