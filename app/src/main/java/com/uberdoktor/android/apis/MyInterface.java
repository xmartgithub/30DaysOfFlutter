package com.uberdoktor.android.apis;

import com.uberdoktor.android.response.AddReviewResponse;
import com.uberdoktor.android.response.AddTreatmentResponse;
import com.uberdoktor.android.response.AppointmentsResponse;
import com.uberdoktor.android.response.DirectGeneralPhysicianResponse;
import com.uberdoktor.android.response.DocProfileResponse;
import com.uberdoktor.android.response.DoctorEarningsResponse;
import com.uberdoktor.android.response.LogoutResponse;
import com.uberdoktor.android.response.MedicalHistoryResponse;
import com.uberdoktor.android.response.MedicineRequestResponse;
import com.uberdoktor.android.response.OverviewResponse;
import com.uberdoktor.android.response.PatientConsultationsResponse;
import com.uberdoktor.android.response.PendingPaymentBookedResponse;
import com.uberdoktor.android.response.PortfolioResponse;
import com.uberdoktor.android.response.ProfileResponse;
import com.uberdoktor.android.response.RegisterResponse;
import com.uberdoktor.android.response.SelectSlotResponse;
import com.uberdoktor.android.response.ShowCredentialsResponse;
import com.uberdoktor.android.response.ShowScheduleResponse;
import com.uberdoktor.android.response.SpecialistResponse;
import com.uberdoktor.android.response.AddDoctorScheduleResponse;
import com.uberdoktor.android.response.DoctorConsultationResponse;
import com.uberdoktor.android.response.DoctorOverviewResponse;
import com.uberdoktor.android.response.DoctorProfileResponse;
import com.uberdoktor.android.response.DoctorScheduleResponse;
import com.uberdoktor.android.response.DoctorStatusResponse;
import com.uberdoktor.android.response.LoginResponse;
import com.uberdoktor.android.response.StartVideoCallResponse;
import com.uberdoktor.android.response.SubmitTreatmentResponse;
import com.uberdoktor.android.response.UpdateImageResponse;
import com.uberdoktor.android.response.VerifyPaymentResponse;
import com.uberdoktor.android.response.SessionRequestResponse;
import com.uberdoktor.android.response.VideoCallRequestResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface MyInterface {


    //////// POST ////////////////

    @FormUrlEncoded
    @POST("api/patient/login")
    Call<LoginResponse> login(@Field("phone_no") String phoneNo,
                              @Field("password") String pwd);

    @FormUrlEncoded
    @POST("api/patient/new_register")
    Call<RegisterResponse> registerPhoneNumber(@Field("phone_no") String phoneNo);

    @FormUrlEncoded
    @POST("api/patient/verify")
    Call<RegisterResponse> verifyPhoneNumber(@Field("phone_no") String phoneNo,
                                             @Field("shortcode") String shortcode);

    @FormUrlEncoded
    @POST("api/patient/setpassword")
    Call<RegisterResponse> registration(@Header("Authorization") String accessToken,
                                        @Field("password") String passwords,
                                        @Field("confirm_password") String confirmPasswords);

    @FormUrlEncoded
    @POST("api/patient/add_review")
    Call<AddReviewResponse> goToAddReview(@Header("Authorization") String accessToken,
                                          @Field("id") String id);

    @FormUrlEncoded
    @POST("api/doctor/new_register")
    Call<RegisterResponse> doctorRegisterPhoneNumber(@Field("phone_no") String phoneNo);

    @FormUrlEncoded
    @POST("api/doctor/setpassword")
    Call<RegisterResponse> doctorRegistration(@Header("Authorization") String accessToken,
                                              @Field("password") String passwords,
                                              @Field("confirm_password") String confirmPasswords);

    @FormUrlEncoded
    @POST("api/patient/update_profile")
    Call<ProfileResponse> updateUserProfile(@Header("Authorization") String accessToken,
                                            @Field("first_name") String firstName,
                                            @Field("last_name") String lastName,
                                            @Field("email") String userEmail,
                                            @Field("dob") String dateOfBirth,
                                            @Field("document_no") String cnic,
                                            @Field("gender") String gender,
                                            @Field("city") String userCity,
                                            @Field("country") String userCountry);

    @Multipart
    @POST("api/patient/update_image")
    Call<UpdateImageResponse> uploadPatientProfileImage(@Header("Authorization") String accessToken,
                                                        @Part MultipartBody.Part file);

    @Multipart
    @POST("api/doctor/update_image")
    Call<UpdateImageResponse> uploadDoctorProfileImage(@Header("Authorization") String accessToken,
                                                       @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/patient/request_medicine")
    Call<MedicineRequestResponse> requestForMedicine(@Header("Authorization") String accessToken,
                                                     @Field("id") int checkupId);

    @FormUrlEncoded
    @POST("api/patient/verify_payment")
    Call<VerifyPaymentResponse> verifyPayment(@Header("Authorization") String accessToken,
                                              @Field("wallet_amount") String walletAmount,
                                              @Field("payment_method") String paymentMethod,
                                              @Field("payment_type") String paymentType,
                                              @Field("sender_name") String senderName,
                                              @Field("sender_number") String senderNumber,
                                              @Field("amount") String amount,
                                              @Field("Trx_ID") String trackingId,
                                              @Field("pending_payment_id") int pendingPaymentId);

    @FormUrlEncoded
    @POST("api/doctor/update_profile")
    Call<DocProfileResponse> updateDoctorProfile(@Header("Authorization") String accessToken,
                                                 @Field("first_name") String firstName,
                                                 @Field("last_name") String lastName,
                                                 @Field("email") String userEmail,
                                                 @Field("dob") String dateOfBirth,
                                                 @Field("document_no") String cnic,
                                                 @Field("city") String userCity,
                                                 @Field("country") String userCountry,
                                                 @Field("license_no") String licenseNo);

    @FormUrlEncoded
    @POST("api/patient/doctor_profile")
    Call<DoctorProfileResponse> getDoctorProfile(@Header("Authorization") String accessToken,
                                                 @Field("doctor_id") String doctorId);

    @FormUrlEncoded
    @POST("api/patient/video_call")
    Call<VideoCallRequestResponse> callRequest(@Header("Authorization") String accessToken,
                                               @Field("appointment_id") String appointmentId);

    @FormUrlEncoded
    @POST("api/patient/payment_booked")
    Call<PendingPaymentBookedResponse> paymentBooked(@Header("Authorization") String accessToken,
                                                     @Field("pending_payment_id") String appointmentId);

    @FormUrlEncoded
    @POST("api/doctor/session_request")
    Call<SessionRequestResponse> requestSession(@Header("Authorization") String accessToken,
                                                @Field("appointment_id") String appointmentId);

    @FormUrlEncoded
    @POST("api/doctor/video_request")
    Call<VideoCallRequestResponse> requestVideoCall(@Header("Authorization") String accessToken,
                                                    @Field("appointment_id") String appointmentId);

    @FormUrlEncoded
    @POST("api/doctor/add_treatment")
    Call<AddTreatmentResponse> addTreatment(@Header("Authorization") String accessToken,
                                            @Field("id") String id);

    @FormUrlEncoded
    @POST("api/doctor/submit_treatment")
    Call<SubmitTreatmentResponse> submitTreatment(@Header("Authorization") String accessToken,
                                                  @Field("id") int id, @Field("symptoms") String symptoms,
                                                  @Field("diagnosis") String diagnosis,
                                                  @Field("treatment") String treatment,
                                                  @Field("appointment_id") String appointment_id);

    @FormUrlEncoded
    @POST("api/patient/search_specialist")
    Call<SpecialistResponse> getSpecialist(@Header("Authorization") String accessToken,
                                           @Field("City") String cityName,
                                           @Field("Speciality") String speciality);

    @GET("api/doctor/show_schedule")
    Call<ShowScheduleResponse> getDocSchedules(@Header("Authorization") String accessToken);

    @GET("api/doctor/earnings")
    Call<DoctorEarningsResponse> getEarnings(@Header("Authorization") String accessToken);

    @GET("api/doctor/logout")
    Call<LogoutResponse> doctorLogout(@Header("Authorization") String accessToken);

    @GET("api/patient/logout")
    Call<LogoutResponse> patientLogout(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("api/patient/submit_review")
    Call<AddReviewResponse> submitReview(@Header("Authorization") String accessToken,
                                         @Field("id") int id,
                                         @Field("stars") String starts,
                                         @Field("comments") String comments);

    @FormUrlEncoded
    @POST("api/patient/doctor_schedule")
    Call<DoctorScheduleResponse> getAppointmentTime(@Header("Authorization") String accessToken,
                                                    @Field("doc_id") String doctorId);

    @FormUrlEncoded
    @POST("api/doctor/add_schedule")
    Call<AddDoctorScheduleResponse> addSchedule(@Header("Authorization") String accessToken,
                                                @Field("start") String startTime,
                                                @Field("end") String endTime,
                                                @Field("date") String date);

    @FormUrlEncoded
    @POST("api/doctor/add_education")
    Call<AddDoctorScheduleResponse> addEducation(@Header("Authorization") String accessToken,
                                                 @Field("institution_name") String institutionName,
                                                 @Field("degree") String degree,
                                                 @Field("year") String year);

    @FormUrlEncoded
    @POST("api/doctor/add_speciality")
    Call<AddDoctorScheduleResponse> addSpecialty(@Header("Authorization") String accessToken,
                                                 @Field("name") String specialtyName
    );


    @GET("api/doctor/show_credentials")
    Call<ShowCredentialsResponse> showCredentials(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("api/patient/select_slot")
    Call<SelectSlotResponse> selectSlot(@Header("Authorization") String accessToken,
                                        @Field("doc_id") String docId,
                                        @Field("appdate") String appointmentDate,
                                        @Field("slot") String slot
    );

    @FormUrlEncoded
    @POST("api/doctor/login")
    Call<LoginResponse> doctorLogin(@Field("phone_no") String phoneNo,
                                    @Field("password") String pwd);

    //////// GET //////////////////

    @GET("api/patient/profile")
    Call<ProfileResponse> userProfile(@Header("Authorization") String accessToken);


    @GET("api/doctor/profile")
    Call<DocProfileResponse> viewDoctorProfile(@Header("Authorization") String accessToken);

    @GET("api/doctor/online")
    Call<DoctorStatusResponse> doctorOnline(@Header("Authorization") String accessToken);

    @GET("api/doctor/offline")
    Call<DoctorStatusResponse> doctorOffline(@Header("Authorization") String accessToken);

    @GET("api/doctor/portfolio")
    Call<PortfolioResponse> doctorPortfolio(@Header("Authorization") String accessToken);

    @GET("api/patient/overview")
    Call<OverviewResponse> patientOverview(@Header("Authorization") String accessToken);

    @GET("api/patient/direct_gp")
    Call<DirectGeneralPhysicianResponse> getDirectGeneralPhysician(@Header("Authorization") String accessToken);

    @GET("api/doctor/overview")
    Call<DoctorOverviewResponse> doctorOverview(@Header("Authorization") String accessToken);

    @GET("api/patient/medicalhistory")
    Call<MedicalHistoryResponse> patientMedicalHistory(@Header("Authorization") String accessToken);

    @GET("api/doctor/consultations")
    Call<DoctorConsultationResponse> doctorConsultations(@Header("Authorization") String accessToken);

    @GET("api/patient/appointments")
    Call<AppointmentsResponse> getAppointmentDetails(@Header("Authorization") String accessToken);

    @GET("api/patient/consultations")
    Call<PatientConsultationsResponse> patientConsultations(@Header("Authorization") String accessToken);


}
