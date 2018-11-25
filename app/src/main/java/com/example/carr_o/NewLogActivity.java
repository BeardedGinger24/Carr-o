package com.example.carr_o;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewLogActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_REPLY_DATE = "com.example.android.loglistsql.DATE";
    public static final String EXTRA_REPLY_LOCATION = "com.example.android.loglistsql.LOCATION";
    public static final String EXTRA_REPLY_MILEAGE = "com.example.android.loglistsql.MILEAGE";
    public static final String EXTRA_REPLY_PRICE = "com.example.android.loglistsql.PRICE";
    public static final String EXTRA_REPLY_NOTES = "com.example.android.loglistsql.NOTES";

    Button mSaveButton, mSelectDate;
    TextView mDisplayDate;
    String date = "";
    int mileage = 0;
    double price = 0.0;

    TextInputEditText mMaintenanceLocation, mCurrentMiles, mPriceOfMaintenance, mNotes;
    TextInputLayout MaintLoc, CurrentMiles, PriceOfMait, Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);

        mSaveButton = (Button) findViewById(R.id.button_save_log);
        mSelectDate = (Button) findViewById(R.id.select_date);
        mDisplayDate = (TextView) findViewById(R.id.showDate);

//        datePicker();
        defaultDate();
        mSelectDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });

        //Containers
        MaintLoc = (TextInputLayout) findViewById(R.id.mait_loc);
        CurrentMiles = (TextInputLayout) findViewById(R.id.current_miles);
        PriceOfMait = (TextInputLayout) findViewById(R.id.price_of_mait);
        Notes = (TextInputLayout) findViewById(R.id.notes);

        //Edit text
        mMaintenanceLocation = (TextInputEditText) findViewById(R.id.et_mait_loc);
        mCurrentMiles = (TextInputEditText) findViewById(R.id.et_current_miles);
        mPriceOfMaintenance = (TextInputEditText) findViewById(R.id.et_price_of_mait);
        mNotes = (TextInputEditText) findViewById(R.id.et_notes);

        mMaintenanceLocation.addTextChangedListener(new MyTextWatcher(mMaintenanceLocation));
        mCurrentMiles.addTextChangedListener(new MyTextWatcher(mCurrentMiles));
        mPriceOfMaintenance.addTextChangedListener(new MyTextWatcher(mPriceOfMaintenance));
        mNotes.addTextChangedListener(new MyTextWatcher(mNotes));

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submitForm()) {
                    Intent replyIntent = new Intent();
                    String date = mDisplayDate.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_DATE, date);

                    String location = mMaintenanceLocation.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_LOCATION, location);

                    replyIntent.putExtra(EXTRA_REPLY_MILEAGE, mileage);
                    replyIntent.putExtra(EXTRA_REPLY_PRICE, price);

                    String notes = mNotes.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_NOTES, notes);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
//        datePicker();
    }

    /**
     * Validating form
     */
    private boolean submitForm() {
//        defaultDate();
        if (!validateLocation()) {
            return false;
        }

        if (!validateMileage()) {
            return false;
        }

        if (!validatePrice()) {
            return false;
        }

//        if(!validateNotes()){
//            return;
//        }

//        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void defaultDate(){
        if(date.isEmpty() || date == "" || date == null){
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            Date today = new Date();
            date = formatter.format(today);
            Log.d("Current Date", "defaultDate: " + date);
            mDisplayDate.setText(date);
        }
    }

    private boolean validateLocation() {
        String address = mMaintenanceLocation.getText().toString().trim();

        if (address.isEmpty()) {
            MaintLoc.setError(getString(R.string.error_missing_information));
            requestFocus(mMaintenanceLocation);
            return false;
        } else {
            MaintLoc.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMileage() {
        String miles = mCurrentMiles.getText().toString().trim();

        if (miles.isEmpty() || miles == "") {
            CurrentMiles.setError(getString(R.string.error_missing_information));
            requestFocus(mCurrentMiles);
            return false;
        } else {
            CurrentMiles.setErrorEnabled(false);
            mileage = Integer.parseInt(miles);
        }

        return true;
    }

    private boolean validatePrice() {
        String sPrice = mPriceOfMaintenance.getText().toString().trim();

        if (sPrice.isEmpty()) {
            PriceOfMait.setError(getString(R.string.error_missing_information));
            requestFocus(mPriceOfMaintenance);
            return false;
        } else {
            PriceOfMait.setErrorEnabled(false);
            price = Double.parseDouble(sPrice);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     *
     * @param view
     */
    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void datePicker(){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * To set date on TextView
     *
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        ((TextView) findViewById(R.id.showDate))
                .setText(dateFormat.format(calendar.getTime()));
        date = dateFormat.format(calendar.getTime());
        Log.d("DATE", "setDate: " + date);
        ((TextView) findViewById(R.id.showDate)).setVisibility(View.VISIBLE);

    }

    /**
     * To receive a callback when the user sets the date.
     *
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    /**
     * Create a DatePickerFragment class that extends DialogFragment.
     * Define the onCreateDialog() method to return an instance of DatePickerDialog
     */
    public static class DatePickerFragment extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog date = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
            date.getDatePicker().setMaxDate(System.currentTimeMillis());
            return date;
        }

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.input_name:
//                    validateName();
//                    break;
//                case R.id.input_email:
//                    validateEmail();
//                    break;
//                case R.id.input_password:
//                    validatePassword();
//                    break;
//            }
        }
    }
}
