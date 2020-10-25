package me.moe.visionalerts.commands

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.entity.channel.GuildChannel
import com.gitlab.kordlib.core.entity.channel.TextChannel
import me.jakejmattson.discordkt.api.arguments.AnyArg
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.moe.visionalerts.conversations.CallConversation
import me.moe.visionalerts.dataclasses.Configuration
import me.moe.visionalerts.extensions.requiredPermissionLevel
import me.moe.visionalerts.services.Permission
import java.awt.Color

fun callCommands(configuration: Configuration) = commands("Call Commands") {
    guildCommand("entry") {
        requiredPermissionLevel = Permission.EVERYONE
        execute {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            CallConversation(configuration)
                    .createConfigurationConversation(guild, responseChannel, Color.GREEN)
                    .startPublicly(discord, author, channel.asChannel())

            respond("All done!")
        }
    }

    guildCommand("update") {
        requiredPermissionLevel = Permission.EVERYONE
        execute {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            CallConversation(configuration)
                    .createConfigurationConversation(guild, responseChannel, Color.ORANGE)
                    .startPublicly(discord, author, channel.asChannel())

            respond("All done!")
        }
    }

    guildCommand("exit") {
        requiredPermissionLevel = Permission.EVERYONE
        execute {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            CallConversation(configuration)
                    .createConfigurationConversation(guild, responseChannel, Color.RED)
                    .startPublicly(discord, author, channel.asChannel())

            respond("All done!")
        }
    }

    guildCommand("trim") {
        requiredPermissionLevel = Permission.EVERYONE
        execute {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            CallConversation(configuration)
                    .createConfigurationConversation(guild, responseChannel, Color.decode("#FFCCCB"))
                    .startPublicly(discord, author, channel.asChannel())

            respond("All done!")
        }
    }

    guildCommand("announcement") {
        requiredPermissionLevel = Permission.EVERYONE
        execute {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            CallConversation(configuration)
                    .createConfigurationConversation(guild, responseChannel, Color.decode("#800080"))
                    .startPublicly(discord, author, channel.asChannel())

            respond("All done!")
        }
    }
}