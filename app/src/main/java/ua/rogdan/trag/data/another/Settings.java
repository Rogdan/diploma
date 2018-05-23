package ua.rogdan.trag.data.another;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {
    @SerializedName("notify_about_tickets")
    @Expose
    private boolean isTicketNotificationOn;

    @SerializedName("notify_about_referral")
    @Expose
    private boolean isReferalNotificationOn;

    public boolean isTicketNotificationOn() {
        return isTicketNotificationOn;
    }

    public void setTicketNotificationOn(boolean ticketNotificationOn) {
        isTicketNotificationOn = ticketNotificationOn;
    }

    public boolean isReferalNotificationOn() {
        return isReferalNotificationOn;
    }

    public void setReferalNotificationOn(boolean referalNotificationOn) {
        isReferalNotificationOn = referalNotificationOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settings settings = (Settings) o;

        return isTicketNotificationOn == settings.isTicketNotificationOn && isReferalNotificationOn == settings.isReferalNotificationOn;
    }
}
