package sathish.discussionforum.supportingfiles;


import java.util.HashMap;

public interface AuthenticatingWebViewCallbackMethods {
    /**
     * Method is called when it is time to display the progress spinner, for example when loading on the web view.
     */
    void startProgressDialog();

    /**
     * Method is called when it is time to hide the progress spinner
     */
    void stopProgressDialog();

    /**
     * Method is called when authentication is complete
     *
     * @param authorizationReturnParameters The params returned with the token
     */
    void getToken(HashMap<String, String> authorizationReturnParameters);
}
