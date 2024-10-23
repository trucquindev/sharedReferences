package com.example.sharedpreferencesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextMsv;
    Button buttonHello;
    TextView textShow;
    Button buttonReset;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextName = findViewById(R.id.edit_name);
        editTextMsv = findViewById(R.id.edit_masv);
        buttonHello = findViewById(R.id.buttonHello);
        textShow = findViewById(R.id.textShow);
        buttonReset = findViewById(R.id.button_clear_data);
        sharedPreferences = getSharedPreferences("dataShared", Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("name", "");
        String savedMsv = sharedPreferences.getString("msv", "");
        if (!savedName.isEmpty() || !savedMsv.isEmpty()) {
            textShow.setText("Xin chào " + savedName + " " + savedMsv);
        }
        editTextName.setText(savedName);
        editTextMsv.setText(savedMsv);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("name", editTextName.getText().toString());
//        editor.putString("msv",editTextMsv.getText().toString());
//        editor.apply();
        buttonHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ EditText
                String name = editTextName.getText().toString();
                String msv = editTextMsv.getText().toString();

                // Lưu dữ liệu vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("msv", msv);
                editor.apply();  // Lưu không đồng bộ, an toàn khi làm việc trên UI thread

                // Cập nhật TextView với giá trị mới
                textShow.setText("Xin chào " + name + " " + msv);
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa dữ liệu trong SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Xóa tất cả dữ liệu
                editor.apply();  // Xác nhận thay đổi

                // Xóa nội dung TextView và EditText
                textShow.setText("");
                editTextName.setText("");
                editTextMsv.setText("");

                // Hiển thị thông báo đã reset thành công
                Toast.makeText(MainActivity.this, "Dữ liệu đã được reset", Toast.LENGTH_SHORT).show();
            }
        });

    }
}