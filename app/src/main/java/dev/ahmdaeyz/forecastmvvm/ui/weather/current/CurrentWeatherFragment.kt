package dev.ahmdaeyz.forecastmvvm.ui.weather.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.ahmdaeyz.forecastmvvm.databinding.CurrentWeatherFragmentBinding
import dev.ahmdaeyz.forecastmvvm.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<CurrentWeatherViewModelFactory>()
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var binding: CurrentWeatherFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentWeatherFragmentBinding.inflate(layoutInflater,container,false)
        viewModel = ViewModelProvider(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        bindUI()
        return binding.root
    }

    private fun bindUI() {
        launch {
            val currentWeather = viewModel.weatherDef.await()
            val weatherLocation = viewModel.weatherLocation.await()
            currentWeather.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                binding.currentWeather = it
                binding.loadingGroup.visibility = View.GONE
            })
            weatherLocation.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                updateLocation(it.cityName)
                updateDateToToday()
            })
        }
    }
    private fun updateLocation( location: String){
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as AppCompatActivity).supportActionBar?.subtitle= "Today"
    }

}
