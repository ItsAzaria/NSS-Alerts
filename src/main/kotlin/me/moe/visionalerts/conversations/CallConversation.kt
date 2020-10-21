package me.moe.visionalerts.conversations

import com.gitlab.kordlib.core.behavior.channel.createEmbed
import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.channel.TextChannel
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.conversation
import me.moe.visionalerts.dataclasses.Configuration
import java.awt.Color
import java.time.Instant

class CallConversation(private val configuration: Configuration) {
    fun createConfigurationConversation(guild: Guild, channel: TextChannel, embedColor: Color) = conversation {
        val playName = promptMessage(EveryArg, "Alert (`cancel` to end):")
        if (playName.toLowerCase().startsWith("cancel")) {
            return@conversation
        }

        val playDescription = promptMessage(EveryArg, "Description (`none` to skip or `cancel` to end) :")
        if (playDescription.toLowerCase().startsWith("cancel")) {
            return@conversation
        }

        channel.createEmbed {
            color = embedColor

            title = playName
            if (!playDescription.toLowerCase().startsWith("none")) {
                description = playDescription
            }

            timestamp = Instant.now()
        }
        channel.createMessage("@everyone")
    }
}