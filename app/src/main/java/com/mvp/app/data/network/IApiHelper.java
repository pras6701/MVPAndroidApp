package com.mvp.app.data.network;

import com.mvp.app.data.network.model.DummyData;
import com.mvp.app.data.network.model.MasterData;
import com.mvp.app.data.network.model.SignInRequest;
import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.data.network.model.SignUpRequest;
import com.mvp.app.data.network.model.SignUpResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface IApiHelper {

    @POST(ApiEndPoint.ENDPOINT_SIGNIN)
    Observable<SignInResponse> signIn(@Body SignInRequest vendorSignInRequest);

    @POST(ApiEndPoint.ENDPOINT_SIGNUP)
    Observable<SignUpResponse> signUp(@Body SignUpRequest vendorSignUpRequest);

    @GET(ApiEndPoint.ENDPOINT_MASTERDATA)
    Observable<MasterData> fetchMasterData();

    @GET(ApiEndPoint.ENDPOINT_DUMMYDATA)
    Observable<List<DummyData>> fetchDummyDatas();
}
