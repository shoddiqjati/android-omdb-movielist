package com.jati.dev.movielist.utils

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.jati.dev.movielist.R
import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * Created by jati on 06/05/18
 */

class ProgressDialog(context: Context) : AlertDialog(context) {

    private var mMessage: String? = null
    private var mCancelable = true

    constructor(context: Context, message: String, cancelable: Boolean) : this(context) {
        this.mMessage = message
        this.mCancelable = cancelable
    }

    constructor(context: Context, message: String) : this(context) {
        this.mMessage = message
    }

    class Builder {
        private var bContext: Context? = null
        private var bMessage: String? = null
        private var bCancelable = true

        fun setContext(context: Context): Builder {
            bContext = context
            return this
        }

        fun setMessage(message: String): Builder {
            bMessage = message
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            bCancelable = cancelable
            return this
        }

        fun build(): ProgressDialog {
            return ProgressDialog(bContext!!,
                    bMessage ?: "", bCancelable)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMessage()
    }

    override fun setMessage(message: CharSequence?) {
        super.setMessage(message)
        this.mMessage = message.toString()
        initMessage()
    }

    private fun initMessage() {
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        tv_message.text = mMessage ?: ""
    }
}