package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projections.repo.ProjectRepository
import ru.quipy.projections.repo.ProjectUserRepository
import ru.quipy.projections.repo.UserRepository
import ru.quipy.projections.views.ProjectViewDomain
import ru.quipy.projections.views.UserViewDomain
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber (
    private val projRepository: ProjectRepository,
    private val projUserRepository: ProjectUserRepository,
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
                createUser(event.userId, event.username)
                logger.info("Task created: {}", event.taskName)
            }

            `when`(TagCreatedEvent::class) { event ->
                logger.info("Tag created: {}", event.tagName)
            }

            `when`(TagCreatedEvent::class) { event ->
                logger.info("Tag created: {}", event.tagName)
            }

            `when`(TagAssignedToTaskEvent::class) { event ->
                logger.info("Tag {} assigned to task {}: ", event.tagId, event.taskId)
            }
        }
    }
    private fun createProject(id: UUID, title: String, creatorId: String) {
        val proj = ProjectViewDomain.Project(id, title,creatorId)
        projUserRepository.save(proj)
        projRepository.save(proj)
    }
    private fun createUser(userId: UUID, userName: String) {
        val user = UserViewDomain.User(userId, userName)
        //Repository.save(user)
    }

    private fun changeUserName(userId: UUID, userName: String) {
        val user = UserViewDomain.User(userId, userName)
        //Repository.update(user)
    }
}