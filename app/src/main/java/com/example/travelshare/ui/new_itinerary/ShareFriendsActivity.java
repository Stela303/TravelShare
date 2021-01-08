package com.example.travelshare.ui.new_itinerary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.travelshare.MainActivity;
import com.example.travelshare.R;
import com.example.travelshare.data.model.Itinerary;
import com.example.travelshare.data.model.User;
import com.example.travelshare.library.Constant;
import com.example.travelshare.library.SingletonMap;
import com.example.travelshare.repository.UserRepository;
import com.example.travelshare.ui.adapter.UserSearchAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShareFriendsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<User> users;
    private ListView userListView;
    private ArrayList<User> userSelected;
    private ListView selectedListView;
    private UserRepository userRepository;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_friends);
        users=new ArrayList<>();
        userSelected =new ArrayList<>();
        userRepository= new UserRepository();
        userRepository.searchAllUsers(new getAllUsersOnCompleteListener());
        userListView= (ListView) findViewById(R.id.usersListView);
        searchView=(SearchView)findViewById(R.id.searchUserview) ;
        searchView.setOnQueryTextListener(this);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (userSelected.contains(users.get(position))) {
                    userSelected.remove(users.get(position));
                } else {
                    userSelected.add(users.get(position));
                }
                selectedListView.setAdapter(new UserSearchAdapter(getApplicationContext(), userSelected));
            }

        });

        selectedListView=(ListView)findViewById(R.id.selectedListView);
        share();
    }

    private class getAllUsersOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        private static final String TAG = "";

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {

            if (task.isSuccessful()) {

                QuerySnapshot querySnapshot = task.getResult();

                if (querySnapshot != null) {

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        User user=document.toObject(User.class);
                        user.setId(document.getId());
                        users.add(user);
                    }
                    userListView.setAdapter(new UserSearchAdapter(getApplicationContext(), users));
                } else {
                    Log.w(TAG, "Error getting documents: ", task.getException());
                }
            }
        }
    }

    public void share(){
        Button shareBtn = (Button) findViewById(R.id.btnShareWithFriends);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(User selected:userSelected){
                    System.out.println(selected.getName());
                    userRepository.share(selected,(Itinerary)SingletonMap.getInstance().get(Constant.ITINERARY_KEY),getApplicationContext());
                }
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()!=0){
            userRepository.searchUserByEmail(new getAllUsersOnCompleteListener(),newText);
        }

        return false;
    }

}