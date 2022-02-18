package com.a.vocabulary15.domain.model

data class Statistics(
    var id: Int,
    var date: Long = 0,
    var points: Int,
    var groupId: Int?)
