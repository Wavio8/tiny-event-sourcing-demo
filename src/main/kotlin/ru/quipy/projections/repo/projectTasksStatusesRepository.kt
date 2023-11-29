package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectTasksStatusesViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PTSRepositoryProjectTask : MongoRepository<ProjectTasksStatusesViewDomain.ProjectTask?, UUID?> {

}
interface PTSRepositoryProjectStatus : MongoRepository<ProjectTasksStatusesViewDomain.ProjectStatus?, UUID?> {
    fun deleteByProjectId(projectId: UUID): ProjectTasksStatusesViewDomain.ProjectStatus?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}
interface PTSRepositoryProjectUser : MongoRepository<ProjectTasksStatusesViewDomain.ProjectUser?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}
interface PTSRepositoryTaskStatus : MongoRepository<ProjectTasksStatusesViewDomain.TaskStatus?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}
interface PTSRepositoryStatus : MongoRepository<ProjectTasksStatusesViewDomain.Status?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    //    fun save(id: UUID,username: String)
    fun getById(id: UUID):UserViewDomain?

}