package com.bignerdranch.android.mediafiles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.mediafiles.dagger.ActivityComponent;
import com.bignerdranch.android.mediafiles.dagger.ActivityModule;
import com.bignerdranch.android.mediafiles.dagger.DaggerActivityComponent;

import lombok.RequiredArgsConstructor;

import static com.bignerdranch.android.mediafiles.MediaFilesApplication.appComponent;

@RequiredArgsConstructor
public class BeanAwareActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .generalComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build();
        injectThis(activityComponent);
    }

    /**
     * Override if subclass needs injection.
     *
     * @param component
     */
    protected void injectThis(@NonNull ActivityComponent component) {
        // no op
    }
}
