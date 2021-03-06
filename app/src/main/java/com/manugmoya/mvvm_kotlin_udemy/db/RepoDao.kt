package com.manugmoya.mvvm_kotlin_udemy.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manugmoya.mvvm_kotlin_udemy.model.Contributor
import com.manugmoya.mvvm_kotlin_udemy.model.Repo
import com.manugmoya.mvvm_kotlin_udemy.model.RepoSearchResult
import java.util.*

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContributors(contributor: List<Contributor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(repo: Repo): Long

    @Query("SELECT * FROM repo WHERE owner_login = :ownerLogin AND name = :name")
    abstract fun load(ownerLogin: String, name: String): LiveData<Repo>

    @Query("SELECT login, avatarUrl, repoName, repoOwner, contributions FROM contributor WHERE repoName = :name AND repoOwner = :owner ORDER BY contributions DESC")
    abstract fun loadContributors(owner: String, name: String): LiveData<List<Contributor>>

    @Query("SELECT * FROM repo WHERE owner_login = :owner ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Repo>> {
        val order = SparseIntArray()

        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }

        return Transformations.map(loadById(repoIds)) { repositories ->
            Collections.sort(repositories) { r1, r2 ->
                val pos1 = order.get(r1.id)
                val pos2 = order.get(r2.id)
                pos1 - pos2
            }
            repositories
        }
    }


    @Query("SELECT * FROM repo WHERE id in(:repoIds)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult
}