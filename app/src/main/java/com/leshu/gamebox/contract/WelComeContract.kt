package com.leshu.gamebox.contract

import com.leshu.gamebox.base.componet.BasePresenter
import com.leshu.gamebox.base.componet.IBaseView
import com.leshu.gamebox.entity.AliUpdateEntity

class WelComeContract{
    interface IWelcomeView :IBaseView{
        fun onRequestAliUpdateInfoSuccess(aliUpdateEntity: AliUpdateEntity)
    }

    interface IWelcomePresenter{
        fun requestAliUpdateInfo()
    }
}
