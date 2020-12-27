package com.example.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.game.Adapters.RoomsDataAdapter;
import com.example.game.Adapters.UsersDataAdapter;
import com.example.game.Models.Game;
import com.example.game.Models.Room;
import com.example.game.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartGameActivity extends AppCompatActivity {
    private enum Role {PLAYER1, PLAYER2}

    private Toolbar toolbar;
    private EditText roomNameInput;
    private ProgressBar progressBar;

    private RecyclerView recyclerViewUsers;
    private RecyclerView recyclerViewRooms;
    private RecyclerView.LayoutManager layoutManagerUsers;
    private RecyclerView.LayoutManager layoutManagerRooms;
    private UsersDataAdapter usersDataAdapter;
    private RoomsDataAdapter roomsDataAdapter;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private List<User> users = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    String specialKey;
    private User selectedUser;
    private Room selectedRoom;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        roomNameInput = findViewById(R.id.roomNameInput);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.getUsersProgressBar);
        recyclerViewUsers = findViewById(R.id.usersList);
        recyclerViewRooms = findViewById(R.id.roomsList);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        readUsers();
        readRooms();

        UsersDataAdapter.OnUserClickListener userClickListener = (user, position) -> {
            selectedUser = user;
            Toast.makeText(getApplicationContext(), "You have chosen " + user.getUsername(), Toast.LENGTH_LONG).show();
        };
        layoutManagerUsers = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(layoutManagerUsers);
        usersDataAdapter = new UsersDataAdapter(this, users, userClickListener);
        recyclerViewUsers.setAdapter(usersDataAdapter);

        RoomsDataAdapter.OnRoomClickListener roomClickListener = (room, position) -> {
            selectedRoom = room;
            goToSelectedRoom();
            Toast.makeText(getApplicationContext(), "You have chosen " + room.getRoomName(), Toast.LENGTH_LONG).show();
        };
        layoutManagerRooms = new LinearLayoutManager(this);
        recyclerViewRooms.setLayoutManager(layoutManagerRooms);
        roomsDataAdapter = new RoomsDataAdapter(this, rooms, roomClickListener);
        recyclerViewRooms.setAdapter(roomsDataAdapter);

        setSupportActionBar(toolbar);
    }

    private void goToSelectedRoom() {
        progressBar.setVisibility(View.VISIBLE);
        wordInput();
    }

    private void startGame() {
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(selectedRoom.getId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room updatedRoom = snapshot.getValue(Room.class);
                if (updatedRoom == null)
                    return;
                Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
                if (currentUser.getUid().equals(updatedRoom.getPlayer1()))
                    intent.putExtra("role", Role.PLAYER1);
                else
                    intent.putExtra("role", Role.PLAYER2);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readRooms() {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rooms.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Room room = dataSnapshot.getValue(Room.class);
                    assert currentUser != null;
                    assert room != null;
                    if (room.getPlayer1().equals(currentUser.getUid()) || room.getPlayer2().equals(currentUser.getUid())) {
                        rooms.add(room);
                    }
                }
                roomsDataAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readUsers() {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    assert currentUser != null;
                    if (!user.getId().equals(currentUser.getUid())) {
                        users.add(user);
                    }
                }
                usersDataAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else if (id == R.id.settings) {
            startActivity(new Intent(getApplicationContext(), ProfileSettings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewRoom(View view) {
        if (selectedUser == null) {
            Toast.makeText(getApplicationContext(), "Please, choose a user to play with", Toast.LENGTH_LONG).show();
            return;
        }
        String roomName = roomNameInput.getText().toString();
        if (roomName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please, enter a room name", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        specialKey = databaseReference.child("rooms").push().getKey();
        assert specialKey != null;
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(specialKey);

        HashMap<String, Object> hashMap = new HashMap<>();
        assert firebaseUser != null;
        hashMap.put("id", specialKey);
        hashMap.put("player1", firebaseUser.getUid());
        hashMap.put("player2", selectedUser.getId());
        hashMap.put("roomName", roomName);
        hashMap.put("game1", new Game());
        hashMap.put("game2", new Game());
        databaseReference.setValue(hashMap);
    }

    public void wordInput() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.dialog_sets, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setView(promptsView);
        final EditText userInput = promptsView.findViewById(R.id.input_text);
        final TextView textView = promptsView.findViewById(R.id.tv);

        textView.setText("Input a word you want your opponent to guess");
        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, id) -> {
                            if (userInput.getText() == null) {
                                Toast.makeText(getApplicationContext(), "Please, input a word", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                return;
                            }
                            databaseReference = FirebaseDatabase.getInstance().getReference("rooms").child(selectedRoom.getId());
                            if (currentUser.getUid().equals(selectedRoom.getPlayer1())) {
                                databaseReference.child("game1").child("word").setValue(userInput.getText().toString());
                            } else {
                                databaseReference.child("game2").child("word").setValue(userInput.getText().toString());
                            }
                            startGame();
                            dialog.cancel();
                        })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }

}