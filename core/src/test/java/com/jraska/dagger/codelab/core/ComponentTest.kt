package com.jraska.dagger.codelab.core

import org.junit.Test

class ComponentTest {
  @Test
  fun createComponent() {
    val fileSystemRepository = DaggerFileSystemComponent.create().fileSystemRepository()
    fileSystemRepository.listDirectories().forEach {
      println(it)
    }
  }
}
