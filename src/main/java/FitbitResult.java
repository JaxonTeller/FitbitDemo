import java.io.Serializable;


public class FitbitResult implements Serializable {

    private final String access_token;

    private final String refresh_token;

    private final int expires_in;

    private final String scope;

    private final String token_type;

    private final String user_id;


    public FitbitResult(String access_token, String refresh_token, int expires_in, String scope, String token_type, String user_id) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.token_type = token_type;
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getUser_id() {
        return user_id;
    }
}
