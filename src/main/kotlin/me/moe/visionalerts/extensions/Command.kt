package me.moe.visionalerts.extensions

import me.jakejmattson.discordkt.api.dsl.Command
import me.moe.visionalerts.services.DEFAULT_REQUIRED_PERMISSION
import me.moe.visionalerts.services.Permission
import java.util.*


private object CommandPropertyStore {
    val permissions = WeakHashMap<Command, Permission>()
}

var Command.requiredPermissionLevel: Permission
    get() = CommandPropertyStore.permissions[this] ?: DEFAULT_REQUIRED_PERMISSION
    set(value) {
        CommandPropertyStore.permissions[this] = value
    }