package com.ssaapp.wishlist
import android.content.ContentResolver
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(private val item: List<Item>,
                      private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    //override to get the onCreate method for a View Holder, which is what a RecyclerView needs to display
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemInfo = inflater.inflate(R.layout.item, parent, false)
        // Return a new holder instance
        return ViewHolder(itemInfo)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position in the list
        val i = item.get(position)
        //TODO: Set item views based on views and data model
        holder.nameTextView.text = i.name
        holder.priceTextView.text = "\$" + i.price.toString()
        holder.urlTextView.text = i.url
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {

        /* TODO: Create member variables for any view that will be set as you render a row. */
        var  nameTextView: TextView
        var  priceTextView: TextView
        var  urlTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.nameTextView)
            priceTextView = itemView.findViewById(R.id.priceTextView)
            urlTextView = itemView.findViewById(R.id.urlTextView)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
        override fun onLongClick(v: View?): Boolean {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLongItemClick(position)
            }
            return true
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }
}