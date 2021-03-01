package com.cliff.myscore.model

data class FixturesRaw(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<FixtureLiveScore>,
    val results: Int
) {
    data class Paging(
        val current: Int,
        val total: Int
    )

    data class Parameters(
        val live: String
    )


}

data class FixtureLiveScore(
    val events: List<Event>,
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val score: Score,
    val teams: Teams
) {
    data class Event(
        val assist: Assist,
        val comments: Any,
        val detail: String,
        val player: Player,
        val team: Team,
        val time: Time,
        val type: String
    ) {
        data class Assist(
            val id: Any,
            val name: Any
        )

        data class Player(
            val id: Int,
            val name: String
        )

        data class Team(
            val id: Int,
            val logo: String,
            val name: String
        )

        data class Time(
            val elapsed: Int,
            val extra: Any
        )
    }

    data class Fixture(
        val date: String,
        val id: Int,
        val periods: Periods,
        val referee: Any,
        val status: Status,
        val timestamp: Int,
        val timezone: String,
        val venue: Venue
    ) {
        data class Periods(
            val first: Int,
            val second: Any
        )

        data class Status(
            val elapsed: Int,
            val long: String,
            val short: String
        )

        data class Venue(
            val city: String,
            val id: Int,
            val name: String
        )
    }

    data class Goals(
        val away: Int,
        val home: Int
    )

    data class League(
        val country: String,
        val flag: String,
        val id: Int,
        val logo: String,
        val name: String,
        val round: String,
        val season: Int
    )

    data class Score(
        val extratime: Extratime,
        val fulltime: Fulltime,
        val halftime: Halftime,
        val penalty: Penalty
    ) {
        data class Extratime(
            val away: Any,
            val home: Any
        )

        data class Fulltime(
            val away: Any,
            val home: Any
        )

        data class Halftime(
            val away: Int,
            val home: Int
        )

        data class Penalty(
            val away: Any,
            val home: Any
        )
    }

    data class Teams(
        val away: Away,
        val home: Home
    ) {
        data class Away(
            val id: Int,
            val logo: String,
            val name: String,
            val winner: Boolean
        )

        data class Home(
            val id: Int,
            val logo: String,
            val name: String,
            val winner: Boolean
        )
    }
}