package ru.quipy.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.ProjectAggregateState
import ru.quipy.logic.addTask
import ru.quipy.logic.create
import ru.quipy.logic.createTag
import ru.quipy.logic.*
import ru.quipy.projections.views.ProjectTasksStatusesViewDomain
import ru.quipy.projections.views.ProjectTasksViewDomain
import ru.quipy.projections.views.ProjectUsersViewDomain
import ru.quipy.service.ProjectService
import ru.quipy.service.UserService
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
        val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
        val userService: UserService,
        val projectService: ProjectService,
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String): ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getAccount(@PathVariable projectId: UUID): ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun createTask(@RequestParam userId: UUID, @PathVariable projectId: UUID, @PathVariable taskName: String): TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTask(userId, taskName)
        }
    }

    @PostMapping("/{projectId}/tags/{tagName}")
    fun createTag(@RequestParam userId: UUID, @PathVariable projectId: UUID, @PathVariable tagName: String, @PathVariable tagColor: String): TagCreatedEvent {
        return projectEsService.update(projectId) {
            it.createTag(userId, tagName, tagColor)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskName}")
    fun assignTagToTask(@RequestParam userId: UUID, @PathVariable projectId: UUID, @RequestParam tagId: UUID, @RequestParam taskId: UUID): TagAssignedToTaskEvent {
        return projectEsService.update(projectId) {
            it.assignTagToTask(userId, tagId, taskId)
        }
    }

    @PostMapping("/{projectId}/membersList")
    fun addUserToProject(@RequestParam userId: UUID, @RequestParam userNewId: UUID, @PathVariable projectId: UUID): UserAddedToProjectEvent {
        return projectEsService.update(projectId) {
            it.addUserToProject(userId, userNewId)
        }
    }

    @PostMapping("/{projectId}")
    fun changeProjectName(@RequestParam userId: UUID, @PathVariable projectId: UUID, @RequestParam title: String): ProjectNameChangedEvent {
        return projectEsService.update(projectId) {
            it.changeProjectName(userId, title)
        }
    }

    @PostMapping("/{projectId}/tags/{tagId}")
    fun deleteTag(@RequestParam userId: UUID, @PathVariable projectId: UUID, @PathVariable tagId: UUID): TagDeletedEvent {
        return projectEsService.update(projectId) {
            it.deleteTag(userId, tagId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    fun assignUserToTask(@RequestParam userId: UUID, @PathVariable projectId: UUID, @RequestParam taskId: UUID): UserAssignedToTaskEvent {
        return projectEsService.update(projectId) {
            it.assignUserToTask(userId, taskId)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    fun changeTaskName(@RequestParam userId: UUID, @PathVariable projectId: UUID, @RequestParam title: String, @RequestParam taskId: UUID): TaskNameChangedEvent {
        return projectEsService.update(projectId) {
            it.changeTaskName(userId, taskId, title)
        }
    }

    @GetMapping("/checkUsername")
    fun checkUsername(@RequestParam username: String): ResponseEntity<Boolean> {
        val isUsernameExists = userService.isUsernameExists(username)
        return ResponseEntity.ok(isUsernameExists)
    }

    @GetMapping("{userId}/projects")
    fun getProjectsByUserId(@PathVariable userId: UUID): ResponseEntity<List<ProjectUsersViewDomain.Project>> {
        val projects = projectService.getProjectsByUserId(userId)
        return ResponseEntity.ok(projects)
    }

    @GetMapping("/users/{projectId}")
    fun getUsersByProjectId(@PathVariable projectId: UUID): ResponseEntity<List<ProjectUsersViewDomain.User>> {
        val users = projectService.getUsersByProjectId(projectId)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/creator/{projectId}")
    fun getCreatorByUserIdAndProjectId(
            @PathVariable projectId: UUID
    ): ResponseEntity<ProjectUsersViewDomain.User?> {
        val creator = projectService.getCreatorProjectId(projectId)
        return ResponseEntity.ok(creator)
    }

    @GetMapping("/project-title/{projectId}")
    fun getProjectTitleByUserIdAndProjectId(
            @PathVariable projectId: UUID
    ): ResponseEntity<String?> {
        val projectTitle = projectService.getProjectTitleByProjectId(projectId)
        return ResponseEntity.ok(projectTitle)
    }

    @GetMapping("/{statusId}/tasks")
    fun getTasksByStatusId(
            @PathVariable statusId: UUID
    ): ResponseEntity<List<UUID?>> {
        val projectTitle = projectService.getTasksByStatusId(statusId)
        return ResponseEntity.ok(projectTitle)
    }

    @GetMapping("/{projectId}/statuses")
    fun getStatusesByProjectId(
            @PathVariable projectId: UUID
    ): ResponseEntity<List<ProjectTasksStatusesViewDomain.Status>> {
        val projectTitle = projectService.getStatusesByProjectId(projectId)
        return ResponseEntity.ok(projectTitle)
    }

    @GetMapping("/{projectId}/statuses")
    fun getTaksByProjectId(
            @PathVariable projectId: UUID
    ): ResponseEntity<List<ProjectTasksViewDomain.Task>> {
        val projectTitle = projectService.getTasksByProjectId(projectId)
        return ResponseEntity.ok(projectTitle)
    }
}