package me.moe.visionalerts.commands

import com.gitlab.kordlib.common.entity.Attachment
import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.behavior.channel.createMessage
import com.gitlab.kordlib.core.entity.channel.TextChannel
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.moe.visionalerts.dataclasses.Configuration
import me.moe.visionalerts.extensions.requiredPermissionLevel
import me.moe.visionalerts.services.Permission
import java.awt.Color
import java.time.Instant

private fun getBuilder(description: String, color: Color, attachment: Attachment? = null): EmbedBuilder {
    val builder = EmbedBuilder()
    builder.description = description
    builder.color = color
    builder.timestamp = Instant.now()
//    if (attachment != null) {
//        builder.image = attachment.url
//    }
    builder.footer {
        text = "Stock VIP - Firm Handshakes"
        icon = "https://i.ibb.co/xYTTZd1/Stock-VIP-Logo.gif"
    }

    return builder
}

fun callCommands(configuration: Configuration) = commands("Call Commands") {
    val mentionRole = "<@&798344963384672358>"


    // ENTRY
    guildCommand("scalpentry") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelScalpLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a scalp channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.GREEN)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Entry scalp sent!")
        }
    }

    guildCommand("swingentry") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelSwingLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a swing channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.GREEN)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Entry swing sent!")
        }
    }





    // UPDATE
    guildCommand("scalpupdate") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelScalpLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a scalp channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.ORANGE)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Update scalp sent!")
        }
    }

    guildCommand("swingupdate") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelSwingLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a swing channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.ORANGE)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Update swing sent!")
        }
    }




    // EXIT
    guildCommand("scalpexit") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelScalpLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a scalp channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.RED)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Exit scalp sent!")
        }
    }

    guildCommand("swingexit") {
        requiredPermissionLevel = Permission.EVERYONE
        execute(EveryArg) {

            val guildConfiguration = configuration[guild.id.longValue] ?: return@execute
            val outputChannel = guildConfiguration.userChannelSwingLinks[author.id.longValue]

            if (outputChannel == null) {
                respond("${author.username} is not setup with a swing channel")
                return@execute
            }

            val responseChannel = discord.api.getChannelOf<TextChannel>(Snowflake(outputChannel)) ?: return@execute
            val builder = getBuilder(args.first, Color.RED)

            responseChannel.createMessage {
                content = mentionRole
                embed = builder
            }

            respond("Exit swing sent!")
        }
    }
}