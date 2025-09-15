package models;



import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "password",
        "passwordRepeat",
        "securityQuestion",
        "securityAnswer"
})
public class NewUserModel implements Serializable {

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("passwordRepeat")
    private String passwordRepeat;
    @JsonProperty("securityQuestion")
    private SecurityQuestion securityQuestion;
    @JsonProperty("securityAnswer")
    private String securityAnswer;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public NewUserModel() {
    }

    public NewUserModel(String email, String password, String passwordRepeat, SecurityQuestion securityQuestion, String securityAnswer) {
        super();
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("passwordRepeat")
    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    @JsonProperty("passwordRepeat")
    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    @JsonProperty("securityQuestion")
    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    @JsonProperty("securityQuestion")
    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    @JsonProperty("securityAnswer")
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    @JsonProperty("securityAnswer")
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NewUserModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("password");
        sb.append('=');
        sb.append(((this.password == null)?"<null>":this.password));
        sb.append(',');
        sb.append("passwordRepeat");
        sb.append('=');
        sb.append(((this.passwordRepeat == null)?"<null>":this.passwordRepeat));
        sb.append(',');
        sb.append("securityQuestion");
        sb.append('=');
        sb.append(((this.securityQuestion == null)?"<null>":this.securityQuestion));
        sb.append(',');
        sb.append("securityAnswer");
        sb.append('=');
        sb.append(((this.securityAnswer == null)?"<null>":this.securityAnswer));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.password == null)? 0 :this.password.hashCode()));
        result = ((result* 31)+((this.securityQuestion == null)? 0 :this.securityQuestion.hashCode()));
        result = ((result* 31)+((this.securityAnswer == null)? 0 :this.securityAnswer.hashCode()));
        result = ((result* 31)+((this.passwordRepeat == null)? 0 :this.passwordRepeat.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.email == null)? 0 :this.email.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NewUserModel) == false) {
            return false;
        }
        NewUserModel rhs = ((NewUserModel) other);
        return (((((((this.password == rhs.password)||((this.password!= null)&&this.password.equals(rhs.password)))&&((this.securityQuestion == rhs.securityQuestion)||((this.securityQuestion!= null)&&this.securityQuestion.equals(rhs.securityQuestion))))&&((this.securityAnswer == rhs.securityAnswer)||((this.securityAnswer!= null)&&this.securityAnswer.equals(rhs.securityAnswer))))&&((this.passwordRepeat == rhs.passwordRepeat)||((this.passwordRepeat!= null)&&this.passwordRepeat.equals(rhs.passwordRepeat))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.email == rhs.email)||((this.email!= null)&&this.email.equals(rhs.email))));
    }

}