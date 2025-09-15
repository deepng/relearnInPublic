package models;

import java.util.random.RandomGenerator;

public class UserData {
    public int id;
    public String userId;
    public String password;
    public String type;
    public String userSecurityAnswer;

    public UserData(String userId, String password, String userSecurityAnswer) {
        this.userId = userId;
        this.password = password;
        this.userSecurityAnswer = userSecurityAnswer;
    }

    public UserData() {}
}
