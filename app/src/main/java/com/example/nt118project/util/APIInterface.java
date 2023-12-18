package com.example.nt118project.util;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.example.nt118project.request.GraphRequestBody;
import com.example.nt118project.response.Asset.Asset;
import com.example.nt118project.response.GraphResponse;
import com.example.nt118project.response.AssetResponse;
import com.example.nt118project.response.LoginResponse;
import com.example.nt118project.response.MapResponse;
import com.example.nt118project.response.UserResponse;
import com.example.nt118project.response.UserRoles;
import com.google.gson.JsonArray;

import java.util.List;

public interface APIInterface {
    @POST("auth/realms/master/protocol/openid-connect/token")
    @FormUrlEncoded
    Call<LoginResponse> login(
            @Field("client_id") String clientId,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );
    @PUT("api/master/user/master/reset-password/{userId}")
    Call<Void> resetPassword(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Header("Content-Type") String contentType,
            @Path("userId") String userID,
            @Body ResetPasswordBody body
    );
    @GET("api/master/user/user")
    Call<UserResponse> getUser(
            @Header("Authorization") String authToken
    );
    @PUT("api/master/user/master/userRoles/{userId}")
    Call<Void> registerUserRole(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Header("Content-Type") String contentType,
            @Path("userId") String userID,
            @Body List<RegisterUserBody> body
    );
    @PUT("api/master/user/master/userRealmRoles/{userId}")
    Call<Void> registerUserRealmRole(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Header("Content-Type") String contentType,
            @Path("userId") String userID,
            @Body List<RegisterUserBody> body
    );
    @GET("api/master/asset/{assetID}")
    Call<AssetResponse> getAsset(@Path("assetID") String assetID, @Header("Authorization") String auth);
    Call<Asset> getAsset1(@Path("assetID") String assetID, @Header("Authorization") String auth);
    @POST("api/master/asset/datapoint/{assetId}/attribute/{attributeName}")
    Call<JsonArray> postData(
            @Path("assetId") String assetId,
            @Path("attributeName") String attributeName,
            @Header("Authorization") String auth,
            @Body GraphRequestBody requestBody
    );

    @GET("api/master/map/js")
    Call<MapResponse> getMap(
            @Header("Authorization") String auth
    );
    @GET("api/master/user/userRoles")
    Call<JsonArray> getUserRoles(@Header("Authorization") String auth);

}
