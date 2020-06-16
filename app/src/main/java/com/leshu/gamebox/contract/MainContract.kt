package com.leshu.gamebox.contract

import android.content.Context
import com.leshu.gamebox.base.componet.IBaseView
import com.leshu.gamebox.entity.GameListEntity
import com.leshu.gamebox.entity.UserDeviceEntity

class MainContract {
    interface IMainView : IBaseView {

        fun onGetGameListSuccess(gameListEntity: GameListEntity)

        fun onDeviceLoginSuccess(userDeviceEntity: UserDeviceEntity)
    }

    interface IMainPresenter {

        fun getGameList()
    }

    interface IDeviceLogin {
        fun deviceLogin(context: Context)
    }
}