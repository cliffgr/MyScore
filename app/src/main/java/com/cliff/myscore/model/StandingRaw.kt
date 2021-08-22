package com.cliff.myscore.model

data class StandingRaw(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
) {
    data class Paging(
        val current: Int,
        val total: Int
    )

    data class Parameters(
        val league: String,
        val season: String
    )

    data class Response(
        val league: League
    )

    data class League(
        val country: String,
        val flag: String,
        val id: Int,
        val logo: String,
        val name: String,
        val season: Int,
        val standings: List<List<Standing>>
    ) {
        data class Standing(
            val all: All,
            val away: Away,
            val description: String,
            val form: String,
            val goalsDiff: Int,
            val group: String,
            val home: Home,
            val points: Int,
            val rank: Int,
            val status: String,
            val team: Team,
            val update: String
        ) {
            data class All(
                val draw: Int,
                val goals: Goals,
                val lose: Int,
                val played: Int,
                val win: Int
            ) {
                data class Goals(
                    val against: Int,
                    val `for`: Int
                )
            }

            data class Away(
                val draw: Int,
                val goals: Goals,
                val lose: Int,
                val played: Int,
                val win: Int
            ) {
                data class Goals(
                    val against: Int,
                    val `for`: Int
                )
            }

            data class Home(
                val draw: Int,
                val goals: Goals,
                val lose: Int,
                val played: Int,
                val win: Int
            ) {
                data class Goals(
                    val against: Int,
                    val `for`: Int
                )
            }

            data class Team(
                val id: Int,
                val logo: String,
                val name: String
            )
        }
    }


}