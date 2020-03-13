package com.jraska.dagger.codelab.core

import dagger.Module
import dagger.Provides
import java.io.File

@Module
object FileSystemModule {

  @Provides
  fun rootDirectory(): File = File.listRoots()[0]
}
