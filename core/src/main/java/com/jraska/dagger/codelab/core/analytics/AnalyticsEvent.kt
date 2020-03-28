package com.jraska.dagger.codelab.core.analytics

import java.util.Collections

data class AnalyticsEvent(
  val key: String,
  val properties: Map<String, Any>
) {
  class Builder internal constructor(private val name: String) {
    private val properties = HashMap<String, Any>()

    private fun addAny(name: String, value: Any): Builder {
      properties[name] = value
      return this
    }

    fun addProperty(name: String, value: String) = addAny(name, value)

    fun addProperty(name: String, value: Double)= addAny(name, value)

    fun addProperty(name: String, value: Boolean)= addAny(name, value)

    fun addProperty(name: String, value: Int)= addAny(name, value)

    fun addProperty(name: String, value: Long)= addAny(name, value)

    fun build(): AnalyticsEvent {
      return AnalyticsEvent(name, Collections.unmodifiableMap(properties))
    }
  }

  companion object {
    fun create(key: String): AnalyticsEvent {
      return AnalyticsEvent(key, emptyMap())
    }

    fun builder(key: String): Builder {
      return Builder(key)
    }
  }
}
