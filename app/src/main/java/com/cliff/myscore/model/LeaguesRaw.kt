package com.cliff.myscore.model

data class LeaguesRaw(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Leagues>,
    val results: Int
) {
    data class Paging(
        val current: Int,
        val total: Int
    )

    data class Parameters(
        val code: String,
        val current: String
    )
}

data class Leagues(
    val country: Country,
    val league: League,
    val seasons: List<Season>
) {
    data class Country(
        val code: String,
        val flag: String,
        val name: String
    )

    data class League(
        val id: Int,
        val logo: String,
        val name: String,
        val type: String
    )

    data class Season(
        val coverage: Coverage,
        val current: Boolean,
        val end: String,
        val start: String,
        val year: Int
    ) {
        data class Coverage(
            val fixtures: Fixtures,
            val odds: Boolean,
            val players: Boolean,
            val predictions: Boolean,
            val standings: Boolean,
            val top_scorers: Boolean
        ) {
            data class Fixtures(
                val events: Boolean,
                val lineups: Boolean,
                val statistics_fixtures: Boolean,
                val statistics_players: Boolean
            )
        }
    }
}