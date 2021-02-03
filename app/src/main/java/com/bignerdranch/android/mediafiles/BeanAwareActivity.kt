package com.bignerdranch.android.mediafiles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.mediafiles.dagger.ActivityComponent
import com.bignerdranch.android.mediafiles.dagger.ActivityModule
import com.bignerdranch.android.mediafiles.dagger.DaggerActivityComponent
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
open class BeanAwareActivity : AppCompatActivity() {
    private lateinit var activityComponent: ActivityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .generalComponent(MediaFilesApplication.appComponent)
                .activityModule(ActivityModule(this))
                .build()
        injectThis(activityComponent)
    }

    /**
     * Override if subclass needs injection.
     *
     * @param component
     */
    protected open fun injectThis(component: ActivityComponent) {
        // no op
    }
}
