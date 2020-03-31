package com.jraska.dagger.codelab.config.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jraska.dagger.codelab.config.MutableConfig
import com.jraska.dagger.codelab.config.di.ConfigComponent
import com.jraska.dagger.codelab.core.di.HasAppComponent
import javax.inject.Inject

class ConfigActivity : AppCompatActivity() {

  @Inject
  lateinit var mutableConfig: MutableConfig

  override fun onCreate(savedInstanceState: Bundle?) {
    ((application as HasAppComponent).appComponent() as ConfigComponent).inject(this)

    super.onCreate(savedInstanceState)

    setupView()
  }

  private fun setupView() {
    val recyclerView = RecyclerView(this)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = ConfigAdapter(mutableConfig)
    setContentView(recyclerView)
  }

  companion object {
    fun start(activity: Activity) {
      val intent = Intent(activity, ConfigActivity::class.java)
      activity.startActivity(intent)
    }
  }

  class ConfigAdapter(val config: MutableConfig) : RecyclerView.Adapter<SimpleHolder>() {
    private val configKeys = config.keys().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
      return SimpleHolder(Switch(parent.context))
    }

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
      val switch = holder.itemView as Switch

      val key = configKeys[position]
      switch.text = key

      val enabled = config.getBoolean(key)
      switch.isChecked = enabled
      switch.setOnCheckedChangeListener { _, isChecked -> config.set(key, isChecked) }
    }

    override fun getItemCount() = configKeys.size
  }

  class SimpleHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
