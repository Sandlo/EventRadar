package com.example.eventradar.helpers

import android.content.Context
import com.example.eventradar.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object OutOfScopeDialog {
    fun show(context: Context) {
        MaterialAlertDialogBuilder(context).setTitle(R.string.scope)
            .setMessage(R.string.scope_summary)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }
}
