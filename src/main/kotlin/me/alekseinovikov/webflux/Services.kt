package me.alekseinovikov.webflux

import org.springframework.stereotype.Service

interface UserService {
    suspend fun findById(id: Long): String?
    suspend fun create(login: String): Long
}

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override suspend fun findById(id: Long): String? =
        userRepository.findById(id)

    override suspend fun create(login: String): Long =
        userRepository.saveAndReturnId(login)

}