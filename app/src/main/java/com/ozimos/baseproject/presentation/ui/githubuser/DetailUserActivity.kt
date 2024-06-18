package com.ozimos.baseproject.presentation.ui.githubuser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import coil.load
import com.ozimos.baseproject.R
import com.ozimos.baseproject.databinding.ActivityDetailUserBinding
import com.ozimos.baseproject.domain.UserDomain
import com.ozimos.baseproject.presentation.viewmodel.DetailUserViewModel
import com.ozimos.baseproject.util.Constants
import com.ozimos.baseproject.util.LogUtil
import com.ozimos.baseproject.util.StateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailUserBinding.inflate(layoutInflater) }
    private val viewModel: DetailUserViewModel by viewModels()
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
    }


    private fun setObserver() {
        viewModel.user.observe(this) {
            when (it) {
                is StateUtil.Loading -> setLoading(true)
                is StateUtil.Error -> {
                    setLoading(false)
                }

                is StateUtil.Success -> {
                    setLoading(false)
                    LogUtil.e(it.data.toString())
                    setData(it.data)
                }
            }
        }
    }

    private fun setData(data: UserDomain?) {
        binding.run {
            ivBackground.load(data?.avatarUrl)
            ivAvatar.load(data?.avatarUrl)

            tvName.text = data?.name
            tvUsername.text = data?.login
            tvPublicRepos.text = data?.publicRepos.toString()

            btnOpenLink.setOnClickListener {
                openWebView(data?.htmlUrl ?: "")
            }
        }
    }

    private fun openWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        // Verify that there are applications registered to handle this intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.run {
            progressBar.isVisible = isLoading
        }
    }

    private fun getData() {
        val bundle = intent.extras
        val username = bundle?.getString(Constants.KEY_GITHUB_USERNAME) ?: ""
        viewModel.getDetail(username)
    }
}