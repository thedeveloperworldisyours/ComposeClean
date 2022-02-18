package com.a.vocabulary15.data

import com.a.vocabulary15.data.local.ElementData
import com.a.vocabulary15.data.local.GroupData
import com.a.vocabulary15.data.local.StatisticsData
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group
import com.a.vocabulary15.domain.model.Statistics

fun Group.toData() = GroupData(
    0,
    name
)

fun GroupData.toModel() = Group(
    id,
    name
)

fun Element.toData() = ElementData(
    0,
    groupId,
    image,
    value,
    link
)

fun ElementData.toModel() = Element(
    id,
    groupId,
    image,
    value,
    link
)

fun Statistics.toData() = StatisticsData(
    0,
    date,
    points,
    groupId
)

fun StatisticsData.toModel() = Statistics(
    id,
    date,
    points,
    groupId
)