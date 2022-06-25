package resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import resttemplate.model.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestConnection {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    public String getAllUsersSessionID (HttpEntity<User> requestEntity) {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
        responseEntity.getBody().forEach(System.out::println); //вывод списка пользователей
        return responseEntity.getHeaders().get("set-cookie").get(0);
    }

    public String addUser (HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public String editUser(HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public String deleteUser(HttpEntity<User> requestEntity, int id) {
        String URL = this.URL + "/" + id;
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.DELETE, requestEntity, String.class);
        return responseEntity.getBody();
    }
}