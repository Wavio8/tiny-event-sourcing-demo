package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectTasksStatusesViewDomain
import ru.quipy.projections.views.ProjectUsersViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PTSRepositoryProjectTask : MongoRepository<ProjectTasksStatusesViewDomain.ProjectTask?, UUID?> {
}
interface PTSRepositoryProjectStatus : MongoRepository<ProjectTasksStatusesViewDomain.ProjectStatus?, UUID?> {
    fun deleteByProjectId(projectId: UUID): ProjectTasksStatusesViewDomain.ProjectStatus?
    fun findAllByProjectId(projectId: UUID): List<ProjectTasksStatusesViewDomain.ProjectStatus>

}
interface PTSRepositoryProjectUser : MongoRepository<ProjectTasksStatusesViewDomain.ProjectUser?, UUID?> {


}
interface PTSRepositoryTaskStatus : MongoRepository<ProjectTasksStatusesViewDomain.TaskStatus?, UUID?> {
    fun findAllByStatusId(statusId: UUID): List<ProjectTasksStatusesViewDomain.TaskStatus?>
}
interface PTSRepositoryStatus : MongoRepository<ProjectTasksStatusesViewDomain.Status?, UUID?> {

    fun findAllByIdIn(statusIds: Collection<UUID>): List<ProjectTasksStatusesViewDomain.Status>

}