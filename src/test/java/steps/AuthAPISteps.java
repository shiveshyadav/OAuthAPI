package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import util.RestApiUtil;
import util.RestPath;

import java.util.LinkedHashMap;

public class AuthAPISteps {

    private String url;
    private String usersUrl;
    private RestApiUtil endPointUtil=new RestApiUtil();
    private Response response;

    public AuthAPISteps()
    {
        this.usersUrl=endPointUtil.getBaseURL()+ RestPath.POST_USER_CODE;
    }

    @Given("OAuth service is up and running")
    public void OAuthService()
    {
        //Service is up and running
        System.out.println("Service is up and running");
    }

    @When("user requests for user code with {string} {string} {string}")
    public void theUserRegisteredAnAccountWith(String clientId, String audience,String scope) {
        JSONObject body=getCodeBody(clientId,audience,scope);
        response=endPointUtil.sendRestApiRequest("POST", usersUrl,body.toString());
    }

    /**
     * Verify status code of response
     * @param statusCode
     */
    @Then("verify {int}")
    public void verifyGetStatusCode(int statusCode) {
        endPointUtil.verifyStatusCode(response,statusCode);
    }


    @Then("user get response verification uri")
    public void userCodeResponse() {
        endPointUtil.ValidateResponse("expires_in",900,response);
        endPointUtil.ValidateResponse("interval",5, response);
        String verification_uri_complete= response.jsonPath().get("verification_uri_complete");
        String verification_uri=response.jsonPath().get("verification_uri");
        String user_code=response.jsonPath().get("user_code");
        Assert.assertEquals(verification_uri_complete,verification_uri+"?user_code="+user_code);
    }

    @Then("user can access verification uri to auth and {int}")
    public void activateDevice(int statusCode) {

        String verification_uri=response.jsonPath().get("verification_uri_complete");
        response=endPointUtil.getRestAPI(verification_uri);
        Assert.assertEquals(statusCode,response.getStatusCode());
    }



    private JSONObject getCodeBody(String clientId, String audience,String scope)
    {
        LinkedHashMap<String,Object> value= new LinkedHashMap<>();
        value.put("client_id",clientId);
        value.put("audience",audience);
        value.put("scope",scope);
        return endPointUtil.createJsonObject(value);

    }

}
