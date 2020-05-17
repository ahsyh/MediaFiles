package com.bignerdranch.android.mediafiles.util.message;


import androidx.annotation.NonNull;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;
import com.bignerdranch.android.mediafiles.util.log.Logger;

import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.functions.Consumer;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * A pub/sub component that allows type-specific events to be published and subscribed to.
 */
public class GlobalBusHelper {

    private static final String TAG = GlobalBusHelper.class.getSimpleName();

    /**
     * The underlying {@link PublishSubject} instance.
     */
    @NonNull private final PublishSubject<Object> publishSubject = PublishSubject.create();
    @NonNull private final Consumer<Throwable> defaultThrowableConsumer;

    @NonNull private final Logger logger;

    GlobalBusHelper(@NonNull Logger logger) {
        this.logger = logger;
        this.defaultThrowableConsumer = t -> logger.e(TAG, "Consumer exception occurred.", t);
    }

    /**
     * Fetches a singular instance of this helper class to be shared across the application
     * @deprecated This Singleton will be removed as soon as we're done migrating to RxJava (AP-11134).
     */
    @Deprecated
    public static GlobalBusHelper getInstance() {
        return InstanceHelper.INSTANCE;
    }

    private static class InstanceHelper {
        private static final GlobalBusHelper INSTANCE =
                new GlobalBusHelper(MediaFilesApplication.getAppComponent().getLogger());
    }

    /**
     * Publishes an event to the underlying {@link PublishSubject}.
     * @param event The event describing the message sent.
     */
    public void publish(@NonNull final Object event) {
        publishSubject.onNext(event);
    }

    /**
     * Subscribes a {@link Consumer} for a specific event type and returns the
     * {@link PausableDisposableObserver} created for the consumer.
     * @param clazz The event type to consume.
     * @param consumer The {@link Consumer}.
     * @param errorConsumer A {@link Consumer} to deal with exceptions.
     * @param scheduler The {@link Scheduler} to use for the consumer execution.
     * @return The {@link PausableDisposableObserver} created for the consumer.
     */
    public <T> PausableDisposableObserver<T> subscribe(@NonNull final Class<T> clazz,
                                                       @NonNull final Consumer<T> consumer, @NonNull final Consumer<Throwable> errorConsumer,
                                                       @NonNull final Scheduler scheduler) {
        final PausableDisposableObserver observer = new PausableDisposableObserver(consumer, errorConsumer);
        publishSubject.filter(e -> e.getClass().equals(clazz))
                .observeOn(scheduler)
                .subscribe(observer);

        return observer;
    }

    /**
     * Subscribes a {@link Consumer} for a specific event type and returns the
     * {@link PausableDisposableObserver} created for the consumer.
     * @param clazz The event type to consume.
     * @param consumer The {@link Consumer}.
     * @param scheduler The {@link Scheduler} to use for the consumer execution.
     * @return The {@link PausableDisposableObserver} created for the consumer.
     */
    public <T> PausableDisposableObserver<T> subscribe(@NonNull final Class<T> clazz,
                                                       @NonNull final Consumer<T> consumer, @NonNull final Scheduler scheduler) {
        return subscribe(clazz, consumer, defaultThrowableConsumer, scheduler);
    }

    /**
     * Subscribes a {@link Consumer} for a specific event type and returns the
     * {@link PausableDisposableObserver} created for the consumer.
     * <p>
     * Note: by default, this method will execute consumer code on the {@link Schedulers#computation()}
     * scheduler. If you need your consumer to execute on a different thread, use
     * {@link #subscribe(Class, Consumer, Scheduler)} instead.
     * @param clazz The event type to consume.
     * @param consumer The {@link Consumer}.
     * @return The {@link PausableDisposableObserver} created for the consumer.
     */
    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    public <T> PausableDisposableObserver<T> subscribe(@NonNull final Class<T> clazz,
                                                       @NonNull final Consumer<T> consumer) {
        return subscribe(clazz, consumer, defaultThrowableConsumer, Schedulers.computation());
    }

    /**
     * Subscribes a {@link Consumer} for a specific event type and returns the
     * {@link PausableDisposableObserver} created for the consumer.
     * <p>
     * Note: by default, this method will execute consumer code on the {@link Schedulers#computation()}
     * scheduler. If you need your consumer to execute on a different thread, use
     * {@link #subscribe(Class, Consumer, Consumer, Scheduler)} instead.
     * @param clazz The event type to consume.
     * @param consumer The {@link Consumer}.
     * @param errorConsumer A {@link Consumer} to deal with exceptions.
     * @return The {@link PausableDisposableObserver} created for the consumer.
     */
    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    public <T> PausableDisposableObserver<T> subscribe(@NonNull final Class<T> clazz,
                                                       @NonNull final Consumer<T> consumer, @NonNull final Consumer<Throwable> errorConsumer) {
        return subscribe(clazz, consumer, errorConsumer, Schedulers.computation());
    }
}

