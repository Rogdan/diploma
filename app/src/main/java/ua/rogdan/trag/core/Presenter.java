package ua.rogdan.trag.core;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Base Presenter implementation by Artem Zinnatullin
 * https://github.com/artem-zinnatullin/qualitymatters/blob/master/app/src/main/java/com/artemzin/qualitymatters/ui/presenters/Presenter.java
 *
 * @param <V> view.
 */
public abstract class Presenter<V> {

    @NonNull
    protected final CompositeSubscription subscriptionsToUnbind = new CompositeSubscription();

    @Nullable
    private volatile V view;

    @CallSuper
    public void bindView(@NonNull V view) {
        final V previousView = this.view;
        if (previousView != null) {
            throw new IllegalStateException("Previous view is not unbounded! previousView = " + previousView);
        }

        this.view = view;
    }

    @Nullable
    protected V view() {
        return view;
    }

    protected final void unsubscribeOnUnbindView(@NonNull Subscription subscription, @NonNull Subscription... subscriptions) {
        subscriptionsToUnbind.add(subscription);

        for (Subscription s : subscriptions) {
            subscriptionsToUnbind.add(s);
        }
    }

    public void cancelSubscribe() {
        subscriptionsToUnbind.clear();
    }


    @CallSuper
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public void unbindView(@NonNull V view) {

        final V previousView = this.view;

        if (previousView == view) {
            this.view = null;
        } else {
            throw new IllegalStateException("Unexpected view! previousView = " + previousView + ", view to unbind = " + view);
        }


        // Unsubscribe all subscriptions that need to be unsubscribed in this lifecycle state.
        subscriptionsToUnbind.clear();
    }

}
