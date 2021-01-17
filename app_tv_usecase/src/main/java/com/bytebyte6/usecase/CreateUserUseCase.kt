package com.bytebyte6.usecase

import com.bytebyte6.base.RxSingleUseCase
import com.bytebyte6.data.dao.UserDao
import com.bytebyte6.data.entity.User

class CreateUserUseCase(
    private val userDao: UserDao
) : RxSingleUseCase<User, User>() {

    override fun doSomething(param: User): User {
        if (userDao.getCount() == 0) {
            userDao.insert(param)
        }
        return userDao.getUser()
    }
}