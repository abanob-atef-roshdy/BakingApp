package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import bebo.bakingapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class VideoFragment extends Fragment {

    private Steps steps;
    private List<Steps> mStepsList;
    private Context mContext;
    @BindView(R.id.np_thumb_txt)
     TextView mNoThumbTxt;
    @BindView(R.id.step_detail_instructions)
     TextView mInstructionView;
    @BindView(R.id.step_detail_index)
     TextView mIndexText;
    @BindView(R.id.next_btn)
     Button nextBtn;
    @BindView(R.id.previous_btn)
     Button previousBtn;
    @BindView(R.id.player_view)
     PlayerView playerView;
    private SimpleExoPlayer player;
    private int currentWindow = 0;
    private boolean playWhenReady = true;
    private long playbackPosition = 0;
    private long position = 0;
    private  Boolean mTwoPane;
    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public void setmStep(Steps mStep, List<Steps> mStepsList) {
        this.steps = mStep;
        this.mStepsList = mStepsList;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
    public VideoFragment() {
        // Required empty public constructor
    }
    public String getScreenOrientation(Context context) {
        final int screenOrientation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (screenOrientation) {
            case Surface.ROTATION_0:
                return "SCREEN_ORIENTATION_PORTRAIT";
            case Surface.ROTATION_90:
                return "SCREEN_ORIENTATION_LANDSCAPE";
            case Surface.ROTATION_180:
                return "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
            default:
                return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment,container,false);
        ButterKnife.bind(this,view);
        if (savedInstanceState != null) {
            steps = (Steps) savedInstanceState.get("step");
            mStepsList = (List<Steps>) savedInstanceState.get("stepslist");
            position = savedInstanceState.getLong("position");
            mTwoPane = savedInstanceState.getBoolean("mTwoPane");
        }
        renderCurrentStep();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = steps.getId();
                index++;
                if (index != mStepsList.size()) {
                    steps = mStepsList.get(index);
                    renderCurrentStep();
                    releasePlayer();
                    initializePlayer();
                }
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = steps.getId();
                if (index != 0) {
                    index--;
                    steps = mStepsList.get(index);
                    renderCurrentStep();
                    releasePlayer();
                    initializePlayer();
                }
            }
        });
     return view;
    }
    private void renderCurrentStep() {
        mInstructionView.setText(steps.getDescription());

        if (mTwoPane) {
            nextBtn.setVisibility(View.GONE);
            previousBtn.setVisibility(View.GONE);
            mIndexText.setVisibility(View.GONE);
        } else {
            setCurrentIndex();
        }

        initializePlayer();

    }


    private void setCurrentIndex() {
        int currentStep = steps.getId();
        currentStep++;
        mIndexText.setText(currentStep + "/" + mStepsList.size());
        if (currentStep == mStepsList.size()) {
            nextBtn.setClickable(false);
            nextBtn.setAlpha((float) 0.5);
        } else {
            nextBtn.setClickable(true);
            nextBtn.setAlpha((float) 1);
        }
        if (currentStep == 1) {
            previousBtn.setClickable(false);
            previousBtn.setAlpha((float) 0.5);
        } else {
            previousBtn.setClickable(true);
            previousBtn.setAlpha((float) 1);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("step", steps);
        outState.putSerializable("stepslist", (Serializable) mStepsList);
        outState.putLong("position", position);
        outState.putBoolean("mTwoPane",mTwoPane);
    }
    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = null;
        if (!TextUtils.isEmpty(steps.getThumbnail())) {
            uri = Uri.parse(steps.getThumbnail());
            playerView.setVisibility(View.VISIBLE);
            mNoThumbTxt.setVisibility(View.INVISIBLE);
        } else if (!TextUtils.isEmpty(steps.getVideoUrl())) {
            uri = Uri.parse(steps.getVideoUrl());
            playerView.setVisibility(View.VISIBLE);
            mNoThumbTxt.setVisibility(View.INVISIBLE);
        } else {
            playerView.setVisibility(View.INVISIBLE);
            mNoThumbTxt.setVisibility(View.VISIBLE);
        }
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        if (position != C.TIME_UNSET) player.seekTo(position);
        if (!mTwoPane && playerView.getVisibility() != View.INVISIBLE) {
            if (getScreenOrientation(getContext()) == "SCREEN_ORIENTATION_PORTRAIT") {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = 800;
                playerView.setLayoutParams(params);
                nextBtn.setVisibility(View.VISIBLE);
                previousBtn.setVisibility(View.VISIBLE);
            } else {
                hideSystemUi();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                playerView.setLayoutParams(params);
                nextBtn.setVisibility(View.INVISIBLE);
                previousBtn.setVisibility(View.INVISIBLE);

            }
        }

    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        position = player.getCurrentPosition();
        releasePlayer();

    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
