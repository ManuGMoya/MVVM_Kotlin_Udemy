package com.manugmoya.mvvm_kotlin_udemy.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.manugmoya.mvvm_kotlin_udemy.model.Repo
import com.manugmoya.mvvm_kotlin_udemy.model.User
import com.manugmoya.mvvm_kotlin_udemy.repository.RepoRepository
import com.manugmoya.mvvm_kotlin_udemy.repository.Resource
import com.manugmoya.mvvm_kotlin_udemy.repository.UserRepository
import com.manugmoya.mvvm_kotlin_udemy.utils.AbsentLiveData
import javax.inject.Inject

class UserViewModel
@Inject constructor(userRepository: UserRepository, repoRepository: RepoRepository) : ViewModel() {

    private val _login = MutableLiveData<String>()
    val login: LiveData<String>
        get() = _login

    val repositories: LiveData<Resource<List<Repo>>> = Transformations
        .switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                repoRepository.loadRepos(login)
            }

        }

    val user: LiveData<Resource<User>> = Transformations
        .switchMap(_login) { login ->
            if(login == null){
                AbsentLiveData.create()
            }else{
                userRepository.loadUser(login)
            }
        }

    fun setLogin(login: String){
        if(_login.value != null){
            _login.value = login
        }
    }

    fun retry(){
        _login.value?.let {
            _login.value = it
        }
    }
}