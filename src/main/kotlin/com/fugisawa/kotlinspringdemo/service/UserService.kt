package com.fugisawa.kotlinspringdemo.service

import com.fugisawa.kotlinspringdemo.dto.UserOutput
import com.fugisawa.kotlinspringdemo.entity.User
import com.fugisawa.kotlinspringdemo.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository) {

    fun createNewUser(user: User): UserOutput {
        checkUserConsistency(user)
        val createdUser = userRepository.save(user)
        return convertUserToUserOutput(createdUser)
    }

    fun retrieveAllUsers(): List<UserOutput> {
        val users = userRepository.findAll()
        return users.map(::convertUserToUserOutput)
    }

    fun retrieveUserByEmail(email: String): UserOutput? {
        val user = userRepository.findByEmailIgnoreCase(email)
        return user?.let(::convertUserToUserOutput)
    }

    fun retrieveUserById(id: Long): UserOutput? {
        val user = userRepository.findById(id).getOrNull()
        return user?.let(::convertUserToUserOutput)
    }

    fun retrieveUsersEmails(): List<String> {
        val emails = userRepository.findAll().map { it.email }
        return emails
    }

    fun deleteUserById(id: Long) {
        userRepository.deleteById(id)
    }

    fun cleanupInactiveUsers() {
        val inactiveUsers = userRepository.findByActive(false)
        for (user in inactiveUsers) {
            userRepository.deleteById(user.id!!)
        }
    }

    fun updateUser(user: User): UserOutput {
        val existingUser = userRepository.findById(user.id!!).getOrNull()
        if (existingUser != null) {
            if (user.name.isNotBlank()) existingUser.name = user.name
            if (user.email.isNotBlank()) existingUser.email = user.email
            if (user.password.isNotBlank()) existingUser.password = user.password
            existingUser.active = user.active

            checkUserConsistency(existingUser)
            return convertUserToUserOutput(userRepository.save(existingUser))
        } else {
            throw IllegalArgumentException("User with ID ${user.id} not found.")
        }
    }

    private fun checkUserConsistency(user: User) {
        if (user.name.isBlank())
            throw IllegalArgumentException("Name is required and must not be blank.")

        if (!user.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")))
            throw IllegalArgumentException("Invalid email format.")

        if (user.password.length < 12)
            throw IllegalArgumentException("Password must have at least 12 characters.")
    }

    private fun convertUserToUserOutput(user: User): UserOutput {
        return UserOutput(user.id!!, user.name, user.email, user.createdAt)
    }
}