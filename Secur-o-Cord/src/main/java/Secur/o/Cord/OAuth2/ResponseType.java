package Secur.o.Cord.OAuth2;

import java.util.Arrays;
import java.util.List;

public enum ResponseType {
    CODE("code"),
    TOKEN("token");
    private String name;
    ResponseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static ResponseType getByName(String name) {
        List<ResponseType> responseTypes = Arrays.asList(ResponseType.values());
        for (ResponseType r: responseTypes) {
            if (r.getName().equals(name))
                return r;
        }
        return null;
    }
}
