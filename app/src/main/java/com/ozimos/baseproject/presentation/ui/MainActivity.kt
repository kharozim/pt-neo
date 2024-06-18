package com.ozimos.baseproject.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ozimos.baseproject.R
import com.ozimos.baseproject.databinding.ActivityMainBinding
import com.ozimos.baseproject.presentation.ui.githubuser.GithubActivity
import com.ozimos.baseproject.presentation.ui.picsum.PhotoActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setView()

    }

    private fun setView() {
        binding.btnGithubUser.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    GithubActivity::class.java
                )
            )
        }
    }


}