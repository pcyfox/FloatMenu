package com.tk.lc.floatbutton;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tk.lib_floatmenu.FloatMenu;
import com.tk.lib_floatmenu.SpeedDialOverlayLayout;

public class MainActivity extends AppCompatActivity {
    FloatMenu dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SpeedDialOverlayLayout mask = findViewById(R.id.mask);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.openOrCloseMenu();
            }
        });

        dialog = new FloatMenu(this, 1, 500, new FloatMenu.IOnItemClicked() {
            @Override
            public void onBackItemClick(boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "资源已锁定", Toast.LENGTH_SHORT).show();
                    dialog.changeLogo(R.drawable.widget_float_button_lock);
                    dialog.setText("资源已锁定");
                } else {
                    dialog.setText("资源已解锁");
                    Toast.makeText(MainActivity.this, "资源已解锁", Toast.LENGTH_SHORT).show();
                    dialog.changeLogo(R.drawable.widget_float_button_unlock);
                }
                dialog.openOrCloseMenu();
            }

            @Override
            public void onClose() {
                mask.hide();
            }

            @Override
            public void onExpand() {
                mask.show();
            }
        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
