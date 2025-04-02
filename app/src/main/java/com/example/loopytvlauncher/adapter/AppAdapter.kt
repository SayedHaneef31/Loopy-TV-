package com.example.loopytvlauncher.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loopytvlauncher.R
import com.example.loopytvlauncher.data.AppInfo

class AppAdapter(
    private val apps: List<AppInfo>,
    private val onAppClick: (AppInfo) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_app, parent, false
        )
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.bind(app)
        holder.itemView.setOnClickListener { onAppClick(app) }
    }

    override fun getItemCount(): Int = apps.size

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appIcon: ImageView = itemView.findViewById(R.id.app_icon)
        private val appName: TextView = itemView.findViewById(R.id.app_name)

        fun bind(app: AppInfo) {
            appIcon.setImageDrawable(app.icon)
            appName.text = app.appName
        }
    }
}