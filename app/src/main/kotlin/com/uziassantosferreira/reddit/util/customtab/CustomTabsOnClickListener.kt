package com.uziassantosferreira.reddit.util.customtab

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.uziassantosferreira.reddit.R
import com.uziassantosferreira.reddit.util.CustomClickURLSpan
import java.lang.ref.WeakReference

class CustomTabsOnClickListener(hostActivity: Activity,
    customTabActivityHelper: CustomTabActivityHelper
) : CustomClickURLSpan.OnClickListener {
    private val mActivityWeakReference: WeakReference<Activity> = WeakReference(hostActivity)
    private val mCustomTabActivityHelperWeakReference: WeakReference<CustomTabActivityHelper> = WeakReference(customTabActivityHelper)

    override fun onClick(view: View, url: String) {
        val activity = mActivityWeakReference.get()
        val customTabActivityHelper = mCustomTabActivityHelperWeakReference.get()
        if (activity != null && customTabActivityHelper != null) {
            val customTabsIntent = getDefaultCustomTabsIntentBuilder(view.context).build()
            customTabsIntent.intent.setPackage(
                CustomTabsHelper.getPackageNameToUse(view.context))
            customTabsIntent.launchUrl(activity, Uri.parse(url))
        }
    }


    private fun getDefaultCustomTabsIntentBuilder(context: Context): CustomTabsIntent.Builder {
        return CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setShowTitle(true)
    }
}