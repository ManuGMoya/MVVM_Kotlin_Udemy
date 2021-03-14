package com.manugmoya.mvvm_kotlin_udemy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [Index("id"), Index("owner_login")],
    primaryKeys = ["name", "owner_login"]
)
data class Repo(
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner,
    @field:SerializedName("stargazers_count")
    val stars: Int

) {
    data class Owner(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("url")
        val url: String?,
    )

    companion object{
        const val UNKOWN_ID = -1
    }
}