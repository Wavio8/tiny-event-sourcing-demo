package ru.quipy.service

import org.springframework.stereotype.Service
import ru.quipy.projections.repo.PURepositoryUser
import ru.quipy.projections.repo.UserRepository

@Service
class UserService(

        private val userRepository: UserRepository,
        private val pURepositoryUser: PURepositoryUser,
) {
    fun isUsernameExists(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }
}