package com.example.ydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.ydemo.databinding.ActivityIndexBinding;

public class IndexActivity extends AppCompatActivity {

    private ActivityIndexBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sampleText.setText(Utils.getEncryptKey());
    }
}
