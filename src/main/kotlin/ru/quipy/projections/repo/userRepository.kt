package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface UserRepository : MongoRepository<UserViewDomain.User?, UUID?> {
    fun getById(id: UUID):UserViewDomain?

    fun existsByUsername(username:String): Boolean

}