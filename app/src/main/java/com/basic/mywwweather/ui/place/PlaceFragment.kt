package com.basic.mywwweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.basic.mywwweather.MainActivity
import com.basic.mywwweather.R
import com.basic.mywwweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @author: Ww
 * @date: 2021/10/28
 */
class PlaceFragment : Fragment(){

    val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        rvPlace.layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(viewModel.placeList)
        adapter.setOnItemClickListener(object : PlaceAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val place = viewModel.placeList[position]
                viewModel.savePlace(place)
                if (activity is WeatherActivity){
                    (activity as WeatherActivity).apply {
                        drawerLayout.closeDrawers()
                        viewModel.locationLng = place.location.lng
                        viewModel.locationLat = place.location.lat
                        viewModel.placeName = place.name
                        refreshWeather()
                    }
                }else{
                    val intent = Intent(context, WeatherActivity::class.java).apply {
                        putExtra("location_lng", place.location.lng)
                        putExtra("location_lat", place.location.lat)
                        putExtra("place_name", place.name)
                    }
                    startActivity(intent)
                    activity?.finish()
                }
            }
        })
        rvPlace.adapter = adapter
        searchPlaceEdit.addTextChangedListener{editable ->
            val text = editable.toString()
            if (text.isNotEmpty()){
                viewModel.searchPlaces(text)
            }else{
                rvPlace.visibility = View.GONE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null){
                rvPlace.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity, "未查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}