package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectTasksViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PTRepositoryProjectTask : MongoRepository<ProjectTasksViewDomain.ProjectTask?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
//    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}
interface PTRepositoryProjectUser : MongoRepository<ProjectTasksViewDomain.ProjectUser?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}
interface PTRepositoryTask : MongoRepository<ProjectTasksViewDomain.Task?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}