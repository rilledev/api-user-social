package io.github.rilledev.quarkussocial.rest.dto;

public class FieldError {

    private String field;
    private String messege;

    public FieldError(String field, String messege) {
        this.field = field;
        this.messege = messege;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }
}
