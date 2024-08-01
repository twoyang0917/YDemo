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
//        binding.sampleText.setText(Utils.getEncryptKey());
//        binding.sampleText.setText(Utils.encrypt1("ydemo"));
//        binding.sampleText.setText(Utils.encrypt2("ydemo".getBytes()));
//        binding.sampleText.setText(Utils.encrypt3("ydemo"));
//        binding.sampleText.setText(Utils.getName());

        binding.sampleText.setText(Dynamic.encrypt1("ydemo"));
//        binding.sampleText.setText(Dynamic.encrypt2("ydemo".getBytes()));
    }
}
