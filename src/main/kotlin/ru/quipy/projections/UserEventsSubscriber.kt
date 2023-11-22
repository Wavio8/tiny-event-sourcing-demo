package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projections.repo.UserRepository
import ru.quipy.projections.views.UserViewDomain
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class UserEventsSubscriber (
        private val userRepository: UserRepository,
){
    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "user-subscriber") {

            `when`(UserCreatedEvent::class) { event ->
                createUser(event.userId, event.username)
                logger.info("User created: {}", event.username)
            }

            `when`(NameUserChangedEvent::class) { event ->
                changeUserName(event.userId, event.nameUser)
                logger.info("User {} userName changed: {}", event.userId, event.nameUser)
            }

        }
    }

    private fun createUser(userId: UUID, userName: String) {
        val user = UserViewDomain.User(userId, userName)
        userRepository.save(user)
    }

    private fun changeUserName(userId: UUID, userName: String) {
        val user = UserViewDomain.User(userId, userName)
        userRepository.save(user)
    }
}