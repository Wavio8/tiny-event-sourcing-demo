package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectUsersViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PURepositoryUser : MongoRepository<ProjectUsersViewDomain.User?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
//    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?
}
interface PURepositoryProject : MongoRepository<ProjectUsersViewDomain.Project?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?
}
interface PURepositoryProjectUser : MongoRepository<ProjectUsersViewDomain.ProjectUser?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?
}