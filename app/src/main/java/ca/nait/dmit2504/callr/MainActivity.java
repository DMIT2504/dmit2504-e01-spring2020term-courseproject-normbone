package ca.nait.dmit2504.callr;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

private ListView mContactList;
private ContactDatabase mContactDatabase;
private EditText mNameInput;
private EditText mNumberInput;
private CheckBox mIgnoreBox;
private Button mAddButton;
private Button mDeleteButton;
private Button mUpdateButton;
private Button mCancelButton;
private Contact mContact;
private long mEditId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactList = findViewById(R.id.lv_activity_main_contacts);
        mNameInput = findViewById(R.id.et_activity_main_name_input);
        mNumberInput = findViewById(R.id.et_activity_main_number_input);
        mIgnoreBox = findViewById(R.id.cb_activity_main_ignore);
        mAddButton = findViewById(R.id.btn_activity_main_add_contact);
        mDeleteButton = findViewById(R.id.btn_activity_main_delete_contact);
        mUpdateButton = findViewById(R.id.btn_activity_main_update_contact);
        mCancelButton = findViewById(R.id.btn_activity_main_cancel);

        mDeleteButton.setVisibility(View.GONE);
        mUpdateButton.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.GONE);

        // Allows the user to select a contact from the list of contacts.  The user can then chose
        // to either change the name, number, ignore status or cancel the selection
        mContactList.setOnItemClickListener((parent, view, position, id) -> {
            mContact = mContactDatabase.findContact(id);
            if (mContact != null) {
                mEditId = id;

                mAddButton.setVisibility(View.GONE);
                mDeleteButton.setVisibility(View.VISIBLE);
                mUpdateButton.setVisibility(View.VISIBLE);
                mCancelButton.setVisibility(View.VISIBLE);

                mNameInput.setText(mContact.getName());
                mNumberInput.setText(mContact.getNumber());
                if (mContact.getIgnore().equals("ignore")){
                    mIgnoreBox.setChecked(true);
                }else {
                    mIgnoreBox.setChecked(false);
                }
            }
        });

        // Allow user to call the selected contact number
        mContactList.setOnItemLongClickListener((parent, view, position, id) -> {
            mContact = mContactDatabase.findContact(id);

            if (!mContact.getNumber().equals("")){
                Intent intent = new Intent(this , DialerActivity.class);
                intent.putExtra("phoneNumber", mContact.getNumber());
                startActivity(intent);
            }

            return true;
        });

        mContactDatabase = new ContactDatabase(this);

        rebindListView();

    }

    private void rebindListView(){
        Cursor dbCursor = mContactDatabase.getAllContacts();
        String[] fromFields = {"name", "number", "ignored"};
        int[] toViews = new int[]{
                R.id.tv_contact_list_name,
                R.id.tv_contact_list_number,
                R.id.tv_contact_list_ignore
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.contact_items,
                dbCursor,
                fromFields,
                toViews,
                0);
        mContactList.setAdapter(cursorAdapter);
    }

    public void onAddContact(View view) {
        String name = mNameInput.getText().toString();
        String number = mNumberInput.getText().toString();

        mContactDatabase.createContact(name, number, mIgnoreBox.isChecked());

        rebindListView();
    }

    public void onDeleteContact(View view) {
        mContactDatabase.deleteContact(mContact.getId());
        rebindListView();
        onCancelSelection(view);
    }

    public void onUpdateContact(View view) {
        String name = mNameInput.getText().toString();
        String number = mNumberInput.getText().toString();

        mContactDatabase.updateContact(mEditId, name, number, mIgnoreBox.isChecked());

        rebindListView();
    }

    public void onCancelSelection(View view) {
        mEditId = 0;
        mAddButton.setVisibility(View.VISIBLE);
        mDeleteButton.setVisibility(View.GONE);
        mUpdateButton.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.GONE);
    }
}