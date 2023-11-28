package ru.quipy.projections.repo

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.projections.views.ProjectViewDomain
import ru.quipy.projections.views.UserViewDomain
import java.util.*


interface ProjectRepository : MongoRepository<ProjectViewDomain.Project?, UUID?> {
    fun findByFirstName(firstName: String?): UserViewDomain?
    fun findByLastName(lastName: String?): List<UserViewDomain?>?
    fun getById(id: UUID):UserViewDomain?
}