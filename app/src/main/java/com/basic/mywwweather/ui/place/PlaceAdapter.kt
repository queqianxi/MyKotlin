package com.basic.mywwweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basic.mywwweather.R
import com.basic.mywwweather.logic.model.Place

/**
 * @author: Ww
 * @date: 2021/10/28
 */
class PlaceAdapter(private val placeList: List<Place>)
    : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var onItemClickListener : OnItemClickListener? = null

    /**kotlin中所有的内部类默认为静态的，这样很好的减少了内存泄漏问题。如果需要在内部类引用外部类的对象，
     * 可以使用inner声明内部类，使内部类变为非静态的，通过this@外部类名，指向外部类。*/
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val placeName : TextView = view.findViewById(R.id.tvPlaceName)
        val placeAddress : TextView = view.findViewById(R.id.tvPlaceAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.place_item, parent, false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

    fun setOnItemClickListener(onClickListener: OnItemClickListener){
        this.onItemClickListener = onClickListener
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }
}