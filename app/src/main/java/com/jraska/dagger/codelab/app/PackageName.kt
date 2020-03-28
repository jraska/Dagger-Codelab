package com.jraska.dagger.codelab.app

import android.content.Context
import javax.inject.Inject

class PackageName @Inject constructor(
  val context: Context
) {
  fun thisAppPackage(): String {
    return context.applicationInfo.packageName
  }
}
