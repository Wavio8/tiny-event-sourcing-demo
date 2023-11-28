package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface UserRepository : MongoRepository<UserViewDomain.User?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
//    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}