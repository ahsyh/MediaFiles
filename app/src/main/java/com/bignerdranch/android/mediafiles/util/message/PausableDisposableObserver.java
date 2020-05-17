package com.bignerdranch.android.mediafiles.util.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Supplier;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * An extension of RxJava's {@link DisposableObserver}, which allows the observer to be paused.
 * @param <T> The event class to observe.
 */
public class PausableDisposableObserver<T> extends DisposableObserver<T> {

    @NonNull private final Consumer<T> consumer;
    @NonNull private final Consumer<Throwable> errorConsumer;
    @NonNull private final AtomicBoolean isPaused;

    /**
     * Constructor, which initializes the dependencies.
     * <p>
     * Construction of pausable disposable observers should be limited to the
     * {@code com.amazon.gallery.foundation.utils.messaging} package.
     * @param consumer The {@link Consumer} method.
     * @param errorConsumer A {@link Consumer} to handle {@link #onNext(Object)} exceptions.
     */
    PausableDisposableObserver(@NonNull final Consumer<T> consumer, @NonNull final Consumer<Throwable> errorConsumer) {
        this.consumer = consumer;
        this.errorConsumer = errorConsumer;
        this.isPaused = new AtomicBoolean(false);
    }

    @Override
    public void onNext(final T t) {
        if (!isPaused.get() && !isDisposed()) {
            try {
                consumer.accept(t);
            } catch (final Exception e) {
                onError(e);
            }
        }
    }

    @Override
    public void onError(final Throwable t) {
        try {
            errorConsumer.accept(t);
        } catch (final Exception e) {
            // The following line should never be reached (see xkcd/2200).
            throw new RuntimeException("An exception occurred in the observer's onError()", e);
        }
    }

    @Override
    public void onComplete() {
        // no-op
    }

    /**
     * Pauses the observer.
     * <p>
     * When the observer is paused, its {@link Consumer} will never be invoked.
     */
    public void pause() {
        isPaused.set(true);
    }

    /**
     * Resumes the observer.
     */
    public void resume() {
        isPaused.set(false);
    }

    /**
     * Returns whether the observer is paused or not.
     * @return {@code True} if the observer is paused and {@code False} otherwise.
     */
    public boolean isPaused() {
        return isPaused.get();
    }

    /**
     * Helper method that will call a {@link Supplier} and return its value if the {@code observer}
     * passed is {@code null} or disposed. Otherwise, the passed reference is returned.
     * @param observer The observer reference.
     * @param supplier The {@link Supplier}.
     * @param <T> The observer's event type.
     * @return A new observer, or the old one if it is still usable.
     */
    public static <T> PausableDisposableObserver<T> newIfDisposed(@Nullable final PausableDisposableObserver<T> observer,
                                                                  @NonNull final Supplier<PausableDisposableObserver<T>> supplier) {
        if (observer == null || observer.isDisposed()) {
            return supplier.get();
        }

        return observer;
    }
}
