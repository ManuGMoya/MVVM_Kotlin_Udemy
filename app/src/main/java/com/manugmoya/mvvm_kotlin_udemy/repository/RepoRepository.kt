package com.manugmoya.mvvm_kotlin_udemy.repository

import androidx.lifecycle.LiveData
import com.manugmoya.mvvm_kotlin_udemy.AppExecutors
import com.manugmoya.mvvm_kotlin_udemy.api.ApiResponse
import com.manugmoya.mvvm_kotlin_udemy.api.GithubApi
import com.manugmoya.mvvm_kotlin_udemy.db.GithubDB
import com.manugmoya.mvvm_kotlin_udemy.db.RepoDao
import com.manugmoya.mvvm_kotlin_udemy.model.Repo
import com.manugmoya.mvvm_kotlin_udemy.utils.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: GithubDB,
    private val repoDao: RepoDao,
    private val githubApi: GithubApi
) {
    private val repoListRateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {

            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimiter.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.loadRepositories(owner)
            }

            override fun createCall(): LiveData<ApiResponse<List<Repo>>> {
                return githubApi.getRepos(owner)
            }

            override fun onFetchFailed() {
                repoListRateLimiter.reset(owner)
            }

        }.asLiveData()
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {

            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }

            override fun shouldFetch(data: Repo?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<Repo> {
                return repoDao.load(ownerLogin = owner, name = name)
            }

            override fun createCall(): LiveData<ApiResponse<Repo>> {
                return githubApi.getRepo(owner = owner, name = name)
            }

        }.asLiveData()
    }

}
