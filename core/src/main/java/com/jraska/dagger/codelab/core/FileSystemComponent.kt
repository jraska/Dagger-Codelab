package com.jraska.dagger.codelab.core

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FileSystemModule::class])
interface FileSystemComponent {
  fun fileSystemRepository(): FileSystemRepository
}
