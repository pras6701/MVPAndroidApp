package com.mvp.app.data.network;


import com.mvp.app.BuildConfig;

public final class ApiEndPoint {

    public static final String ENDPOINT_SIGNUP = BuildConfig.BASE_URL + "58bc6421100000591c109ede";
    public static final String ENDPOINT_MASTERDATA = BuildConfig.BASE_URL + "58c19b66110000571763f052";
    public static final String ENDPOINT_SIGNIN = BuildConfig.BASE_URL + "58bc643f100000591c109edf";
    public static final String ENDPOINT_DUMMYDATA = BuildConfig.BASE_URL + "58c1a1cb110000bc1763f06c";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
