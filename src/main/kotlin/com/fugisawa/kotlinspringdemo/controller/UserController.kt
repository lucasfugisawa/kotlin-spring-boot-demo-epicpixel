package com.fugisawa.kotlinspringdemo.controller

import com.fugisawa.kotlinspringdemo.dto.UserOutput
import com.fugisawa.kotlinspringdemo.entity.User
import com.fugisawa.kotlinspringdemo.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: User): UserOutput {
        val createNewUser = userService.createNewUser(user)
        return createNewUser
    }

    @GetMapping
    fun getAllUsers(): List<UserOutput> {
        val users = userService.retrieveAllUsers()
        return users
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserOutput? {
        val user = userService.retrieveUserById(id)
        return user
    }

    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): UserOutput? {
        val user = userService.retrieveUserByEmail(email)
        return user
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long) {
        userService.deleteUserById(id)
    }

    @PutMapping
    fun updateUser(@RequestBody user: User): UserOutput {
        val updatedUser = userService.updateUser(user)
        return updatedUser
    }
}