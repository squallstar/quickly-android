package com.squallstar.quickly.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squallstar.quickly.R;

import java.util.List;

/**
 * Created by nicholas on 22/07/15.
 */
public class AppsAdapter extends BaseAdapter {
    private Context mContext;
    private PackageManager pm;
    private List<ResolveInfo> apps;

    public AppsAdapter(Context mContext, PackageManager pm, List<ResolveInfo> apps) {
        this.mContext = mContext;
        this.pm = pm;
        this.apps = apps;
    }

    public int getCount() {
        return apps.size();
    }

    public ResolveInfo getItem(int position) {
        return apps.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        ResolveInfo app = getItem(position);

        if (app == null) {
            return null;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.grid_app, parent, false);
        } else {
            view = (View) convertView;
        }

        TextView appName = (TextView) view.findViewById(R.id.appName);
        appName.setText(app.loadLabel(pm));

        ImageView icon = (ImageView) view.findViewById(R.id.appIcon);
        icon.setImageDrawable(app.loadIcon(pm));

        return view;
    }
}