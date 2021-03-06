package com.manugmoya.mvvm_kotlin_udemy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manugmoya.mvvm_kotlin_udemy.model.Contributor
import com.manugmoya.mvvm_kotlin_udemy.model.Repo
import com.manugmoya.mvvm_kotlin_udemy.model.RepoSearchResult
import com.manugmoya.mvvm_kotlin_udemy.model.User

@Database(
    entities = [
        User::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class
    ],
    version = 1
)
abstract class GithubDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}
