package christmas.view.input.Template;

public interface InputValidatorCallback<T> {
    T validate(String input);
}