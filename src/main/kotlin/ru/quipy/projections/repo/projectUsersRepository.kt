package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectUsersViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PURepositoryUser : MongoRepository<ProjectUsersViewDomain.User?, UUID?> {
    fun findAllByIdIn(projectIds: Collection<UUID>): List<ProjectUsersViewDomain.User>
    fun getById(id: UUID):ProjectUsersViewDomain.User?
}
interface PURepositoryProject : MongoRepository<ProjectUsersViewDomain.Project?, UUID?> {

    fun findAllByIdIn(projectIds: Collection<UUID>): List<ProjectUsersViewDomain.Project>



    fun getById(id: UUID):ProjectUsersViewDomain.Project?
}
interface PURepositoryProjectUser : MongoRepository<ProjectUsersViewDomain.ProjectUser?, UUID?> {
    fun findAllByUserId(userId: UUID): List<ProjectUsersViewDomain.ProjectUser>

    fun findAllByProjectId(projectId: UUID): List<ProjectUsersViewDomain.ProjectUser>

    fun existsByUserIdAndProjectId (userId: UUID, projectId: UUID): Boolean

    fun findByUserIdAndProjectId(userId: UUID, projectId: UUID): ProjectUsersViewDomain.ProjectUser?

    fun getById(id: UUID):UserViewDomain?
}