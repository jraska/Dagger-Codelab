package com.jraska.dagger.codelab.core.analytics.lib

/**
 * Lets pretend this is a library class from Firebase/Google Analytics/Mixpanel etc. and we don't own this implementation
 */
class AnalyticsLibrary {
  internal val inMemoryData: MutableList<LibraryeEventEntry> = mutableListOf()

  fun reportEvent(key: String, properties: Map<String, Any>) {
    // Communication with external system etc. Now only adds to memory to demonstrate instance management
    inMemoryData.add(LibraryeEventEntry(key, properties))
  }

  internal data class LibraryeEventEntry(
    val key: String,
    val properties: Map<String, Any>
  )
}
