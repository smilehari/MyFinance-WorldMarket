package com.harishri.financepal.data.remote.datasource

import android.util.Log
import com.harishri.financepal.data.remote.api.MyFinanceApiService
import com.harishri.financepal.data.remote.dto.DeviceInfoDto
import com.harishri.financepal.data.remote.dto.UserDto
import com.harishri.financepal.domain.model.DeviceInfo
import com.harishri.financepal.domain.model.NetworkResult
import com.harishri.financepal.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteUserDataSource @Inject constructor(
    private val apiService: MyFinanceApiService
) {

    suspend fun syncUserToServer(user: User): NetworkResult<String> = withContext(Dispatchers.IO) {
        try {
            val userDto = user.toDto()
            val response = apiService.syncUser(userDto)

            if (response.isSuccessful && response.body()?.success == true) {
                Log.d("RemoteUserDataSource", "User synced to server successfully: ${user.userId}")
                NetworkResult.Success(response.body()?.data ?: "Success")
            } else {
                val error =
                    Exception("Server error: ${response.code()} - ${response.body()?.message}")
                Log.e("RemoteUserDataSource", "Failed to sync user to server = $error")
                NetworkResult.Error(error)
            }
        } catch (e: Exception) {
            Log.e("RemoteUserDataSource", "Network error while syncing user = $e")
            NetworkResult.Error(e)
        }
    }

    suspend fun fetchUserFromServer(userId: String): NetworkResult<User> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUser(userId)

                if (response.isSuccessful && response.body()?.success == true) {
                    val userDto = response.body()?.data
                    if (userDto != null) {
                        NetworkResult.Success(userDto.toDomainModel())
                    } else {
                        NetworkResult.Error(Exception("User data is null"))
                    }
                } else {
                    NetworkResult.Error(Exception("Server error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("RemoteUserDataSource", "Network error while fetching user $e")
                NetworkResult.Error(e)
            }
        }

    suspend fun validateToken(idToken: String): NetworkResult<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val request = mapOf("id_token" to idToken)
                val response = apiService.validateToken(request)

                if (response.isSuccessful && response.body()?.success == true) {
                    val isValid = response.body()?.data?.isValid ?: false
                    NetworkResult.Success(isValid)
                } else {
                    NetworkResult.Error(Exception("Token validation failed"))
                }
            } catch (e: Exception) {
                Log.e("RemoteUserDataSource", "Network error while validating token $e")
                NetworkResult.Error(e)
            }
        }
}

// Extension functions for DTO mapping
private fun User.toDto(): UserDto {
    return UserDto(
        userId = userId,
        name = name,
        email = email,
        photoUrl = photoUrl,
        idToken = idToken,
        deviceInfo = deviceInfo.toDto(),
        createdAt = createdAt,
        lastLoginAt = lastLoginAt
    )
}

private fun DeviceInfo.toDto(): DeviceInfoDto {
    return DeviceInfoDto(
        deviceModel = deviceModel,
        osVersion = osVersion,
        appVersion = appVersion,
        deviceId = deviceId
    )
}

private fun UserDto.toDomainModel(): User {
    return User(
        userId = userId,
        name = name,
        email = email,
        photoUrl = photoUrl,
        idToken = idToken,
        deviceInfo = deviceInfo.toDomainModel(),
        createdAt = createdAt,
        lastLoginAt = lastLoginAt,
        isActive = true
    )
}

private fun DeviceInfoDto.toDomainModel(): DeviceInfo {
    return DeviceInfo(
        deviceModel = deviceModel,
        osVersion = osVersion,
        appVersion = appVersion,
        deviceId = deviceId
    )
}