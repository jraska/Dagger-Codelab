package com.jraska.dagger.codelab.app

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageName @Inject constructor(
  @ApplicationContext val context: Context
) {
  fun thisAppPackage(): String {
    return context.applicationInfo.packageName
  }
}
