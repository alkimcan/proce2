package com.spordestek.app.data.models

import com.google.gson.annotations.SerializedName

// Kullanıcı Modeli
data class User(
    val id: Int,
    val openId: String,
    val name: String?,
    val email: String?,
    val role: String,
    val tcKimlik: String?,
    val telefon: String?
)

// Kulüp Modeli
data class Club(
    val id: Int,
    val name: String,
    val description: String?,
    val logo: String?,
    val category: String?,
    val city: String?,
    val totalDonations: Int,
    val createdAt: String
)

// Adım Modeli
data class Step(
    val id: Int,
    val userId: Int,
    val stepCount: Int,
    val date: String,
    val convertedToPoints: Int,
    val pointsEarned: Int,
    val createdAt: String
)

// Bağış Modeli
data class Donation(
    val id: Int,
    val userId: Int,
    val clubId: Int,
    val points: Int,
    val message: String?,
    val createdAt: String,
    val clubName: String?,
    val clubLogo: String?
)

// Kullanıcı Puanları Modeli
data class UserPoints(
    val userId: Int,
    val totalPoints: Int,
    val availablePoints: Int,
    val totalSteps: Int,
    val totalDonations: Int
)

// Liderlik Tablosu Modeli
data class LeaderboardEntry(
    val userId: Int,
    val userName: String,
    val totalPoints: Int,
    val totalSteps: Int,
    val rank: Int
)

// API İstekleri
data class SaveStepsRequest(
    val stepCount: Int,
    val date: String
)

data class ConvertPointsRequest(
    val date: String
)

data class DonateRequest(
    val clubId: Int,
    val points: Int,
    val message: String?
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

// API Yanıtları
data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)


data class ConvertPointsResponse(
    val success: Boolean,
    val message: String?,
    val pointsEarned: Int
)

data class LoginResponse(
    val token: String,
    val user: User
)
