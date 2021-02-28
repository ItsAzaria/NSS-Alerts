package me.moe.visionalerts

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.common.entity.Status
import com.gitlab.kordlib.gateway.Intent
import com.gitlab.kordlib.gateway.Intents
import com.gitlab.kordlib.gateway.PrivilegedIntent
import me.moe.visionalerts.dataclasses.Configuration
import me.moe.visionalerts.services.BotStatsService
import me.moe.visionalerts.services.PermissionsService
import me.jakejmattson.discordkt.api.dsl.bot
import me.moe.visionalerts.extensions.requiredPermissionLevel
import java.awt.Color

@PrivilegedIntent
suspend fun main() {
    val token = System.getenv("BOT_TOKEN") ?: null
    val prefix = System.getenv("DEFAULT_PREFIX") ?: "<none>"

    require(token != null) { "Expected the bot token as an environment variable" }

    bot(token) {
        prefix {
            val configuration = discord.getInjectionObjects(Configuration::class)

            guild?.let { configuration[it.id.longValue]?.prefix } ?: prefix
        }

        configure {
            allowMentionPrefix = true
            commandReaction = null
            theme = Color.MAGENTA
        }

        mentionEmbed {
            val configuration = it.discord.getInjectionObjects(Configuration::class)
            val botStats = it.discord.getInjectionObjects(BotStatsService::class)
            val guildConfiguration = configuration[it.guild!!.id.longValue]

            color = it.discord.configuration.theme

            thumbnail {
                url = it.discord.api.getSelf().avatar.url
            }

            field {
                name = "Prefix"
                value = it.prefix()
                inline = true
            }

            field {
                name = "Ping"
                value = botStats.ping
                inline = true
            }

            if (guildConfiguration != null) {
                val staffRole = it.guild!!.getRole(Snowflake(guildConfiguration.staffRole))
                val commandChannel = it.guild!!.getChannel(Snowflake(guildConfiguration.commandChannel))
                field {

                    name = "Configuration"
                    value = "```" +
                            "Staff Role: ${staffRole.name}\n" +
                            "Command Channel: ${commandChannel.name}" +
                            "```"
                }
            }

            field {
                val versions = it.discord.versions

                name = "Bot Info"
                value = "```" +
                        "Version: 1.0.0\n" +
                        "DiscordKt: ${versions.library}\n" +
                        "Kord: ${versions.kord}\n" +
                        "Kotlin: ${versions.kotlin}" +
                        "```"
            }

            field {
                name = "Uptime"
                value = botStats.uptime
            }
        }

        permissions {
            val requiredPermissionLevel = command.requiredPermissionLevel
            val guild = guild ?: return@permissions false
            val member = user.asMember(guild.id)

            val permissionsService = discord.getInjectionObjects(PermissionsService::class)
            return@permissions permissionsService.hasClearance(member, requiredPermissionLevel)
        }

        intents {
            Intents.nonPrivileged.intents.forEach {
                +it
            }

            +Intent.GuildMembers
        }

        presence {
            status = Status.Invisible
        }
    }
}