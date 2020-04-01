package fudan.se.lab2.exception.GenericException;

public class JsonObjectCreatedException extends RuntimeException {
    public JsonObjectCreatedException(Object object) {
        super("Json object for object '" + object.toString() + "'(class: " + object.getClass().getName() + ") created failed");
    }
}
