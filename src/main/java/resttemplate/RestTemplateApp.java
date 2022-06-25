package resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import resttemplate.model.User;


@SpringBootApplication
public class RestTemplateApp {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RestTemplateApp.class, args);

        String code = getAnswer(applicationContext);
        System.out.println(code);
    }

    private static String getAnswer (ApplicationContext applicationContext) {
        RestConnection restConnection = applicationContext.getBean("restConnection", RestConnection.class);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String sessionID = restConnection.getAllUsersSessionID(new HttpEntity<>(httpHeaders));
        httpHeaders.set("cookie", sessionID);

        User user = new User(3L, "James", "Brown", (byte)30);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        //Создание юзера
        String firsPart = restConnection.addUser(httpEntity);

        //Редактирование юзера
        user.setName("Thomas");
        user.setLastName("Shelby");
        String secondPart = restConnection.editUser(httpEntity);

        //удаление юзера
        String thirdPart = restConnection.deleteUser(httpEntity, 3);

        return firsPart + secondPart + thirdPart;
    }
}
