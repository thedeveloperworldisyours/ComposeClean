package com.a.vocabulary15.data

import com.a.vocabulary15.data.local.ElementData
import com.a.vocabulary15.data.local.GroupData
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Group

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