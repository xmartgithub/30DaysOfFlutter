package com.uberdoktor.android.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.BookedAppointmentsAdapter;
import com.uberdoktor.android.adapter.QueueDetailsAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.fragment.DoctorSignInFragment;
import com.uberdoktor.android.fragment.PatientSignInFragment;
import com.uberdoktor.android.response.AddReviewResponse;
import com.uberdoktor.android.response.AddTreatmentResponse;

import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoCall extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener, Publisher.CameraListener {

    public static String API_KEY = "47140604";
    private String SESSION_ID;
    private String TOKEN;
    private static final String LOG_TAG = VideoCall.class.getSimpleName();
    //    private static int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    public static int checkupId;
    public static int treatmentId;
    public static String patientName;
    public static String addTreatmentAppointmentId;

    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    private ConstraintLayout mPublisherViewContainer;
    private ConstraintLayout mSubscriberViewContainer;

    private ImageView muteAudioImageViewButton;
    private ImageView disabledVideoCallImageViewButton;
    private ImageView switchCameraImageViewButton;
    private ImageView disconnectImageViewButton;

    SharedPrefManager sharedPrefManager;
    private TextView textViewDocNotOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_call_layout);

        if (DoctorSignInFragment.doctorType != null) {
            SESSION_ID = QueueDetailsAdapter.session;
            TOKEN = QueueDetailsAdapter.token;
//            Toast.makeText(this, "onCreateDoctor Session" + SESSION_ID, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "onCreateDoctor token" + TOKEN, Toast.LENGTH_SHORT).show();
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle("Video Call");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (PatientSignInFragment.patientType != null) {
            SESSION_ID = BookedAppointmentsAdapter.patientSession;
            TOKEN = BookedAppointmentsAdapter.patientToken;
//            Toast.makeText(this, "onCreatePatient Session" + SESSION_ID, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "onCreatePatient token" + TOKEN, Toast.LENGTH_SHORT).show();
        }

        textViewDocNotOnline = findViewById(R.id.tv_doctor_not_online);

        disabledVideoCallImageViewButton = findViewById(R.id.disabled_video_call_image_view_btn);

        muteAudioImageViewButton = findViewById(R.id.mute_audio_image_view_btn);

        switchCameraImageViewButton = findViewById(R.id.switch_camera_image_view_btn);

        disconnectImageViewButton = findViewById(R.id.disconnect_video_call_image_view_btn);

        sharedPrefManager = new SharedPrefManager(this);

        mPublisher = new Publisher.Builder(this).resolution(Publisher.CameraCaptureResolution.HIGH).build();

        muteAudioImageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublisher.setPublishAudio(false);
                Toast.makeText(VideoCall.this, "audio disabled", Toast.LENGTH_SHORT).show();
                muteAudioImageViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPublisher.setPublishAudio(true);
                        Toast.makeText(VideoCall.this, "audio enabled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if (mPublisher.getPublishAudio()) {
            muteAudioImageViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPublisher.setPublishAudio(false);
                }
            });
        } else if (!mPublisher.getPublishAudio()) {
            muteAudioImageViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPublisher.setPublishAudio(true);
                }
            });
        }

        switchCameraImageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublisher.cycleCamera();
            }
        });

        disconnectImageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DoctorSignInFragment.doctorType != null) {
                    disconnectVideoCall();
                    gotoAddTreatmentActivity();
                }
                if (PatientSignInFragment.patientType != null) {
                    disconnectPatientVideoCall();
                    gotoAppointmentReview();
                }
            }
        });

        requestPermissions();

    }
    /* Activity lifecycle methods */

//    @Override
//    protected void onPause() {
//        Log.d(LOG_TAG, "onPause");
//        super.onPause();
//
//        if (mSession != null) {
//            mSession.onPause();
//        }
//
//    }

//    @Override
//    protected void onResume() {
//
//        Log.d(LOG_TAG, "onResume");
//
//        super.onResume();
//
//        if (mSession != null) {
//            mSession.onResume();
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = findViewById(R.id.publisher_container);
            mSubscriberViewContainer = findViewById(R.id.subscriber_container);
            if (PatientSignInFragment.patientType != null) {
                SESSION_ID = BookedAppointmentsAdapter.patientSession;
                TOKEN = BookedAppointmentsAdapter.patientToken;
            }
            // initialize and connect to the session
//            Toast.makeText(this, "SESSION Found " + SESSION_ID, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "TOKEN FOUND " + TOKEN, Toast.LENGTH_SHORT).show();
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);
        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        textViewDocNotOnline.setVisibility(View.GONE);

        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(LOG_TAG, "onDisconnected: Disconnected from session: " + session.getSessionId());
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
//          mSession.setSessionListener(this);
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
        Log.d(LOG_TAG, "onStreamDropped: Stream Dropped: " + stream.getStreamId() + " in session: " + session.getSessionId());

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    public void disconnectVideoCall() {
        mSession.unsubscribe(mSubscriber);
        mPublisher.setPublishAudio(false);
        mPublisher.setPublishVideo(false);
    }

    public void disconnectPatientVideoCall() {
        mSession.unsubscribe(mSubscriber);
        mPublisher.setPublishAudio(false);
        mPublisher.setPublishVideo(false);
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "onError: " + opentokError.getErrorDomain() + " : " +
                opentokError.getErrorCode() + " - " + opentokError.getMessage() + " in session: " + session.getSessionId());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "onStreamCreated: Publisher Stream Created. Own stream " + stream.getStreamId());
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "onStreamDestroyed: Publisher Stream Destroyed. Own stream " + stream.getStreamId());
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "onError: " + opentokError.getErrorDomain() + " : " +
                opentokError.getErrorCode() + " - " + opentokError.getMessage());
    }

    @Override
    public void onCameraChanged(Publisher publisher, int newCameraId) {
        Toast.makeText(this, newCameraId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraError(Publisher publisher, OpentokError opentokError) {
        Toast.makeText(this, opentokError.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void gotoAppointmentReview() {
        Call<AddReviewResponse> call = RetrofitClient.getInstance().getMyInterface().goToAddReview("Bearer " + sharedPrefManager.getAccessToken(), BookedAppointmentsAdapter.bookedAppointmentId);
        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddReviewResponse> call, @NotNull Response<AddReviewResponse> response) {
                AddReviewResponse addReviewResponse = response.body();
                if (response.isSuccessful()) {
                    assert addReviewResponse != null;
                    checkupId = addReviewResponse.getId();
                    startActivity(new Intent(VideoCall.this, AppointmentFeedback.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddReviewResponse> call, @NotNull Throwable t) {
                Toast.makeText(VideoCall.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotoAddTreatmentActivity() {
        Call<AddTreatmentResponse> call = RetrofitClient.getInstance().getMyInterface().addTreatment("Bearer " + sharedPrefManager.getAccessToken(), QueueDetailsAdapter.appointmentId);
        call.enqueue(new Callback<AddTreatmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddTreatmentResponse> call, @NotNull Response<AddTreatmentResponse> response) {
                AddTreatmentResponse addTreatmentResponse = response.body();
                if (response.isSuccessful()) {
                    assert addTreatmentResponse != null;
                    treatmentId = addTreatmentResponse.getId();
                    patientName = addTreatmentResponse.getPatient().listIterator().next().getFirstName() + " " + addTreatmentResponse.getPatient().listIterator().next().getFirstName();
                    addTreatmentAppointmentId = addTreatmentResponse.getAppointmentId();
                    startActivity(new Intent(VideoCall.this, TreatmentActivity.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddTreatmentResponse> call, @NotNull Throwable t) {
                Toast.makeText(VideoCall.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}