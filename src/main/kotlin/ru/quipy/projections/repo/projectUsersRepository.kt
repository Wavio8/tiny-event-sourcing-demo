package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectUsersViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface ProjectUserRepository : MongoRepository<ProjectUsersViewDomain?, String?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
//    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}