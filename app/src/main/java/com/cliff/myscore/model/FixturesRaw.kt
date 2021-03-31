package com.cliff.myscore.model

data class FixturesRaw(
    val errors: List<Any>,
    val paging: Paging,
    val response: List<FixtureLiveScore>,
    val results: Int
) {
    data class Paging(
        val current: Int,
        val total: Int
    )

}

data class FixtureLiveScore(
    val events: List<Event>,
    val fixture: Fixture,
    val goals: Goals,
    val league: League,
    val lineups: List<Lineup>,
    val players: List<Player>,
    val score: Score,
    val statistics: List<Statistic>,
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
        val referee: String,
        val status: Status,
        val timestamp: Int,
        val timezone: String,
        val venue: Venue
    ) {
        data class Periods(
            val first: Int,
            val second: Int
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

    data class Lineup(
        val coach: Coach,
        val formation: String,
        val startXI: List<StartXI>,
        val substitutes: List<Substitute>,
        val team: Team
    ) {
        data class Coach(
            val id: Int,
            val name: String
        )

        data class StartXI(
            val player: Player
        ) {
            data class Player(
                val id: Int,
                val name: String,
                val number: Int,
                val pos: String
            )
        }

        data class Substitute(
            val player: Player
        ) {
            data class Player(
                val id: Int,
                val name: String,
                val number: Int,
                val pos: String
            )
        }

        data class Team(
            val id: Int,
            val logo: String,
            val name: String
        )
    }

    data class Player(
        val players: List<PlayerList>,
        val team: Team
    ) {
        data class PlayerList(
            val player: Player,
            val statistics: List<Statistic>
        ) {
            data class Player(
                val id: Int,
                val name: String,
                val photo: String
            )

            data class Statistic(
                val cards: Cards,
                val dribbles: Dribbles,
                val duels: Duels,
                val fouls: Fouls,
                val games: Games,
                val goals: Goals,
                val offsides: Any,
                val passes: Passes,
                val penalty: Penalty,
                val shots: Shots,
                val tackles: Tackles
            ) {
                data class Cards(
                    val red: Int,
                    val yellow: Int
                )

                data class Dribbles(
                    val attempts: Any,
                    val past: Any,
                    val success: Any
                )

                data class Duels(
                    val total: Any,
                    val won: Any
                )

                data class Fouls(
                    val committed: Any,
                    val drawn: Any
                )

                data class Games(
                    val captain: Boolean,
                    val minutes: Int,
                    val number: Int,
                    val position: String,
                    val rating: String,
                    val substitute: Boolean
                )

                data class Goals(
                    val assists: Any,
                    val conceded: Int,
                    val saves: Int,
                    val total: Any
                )

                data class Passes(
                    val accuracy: String,
                    val key: Any,
                    val total: Int
                )

                data class Penalty(
                    val commited: Any,
                    val missed: Int,
                    val saved: Int,
                    val scored: Int,
                    val won: Any
                )

                data class Shots(
                    val on: Any,
                    val total: Any
                )

                data class Tackles(
                    val blocks: Any,
                    val interceptions: Any,
                    val total: Any
                )
            }
        }

        data class Team(
            val id: Int,
            val logo: String,
            val name: String,
            val update: String
        )
    }

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
            val away: Int,
            val home: Int
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

    data class Statistic(
        val statistics: List<StatisticsList>,
        val team: Team
    ) {
        data class StatisticsList(
            val type: String,
            val value: Any
        )

        data class Team(
            val id: Int,
            val logo: String,
            val name: String
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
