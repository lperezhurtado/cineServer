package net.ausiasmarch.cineServer.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioBean {
    
    @Schema(example = "luisp")
    private String login = "";

    @Schema(example = "admin1")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
