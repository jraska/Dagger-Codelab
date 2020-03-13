package com.jraska.dagger.codelab.core

import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileSystemRepository @Inject constructor(
  val root: File,
  val directoryFilter: DirectoryFilter
) {
  fun listDirectories(): List<File> {
    return root.listFiles(directoryFilter)?.asList() ?: emptyList()
  }
}
