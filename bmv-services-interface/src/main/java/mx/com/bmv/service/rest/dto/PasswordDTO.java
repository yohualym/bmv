package mx.com.bmv.service.rest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


public class PasswordDTO implements Serializable{
    
    private static final long serialVersionUID = -8017227311318830627L;
    
    @NotNull
    private String password;
    
    public PasswordDTO() {
        super();
    }

    public PasswordDTO(String password) {
        super();
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
