package ru.quipy.projections.views

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class ProjectTasksStatusesViewDomain {
    @Document("project-tasks-statuses-view")
    data class ProjectTask(
            @Id
            override val id: UUID,
            val taskId: UUID,
            val projectId: UUID
    ) : Unique<UUID>

    data class ProjectStatus(
            @Id
            override val id: UUID,
            val projectId: UUID,
            val statusId: UUID
    ) : Unique<UUID>

    data class ProjectUser(
            @Id
            override val id: UUID,
            val userId: UUID,
            val projectId: UUID
    ) : Unique<UUID>

    data class TaskStatus(
            @Id
            override val id: UUID,
            val taskId: UUID,
            val statusId: UUID
    ) : Unique<UUID>

    data class Status(
            val id: UUID = UUID.randomUUID(),
            val name: String,
            val color: String
    )
}