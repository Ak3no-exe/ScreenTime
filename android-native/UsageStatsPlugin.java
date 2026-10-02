package com.screentimestats.app;

import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.provider.Settings;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.HashMap;
import java.util.Map;

@CapacitorPlugin(name = "UsageStats")
public class UsageStatsPlugin extends Plugin {

    private boolean hasAccess() {
        AppOpsManager o = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
        return o.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(),
                getContext().getPackageName()) == AppOpsManager.MODE_ALLOWED;
    }

    @PluginMethod
    public void hasAccess(PluginCall call) {
        JSObject r = new JSObject();
        r.put("granted", hasAccess());
        call.resolve(r);
    }

    @PluginMethod
    public void openSettings(PluginCall call) {
        Intent i = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(i);
        call.resolve();
    }

    @PluginMethod
    public void getDay(PluginCall call) {
        JSArray out = new JSArray();
        JSObject res = new JSObject();
        res.put("apps", out);
        Double ds = call.getDouble("start"), de = call.getDouble("end");
        if (!hasAccess() || ds == null || de == null) { call.resolve(res); return; }
        long end = de.longValue();
        UsageStatsManager usm = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents ev = usm.queryEvents(ds.longValue(), end);
        UsageEvents.Event x = new UsageEvents.Event();
        Map<String, Long> start = new HashMap<>(), total = new HashMap<>();
        Map<String, Integer> opens = new HashMap<>();
        while (ev.hasNextEvent()) {
            ev.getNextEvent(x);
            String p = x.getPackageName();
            int t = x.getEventType();
            if (t == UsageEvents.Event.ACTIVITY_RESUMED) {
                if (!start.containsKey(p)) opens.put(p, opens.getOrDefault(p, 0) + 1);
                start.put(p, x.getTimeStamp());
            } else if (t == UsageEvents.Event.ACTIVITY_PAUSED || t == UsageEvents.Event.ACTIVITY_STOPPED) {
                Long s = start.remove(p);
                if (s != null) total.put(p, total.getOrDefault(p, 0L) + (x.getTimeStamp() - s));
            }
        }
        for (Map.Entry<String, Long> e : start.entrySet())
            total.put(e.getKey(), total.getOrDefault(e.getKey(), 0L) + (end - e.getValue()));
        PackageManager pm = getContext().getPackageManager();
        for (Map.Entry<String, Long> e : total.entrySet()) {
            if (e.getValue() <= 0) continue;
            String label = e.getKey();
            try { label = pm.getApplicationLabel(pm.getApplicationInfo(e.getKey(), 0)).toString(); } catch (Exception ignored) {}
            JSObject a = new JSObject();
            a.put("pkg", e.getKey()); a.put("label", label); a.put("ms", e.getValue());
            a.put("opens", opens.getOrDefault(e.getKey(), 0));
            out.put(a);
        }
        call.resolve(res);
    }
                       }
