package com.jaxs.jprice.brighterassesment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaxs.jprice.brighterassesment.model.Product;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmResults;

public class Uploadinfo extends AppCompatActivity {

    private EditText txtRName,txtRDesc,txtRPrice,txtSalePrice;
    private Button btnAdd,btnView,btnDelete,btnUpdate;
    private TextView txtView;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadinfo);
         realm = Realm.getDefaultInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtRName = (EditText) findViewById(R.id.txtRName);
        txtRDesc = (EditText) findViewById(R.id.TxtRDesc);
        txtRPrice = (EditText) findViewById(R.id.txtRegularPrice);
        txtSalePrice = (EditText) findViewById(R.id.txtRSalePrice);
        btnAdd = (Button) findViewById(R.id.btnCreate);
        btnDelete = (Button) findViewById(R.id.btnDel);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnView = (Button) findViewById(R.id.btnView);
        txtView = (TextView) findViewById(R.id.txtView);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_database(txtRName.getText().toString().trim());

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_to_database(txtRName.getText().toString().trim(),txtRDesc.getText().toString().trim(),txtRPrice.getText().toString().trim(),txtSalePrice.getText().toString().trim());

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String name = txtRName.getText().toString();

                delete_from_database(name);

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                referesh_database();


            }
        });

    }

    private void update_database(final String names) {


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Product product = bgRealm.where(Product.class).equalTo("mName", names).findFirst();
                product.setDesc(txtRDesc.getText().toString().trim());
                product.setSalePrice(txtSalePrice.getText().toString().trim());
                product.setRegularPrice(txtRPrice.getText().toString().trim());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(),"Record Updated",Toast.LENGTH_LONG).show();

            }
        });


    }

    private void delete_from_database(String name) {
        final RealmResults<Product> products =  realm.where(Product.class).equalTo("mName",name).findAll();
        realm.executeTransaction(new Transaction() {
            @Override
            public void execute(Realm realm) {
                products.deleteFromRealm(0);
                Toast.makeText(getApplicationContext(),"Record Deleted", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void referesh_database() {

        RealmResults<Product> result = realm.where(Product.class).findAllAsync();
        result.load();

        String output = "";

        for(Product product:result){


           output += product.toString();

        }

            txtView.setText(output);
    }

    private void save_to_database(final String name, final String desc, final String rprice, final String sprice) {

        realm.executeTransactionAsync(new Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Product RpRODUCT = bgRealm.createObject(Product.class);
                RpRODUCT.setmName(name);
                RpRODUCT.setDesc(desc);
                RpRODUCT.setRegularPrice(rprice);
                RpRODUCT.setSalePrice(sprice);
            }
        }, new Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.

                Toast.makeText(getApplicationContext(),"RecordAdded", Toast.LENGTH_LONG).show();

            }
        }, new Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.

                Toast.makeText(getApplicationContext(),"Record not added" + error.getMessage() ,Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();

    }
}
