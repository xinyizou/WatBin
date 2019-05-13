package com.example.watbin

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ItemAdapter (itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private var items: List<Item>

    init {
        this.items = itemList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater.inflate(R.layout.item_profile, parent, false))

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val item = items[position]

        holder.bind(item)

        holder.itemView.setOnClickListener { v ->
            val expanded = item.expanded
            item.expanded = (expanded.not())
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView
        private val description: TextView
        private val category: TextView
        private val subItems: View

        init {
            name = itemView.findViewById(R.id.item_name)
            description = itemView.findViewById(R.id.item_description)
            category = itemView.findViewById(R.id.item_category)
            subItems = itemView.findViewById(R.id.sub_item)
        }

        fun bind(item: Item) {
            val expanded = item.expanded
            subItems.visibility = if (expanded) View.VISIBLE else View.GONE

            name.setText(item.name)
            description.setText(item.description)
            category.setText(item.category.toString())

            if (item.category == Category.GARBAGE) {
                name.setBackgroundColor(Color.parseColor("#ff000000"))
                name.setTextColor(Color.parseColor("#ffffffff"))
            }

            else if (item.category == Category.ORGANIC) {
                name.setBackgroundColor(Color.parseColor("#98FB98"))
                name.setTextColor(Color.parseColor("#ff000000"))
            }

            else if (item.category == Category.PAPERS) {
                name.setBackgroundColor(Color.parseColor("#D3D3D3"))
                name.setTextColor(Color.parseColor("#ff000000"))
            }

            else if (item.category == Category.CONTAINERS) {
                name.setBackgroundColor(Color.parseColor("#B0E0E6"))
                name.setTextColor(Color.parseColor("#ff000000"))
            }

            else if (item.category == Category.OTHER) {
                name.setBackgroundColor(Color.parseColor("#FFFF66"))
                name.setTextColor(Color.parseColor("#ff000000"))
            }
        }
    }

    fun filterList(filteredList: ArrayList<Item>) {
        items = filteredList
        notifyDataSetChanged()
    }
}