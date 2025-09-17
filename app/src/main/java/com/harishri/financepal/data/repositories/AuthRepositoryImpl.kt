package com.harishri.financepal.data.repositories


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.harishri.financepal.data.local.dao.UserDao
import com.harishri.financepal.data.local.entity.UserEntity
import com.harishri.financepal.domain.model.DeviceInfo
import com.harishri.financepal.domain.repositories.AuthRepository
import com.harishri.financepal.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
    private val userRepository: UserRepository,

) : AuthRepository {

    /**
     * Attempts to sign in the user with Firebase using a Google ID token.
     * On success, it saves the user's details to the local Room database.
     * The ViewModel can then observe this local database change via the getAuthState() Flow.
     */
    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user

            Log.d("AuthRepositoryImpl", "signInWithGoogle: firebaseUser is null  = ${firebaseUser == null} ")
            Log.d("AuthRepositoryImpl", "signInWithGoogle: firebaseUser uid  = ${firebaseUser?.uid} ")
            if (firebaseUser != null) {
                val userEntity = firebaseUserToEntity(firebaseUser,idToken)
                userDao.insert(userEntity)
                Log.d("AuthRepositoryImpl", "signInWithGoogle: firebaseUser name  = ${firebaseUser?.displayName} ")
                Log.d("AuthRepositoryImpl", "signInWithGoogle: firebaseUser email = ${firebaseUser?.email} ")
                Log.d("AuthRepositoryImpl", "signInWithGoogle: userDao.getUserCount() = ${userDao.getUserCount()} ")
                Result.success(Unit)
            } else {
                    Result.failure(Exception("Firebase user is null"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "catch exception signInWithGoogle: ${e.message} ")
            println(e)
            Result.failure(e)
        }

    }

    private fun firebaseUserToEntity (firebaseUser: FirebaseUser?, idToken: String): UserEntity{
        return UserEntity(
            userId = firebaseUser?.uid ?: "",
            displayName = firebaseUser?.displayName,
            email = firebaseUser?.email?: "",
            photoUrl = firebaseUser?.photoUrl?.toString(),
            idToken = idToken,
            deviceInfo = DeviceInfo(
                deviceModel = "Build.MODEL", // TODO: Replace with actual device info
                osVersion = "Build.VERSION.RELEASE", // TODO: Replace with actual device info
                appVersion = "1.0.0", // TODO: Replace with actual app version
                deviceId = "" // TODO: Replace with actual device ID
            ),
            isEmailVerified = firebaseUser?.isEmailVerified ?: false,
            createdAt = System.currentTimeMillis(),
            lastLoginAt = System.currentTimeMillis(),
            isActive = true
        )

    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            val userId = userDao.getCurrentUserId()
            userDao.deleteUser(userId) // Clear the local user data on sign out
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAuthState(): Flow<UserEntity?> {
        return userRepository.getCurrentUser()
    }

    override suspend fun refreshToken(): Result<String> {
        // Implementation would depend on your token refresh logic
        return Result.success("new_token")
    }
    /*override suspend fun signOut(): Result<Unit> {
        return try {
            val clearResult = userRepository.clearAllUsers()
            if (clearResult.isSuccess) {
                _loginUiState.value = LoginUiState.Unauthenticated
                Log.d("AuthRepositoryImpl", "User signed out successfully")
                Result.success(Unit)
            } else {
                Result.failure(clearResult.exceptionOrNull() ?: Exception("Sign out failed"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl",  "Sign out failed")
            Result.failure(e)
        }
    }*/


}