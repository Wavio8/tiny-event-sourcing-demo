package ru.quipy.projections.views

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import ru.quipy.logic.UserEntity
import java.util.*

class ProjectUsersViewDomain {
    @Document("project-users-view")
    data class ProjectUser(
            @Id
            override val id: UUID,
            val userId: UUID,
            val projectId: UUID
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