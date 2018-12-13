package com.wecareit.retrofit;

import com.wecareit.model.Area;
import com.wecareit.model.AssignedTo;
import com.wecareit.model.Attendance;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.CompanyCarPost;
import com.wecareit.model.DocCategory;
import com.wecareit.model.Document;
import com.wecareit.model.EventsRes;
import com.wecareit.model.EventspostBody;
import com.wecareit.model.InfoRes;
import com.wecareit.model.LoginResponse;
import com.wecareit.model.Login;
import com.wecareit.model.LogoutResponse;
import com.wecareit.model.MainModel;
import com.wecareit.model.Major_Keyword;
import com.wecareit.model.News;
import com.wecareit.model.NewsPost;
import com.wecareit.model.NewsPostResponse;
import com.wecareit.model.NewsResponse;
import com.wecareit.model.NotesRes;
import com.wecareit.model.PostTask;
import com.wecareit.model.PrivateCarPost;
import com.wecareit.model.Tasks;
import com.wecareit.model.UpdateNews;
import com.wecareit.model.UpdateTitle;
import com.wecareit.model.Userlist;
import com.wecareit.model.Vehicle;
import com.wecareit.model.Vehicles;
import com.wecareit.model.WeeklySchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetAPIService {
    @POST("/api/users/login/")
    Call<LoginResponse> doLogin(@Body Login login);

    @POST("/api/users/logout/")
    Call<LogoutResponse> doLogout();

    //Clients//////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/clients/")
    Call<ArrayList<Client>> readClients(@Header("authorization") String token);

    //Users//////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/users/")
    Call<ArrayList<AuthorRes>> readUsers(@Header("authorization") String token);

    //Vehicles//////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/drivinglog/vehicles/")
    Call<ArrayList<Vehicle>> readVehicles(@Header("authorization") String token);

    //drivingcategories//////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/drivinglog/categories/")
    Call<ArrayList<Client>> readDrivingCategories(@Header("authorization") String token);

    //Areas//////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/areas/")
    Call<ArrayList<Area>> readAreas(@Header("authorization") String token);


    // Attendance /////////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/attendance/{date}")
    Call<Attendance> readAttendance(@Header("authorization") String token, @Path("date") String selectedDate);

    @Headers({"Accept: application/json"})
    @PUT("/api/attendance/{date}/")
    Call<ResponseBody> updateAttendance(@Header("authorization") String token, @Path("date") String selectedDate, @Body Attendance body);


    // Document ////////////////////////////////////
    @Headers({"Accept: application/json"})
    @GET("/api/documents/")
    Call<ArrayList<Document>> readAllDocuments(@Header("authorization") String token, @Query("category") int category, @Query("ordering") String ordering);

    @Headers({"Accept: application/json"})
    @GET("/api/documents/categories/")
    Call<ArrayList<DocCategory>> readDocuments(@Header("authorization") String token, @Query("ordering") String ordering);

    @Headers({"Accept: application/json"})
    @GET("/api/documents/")
    Call<ArrayList<Document>> deleteDocuments(@Header("authorization") String token, @Query("url") String url);

    @Headers({"Accept: application/json"})
    @DELETE("/api/documents/categories/")
    Call<ArrayList<DocCategory>> readCategory(@Header("authorization") String token);


    // WeeklySchedule
    @Headers({"Accept: application/json"})
    @GET("/api/events/")
    Call<ArrayList<EventsRes>> readWeeklySchedule(@Header("authorization") String token, @Query("week_start") String weekstart);

    @Headers({"Accept: application/json"})
    @POST("/api/events/")
    Call<EventsRes> writeWeeklySchedule(@Header("authorization") String token, @Body EventspostBody eventsBody);

    @Headers({"Accept: application/json"})
    @PUT("/api/events/{id}/")
    Call<EventsRes> updateWeeklySchedule(@Header("authorization") String token, @Path("id") int id, @Body EventspostBody eventsBody);

    @Headers({"Accept: application/json"})
    @GET("/api/events/")
    //Call<ArrayList<EventsRes>> filterWeeklySchedule(@Header("authorization") String token,@Query("clients") String clients, @Query("users") String users, @Query("vehicles") String vehicles, @Query("week_start") String weekstart );
    Call<ArrayList<EventsRes>> filterWeeklySchedule(@Header("authorization") String token,@Query("clients") ArrayList<Integer> clients, @Query("users") ArrayList<Integer> users, @Query("vehicles") ArrayList<Integer> vehicles, @Query("week_start") String weekstart );

    @Headers({"Accept: application/json"})
    @DELETE("/api/events/{id}/")
    Call<String> deleteWeeklySchedule(@Header("authorization") String token,@Path("id") int id);


    // Notes
    @Headers({"Accept: application/json"})
    @GET("/api/notes/")
    Call<ArrayList<NotesRes>> readNotes(@Header("authorization") String token, @Query("date") String date);
//    Call<ArrayList<NotesRes>> readNotes(@Header("authorization") String token, @Query("date") String date, @Query("area") int area, @Query("category") int category, @Query("clients") int clients);

    @Headers({"Accept: application/json"})
    @POST("/api/notes/")
    Call<ArrayList<NotesRes>> writeNotes(@Header("authorization") String token, @Query("date") String date);

    @Headers({"Accept: application/json"})
    @GET("/api/notes/")
    //Call<ArrayList<NotesRes>> filterNotes(@Header("authorization") String token, @Query("area") String area_id, @Query("category") String category, @Query("clients") String clients, @Query("keyword") String keyword, @Query("time_range") String time_range, @Query("date") String date );
    Call<ArrayList<NotesRes>> filterNotes(@Header("authorization") String token, @Query("area") int area_id, @Query("category") int category, @Query("clients") int clients, @Query("keyword") int keyword, @Query("time_range") int time_range, @Query("date") String date );
//    Call<ArrayList<NotesRes>> filterNotes(@FieldMap Map<String, String> params);

    @Headers({"Accept: application/json"})
    @GET("/api/main-categories/")
    Call<ArrayList<Area>> readMainCategories(@Header("authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("/api/major-keywords/")
    Call<ArrayList<Major_Keyword>> readMajorKeywords(@Header("authorization") String token);


    //Information
    @Headers({"Accept: application/json"})
    @GET("/api/info-tab/{tab_id}/info-boxes/")
    Call<ArrayList<InfoRes>> readInfo(@Header("authorization") String token, @Path("tab_id") int tab_id);

    @Multipart
    @Headers({"Accept: application/json"})
    @PUT("/api/info-tab/{tab_id}/info-boxes/{id}/")
    Call<InfoRes> postInfo(@Header("authorization") String token, @Path("tab_id") int tab_id, @Path("id") int id, @Part("text") String text );

    //Drivinglog
    @Headers({"Accept: application/json"})
    @POST("/api/drivinglog/companycar/")
    Call<CompanyCarPost> postCompanyCar(@Header("authorization") String token, @Body CompanyCarPost companyCarPost);

    @Headers({"Accept: application/json"})
    @POST("/api/drivinglog/privatecar/")
    Call<PrivateCarPost> postPrivateCar(@Header("authorization") String token, @Body PrivateCarPost privateCarPost);

    //News

    @Headers({"Accept: application/json"})
    @GET("/api/news/")
    Call<ArrayList<NewsResponse>> readNews(@Header("authorization") String token, @Query("me_mentioned") String me_mentioned);

    @Headers({"Accept: application/json"})
    @POST("/api/news/")
    Call<NewsPostResponse> postNews(@Header("authorization") String token, @Body NewsPost userlist);

    @Headers({"Accept: application/json"})
    @POST("/api/news/{parent_entry_id}/replies/")
    Call<NewsResponse> postComment(@Header("authorization") String token, @Path("parent_entry_id") int id, @Body UpdateNews body);

    @Headers({"Accept: application/json"})
    @PATCH("/api/news/{id}")
    Call<NewsResponse> updateTitle(@Header("authorization") String token, @Path("id") int id, @Body UpdateNews body, @Query("me_mentioned") String me_mentioned );


    //Start
    @Headers({"Accept: application/json"})
    @GET("/api/start/")
    Call<MainModel> readStart(@Header("authorization") String token);

    //Taks
    @Headers({"Accept: application/json"})
    @GET("/api/tasks/")
    Call<ArrayList<Tasks>> readTasks(@Header("authorization") String token);

    @Headers({"Accept: application/json"})
    @GET("/api/tasks/")
    Call<ArrayList<Tasks>> readUserTasks(@Header("authorization") String token, @Query("id") int id);

    @Headers({"Accept: application/json"})
    @POST("/api/tasks/")
    Call<ResponseBody> postTask(@Header("authorization") String token, @Body PostTask postTask);
}
