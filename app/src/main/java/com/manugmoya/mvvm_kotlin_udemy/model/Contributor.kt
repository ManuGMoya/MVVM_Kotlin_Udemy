package com.manugmoya.mvvm_kotlin_udemy.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["repoName", "repoOwner", "login"],
    foreignKeys = [ForeignKey(
        entity = Repo::class, // Entidad con la que se va a relacionar.
        parentColumns = ["name", "owner_login"], // Columnas de la Entidad con la que la relacionamos.
        childColumns = ["repoName", "repoOwner"], // Columnas de esta propia clase/Entidad.
        onUpdate = ForeignKey.CASCADE // Manera de actualización. Propaga la actualización en la clave padre e hijas.
    )]
)
data class Contributor(

    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("contributions")
    val contributions: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String
) {
    lateinit var repoName: String
    lateinit var repoOwner: String
}

