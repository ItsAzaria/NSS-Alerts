package me.moe.visionalerts.conversations

import com.gitlab.kordlib.core.entity.Guild
import me.jakejmattson.discordkt.api.arguments.ChannelArg
import me.moe.visionalerts.dataclasses.Configuration
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.arguments.RoleArg
import me.jakejmattson.discordkt.api.dsl.Conversation
import me.jakejmattson.discordkt.api.dsl.conversation

class ConfigurationConversation(private val configuration: Configuration) {
    fun createConfigurationConversation(guild: Guild) = conversation {
        val prefix = promptMessage(EveryArg, "Bot prefix:")
        val botChannel = promptMessage(ChannelArg, "Bot channel:")
        val staffRole = promptMessage(RoleArg, "Staff role:")

        configuration.setup(guild, prefix, botChannel, staffRole)
    }
}

