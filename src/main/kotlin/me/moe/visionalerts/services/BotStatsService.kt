package me.moe.visionalerts.services

import me.moe.visionalerts.dataclasses.Configuration
import me.moe.visionalerts.util.timeToString
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import java.util.*

@Service
class BotStatsService(private val discord: Discord) {
    private var startTime: Date = Date()

    val uptime: String
        get() = timeToString(Date().time - startTime.time)

    val ping: String
        get() = "${discord.api.gateway.averagePing}"
}