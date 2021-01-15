package com.bytebyte6.view.usecase

import com.bytebyte6.base.RxSingleUseCase
import com.bytebyte6.data.dao.UserDao
import com.bytebyte6.data.entity.User

class UpdateUserUseCase(private val userDao: UserDao) : RxSingleUseCase<User, Boolean>(){
    override fun doSomething(param: User): Boolean {
        userDao.update(param)
        return (true)
    }
}