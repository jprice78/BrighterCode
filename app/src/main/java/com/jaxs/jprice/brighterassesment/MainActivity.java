package com.jaxs.jprice.brighterassesment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaxs.jprice.brighterassesment.adapters.BrighterAdapterJson;
import com.jaxs.jprice.brighterassesment.model.Product;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private BrighterAdapterJson adapter;

    ArrayList<Product> mProduct = new ArrayList<Product>();
    public ProgressDialog mDialog;
    Double mCheck;
    CoordinatorLayout coordinatorLayout;


    public static final String BURL = "https://s3.us-east-2.amazonaws.com/brighters/brproducts.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        SharedPreferences sharedPreferences = getSharedPreferences("CheckData" ,Context.MODE_PRIVATE);
        Boolean hasEntered = sharedPreferences.getBoolean("Check",false);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        /* prepareAlbums();


        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        try {
            Glide.with(this).load(R.drawable.falcons).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        getData();


    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    private void getData() {


        mDialog = new ProgressDialog(MainActivity.this);

        mDialog.setMessage("Fetching BrighterAssessment Data");

        //mDialog.setProgressStyle(R.style.ProgressBar);

        mDialog.setCancelable(false);

        mDialog.show();


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BURL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {



            }

            @Override
            public void onResponse(Response response) throws IOException {


                String jsonData = response.body().string();

                if (response.isSuccessful()) {


                    try {
                        mProduct = getProduct(jsonData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            adapter = new BrighterAdapterJson(MainActivity.this, mProduct);

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            mDialog.dismiss();
                        }
                    });


                }Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No Internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                getData();
                            }
                        });

            }
        });


    }

    private ArrayList<Product> getProduct(String jsonData) throws JSONException {

        JSONObject abqdata = new JSONObject(jsonData);

        JSONArray data = abqdata.getJSONArray("PRODUCTS");

        for (int i = 0; i < data.length(); i++) {


            JSONObject jsonAbq = data.getJSONObject(i).getJSONObject("attributes");

            Product products = new Product();

            products.setmName(jsonAbq.getString("NAME"));

            products.setDesc(jsonAbq.getString("description"));

            products.setmImage(jsonAbq.getString("JPG_URL"));



            //abqArt.setmLat(jsonAbq.getDouble("Y"));
            //bqArt.setmLong(jsonAbq.getDouble("X"));
            mProduct.add(products);

        }


        return mProduct;
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;


    }

}

