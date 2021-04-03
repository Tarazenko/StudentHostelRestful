package by.bntu.tarazenko.hostelrestful.services.exceptions;

public class EntityExistException extends RuntimeException{
    public EntityExistException(String message){
        super(message);
    }
}
