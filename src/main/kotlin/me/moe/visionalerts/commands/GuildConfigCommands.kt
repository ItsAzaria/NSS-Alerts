package me.moe.visionalerts.commands

import me.jakejmattson.discordkt.api.arguments.ChannelArg
import me.moe.visionalerts.conversations.ConfigurationConversation
import me.moe.visionalerts.dataclasses.Configuration
import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.arguments.RoleArg
import me.jakejmattson.discordkt.api.arguments.UserArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.moe.visionalerts.extensions.requiredPermissionLevel
import me.moe.visionalerts.services.Permission

fun guildConfigCommands(configuration: Configuration) = commands("Configuration") {
    guildCommand("setup") {
        description = "Setup a guild to use this bot."
        requiredPermissionLevel = Permission.GUILD_OWNER
        execute {
            if (configuration.hasGuildConfig(guild.id.longValue)) {
                respond("Guild configuration exists. To modify it use the commands to set values.")
                return@execute
            }

            ConfigurationConversation(configuration)
                    .createConfigurationConversation(guild)
                    .startPublicly(discord, author, channel)

            respond("${guild.name} has been setup")
        }
    }

    guildCommand("setprefix") {
        description = "Set the bot prefix."
        requiredPermissionLevel = Permission.STAFF
        execute(EveryArg) {
            val prefix = args.first

            configuration[guild.id.longValue]?.prefix = prefix
            configuration.save()

            respond("Prefix set to: **$prefix**")
        }
    }

    guildCommand("setstaffrole") {
        description = "Set the bot staff role."
        requiredPermissionLevel = Permission.STAFF
        execute(RoleArg) {
            val role = args.first

            configuration[guild.id.longValue]?.staffRole = role.id.longValue
            configuration.save()

            respond("Role set to: **${role.name}**")
        }
    }

    guildCommand("setcommandchannel") {
        description = "Set the bot command channel."
        requiredPermissionLevel = Permission.STAFF
        execute(ChannelArg) {
            val channel = args.first

            configuration[guild.id.longValue]?.commandChannel = channel.id.longValue
            configuration.save()

            respond("Command channel set to: **${channel.name}**")
        }
    }

    guildCommand("linkscalp") {
        description = "Link a user to a channel."
        requiredPermissionLevel = Permission.STAFF
        execute(UserArg, ChannelArg) {
            val (user, channel) = args
            val config = configuration[guild.id.longValue] ?: return@execute

            if (config.userChannelScalpLinks.containsKey(user.id.longValue)) {
                respond("${user.username} is already linked to a scalp channel.")
                return@execute
            }

            config.userChannelScalpLinks[user.id.longValue] = channel.id.longValue
            configuration.save()

            respond("${user.username}'s scalps are now linked to ${channel.mention}")
        }
    }

    guildCommand("unlinkscalp") {
        description = "unlink a user from a channel."
        requiredPermissionLevel = Permission.STAFF
        execute(UserArg) {
            val user = args.first
            val config = configuration[guild.id.longValue] ?: return@execute

            if (!config.userChannelScalpLinks.containsKey(user.id.longValue)) {
                respond("${user.username} is not linked to any scalp channels.")
                return@execute
            }

            config.userChannelScalpLinks.remove(user.id.longValue)
            configuration.save()

            respond("${user.username} is now not linked to a scalp channel")
        }
    }

    guildCommand("linkswing") {
        description = "Link a user to a channel."
        requiredPermissionLevel = Permission.STAFF
        execute(UserArg, ChannelArg) {
            val (user, channel) = args
            val config = configuration[guild.id.longValue] ?: return@execute

            if (config.userChannelSwingLinks.containsKey(user.id.longValue)) {
                respond("${user.username} is already linked to a swing channel.")
                return@execute
            }

            config.userChannelSwingLinks[user.id.longValue] = channel.id.longValue
            configuration.save()

            respond("${user.username}'s swings are now linked to ${channel.mention}")
        }
    }

    guildCommand("unlinkswing") {
        description = "unlink a user from a channel."
        requiredPermissionLevel = Permission.STAFF
        execute(UserArg) {
            val user = args.first
            val config = configuration[guild.id.longValue] ?: return@execute

            if (!config.userChannelSwingLinks.containsKey(user.id.longValue)) {
                respond("${user.username} is not linked to any swing channels.")
                return@execute
            }

            config.userChannelSwingLinks.remove(user.id.longValue)
            configuration.save()

            respond("${user.username} is now not linked to a swing channel")
        }
    }
}