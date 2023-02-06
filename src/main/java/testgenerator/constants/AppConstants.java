package testgenerator.constants;

import java.util.List;

public class AppConstants {

    public static final String REALM = "testgeneratorapp";

    public static final String AUTH_SERVER_URL  = "http://localhost:8080";

    public static final List<String> AVAILABLE_ROLES = List.of(
            "CANDIDATE", "USER", "CORRECTOR", "ADMIN", "SUPER-ADMIN"
    );

}
