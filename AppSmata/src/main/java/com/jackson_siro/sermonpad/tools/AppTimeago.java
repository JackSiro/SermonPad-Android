package com.jackson_siro.sermonpad.tools;

import java.util.Date;

import com.jackson_siro.sermonpad.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;

public class AppTimeago {

    protected Context context;

    public AppTimeago(Context context) {
        this.context = context;
    }

    public String timeAgo(Date date) {
        return timeAgo(date.getTime());
    }

    @SuppressLint("StringFormatMatches")
    public String timeAgo(long millis) {
        long diff = new Date().getTime() - millis;

        Resources r = context.getResources();

        String prefix = r.getString(R.string.time_ago_prefix);
        String suffix = r.getString(R.string.time_ago_suffix);

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String finaltime;

        if (seconds < 45) {
            finaltime = r.getString(R.string.time_ago_seconds, Math.round(seconds));
        } else if (seconds < 90) {
            finaltime = r.getString(R.string.time_ago_minute, 1);
        } else if (minutes < 45) {
            finaltime = r.getString(R.string.time_ago_minutes, Math.round(minutes));
        } else if (minutes < 90) {
            finaltime = r.getString(R.string.time_ago_hour, 1);
        } else if (hours < 24) {
            finaltime = r.getString(R.string.time_ago_hours, Math.round(hours));
        } else if (hours < 42) {
            finaltime = r.getString(R.string.time_ago_day, 1);
        } else if (days < 30) {
            finaltime = r.getString(R.string.time_ago_days, Math.round(days));
        } else if (days < 45) {
            finaltime = r.getString(R.string.time_ago_month, 1);
        } else if (days < 365) {
            finaltime = r.getString(R.string.time_ago_months, Math.round(days / 30));
        } else if (years < 1.5) {
            finaltime = r.getString(R.string.time_ago_year, 1);
        } else {
            finaltime = r.getString(R.string.time_ago_years, Math.round(years));
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(finaltime);

        if (suffix != null && suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }

        return sb.toString().trim();
    }
}