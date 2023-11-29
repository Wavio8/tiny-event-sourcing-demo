package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projections.repo.*
import ru.quipy.projections.views.*
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber (
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

){

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "project-subscriber") {

            `when`(ProjectCreatedEvent::class) { event ->
                createProject(event.projectId, event.title,event.creatorId)
                logger.info("Project created: {}", event.title)
            }

            `when`(TaskCreatedEvent::class) { event ->
                createTask(event.projectId, event.taskId,event.taskName)
                logger.info("Task created: {}", event.taskName)
            }

            `when`(TagCreatedEvent::class) { event ->
                createTag(event.projectId,event.tagId,event.tagName,event.color)
                logger.info("Tag created: {}", event.tagName)
            }

            `when`(TagAssignedToTaskEvent::class) { event ->
                tagAssignedToTask(event.projectId,event.taskId,event.tagId)
                logger.info("Tag {} assigned to task {}: ", event.tagId, event.taskId)
            }
            `when`(UserAddedToProjectEvent::class) { event ->
                userAddedToProject(event.projectId,event.userId)
                logger.info("User {} Added To Project {}: ", event.userId, event.projectId)
            }
            `when`(ProjectNameChangedEvent::class) { event ->
                projectNameChanged(event.projectId,event.title)
                logger.info("Project {} NameChanged {}: ", event.projectId, event.title)
            }
            `when`(TagDeletedEvent::class) { event ->
                tagDeleted(event.projectId,event.tagId)
                logger.info("Tag {} Deleted {}: ",event.tagId, event.projectId )
            }
            `when`(UserAssignedToTaskEvent::class) { event ->
                userAssignedToTask(event.projectId,event.userId,event.taskId)
                logger.info("User {} AssignedToTask {}: ", event.userId, event.taskId)
            }
            `when`(TaskNameChangedEvent::class) { event ->
                taskNameChanged(event.projectId,event.taskId,event.title)
                logger.info("Task {} NameChanged {}: ", event.taskId, event.title)
            }
        }
    }
    private fun createProject(id: UUID, title: String, creatorId: String) {
        val proj = ProjectViewDomain.Project(id, title,creatorId)
        projRepository.save(proj)
        pURepositoryProject.save(proj as ProjectUsersViewDomain.Project)
        pURepositoryProjectUser.save(ProjectUsersViewDomain.ProjectUser(id=UUID.randomUUID(),UUID.fromString(creatorId),id))

    }
    private fun createTag(projectId: UUID, tagId: UUID,tagName: String,color: String) {
        val statusCreated = ProjectTasksStatusesViewDomain.Status(tagId, tagName,color)
        pTSRepositoryStatus.save(statusCreated)
        pTSRepositoryProjectStatus.save(ProjectTasksStatusesViewDomain.ProjectStatus(projectId, tagId))

    }
    private fun createTask(projectId: UUID,taskId: UUID,taskName: String) {
        val tasksCreated = ProjectTasksViewDomain.Task(taskId, taskName,null,null)
        val id =UUID.randomUUID();
        pTRepositoryTask.save(tasksCreated)
        pTRepositoryProjectTask.save(ProjectTasksViewDomain.ProjectTask(id,taskId,projectId ))
        pTSRepositoryProjectTask.save(ProjectTasksStatusesViewDomain.ProjectTask(id,taskId,projectId))

    }
    private fun tagAssignedToTask(projectId: UUID,taskId: UUID,tagId: UUID) {
        val tagsAssigned = ProjectTasksStatusesViewDomain.TaskStatus(id=UUID.randomUUID(), taskId,tagId)
        pTSRepositoryTaskStatus.save(tagsAssigned)

    }
    private fun userAddedToProject(projectId: UUID,userId: UUID) {
        val id=UUID.randomUUID()
        val userAdded = ProjectUsersViewDomain.ProjectUser(id, userId,projectId)
        pURepositoryProjectUser.save(userAdded)
        pTRepositoryProjectUser.save(ProjectTasksViewDomain.ProjectUser(id,userId,projectId))
        pTSRepositoryProjectUser.save(ProjectTasksStatusesViewDomain.ProjectUser(id,userId,projectId))

    }
    private fun projectNameChanged(projectId: UUID,title: String) {
        val proj = projRepository.findById(projectId)
        proj.get().projectTitle=title
        pURepositoryProject.findById(projectId).get().projectTitle=title
    }
    private fun tagDeleted(projectId: UUID,tagId: UUID) {
        val proj = ProjectViewDomain.Project(id, title,creatorId)
        projRepository.save(proj)
        projUserRepository.save(proj)

    }
    private fun userAssignedToTask(projectId: UUID,userId: UUID,taskId: UUID) {
        val proj = projRepository.findById(projectId)
        proj.
        ProjectViewDomain.Project(id, title,creatorId)
        projRepository.save(proj)
        projUserRepository.save(proj)

    }
    private fun taskNameChanged(projectId: UUID,taskId: UUID,title: String) {
        val proj = ProjectViewDomain.Project(id, title,creatorId)
        projRepository.save(proj)
        projUserRepository.save(proj)

    }
}