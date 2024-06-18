package com.ozimos.baseproject.presentation.ui.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.ozimos.baseproject.R
import com.ozimos.baseproject.databinding.ActivityGithubBinding
import com.ozimos.baseproject.domain.UserDomain
import com.ozimos.baseproject.presentation.viewmodel.GithubViewModel
import com.ozimos.baseproject.util.Constants
import com.ozimos.baseproject.util.LogUtil
import com.ozimos.baseproject.util.SharedPreferenceUtil
import com.ozimos.baseproject.util.StateUtil
import com.ozimos.baseproject.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGithubBinding.inflate(layoutInflater) }
    private val viewModel: GithubViewModel by viewModels()
    private val adapter by lazy { GithubAdapter() }
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
            showToast(it.login)
            gotoDetail(it)
        }
        binding.run {
            rvUser.adapter = adapter
            edtSearch.doAfterTextChanged {
                if (it.isNullOrEmpty()) {
                    viewModel.searchUser("kharozim")
                } else {
                    viewModel.searchUser(it.toString())
                }
            }
        }
    }

    private fun gotoDetail(user: UserDomain) {
        val bundle = Bundle()
        bundle.putString(Constants.KEY_GITHUB_USERNAME, user.login)
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setObserver() {
        viewModel.users.observe(this) {
            when (it) {
                is StateUtil.Loading -> setLoading(true)
                is StateUtil.Error -> {
                    setLoading(false)
                    binding.tvLabel.text = it.message
                    binding.tvLabel.visibility = View.VISIBLE
                }

                is StateUtil.Success -> {
                    setLoading(false)
                    binding.tvLabel.visibility = View.GONE
                    LogUtil.e(it.data.toString())
                    setData(it.data)
                }
            }

        }
    }

    private fun setData(data: List<UserDomain>?) {
        adapter.submitList(data)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.run {
            progressBar.isVisible = isLoading
            tvLabel.isVisible = !isLoading
        }
    }

    private fun getData() {
        SharedPreferenceUtil.setString(this, Constants.KEY_BASE_URL, Constants.BASE_URL_GITHUB_USER)
        viewModel.searchUser("kharozim")
    }
}