package com.example.tuan9;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Music> arrayList;
    private CustomMusicAdapter adapter;
    private ListView songList;
    private ImageView imageNext, imageBack;
    private List<Music> UserSelection = new ArrayList<Music>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmusic);

        songList = (ListView) findViewById(R.id.songList);
        arrayList = new ArrayList<>();
        arrayList.add(new Music("How You Like That","BlackPink",R.raw.how_you_like_that));
        arrayList.add(new Music("Ice Cream","BlackPink, Selena Gomez ",R.raw.ice_cream));
        arrayList.add(new Music("Pretty Savage","BlackPink",R.raw.pretty_savage));
        arrayList.add(new Music("Bet You Wanna (Feat.Cardi B)","BlackPink",R.raw.bet_you_wanna));
        arrayList.add(new Music("Lovesick Girls","BlackPink",R.raw.lovesick_girls));
        arrayList.add(new Music("Crazy Over You","BlackPink",R.raw.crazy_over_you));
        arrayList.add(new Music("Love To Hate Me","BlackPink",R.raw.love_to_hate_me));
        arrayList.add(new Music("You Never Know","BlackPink",R.raw.you_never_know));

        adapter = new CustomMusicAdapter(this,R.layout.custom_music_item,arrayList);
        songList.setAdapter(adapter);
        songList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        songList.setMultiChoiceModeListener(modeListener);
        registerForContextMenu(songList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Toast.makeText(this,"Bạn vừa chọn menu Add New",Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Toast.makeText(this,"Bạn vừa chọn menu Edit ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"Bạn vừa chọn menu Delete ",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contexts_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Toast.makeText(this,"Bạn vừa chọn menu Add New",Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit:
                Toast.makeText(this,"Bạn vừa chọn menu Edit ",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                arrayList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);

        }
        return super.onContextItemSelected(item);
    }
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if (UserSelection.contains(arrayList.get(position))){
            UserSelection.remove(arrayList.get(position));
        }
        else {
            UserSelection.add(arrayList.get(position));
        }
        mode.setTitle(UserSelection.size() + "items selected...");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.contexts_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.delete:
                    adapter.removeItems(UserSelection);
                    mode.finish();
                    return  true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
UserSelection.clear();
        }
    };
}