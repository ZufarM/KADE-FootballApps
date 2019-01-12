package com.zufar.footballapps.model

import com.google.gson.annotations.SerializedName

class Team (
    @SerializedName("idTeam")
    var idTeam: String? = "",

    @SerializedName("strTeam")
    var strTeam: String? = "",

    @SerializedName("strTeamBadge")
    var strTeamBadge: String? = "",

    @SerializedName("intFormedYear")
    var intFormedYear: String? = "",

    @SerializedName("strStadium")
    var strStadium: String? = "",

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = ""
)