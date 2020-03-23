package com.jraska.dagger.codelab.app

import android.content.Context
import javax.inject.Inject

class StringProvider @Inject constructor(
  val context: Context
) {
  fun string(id: Int): String {
    return context.getString(id)
  }
}
