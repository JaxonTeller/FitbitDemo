import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitbitStarter {

    static  List<String> refreshTokenList=new ArrayList<>();

    public static void main(String[] args) throws UnsupportedEncodingException {
        String clientId = "22C2MX";
        String urlEncodedString = "";
        urlEncodedString = URLEncoder.encode("https://www.example.com", "UTF-8");
        String clientSecret = "7e47cf82afa37c67a36ffef81b6cbc48";

        /*Step1 Authorization URL returning Auth Code*/
        String authUrl = "https://www.fitbit.com/oauth2/authorize?" +
                "response_type=code&client_id=" +
                clientId +
                "&redirect_uri=" +
                urlEncodedString +
                "&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight";
        System.out.println("Auth URL " + authUrl);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Auth Code: ");
        String authCode = scanner.nextLine();

        FitbitResult fitbitResult=generateAccessToken(authCode);
        String accessToken=fitbitResult.getAccess_token();

        System.out.println("Access token "+accessToken);
        System.out.println("Scope "+fitbitResult.getScope());

        checkValidityOfAccessToken(accessToken);

    }


    private static FitbitResult generateAccessToken(String authCode) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.
                fromHttpUrl("https://api.fitbit.com/oauth2/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authCode)
                .queryParam("client_id", "22C2MX")
                .queryParam("redirect_uri","https://www.example.com");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.setBasicAuth("MjJDMk1YOjdlNDdjZjgyYWZhMzdjNjdhMzZmZmVmODFiNmNiYzQ4");

        HttpEntity request = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("-------------------Asking For Access Token---------------------------");

        ResponseEntity<String> exchange = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.POST, request, String.class);
        Gson gson=new Gson();
        FitbitResult fitbitResult = gson.fromJson(exchange.getBody(), FitbitResult.class);

        System.out.println("------------------------Request For Access Token is Successfull----------------------");
        return fitbitResult;
    }
    private static void checkValidityOfAccessToken(String accessToken) {

        System.out.println("---------------Checking validity of Access token by making request to access user's profile-----------------");
        RestTemplate template=new RestTemplate();
        HttpHeaders getHttpHeaders=new HttpHeaders();
        getHttpHeaders.setBearerAuth(accessToken);
        HttpEntity entity1=new HttpEntity(getHttpHeaders);
        ResponseEntity<String> exchange = template.exchange("https://api.fitbit.com/1/user/-/profile.json", HttpMethod.GET, entity1, String.class);
        System.out.println("Fitbit User Info "+exchange.getBody());
    }
}
