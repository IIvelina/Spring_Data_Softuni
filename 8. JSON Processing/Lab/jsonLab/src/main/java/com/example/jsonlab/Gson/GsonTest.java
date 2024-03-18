package com.example.jsonlab.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class GsonTest {
    public static void main(String[] args) {

        //Gson gson = new Gson()...

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        LoginData loginData = new LoginData("ivan", "1234");

        String result = gson.toJson(loginData);

        System.out.println(result);
    }

    static class LoginData{
        @Expose
        private String userName;
        @Expose
        private String password;

        public LoginData(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
