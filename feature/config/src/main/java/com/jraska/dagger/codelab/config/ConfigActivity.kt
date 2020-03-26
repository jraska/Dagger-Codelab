package com.jraska.dagger.codelab.config

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jraska.dagger.codelab.config.di.ConfigComponent
import com.jraska.dagger.codelab.core.di.HasAppComponent
import javax.inject.Inject

class ConfigActivity : AppCompatActivity() {

  @Inject
  lateinit var inMemoryConfig: InMemoryConfig

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    ((application as HasAppComponent).appComponent() as ConfigComponent).inject(this)

    setupView()
  }

  private fun setupView() {
    val recyclerView = RecyclerView(this)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ConfigAdapter(inMemoryConfig)
    setContentView(recyclerView)
  }

  companion object {
    fun start(activity: Activity) {
      val intent = Intent(activity, ConfigActivity::class.java)
      activity.startActivity(intent)
    }
  }

  class ConfigAdapter(val inMemoryConfig: InMemoryConfig) : RecyclerView.Adapter<SimpleHolder>() {
    val configKeys = inMemoryConfig.keys().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
      return SimpleHolder(Switch(parent.context))
    }

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
      val switch = holder.itemView as Switch

      val key = configKeys[position]
      switch.text = key

      val enabled = inMemoryConfig.getBoolean(key)
      switch.isChecked = enabled
      switch.setOnCheckedChangeListener { _, isChecked -> inMemoryConfig.set(key, isChecked) }
    }

    override fun getItemCount() = configKeys.size
  }

  class SimpleHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
