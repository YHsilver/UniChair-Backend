package fudan.se.lab2.exception;

public class JsonObjectCreatedException extends RuntimeException {
    //TODO: 实现一个Json数据封装类
    public JsonObjectCreatedException(Object object){
        super("Json object for object '" + object.toString() + "' created failed");
    }
}
