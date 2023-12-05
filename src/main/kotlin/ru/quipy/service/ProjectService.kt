package ru.quipy.service

import org.springframework.stereotype.Service
import ru.quipy.projections.repo.*
import ru.quipy.projections.views.ProjectTasksStatusesViewDomain
import ru.quipy.projections.views.ProjectTasksViewDomain
import ru.quipy.projections.views.ProjectUsersViewDomain
import java.util.*

@Service
class ProjectService(

        private val pURepositoryUser: PURepositoryUser,
        private val projRepository: ProjectRepository,
        private val pURepositoryProject: PURepositoryProject,
        private val pURepositoryProjectUser: PURepositoryProjectUser,

        private val pTSRepositoryProjectStatus: PTSRepositoryProjectStatus,
        private val pTSRepositoryStatus: PTSRepositoryStatus,
        private val pTSRepositoryProjectTask: PTSRepositoryProjectTask,
        private val pTSRepositoryProjectUser: PTSRepositoryProjectUser,
        private val pTSRepositoryTaskStatus: PTSRepositoryTaskStatus,

        private val pTRepositoryTask: PTRepositoryTask,
        private val pTRepositoryProjectTask: PTRepositoryProjectTask,
        private val pTRepositoryProjectUser: PTRepositoryProjectUser,
) {
    fun isUserInProject(userId: UUID, projectId: UUID): Boolean {
        return pURepositoryProjectUser.existsByUserIdAndProjectId(userId, projectId)
    }

    fun getProjectsByUserId(userId: UUID): List<ProjectUsersViewDomain.Project> {
        val projectUsers = pURepositoryProjectUser.findAllByUserId(userId)
        val projectIds = projectUsers.map { it.projectId }
        return pURepositoryProject.findAllByIdIn(projectIds)
    }

    fun getUsersByProjectId(projectId: UUID): List<ProjectUsersViewDomain.User> {
        val projectUsers = pURepositoryProjectUser.findAllByProjectId(projectId)
        val userIds = projectUsers.map { it.userId }
        return pURepositoryUser.findAllByIdIn(userIds)
    }

    fun getCreatorProjectId(projectId: UUID): ProjectUsersViewDomain.User? {
        val projectUser = pURepositoryProject.getById(projectId)
        return projectUser?.let { pURepositoryUser.getById(it.creatorId) }
    }

    fun getProjectTitleByProjectId(projectId: UUID): String? {
        val projectUser = pURepositoryProject.getById(projectId)
        return projectUser?.let { projectUser.projectTitle }
    }

    fun getTasksByStatusId(statusId: UUID): List<UUID?> {
        val tasksStatus = pTSRepositoryTaskStatus.findAllByStatusId(statusId)
        return tasksStatus.map { it?.taskId }
        //check in project
    }

    fun getStatusesByProjectId(projectId: UUID): List<ProjectTasksStatusesViewDomain.Status> {
        val projectStatus = pTSRepositoryProjectStatus.findAllByProjectId(projectId)
        val statusIds = projectStatus.map { it.statusId }
        return pTSRepositoryStatus.findAllByIdIn(statusIds)
    }

    fun getTasksByProjectId(projectId: UUID): List<ProjectTasksViewDomain.Task> {
        val projectTasks = pTRepositoryProjectTask.findAllByProjectId(projectId)
        val taskIds = projectTasks.map { it.taskId}
        return pTRepositoryTask.findAllByIdIn(taskIds)
    }
}