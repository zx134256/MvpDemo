package com.leshu.gamebox.entity

import com.leshu.gamebox.base.baseEntity.BaseResult

class GameListEntity : BaseResult() {
    var hotRank: List<GameIndexEntity>? = null


    override fun toString(): String {
        return "GameListEntity(hotRank=$hotRank)"
    }


}