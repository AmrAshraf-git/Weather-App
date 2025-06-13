package com.ipro.weatherapp.data.location.repository

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.ipro.weatherapp.domain.exception.LocationUnavailableException
import com.ipro.weatherapp.domain.model.LocationCoordinate
import com.ipro.weatherapp.domain.repository.LocationRepo
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationRepoImp(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepo {

    override suspend fun getCurrentLocation(): LocationCoordinate = suspendCancellableCoroutine { cont ->
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            cont.resumeWithException(SecurityException("Location permission not granted"))
            return@suspendCancellableCoroutine
        }

        try {
            fusedLocationProviderClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(LocationCoordinate(location.latitude, location.longitude))
                    } else {
                        cont.resumeWithException(LocationUnavailableException())
                    }
                }
                .addOnFailureListener { e ->
                    cont.resumeWithException(e)
                }
        } catch (e: SecurityException) {
            cont.resumeWithException(e)
        }
    }
}