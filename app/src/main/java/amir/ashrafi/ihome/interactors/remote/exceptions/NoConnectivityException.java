package amir.ashrafi.ihome.interactors.remote.exceptions;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}
