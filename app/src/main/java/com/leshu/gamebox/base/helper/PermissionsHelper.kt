package com.jiutong.base.helper

import android.app.Activity
import android.app.AlertDialog

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.jiutong.base.utils.UIUtil
import com.leshu.gamebox.R
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*

/**
 * @author: xiangyun_liu
 * @date: 2018/5/31 10:26
 *
 * 危险权限申请
 *
 *
 *
 *
 * Android6.0及以上危险权限
 * 权限组       权限
 * CALENDAR     READ_CALENDAR
 * -------------WRITE_CALENDAR
 * CAMERA       CAMERA
 * CONTACTS     READ_CONTACTS
 * -------------WRITE_CONTACTS
 * -------------GET_ACCOUNTS
 * LOCATION     ACCESS_FINE_LOCATION
 * -------------ACCESS_COARSE_LOCATION
 * MICROPHONE   RECORD_AUDIO
 * PHONE        READ_PHONE_STATE
 * -------------CALL_PHONE
 * -------------READ_CALL_LOG
 * -------------WRITE_CALL_LOG
 * -------------ADD_VOICEMAIL
 * -------------USE_SIP
 * -------------PROCESS_OUTGOING_CALLS
 * SENSORS      BODY_SENSORS
 * SMS          SEND_SMS
 * -------------RECEIVE_SMS
 * -------------READ_SMS
 * -------------RECEIVE_WAP_PUSH
 * -------------RECEIVE_MMS
 * STORAGE      READ_EXTERNAL_STORAGE
 * -------------WRITE_EXTERNAL_STORAGE
 */

class PermissionsHelper(private val mActivity: Activity) {
    private val mRxPermissions: RxPermissions = RxPermissions(mActivity)

    /**
     * 危险权限
     */
    private var mRiskPermissions: HashMap<String, String> = HashMap()

    /**
     * app请求权限
     */
    private var mRequestPermissions: Array<out String>? = null
    private var mRequestCount: Int = 0

    /**
     * 被勾选拒绝不在提示的权限
     */
    private val mTips = mutableListOf<String>()

    /**
     * 是否显示提示设置对话框
     */
    private var isShowDialog: Boolean = false

    /**
     * 去设置授权提示对话框
     */
    private var mSettingDialog: AlertDialog? = null

    /**
     * 对话框是否消失
     */
    val isDismiss: Boolean
        get() = mSettingDialog == null || !mSettingDialog!!.isShowing

    init {
        initRiskPermissions()
    }

    private fun initRiskPermissions() {
        mRiskPermissions[android.Manifest.permission.READ_CALENDAR] =
            UIUtil.getString(R.string.calendar)
        mRiskPermissions[android.Manifest.permission.WRITE_CALENDAR] =
            UIUtil.getString(R.string.calendar)
        mRiskPermissions[android.Manifest.permission.CAMERA] = UIUtil.getString(R.string.camera)
        mRiskPermissions[android.Manifest.permission.READ_CONTACTS] =
            UIUtil.getString(R.string.contacts)
        mRiskPermissions[android.Manifest.permission.WRITE_CONTACTS] =
            UIUtil.getString(R.string.contacts)
        mRiskPermissions[android.Manifest.permission.GET_ACCOUNTS] =
            UIUtil.getString(R.string.contacts)
        mRiskPermissions[android.Manifest.permission.ACCESS_FINE_LOCATION] =
            UIUtil.getString(R.string.location)
        mRiskPermissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] =
            UIUtil.getString(R.string.location)
        mRiskPermissions[android.Manifest.permission.RECORD_AUDIO] =
            UIUtil.getString(R.string.record_audio)
        mRiskPermissions[android.Manifest.permission.READ_PHONE_STATE] =
            UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.CALL_PHONE] = UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.READ_CALL_LOG] =
            UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.WRITE_CALL_LOG] =
            UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.ADD_VOICEMAIL] =
            UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.USE_SIP] = UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.PROCESS_OUTGOING_CALLS] =
            UIUtil.getString(R.string.phone)
        mRiskPermissions[android.Manifest.permission.BODY_SENSORS] =
            UIUtil.getString(R.string.body_sensors)
        mRiskPermissions[android.Manifest.permission.SEND_SMS] = UIUtil.getString(R.string.sms)
        mRiskPermissions[android.Manifest.permission.RECEIVE_SMS] = UIUtil.getString(R.string.sms)
        mRiskPermissions[android.Manifest.permission.READ_SMS] = UIUtil.getString(R.string.sms)
        mRiskPermissions[android.Manifest.permission.RECEIVE_WAP_PUSH] =
            UIUtil.getString(R.string.sms)
        mRiskPermissions[android.Manifest.permission.RECEIVE_MMS] = UIUtil.getString(R.string.sms)
        mRiskPermissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] =
            UIUtil.getString(R.string.storage)
        mRiskPermissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] =
            UIUtil.getString(R.string.storage)
    }

    /**
     * 授权对话框
     */
    private fun showPermissionTipsDialog(
        activity: Activity,
        message: String,
        isCanCancel: Boolean
    ) {
        if (mSettingDialog == null || !mSettingDialog!!.isShowing) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(R.string.request_permission)
            builder.setMessage(message)
            builder.setPositiveButton(R.string.setting) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:" + activity.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                activity.startActivity(intent)
            }
            if (isCanCancel) {
                builder.setNegativeButton(R.string.cancel, null)
            }
            builder.setCancelable(false)
            mSettingDialog = builder.create()
            mSettingDialog!!.setCanceledOnTouchOutside(false)
            mSettingDialog!!.show()
        }
    }

    /**
     * 请求权限
     *
     * @param allAgreeRun  权限全部同意调用
     * @param onlyRefuseShowTipsDialog 是否只要拒绝权限就显示去设置对话框，无需勾选不在提示
     * @param isCanCancel 显示去设置对话框能否取消
     * @param permission 单个权限
     */
    fun request(
        allAgreeRun: (() -> Unit)?,
        onlyRefuseShowTipsDialog: Boolean = false,
        isCanCancel: Boolean = true,
        permission: String
    ) {
        request(allAgreeRun, null, true, onlyRefuseShowTipsDialog, isCanCancel, permission)
    }

    /**
     * 请求权限
     *
     * @param allAgreeRun  权限全部同意调用
     * @param onlyRefuseShowTipsDialog 是否只要拒绝权限就显示去设置对话框，无需勾选不在提示
     * @param isCanCancel 显示去设置对话框能否取消
     * @param permissions 权限数组
     */
    fun request(
        allAgreeRun: (() -> Unit)?,
        onlyRefuseShowTipsDialog: Boolean = false,
        isCanCancel: Boolean = true,
        vararg permissions: String
    ) {
        request(allAgreeRun, null, true, onlyRefuseShowTipsDialog, isCanCancel, *permissions)
    }

    /**
     * 请求权限
     *
     * @param allAgreeRun      权限全部同意调用
     * @param noAllAgreeRun    权限非全部同意调用
     * @param isShowTipsDialog 是否显示去设置对话框
     * @param onlyRefuseShowTipsDialog 是否只要拒绝权限就显示去设置对话框，无需勾选不在提示
     * @param isCanCancel 显示去设置对话框能否取消
     * @param permissions      权限
     */
    fun request(
        allAgreeRun: (() -> Unit)?,
        noAllAgreeRun: (() -> Unit)?,
        isShowTipsDialog: Boolean,
        onlyRefuseShowTipsDialog: Boolean,
        isCanCancel: Boolean,
        vararg permissions: String
    ) {
        if (permissions.isNotEmpty()) {
            mRequestPermissions = permissions
            mRequestCount = 0
            mTips.clear()
            isShowDialog = false
            mRxPermissions.requestEach(*permissions).subscribe { permission ->
                mRequestCount++

                grantedHandle(permission)

                //被拒绝的权限
                if (!permission.granted) {
                    mRiskPermissions.keys
                        .filter { permission.name == it && !mTips.contains(mRiskPermissions[it]) }
                        .forEach { key -> mRiskPermissions[key]?.let { mTips.add(it) } }
                }
                //只要拒绝就显示去设置对话框，无需勾选不在提示
                if (onlyRefuseShowTipsDialog && !permission.granted) {
                    isShowDialog = true

                    //拒绝的同时勾选不在提示才显示去设置对话框
                } else if (!onlyRefuseShowTipsDialog && !permission.granted && !permission.shouldShowRequestPermissionRationale) {
                    isShowDialog = true
                }
                if (mRequestCount == mRequestPermissions!!.size) {
                    if (mTips.isEmpty()) {
                        allAgreeRun?.invoke()
                    } else {
                        noAllAgreeRun?.invoke()
                        if (isShowTipsDialog && isShowDialog) {
                            var keys = ""
                            for (i in mTips.indices) {
                                keys += mTips[i] + if (i == mTips.size - 1) "" else "、"
                            }
                            val message = mActivity.getString(
                                R.string.permission_tips,
                                mActivity.getString(R.string.app_name),
                                keys,
                                mActivity.getString(R.string.app_name)
                            )
                            showPermissionTipsDialog(mActivity, message, isCanCancel)
                        }
                    }
                }
            }
        }
    }


    /**
     * 全局的同意某一权限后的处理操作
     */
    private fun grantedHandle(permission: Permission) {
        if (permission.granted) {
            //存储权限
            if (permission.name == android.Manifest.permission.READ_EXTERNAL_STORAGE || permission.name == android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {

            }
        }
    }

    /**
     * 集合中的对象清理避免内存泄露
     */
    fun finshPermissions() {
        mRiskPermissions?.clear()
        mTips?.clear()
    }
}
