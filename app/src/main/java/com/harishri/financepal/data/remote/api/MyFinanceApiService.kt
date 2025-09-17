package com.harishri.financepal.data.remote.api

import com.harishri.financepal.data.local.models.UserCredentials
import com.harishri.financepal.data.remote.dto.AuthRequest
import com.harishri.financepal.data.remote.dto.ServerResponse
import com.harishri.financepal.data.remote.dto.TokenValidationResponse
import com.harishri.financepal.data.remote.dto.UserDto
import com.harishri.financepal.data.remote.models.LoginDetailsServerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface MyFinanceApiService {
    @POST("api/user/login")
    suspend fun sendUserCredentials(@Body credentials: UserCredentials): Response<LoginDetailsServerResponse>

    @POST("api/auth/google")
    suspend fun authenticateWithGoogle(@Body request: AuthRequest): Response<ServerResponse<UserDto>>

    @POST("api/user/sync")
    suspend fun syncUser(@Body user: UserDto): Response<ServerResponse<String>>

    @GET("api/user/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<ServerResponse<UserDto>>

    @POST("api/auth/validate-token")
    suspend fun validateToken(@Body tokenRequest: Map<String, String>): Response<ServerResponse<TokenValidationResponse>>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body refreshRequest: Map<String, String>): Response<ServerResponse<String>>

    @DELETE("api/auth/logout")
    suspend fun logout(@Body logoutRequest: Map<String, String>): Response<ServerResponse<String>>


}