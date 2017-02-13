package brianmccabe.coffeenow.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brianmccabe.coffeenow.R;
import brianmccabe.coffeenow.adapters.CartItemAdapter;
import brianmccabe.coffeenow.data.CoffeeNowContentProvider;
import brianmccabe.coffeenow.data.DatabaseHandler;
import brianmccabe.coffeenow.models.Coffee;

public class ShoppingCart extends AppCompatActivity {
    TextInputLayout inputLayoutEmail;
    TextInputEditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView cartList;


        Button orderButton;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.email_field);
        inputEmail = (TextInputEditText) findViewById(R.id.et_email);
        orderButton = (Button) findViewById(R.id.order_button);

        inputEmail.addTextChangedListener(new OrderWatcher(inputEmail));

        cartList = (ListView) findViewById(R.id.cart_list);
        final DatabaseHandler db = new DatabaseHandler(this);
        List<Coffee> coffeeList =new ArrayList<>();
        String URL = CoffeeNowContentProvider.URL_CART;

        Uri coffee = Uri.parse(URL);
        Cursor c = getContentResolver().query(coffee, null, null, null, getString(R.string.content_provider_name));

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    byte[] bytes;
                    String name = c.getString(c.getColumnIndex(CoffeeNowContentProvider.name));

                    bytes = c.getBlob(c.getColumnIndex(CoffeeNowContentProvider.icon));

                    String price = c.getString(c.getColumnIndex(CoffeeNowContentProvider.price));

                    Coffee coffee1 = new Coffee(name, bytes, price);
                    coffeeList.add(coffee1);
                } while (c.moveToNext());
            }

            c.close();
        }



        cartList.setAdapter(new CartItemAdapter(this, coffeeList));
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        Toast.makeText(this, R.string.Successfull_order, Toast.LENGTH_LONG).show();
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.invalid_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class OrderWatcher implements TextWatcher {

        private View view;

        private OrderWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_email:
                    validateEmail();
                    break;
            }
        }
    }

}
