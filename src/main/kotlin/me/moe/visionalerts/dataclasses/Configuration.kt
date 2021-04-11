package me.moe.visionalerts.dataclasses

import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.core.entity.Role
import com.gitlab.kordlib.core.entity.channel.Channel
import me.jakejmattson.discordkt.api.dsl.Data

data class Configuration(
        val ownerId: Long = 345541952500006912,
        val guildConfigurations: MutableMap<Long, GuildConfiguration> = mutableMapOf()
) : Data("config/config.json") {
    operator fun get(id: Long) = guildConfigurations[id]
    fun hasGuildConfig(guildId: Long) = guildConfigurations.containsKey(guildId)

    fun setup(guild: Guild, prefix: String, commandChannel: Channel, staffRole: Role) {
        if (guildConfigurations[guild.id.longValue] != null) return

        val newConfiguration = GuildConfiguration(
                guild.id.value,
                prefix,
                commandChannel.id.longValue,
                staffRole.id.longValue
        )
        guildConfigurations[guild.id.longValue] = newConfiguration
        save()
    }
}

data class GuildConfiguration(
        val id: String,
        var prefix: String,
        var commandChannel: Long,
        var staffRole: Long,
        val userChannelLinks: MutableMap<Long, Long> = mutableMapOf(),
)