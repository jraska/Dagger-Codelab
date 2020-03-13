package com.jraska.dagger.codelab.core

import java.io.File
import java.io.FileFilter
import javax.inject.Inject

class DirectoryFilter @Inject constructor() : FileFilter {
  override fun accept(file: File): Boolean {
    return file.isDirectory
  }
}
