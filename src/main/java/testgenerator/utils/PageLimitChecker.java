package testgenerator.utils;

public class PageLimitChecker {

    private static final Integer ACTUAL_LIMIT = 100;

    public static Integer checkPageLimit(Integer requestedLimit) {

        return requestedLimit>ACTUAL_LIMIT ? ACTUAL_LIMIT:requestedLimit;
    }
}
