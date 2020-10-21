package me.moe.visionalerts.preconditions

import me.jakejmattson.discordkt.api.dsl.precondition
import me.moe.visionalerts.dataclasses.Configuration

fun commandChannelPreconditions(configuration: Configuration) = precondition {
    command ?: return@precondition fail()
    val guild = guild ?: return@precondition fail()

    val guildConfig = configuration[guild.id.longValue] ?: return@precondition

    if (guildConfig.commandChannel != channel.id.longValue)
        fail()
}