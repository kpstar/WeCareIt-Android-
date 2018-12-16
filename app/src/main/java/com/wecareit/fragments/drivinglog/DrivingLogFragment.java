package com.wecareit.fragments.drivinglog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Client;
import com.wecareit.model.CompanyCarPost;
import com.wecareit.model.PrivateCarPost;
import com.wecareit.model.Vehicle;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrivingLogFragment extends TemplateFragment {

    private CompanyCarPost companyCarPost;
    private PrivateCarPost privateCarPost;
    private TextView tvTab_companycar, tvTab_travelbill, tvFirstCar, tvSecondCar, tvThirdCar;
    private TextView tvBeach_companycar, tvLib_companycar, tvShop_companycar, tv12_companycar;
    private TextView tvBeach_travelbill, tvLib_travelbill, tvShop_travelbill, tv12_travelbill;
    private TextView tvError, tvKM;
    private LinearLayout lnCompanycar, lnTravelbill, lnBottom_companycar, lnBottom_travelbill, lnTab;
    private LinearLayout lnButton_firstcar, lnButton_secondCar, lnButton_thirdcar, lnMain;
    private MultiSpinner sp_Client_Companycar, sp_Client_Travelbill;
    private EditText etMeasureStart_companycar, etMeasureDest_companycar, etAddressStart_companycar, etAddressDest_companycar;
    private EditText etRegister_travelbill, etDate_travelbill, etAddressStart_travelbill, etAddressTravel_travelbill, etDrivingKilo_travelbill;
    private DatePickerDialog mDatePickerDialog;
    private ArrayList<Integer> client;
    private int categoryID_companycar, vehicle_ID_companycar, categoryID_travelbill, vehicle_ID_travelbill;
    private int flag_tab = 0;
    private String address_start, address_end, odometer_start, odometer_end, km, regno, date, client_string;

    private Calendar myCalendar = Calendar.getInstance();

    public static DrivingLogFragment createInstance() {
        return new DrivingLogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        //clients_list = new String[100];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_drivinglog, container, false);

    }

    public void onItemsSelected(boolean[] selected){
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("KÖRJOURNAL");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etMeasureStart_companycar = view.findViewById(R.id.etMeasureStart_companycar);
        etMeasureDest_companycar = view.findViewById(R.id.etMeasureDest_companycar);
        etAddressStart_companycar = view.findViewById(R.id.etAddressTravel_companycar);
        etAddressDest_companycar = view.findViewById(R.id.etAddressDest_companycar);
        etRegister_travelbill = view.findViewById(R.id.etRegister_travelbill);
        etDate_travelbill = view.findViewById(R.id.etDate_travelbill);
        etAddressStart_travelbill = view.findViewById(R.id.etAddressStart_travelbill);
        etAddressTravel_travelbill = view.findViewById(R.id.etAddressTravel_travelbill);
        etDrivingKilo_travelbill = view.findViewById(R.id.etDrivingKilo_travelbill);

        tvError = view.findViewById(R.id.tvTravelEndError_drivinglog);
        tvKM = view.findViewById(R.id.tvTravelEnd_drivinglog);
        tvTab_companycar = view.findViewById(R.id.tvCompanycar_drivinglogfragment);
        tvTab_travelbill = view.findViewById(R.id.tvTravelbill_drivinglogfragment);
        lnCompanycar = view.findViewById(R.id.lnCompanycar_drivingfragment);
        lnTravelbill = view.findViewById(R.id.lnTravelbill_drivingfragment);
        lnBottom_companycar = view.findViewById(R.id.lnBottomline_companycar);
        lnBottom_travelbill = view.findViewById(R.id.lnBottomline_travelbill);
        lnButton_firstcar = view.findViewById(R.id.lnFirstcar_drivingfragment);
        lnButton_secondCar = view.findViewById(R.id.lnSecondcar_drivingfragment);
        lnButton_thirdcar = view.findViewById(R.id.lnThirdcar_drivingfragment);
        tvBeach_companycar = view.findViewById(R.id.tvBeach_companycar);
        tvLib_companycar = view.findViewById(R.id.tvLib_companycar);
        tvShop_companycar = view.findViewById(R.id.tvShop_companycar);
        tv12_companycar = view.findViewById(R.id.tv12_companycar);
        tvBeach_travelbill = view.findViewById(R.id.tvBeach_travelbill);
        tvLib_travelbill = view.findViewById(R.id.tvLib_travelbill);
        tvShop_travelbill = view.findViewById(R.id.tvShop_travelbill);
        tv12_travelbill = view.findViewById(R.id.tv12_travelbill);
        tvFirstCar = view.findViewById(R.id.tvFirstcar);
        tvSecondCar = view.findViewById(R.id.tvSecondcar);
        tvThirdCar = view.findViewById(R.id.tvThirdcar);
        lnMain = view.findViewById(R.id.lnMain_drivinglog);
        lnTab = view.findViewById(R.id.lnTab_drivingfragment);

        GradientDrawable gd_tab = new GradientDrawable();
        gd_tab.setShape(GradientDrawable.RECTANGLE);
        gd_tab.setStroke(2,Color.LTGRAY);
        gd_tab.setCornerRadius(3.0f);

        lnTab.setBackground(gd_tab);

        GradientDrawable gd_main = new GradientDrawable();
        gd_main.setShape(GradientDrawable.RECTANGLE);
        gd_main.setStroke(2,Color.LTGRAY);
        gd_main.setCornerRadius(5.0f);
        lnMain.setBackground(gd_main);

        GradientDrawable gd_edit = new GradientDrawable();
        gd_edit.setShape(GradientDrawable.RECTANGLE);
        gd_edit.setStroke(2,Color.LTGRAY);
        gd_edit.setCornerRadius(3.0f);

        etMeasureStart_companycar.setBackground(gd_edit);
        etAddressDest_companycar.setBackground(gd_edit);
        etAddressStart_companycar.setBackground(gd_edit);
        etMeasureDest_companycar.setBackground(gd_edit);
        etAddressStart_travelbill.setBackground(gd_edit);
        etAddressTravel_travelbill.setBackground(gd_edit);
        etDate_travelbill.setBackground(gd_edit);
        etDrivingKilo_travelbill.setBackground(gd_edit);
        etRegister_travelbill.setBackground(gd_edit);

        etDate_travelbill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DrivingLogFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvTab_companycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_tab = 1;
                lnCompanycar.setVisibility(View.VISIBLE);
                lnBottom_companycar.setVisibility(View.VISIBLE);
                tvTab_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                lnTravelbill.setVisibility(View.GONE);
                lnBottom_travelbill.setVisibility(View.INVISIBLE);
                tvTab_travelbill.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
        });
        tvTab_travelbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_tab = 0;
                lnCompanycar.setVisibility(View.GONE);
                lnBottom_companycar.setVisibility(View.INVISIBLE);
                tvTab_companycar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                lnTravelbill.setVisibility(View.VISIBLE);
                lnBottom_travelbill.setVisibility(View.VISIBLE);
                tvTab_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
            }
        });
        lnButton_firstcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_ID_companycar = 1;
                lnButton_firstcar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                lnButton_secondCar.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                lnButton_thirdcar.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                tvFirstCar.setTextColor(getResources().getColor(R.color.colorWhite));
                tvSecondCar.setTextColor(getResources().getColor(R.color.colorText));
                tvThirdCar.setTextColor(getResources().getColor(R.color.colorText));
                loadData();
            }
        });
        lnButton_secondCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_ID_companycar = 2;
                lnButton_firstcar.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                lnButton_secondCar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                lnButton_thirdcar.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                tvFirstCar.setTextColor(getResources().getColor(R.color.colorText));
                tvSecondCar.setTextColor(getResources().getColor(R.color.colorWhite));
                tvThirdCar.setTextColor(getResources().getColor(R.color.colorText));
                loadData();
            }
        });
        lnButton_thirdcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicle_ID_companycar = 4;
                lnButton_firstcar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                lnButton_secondCar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                lnButton_thirdcar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvFirstCar.setTextColor(getResources().getColor(R.color.colorText));
                tvSecondCar.setTextColor(getResources().getColor(R.color.colorText));
                tvThirdCar.setTextColor(getResources().getColor(R.color.colorWhite));
                loadData();
            }
        });
        tvBeach_companycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_companycar = 1;
                tvBeach_companycar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvLib_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_companycar.setTextColor(getResources().getColor(R.color.colorWhite));
                tvLib_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tv12_companycar.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tvLib_companycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_companycar = 2;
                tvBeach_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_companycar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvShop_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_companycar.setTextColor(getResources().getColor(R.color.colorWhite));
                tvShop_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tv12_companycar.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tvShop_companycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_companycar = 3;
                tvBeach_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_companycar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tv12_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_companycar.setTextColor(getResources().getColor(R.color.colorWhite));
                tv12_companycar.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tv12_companycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_companycar = 4;
                tvBeach_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_companycar.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_companycar.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvBeach_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_companycar.setTextColor(getResources().getColor(R.color.colorText));
                tv12_companycar.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });
        tvBeach_travelbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_travelbill = 1;
                tvBeach_travelbill.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvLib_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_travelbill.setTextColor(getResources().getColor(R.color.colorWhite));
                tvLib_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tv12_travelbill.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tvLib_travelbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_travelbill = 2;
                tvBeach_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_travelbill.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvShop_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_travelbill.setTextColor(getResources().getColor(R.color.colorWhite));
                tvShop_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tv12_travelbill.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tvShop_travelbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_travelbill = 3;
                tvBeach_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_travelbill.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tv12_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvBeach_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_travelbill.setTextColor(getResources().getColor(R.color.colorWhite));
                tv12_travelbill.setTextColor(getResources().getColor(R.color.colorText));
            }
        });
        tv12_travelbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryID_travelbill = 4;
                tvBeach_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvLib_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tvShop_travelbill.setBackgroundColor(getResources().getColor(R.color.colorCardView));
                tv12_travelbill.setBackgroundColor(getResources().getColor(R.color.colorGreenText));
                tvBeach_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvLib_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tvShop_travelbill.setTextColor(getResources().getColor(R.color.colorText));
                tv12_travelbill.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        });

        GradientDrawable gd_spinner = new GradientDrawable();
        gd_spinner.setShape(GradientDrawable.RECTANGLE);
        gd_spinner.setStroke(2,Color.LTGRAY);
        gd_spinner.setCornerRadius(3.0f);

        sp_Client_Companycar = view.findViewById(R.id.spUsers_companycar);
        sp_Client_Companycar.setItems(Global.clientslist, getString(R.string.all));
        sp_Client_Companycar.setBackground(gd_spinner);

        sp_Client_Travelbill = view.findViewById(R.id.spUsers_travelbill);
        sp_Client_Travelbill.setItems(Global.clientslist, getString(R.string.all));
        sp_Client_Travelbill.setBackground(gd_spinner);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_drivinglog, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        TextView tv_Submit = (TextView)view.findViewById(R.id.menu_item_drivinglog_submit);
        tv_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new ArrayList<Integer>();
                ArrayList<Client> client_list = new ArrayList<Client>();
                if(flag_tab == 0){
                    address_start = etAddressStart_companycar.getText().toString();
                    address_end = etAddressDest_companycar.getText().toString();
                    odometer_start = etMeasureStart_companycar.getText().toString();
                    odometer_end = etMeasureDest_companycar.getText().toString();
                    client_string = sp_Client_Companycar.getSelectedItem().toString();
                    String[] splited_client = client_string.split(", ");
                    if (splited_client[0].equals("Alla")){
                        client.add(1);
                        client.add(2);
                        client.add(3);
                        client.add(4);
                    } else {
                        client_list = Global.clients;
                        for (Client client1: client_list){
                            for(int i=0;i<splited_client.length;i++){
                                if(client1.getName().equals(splited_client[i])){
                                    client.add(client1.getId());
                                }
                            }
                        }
                    }

                    Double start_km = Double.parseDouble(etMeasureStart_companycar.getText().toString());
                    Double end_km = Double.parseDouble(etMeasureDest_companycar.getText().toString());
                    if(start_km>=end_km){
                        tvKM.setTextColor(getResources().getColor(R.color.colorPinknote));
                        tvError.setVisibility(View.VISIBLE);
                    } else {
                        tvKM.setTextColor(Color.GRAY);
                        tvError.setVisibility(View.GONE);
                        postLog();
                    }
                } else {
                    address_start = etAddressStart_travelbill.getText().toString();
                    address_end = etAddressTravel_travelbill.getText().toString();
                    date = etDate_travelbill.getText().toString();
                    km = etDrivingKilo_travelbill.getText().toString();
                    regno = etRegister_travelbill.getText().toString();
                    client_string = sp_Client_Travelbill.getSelectedItem().toString();
                    String[] splited_client = client_string.split(", ");
                    if (splited_client[0].equals("Alla")){
                        client.add(1);
                        client.add(2);
                        client.add(3);
                        client.add(4);
                    } else {
                        client_list = Global.clients;
                        for (Client client1: client_list){
                            for(int i=0;i<splited_client.length;i++){
                                if(client1.getName().equals(splited_client[i])){
                                    client.add(client1.getId());
                                }
                            }
                        }
                    }
                    postLog();
                }

            }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate_travelbill.setText(sdf.format(myCalendar.getTime()));
    }

    public void postLog() {
        if(flag_tab == 0){

            companyCarPost =new CompanyCarPost(address_end,address_start,client,categoryID_companycar,odometer_start,odometer_end,vehicle_ID_companycar);

            Call<CompanyCarPost> call = Global.getAPIService.postCompanyCar("Token " + Global.token, companyCarPost);

            call.enqueue(new Callback<CompanyCarPost>() {
                @Override
                public void onResponse(Call<CompanyCarPost> call, Response<CompanyCarPost> response) {
                    if(response.code() == 401){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    Log.d("Success",""+response.code());
                    if (response.isSuccessful()) {
                        Log.d("Success","Success"+response.code());
                        /*Global.eventsFragment = EventsFragment.createInstance();
                        Global.fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, Global.eventsFragment)
                                .addToBackStack(null)
                                .commit();
                        Global.floatingButton.setVisibility(View.VISIBLE);*/
                    }
                }
                @Override
                public void onFailure(Call<CompanyCarPost> call, Throwable t) {
                }
            });
        } else {

            privateCarPost =new PrivateCarPost(address_end,address_start,client,categoryID_travelbill,date,km,regno);

            Call<PrivateCarPost> call = Global.getAPIService.postPrivateCar("Token " + Global.token, privateCarPost);

            call.enqueue(new Callback<PrivateCarPost>() {
                @Override
                public void onResponse(Call<PrivateCarPost> call, Response<PrivateCarPost> response) {
                    if(response.code() == 401){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    Log.d("Success",""+response.code());
                    if (response.isSuccessful()) {
                        Log.d("Success","Success"+response.code());
                        /*Global.eventsFragment = EventsFragment.createInstance();
                        Global.fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, Global.eventsFragment)
                                .addToBackStack(null)
                                .commit();
                        Global.floatingButton.setVisibility(View.VISIBLE);*/
                    }
                }
                @Override
                public void onFailure(Call<PrivateCarPost> call, Throwable t) {
                }
            });
        }
    }

    public void loadData() {
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles = Global.vehicles;
        for(Vehicle vehicle: vehicles){
            if(vehicle_ID_companycar==vehicle.getId()){
                etAddressStart_companycar.setText(vehicle.getAddresssstart());
                etMeasureStart_companycar.setText(vehicle.getOdometer());
            }
        }
    }

}
