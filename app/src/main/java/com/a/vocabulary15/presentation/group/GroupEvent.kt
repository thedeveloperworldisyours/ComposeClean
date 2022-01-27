package com.a.vocabulary15.presentation.group

sealed class GroupEvent {
    class OpenAddGroupDialog(val open: Boolean): GroupEvent()
}