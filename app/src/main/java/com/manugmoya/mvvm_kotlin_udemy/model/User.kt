package com.manugmoya.mvvm_kotlin_udemy.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login"])
data class User(

    @field:SerializedName("login")
    val login: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("repos_url")
    val reposUrl : String?,
    @field:SerializedName("blog")
    val blog: String?
)