package com.wecareit.common;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.wecareit.fragments.attendance.AttendanceFragment;
import com.wecareit.fragments.document.DocumentAddFragment;
import com.wecareit.fragments.document.DocumentFragment;
import com.wecareit.fragments.document.DocumentViewAllFragment;
import com.wecareit.fragments.drivinglog.DrivingLogFragment;
import com.wecareit.fragments.events.EventsFragment;
import com.wecareit.fragments.events.EventsUpdateFragment;
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.fragments.meeting.AgendaFragment;
import com.wecareit.fragments.meeting.MeetingsFragment;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.fragments.notes.MonthlySummeryFragment;
import com.wecareit.fragments.notes.NotesAddFragment;
import com.wecareit.fragments.notes.NotesFragment;
import com.wecareit.fragments.routine.RoutineAddFragment;
import com.wecareit.fragments.routine.RoutineFragment;
import com.wecareit.fragments.start.StartFragment;
import com.wecareit.fragments.tasks.TasksAddFragment;
import android.text.format.DateFormat;
import com.wecareit.fragments.tasks.TasksFragment;
import com.wecareit.model.Area;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.LoginResponse;
import com.wecareit.model.Spinners;
import com.wecareit.model.User;
import com.wecareit.model.Vehicle;
import com.wecareit.model.Vehicles;
import com.wecareit.retrofit.GetAPIService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Global {
    public static String TAG = "WeCareIT";
    public static int REQUEST_CODE_SELECT_FILE_FOR_IMPORT = 101;

    public static Toolbar toolbar;
    public static FloatingActionButton floatingButton;
    public static FloatingActionButton monthlyButton;
    public static GetAPIService getAPIService;

    public static FragmentManager fragmentManager;
    public static StartFragment startFragment;
    public static EventsFragment eventsFragment;
    public static NewsFragment newsFragment;
    public static InformationFragment informationFragment;
    public static NotesFragment notesFragment;
    public static NotesAddFragment notesAddFragment;
    public static AttendanceFragment attendanceFragment;
    public static DocumentFragment documentFragment;
    public static DocumentAddFragment documentAddFragment;
    public static DocumentViewAllFragment documentViewAllFragment;
    public static TasksFragment tasksFragment;
    public static TasksAddFragment tasksAddFragment;
    public static DrivingLogFragment drivingLogFragment;
    public static EventsUpdateFragment eventsUpdateFragment;
    public static AgendaFragment agendaFragment;
    public static MonthlySummeryFragment monthlySummeryFragment;
    public static MeetingsFragment meetingsFragment;
    public static RoutineFragment routineFragment;
    public static RoutineAddFragment routineAddFragment;

    public static String token;
    public static User user;
    public static int user_ID;
    public static ArrayList<Client> clients;
    public static ArrayList<Vehicle> vehicles;
    public static ArrayList<AuthorRes> users;

    public static String[] timeintervals_array = {"","Alla datum", "Idag", "Senaste 7 dagarna", "Denna månad", "Detta år"};
    public static String[] eventsupdatetimeintervals_array = {"15 minuter", "30 minuter", "45 minuter", "1 timme", "1 timme 15 minuter", "1 timme 30 minuter", "1 timme 45 minuter", "2 timme",
            "2 timme 15 minuter", "2 timme 30 minuter", "2 timme 45 minuter", "3 timme", "3 timme 15 minuter", "3 timme 30 minuter", "3 timme 45 minuter", "4 timme",
            "4 timme 30 minuter", "5 timme", "5 timme 30 minuter", "6 timme", "6 timme 30 minuter", "7 timme", "7 timme 30 minuter", "8 timme",
            "9 timme", "10 timme", "11 timme", "12 timme"};
    public static String[] notestimeintervals_array = {"Idag / valt datum","15 dagar", "30 dagar", "90 dagar", "1 år"};
    public static String[] status_array = {"","Alla", "Ej klar", "Klar", "Inställd"};
    public static String[] weekDays = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};
    public static String[] categories = {"Generella", "Dagverksamheten", "Boende", "Personal", "Kvalitetssystem", "APT / Personalmöte"};
    public static String[] months = {"januari", "februari", "mars", "april", "maj", "juni", "juli", "augusti", "september", "oktober", "november", "december"};
    public static String[] years = {"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009"};
    public static ArrayList<String> categorylist = new ArrayList<String>();
    public static ArrayList<String> clientslist = new ArrayList<String>();
    public static ArrayList<String> userslist = new ArrayList<String>();
    public static ArrayList<String> vehicleslist = new ArrayList<String>();
    public static ArrayList<Integer> vehiclesIDlist = new ArrayList<Integer>();
    public static ArrayList<String> areaslist = new ArrayList<String>();
    public static ArrayList<String> month_majorKeywordList = new ArrayList<String>();
    public static ArrayList<String> main_categorieslist = new ArrayList<String>();
    public static ArrayList<String> major_keywordslist = new ArrayList<String>();
    public static ArrayList<String> minor_keywordslist = new ArrayList<String>();
    public static ArrayList<String> drivingcategories = new ArrayList<String>();
    public static ArrayList<Integer> drivingcategoriesID = new ArrayList<Integer>();
    public static ArrayList<Spinners> testSpinArray = new ArrayList<Spinners>();

    public static String dateString(String nowDate) {
        String day = "Söndag";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(nowDate);
            day = (String) DateFormat.format("EEEE", date);
            switch (day) {
                case "Monday":
                    day = "Måndag";
                    break;
                case "Tuesday":
                    day = "Tisdag";
                    break;
                case "Wednesday":
                    day = "Onsdag";
                    break;
                case "Thursday":
                    day = "Torsdag";
                    break;
                case "Friday":
                    day = "Fredag";
                    break;
                case "Saturday":
                    day = "Lördag";
                    break;
                case "Sunday":
                    day = "Söndag";
                    break;
            }
            day += " " + nowDate.split("-")[2];
            int monthId = Integer.parseInt(nowDate.split("-")[1]);
            day += " " + months[monthId - 1];
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;
    }
}
