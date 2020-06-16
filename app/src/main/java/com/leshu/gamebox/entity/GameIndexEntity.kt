package com.leshu.gamebox.entity

data class GameIndexEntity(
    var devUrl: String = "",
    var gold: Int = 0,
    var playTime: Int = 0,
    var rewardTag: Int = 0,
    var id: Int = 0,
    var type: Int = 0,
    var status: Int = 0,
    var userCount: Int = 0,
    var name: String = "",
    var icon: String = "",
    var icon2: String = "",
    var releaseUrl: String = "",
    var isOfen: Boolean = false,
    var subType: Int = 0,
    var activityId: Int = 0,
    var cash: Int = 0,
    var thirdParty: Int = 0,
    var appGameId: Int = 0,
    var gameId: String = ""
) {
}