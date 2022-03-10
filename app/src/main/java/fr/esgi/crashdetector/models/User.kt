package fr.esgi.crashdetector.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val nom: String,
    val prenom: String,
    val email: String,
    val tel: String,
    val idgoogle: String,
    val idscooter: String
): Parcelable
