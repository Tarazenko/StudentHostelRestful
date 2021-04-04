package by.bntu.tarazenko.hostelrestful.services.exceptions;

public class CategoryAlreadyExistException extends EntityExistException{
    public CategoryAlreadyExistException(String message){
        super(message);
    }
}
