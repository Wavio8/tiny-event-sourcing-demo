package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectTasksStatusesViewDomain
import ru.quipy.projections.views.ProjectTasksViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface PTRepositoryProjectTask : MongoRepository<ProjectTasksViewDomain.ProjectTask?, UUID?> {
    fun findAllByProjectId(id: UUID): List<ProjectTasksViewDomain.ProjectTask>

}
interface PTRepositoryProjectUser : MongoRepository<ProjectTasksViewDomain.ProjectUser?, UUID?> {


}
interface PTRepositoryTask : MongoRepository<ProjectTasksViewDomain.Task?, UUID?> {
    fun findAllByIdIn(tasksIds: Collection<UUID>): List<ProjectTasksViewDomain.Task>

}