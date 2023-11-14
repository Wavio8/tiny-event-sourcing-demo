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
            val taskId: UUID,
            val statusId: UUID
    ) : Unique<UUID>

    data class Task(
            @Id
            override val id: UUID,
            var name: String,
            var statusId: UUID?,
            var userId: UUID?,
    ) : Unique<UUID>
}