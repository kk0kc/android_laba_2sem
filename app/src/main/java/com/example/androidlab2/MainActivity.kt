package com.example.androidlab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlab2.databinding.ActivityMainBinding

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
