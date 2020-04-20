package Secur.o.Cord.OAuth2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class Authorize {

    /** Generates and redirects the client to a Discord authorization link
     *
     * @param responseType One of two response types
     * @see ResponseType
     * @param clientId The client Id of the Discord Application
     * @param state A unique String to check the validation state
     * @param redirectURI Discord redirects you to this URI after authorization
     * @param scopes One or more authorization scopes
     * @see Scope
     * @return A redirect to the generated Discord authorization link
     * @throws IOException if encoding of the Redirect URI fails
     * @throws IllegalArgumentException if ResponseType or Scope is invalid
     */
    @RequestMapping(value = "/api/oauth2/authorize")
    public static ModelAndView auth(@RequestParam("response_type") String responseType,
                              @RequestParam("client_id") String clientId,
                              @RequestParam(value = "state", required = false) String state,
                              @RequestParam("redirect_uri") URI redirectURI,
                              @RequestParam("scope") String scopes)
                              throws IOException, IllegalArgumentException {

        if (ResponseType.getByName(responseType) == null){
            throw new IllegalArgumentException("Invalid response type: " + responseType);
        }

        ArrayList<String> scopeNames = new ArrayList<>(Arrays.asList(scopes.split("%20")));

        for (String s : scopeNames){
            if (Scope.getByName(s) == null) {
                throw new IllegalArgumentException("Invalid scope: " + s);
            }
        }

        String redirectURIString = URLEncoder.encode(redirectURI.toString(), "UTF-8");
        String urlString = "";
        if (state != null)
        urlString = "https://discordapp.com/oauth2/authorize?response_type=" + responseType +
                "&client_id=" + clientId + "&state=" + state + "&scope=" + String.join("%20", scopeNames)
                + "&redirect_uri=" + redirectURIString;
        else
            urlString = "https://discordapp.com/oauth2/authorize?response_type=" + responseType +
                    "&client_id=" + clientId + "&scope=" + String.join("%20", scopeNames)
                    + "&redirect_uri=" + redirectURIString;

        return new ModelAndView("redirect:" + urlString);
    }



}
