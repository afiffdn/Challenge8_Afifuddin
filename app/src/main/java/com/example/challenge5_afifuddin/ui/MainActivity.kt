package com.example.challenge5_afifuddin.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challenge5_afifuddin.R
import com.example.challenge5_afifuddin.adapter.NowMovieShowingAdapter
import com.example.challenge5_afifuddin.adapter.TopRatedAdapter
import com.example.challenge5_afifuddin.databinding.ActivityMainBinding
import com.example.challenge5_afifuddin.datastore.DatastoreManager
import com.example.challenge5_afifuddin.model.Detail
import com.example.challenge5_afifuddin.room.Database
import com.example.challenge5_afifuddin.model_movies_now_showing.Result
import com.example.challenge5_afifuddin.service.Status
import com.example.challenge5_afifuddin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    lateinit var datastore: DatastoreManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getAllMovieNowShowing()
        viewModel.getTopRated()

        viewModel.getUserPref()
        viewModel.userPref.observe(this){
            if (it != null){
                binding.tvWelcome.text = getString(R.string.welcome_username, it.username)
            }
        }

        fetchDataAllNowPlaying()
        fetchDataTopRated()
        binding.ivUser.setOnClickListener {
           startActivity(Intent(this, UserActivity::class.java))
        }

    }

    private fun fetchDataTopRated() {
        viewModel.datatop.observe(this){
            val adapter = TopRatedAdapter(object  :TopRatedAdapter.OnClickListener{
                override fun onClickItem(data: com.example.challenge5_afifuddin.model_movies_top_rated.Result) {
                    val detail = Detail(
                        data.title,
                        data.overview,
                        data.releaseDate,
                        data.posterPath
                    )
                    val toDetail = Intent(this@MainActivity, DetailActivity::class.java).apply {
                        putExtra("data",detail)
                    }
                    startActivity(toDetail)
                }
            })
            it.data?.let { it1 -> adapter.submitData(it1.results) }
            binding.rvMovieTopRated.adapter = adapter
        }

    }

    private fun fetchDataAllNowPlaying() {
        viewModel.data.observe(this){ resource ->
        when(resource.status){
            Status.SUCCESS ->{
                val adapter = NowMovieShowingAdapter(object :NowMovieShowingAdapter.onClickListener{
                    override fun onClickItem(data: Result) {
                        val detail = Detail(
                            data.title,
                            data.overview,
                            data.releaseDate,
                            data.posterPath
                        )
                        val toDetail = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra("data",detail)
                        }
                        startActivity(toDetail)
                    }
                    }
                )
                adapter.submitData(resource.data?.results)
                binding.rvMovie.adapter = adapter
            }
            else -> {}
        }
        }
    }

}