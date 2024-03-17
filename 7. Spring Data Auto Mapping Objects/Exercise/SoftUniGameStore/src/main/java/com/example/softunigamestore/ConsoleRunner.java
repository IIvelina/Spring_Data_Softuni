package com.example.softunigamestore;

import com.example.softunigamestore.entities.users.LoginDTO;
import com.example.softunigamestore.entities.users.RegisterDTO;
import com.example.softunigamestore.entities.users.User;
import com.example.softunigamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
 private final String REGISTER_USER_COMMAND = "RegisterUser";
 private final String LOGIN_USER_COMMAND = "LoginUser";
 private final String LOGOUT_USER_COMMAND = "Logout";

 private final UserService userService;

 @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    private String execute(String commandLine){
        String[] commandParts = commandLine.split("\\|");

        String commandName = commandParts[0];

        String commandOutput = switch (commandName) {
            case REGISTER_USER_COMMAND -> {
                RegisterDTO registerData = new RegisterDTO(commandParts);
                User user = userService.register(registerData);
                yield String.format("%s was registered", user.getFullName());
            }
            /*
            case LOGIN_USER_COMMAND -> {
                LoginDTO loginData = new LoginDTO(commandParts);User user = userService.login(loginData);


                yield String.format("Successfully logged in %s", user.getFullName());
            }

             */
          //  case LOGOUT_USER_COMMAND -> userService.logout();
            default -> "unknown command";
        };
        return commandOutput;
    }

    @Override
    public void run(String... args) throws Exception {
       Scanner scanner = new Scanner(System.in);


        while (true) { // По-добре е да има цикъл, за да продължава да чете команди от конзолата
            String command = scanner.nextLine();
            String output = execute(command); // Извикване на метода execute с въведената команда
            System.out.println(output); // Извеждане на резултата от изпълнението на командата
        }
    }
}
