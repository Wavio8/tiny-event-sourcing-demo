package ru.quipy.projections.views

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class ProjectTasksViewDomain {
    @Document("project-users-view")
    data class ProjectTask(
            @Id
            override val id: UUID,
            val taskId: UUID,
            val projectId: UUID
    ) : Unique<UUID>

    data class Task(
            @Id
            override val id: UUID,
            var name: String,
            var tag: UUID?,
            var userId: UUID?,
    ) : Unique<UUID>

    data class User(
            @Id
            override val id: UUID,
            var username: String
    ) : Unique<UUID>

    data class Project(
            @Id
            override val id: UUID,
            var projectTitle: String,
            var creatorId: String,
    ) : Unique<UUID>
}