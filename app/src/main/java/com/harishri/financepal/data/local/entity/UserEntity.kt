package com.harishri.financepal.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harishri.financepal.domain.model.DeviceInfo
import com.harishri.financepal.domain.model.User

//Stores Google Sign-In user information
//Links all other data to specific users

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val displayName: String?,
    val email: String,
    val photoUrl: String?,
    val idToken: String?,
    @Embedded val deviceInfo: DeviceInfo,
    val isEmailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val isActive: Boolean
)

// Extension functions for mapping
fun UserEntity.toDomainModel(): User {
    return User(
        userId = userId,
        name = displayName,
        email = email,
        photoUrl = photoUrl,
        idToken = idToken,
        deviceInfo = deviceInfo,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt,
        isActive = isActive
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = userId,
        displayName = name,
        email = email,
        photoUrl = photoUrl,
        idToken = idToken,
        deviceInfo = deviceInfo,
        createdAt = createdAt,
        lastLoginAt = lastLoginAt,
        isActive = isActive
    )
}


