package com.example.jsonlab.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class GsonTest1 {

    private static final String json =
                    """
                    {
                      "userName": "ivan",
                      "password": "1234"
                    }     
                    """;

    public static void main(String[] args) {

        //Gson gson = new Gson()...

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        LoginData loginData = gson.fromJson(json, LoginData.class);

        System.out.println(loginData);

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

        @Override
        public String toString() {
            return "LoginData{" +
                    "userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
