package com.ozimos.baseproject.presentation.ui.picsum

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.ozimos.baseproject.R
import com.ozimos.baseproject.databinding.ActivityPhotoBinding
import com.ozimos.baseproject.domain.PicsumDomain
import com.ozimos.baseproject.presentation.viewmodel.MyViewModel
import com.ozimos.baseproject.util.Constants
import com.ozimos.baseproject.util.LogUtil
import com.ozimos.baseproject.util.SharedPreferenceUtil
import com.ozimos.baseproject.util.StateUtil
import com.ozimos.baseproject.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private val binding by lazy { ActivityPhotoBinding.inflate(layoutInflater) }
    private val adapter by lazy { PicsumAdapter() }

    private val listPicture = arrayListOf<PicsumDomain>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getData()
        setObserver()
        setView()
    }

    private fun setView() {
        adapter.onClick = {
            showToast("${it.id} + ${it.author}")
        }
        adapter.onLastItem = {
            viewModel.loadMorePicture()
        }
        binding.rvPicture.adapter = adapter
    }

    private fun getData() {
        setBaseUrl()
        listPicture.clear()
        viewModel.initPicture()
    }

    private fun setBaseUrl() {
        SharedPreferenceUtil.setString(this, Constants.KEY_BASE_URL, Constants.BASE_URL_PICSUM)
    }

    private fun setObserver() {
        viewModel.photos.observe(this) { state ->
            when (state) {
                is StateUtil.Loading -> setLoading(true)
                is StateUtil.Error -> {
                    setLoading(false)
                    binding.tvLabel.text = state.message
                    binding.tvLabel.visibility = View.VISIBLE
                }

                is StateUtil.Success -> {
                    setLoading(false)
                    binding.tvLabel.visibility = View.GONE
                    LogUtil.e(state.data.toString())
                    setData(state.data)
                }
            }
        }
    }

    private fun setData(data: List<PicsumDomain>?) {
        listPicture.addAll(data ?: emptyList())
        adapter.submitList(listPicture)
    }


    private fun setLoading(isLoading: Boolean) {
        binding.run {
            progressBar.isVisible = isLoading
            tvLabel.isVisible = !isLoading
        }
    }
}