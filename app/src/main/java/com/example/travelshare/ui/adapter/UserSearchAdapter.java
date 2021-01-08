package com.example.travelshare.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.travelshare.R;
import com.example.travelshare.data.model.User;

import java.util.List;

public class UserSearchAdapter extends ArrayAdapter<User> {

        private final LayoutInflater layoutInflater;

        public UserSearchAdapter(Context context, List<User> users)
        {
            super(context, 0,users);
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // holder pattern
            Holder holder = null;
            if (convertView == null)
            {
                holder = new Holder();

                convertView = layoutInflater.inflate(R.layout.users_listview, null);
                holder.setTextViewUserName((TextView) convertView.findViewById(R.id.textViewUserName));
                convertView.setTag(holder);
            }
            else
            {
                holder = (Holder) convertView.getTag();
            }

            User user = getItem(position);
            holder.getTextViewUserName().setText(user.getEmail());
            return convertView;
        }

        static class Holder
        {
            public TextView getTextViewUserName() {
                return TextViewUserName;
            }

            public void setTextViewUserName(TextView textViewUserName) {
                TextViewUserName = textViewUserName;
            }

            TextView TextViewUserName;
            CheckBox checkBox;


            public CheckBox getCheckBox()
            {
                return checkBox;
            }
            public void setCheckBox(CheckBox checkBox)
            {
                this.checkBox = checkBox;
            }

        }
}
