package com.spordestek.app.data.fitness

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.concurrent.TimeUnit

class GoogleFitManager(private val context: Context) {
    
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    
    fun hasPermissions(): Boolean {
        return GoogleSignIn.hasPermissions(
            GoogleSignIn.getAccountForExtension(context, fitnessOptions),
            fitnessOptions
        )
    }
    
    fun getFitnessOptions(): FitnessOptions = fitnessOptions
    
    suspend fun getTodaySteps(): Int {
        val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        
        val endTime = System.currentTimeMillis()
        
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()
        
        return try {
            val response = Fitness.getHistoryClient(context, account)
                .readData(readRequest)
                .await()
            
            var totalSteps = 0
            response.buckets.forEach { bucket ->
                bucket.dataSets.forEach { dataSet ->
                    dataSet.dataPoints.forEach { dataPoint ->
                        totalSteps += dataPoint.getValue(Field.FIELD_STEPS).asInt()
                    }
                }
            }
            totalSteps
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
    
    suspend fun getStepsForDate(year: Int, month: Int, day: Int): Int {
        val account = GoogleSignIn.getAccountForExtension(context, fitnessOptions)
        
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endTime = calendar.timeInMillis
        
        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()
        
        return try {
            val response = Fitness.getHistoryClient(context, account)
                .readData(readRequest)
                .await()
            
            var totalSteps = 0
            response.buckets.forEach { bucket ->
                bucket.dataSets.forEach { dataSet ->
                    dataSet.dataPoints.forEach { dataPoint ->
                        totalSteps += dataPoint.getValue(Field.FIELD_STEPS).asInt()
                    }
                }
            }
            totalSteps
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}
