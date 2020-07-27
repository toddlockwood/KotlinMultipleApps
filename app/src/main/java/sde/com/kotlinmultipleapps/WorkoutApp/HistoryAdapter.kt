package sde.com.kotlinmultipleapps.WorkoutApp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*
import sde.com.kotlinmultipleapps.R

class HistoryAdapter(val context: Context, val items : ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val llHistoryViewItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPosition = view.tvPosition


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row, parent,false))
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvPosition.text = (position+1).toString()
        holder.tvItem.text = item
        if (position % 2 == 0) {
            holder.llHistoryViewItem.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.llHistoryViewItem.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }
}