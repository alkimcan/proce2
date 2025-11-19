package com.spordestek.app.data.api

import com.spordestek.app.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Auth endpoints
    @POST("api/trpc/auth.login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("api/trpc/auth.register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<LoginResponse>>

    @GET("api/trpc/auth.me")
    suspend fun getCurrentUser(): Response<User>
    
    @POST("api/trpc/auth.logout")
    suspend fun logout(): Response<ApiResponse<Unit>>
    
    // Clubs endpoints
    @GET("api/trpc/clubs.list")
    suspend fun getClubs(): Response<ApiResponse<List<Club>>>
    
    @GET("api/trpc/clubs.detail")
    suspend fun getClubDetail(@Query("id") id: Int): Response<ApiResponse<Club>>
    
    @GET("api/trpc/clubs.topDonated")
    suspend fun getTopClubs(@Query("limit") limit: Int = 10): Response<ApiResponse<List<Club>>>
    
    // Steps endpoints
    @POST("api/trpc/steps.saveSteps")
    suspend fun saveSteps(@Body request: SaveStepsRequest): Response<ApiResponse<Unit>>
    
    @GET("api/trpc/steps.getTodaySteps")
    suspend fun getTodaySteps(@Query("date") date: String): Response<ApiResponse<Step?>>
    
    @GET("api/trpc/steps.history")
    suspend fun getStepsHistory(): Response<ApiResponse<List<Step>>>
    
    @POST("api/trpc/steps.convertToPoints")
    suspend fun convertToPoints(@Body request: ConvertPointsRequest): Response<ApiResponse<ConvertPointsResponse>>
    
    // Points endpoints
    @GET("api/trpc/points.getMyPoints")
    suspend fun getMyPoints(): Response<ApiResponse<UserPoints>>
    
    // Donations endpoints
    @POST("api/trpc/donations.donate")
    suspend fun makeDonation(@Body request: DonateRequest): Response<ApiResponse<Unit>>
    
    @GET("api/trpc/donations.myDonations")
    suspend fun getMyDonations(): Response<ApiResponse<List<Donation>>>
    
    @GET("api/trpc/donations.clubDonations")
    suspend fun getClubDonations(@Query("clubId") clubId: Int): Response<ApiResponse<List<Donation>>>
    
    // Leaderboard endpoints
    @GET("api/trpc/leaderboard.getLeaderboard")
    suspend fun getLeaderboard(): Response<ApiResponse<List<LeaderboardEntry>>>
}
