package com.jaxs.jprice.brighterassesment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.nameofproducts)TextView pName;
    @BindView(R.id.Txtname)TextView txtname;
    @BindView(R.id.TxtDesc)TextView txtDesc;
    @BindView(R.id.thumbnail_image_header)ImageView image;
    EditText nameEditText,urlEditText,descEditText;
    Button saveBtn;

    public String mName;
    public String mDesc;
    public String mThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mName = getIntent().getExtras().getString("name");
        mDesc = getIntent().getExtras().getString("Desc");
        mThumbnail = getIntent().getExtras().getString("thumbnail");
        pName.setText(mName);
        txtDesc.setText(mDesc);
        Picasso.with(DetailActivity.this).load(mThumbnail).into(image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailActivity.this,Uploadinfo.class);
                startActivity(intent);


            }
        });
    }



}
