package com.fugisawa.kotlinspringdemo.repository

import com.fugisawa.kotlinspringdemo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmailIgnoreCase(email: String): User?
    fun findByCreatedAtAfter(date: Instant): List<User>
    fun findByActive(active: Boolean): List<User>
}