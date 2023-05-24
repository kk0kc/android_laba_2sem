package com.example.androidlab2.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlab2.presentation.fragment.MainFragment
import com.example.androidlab2.R
import com.example.androidlab2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment(), "MainFragment")
                .commit()
        }
    }
}
