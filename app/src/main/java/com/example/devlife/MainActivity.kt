package com.example.devlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.devlife.presentation.error.ErrorFragment
import com.example.devlife.presentation.post.PostFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToPost()
        }
    }

    override fun navigateToError() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ErrorFragment.newInstance())
            .commit()
    }

    override fun navigateToPost() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostFragment.newInstance())
            .commit()
    }
}