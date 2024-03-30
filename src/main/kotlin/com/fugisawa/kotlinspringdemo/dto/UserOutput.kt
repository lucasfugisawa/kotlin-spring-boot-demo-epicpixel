package com.fugisawa.kotlinspringdemo.dto

import java.time.Instant

class UserOutput(
    var id: Long? = null,
    var name: String,
    var email: String,
    var createdAt: Instant = Instant.now(),
)
