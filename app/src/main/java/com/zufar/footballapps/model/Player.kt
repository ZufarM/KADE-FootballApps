package com.zufar.footballapps.model

import com.google.gson.annotations.SerializedName

class Player (
    @SerializedName("idPlayer")
    var idPlayer: String? = "",

    @SerializedName("strPlayer")
    var strPlayer: String? = "",

    @SerializedName("strPosition")
    var strPosition: String? = "",

    // img Icon
    @SerializedName("strCutout")
    var strCutout: String? = "",

    // img Banner
    @SerializedName("strFanart1")
    var strFanart1: String? = "",

    @SerializedName("strHeight")
    var strHeight: String? = "",

    @SerializedName("strWeight")
    var strWeight: String? = "",

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = ""
)