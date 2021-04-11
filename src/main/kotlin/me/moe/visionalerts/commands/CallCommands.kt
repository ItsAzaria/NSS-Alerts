package me.moe.visionalerts.commands

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.Attachment
import com.gitlab.kordlib.core.entity.User
import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.moe.visionalerts.dataclasses.Configuration
import me.moe.visionalerts.extensions.requiredPermissionLevel
import me.moe.visionalerts.services.Permission
import java.awt.Color
import java.time.Instant

private fun getBuilder(description: String, color: Color, author: User, attachment: Attachment? = null): EmbedBuilder {
    val builder = EmbedBuilder()
    builder.description = description
    builder.color = color
    builder.timestamp = Instant.now()
    builder.author {
        name = author.username
        icon = author.avatar.url
    }

    if (attachment != null && attachment.isImage) {
        builder.image = attachment.url
    }

    builder.footer {
        text = "Non-Stop Signaling"
    }

    return builder
}

fun callCommands(configuration: Configuration) = commands("Call Commands") {
    val token = System.getenv("ROLE_MENTION") ?: null

    val mentionRole = "<@&${token}>"


    // ENTRY
    guildCommand("entry") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.GREEN, author, message.attachments.firstOrNull())

            responseChannel.createMessage {
                if (token != null)
                    content = mentionRole
                embed = builder
            }

            respond("Entry sent!")
        }
    }



    // UPDATE
    guildCommand("update") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.ORANGE, author)

            responseChannel.createMessage {
                if (token != null)
                    content = mentionRole
                embed = builder
            }

            respond("Update sent!")
        }
    }

    // EXIT
    guildCommand("exit") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.RED, author)

            responseChannel.createMessage {
                if (token != null)
                    content = mentionRole
                embed = builder
            }

            respond("Exit sent!")
        }
    }
}