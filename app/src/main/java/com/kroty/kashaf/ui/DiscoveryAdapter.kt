package com.kroty.kashaf.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kroty.kashaf.data.Discovery
import com.kroty.kashaf.R

/**
 * محول القوائم (RecyclerView) لعناصر الاكتشافات.
 */
class DiscoveryAdapter(
    private val onClick: (Discovery) -> Unit
) : ListAdapter<Discovery, DiscoveryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.itemTitle)
        val category: TextView = view.findViewById(R.id.itemCategory)
        val summary: TextView = view.findViewById(R.id.itemSummary)
        val date: TextView = view.findViewById(R.id.itemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_discovery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.category.text = item.category
        holder.summary.text = item.description.ifEmpty {
            holder.itemView.context.getString(R.string.no_description)
        }
        holder.date.text = item.date
        holder.itemView.setOnClickListener { onClick(item) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Discovery>() {
            override fun areItemsTheSame(oldItem: Discovery, newItem: Discovery) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Discovery, newItem: Discovery) =
                oldItem == newItem
        }
    }
}
