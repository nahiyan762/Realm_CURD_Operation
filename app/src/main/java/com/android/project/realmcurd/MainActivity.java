package com.android.project.realmcurd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    EditText et_id,et_name,et_desig,et_dept;
    ListView listView;
    ArrayAdapter<Employee> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_dept = findViewById(R.id.et_dept);
        et_desig = findViewById(R.id.et_desig);
        listView = findViewById(R.id.lview);

        ButterKnife.bind(this);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }


    public void addData(View view){
        realm.beginTransaction();
        Employee employee = realm.createObject(Employee.class);
        employee.setId(et_id.getText().toString());
        employee.setName(et_name.getText().toString());
        employee.setDepartment(et_dept.getText().toString());
        employee.setDesignation(et_desig.getText().toString());
        realm.commitTransaction();

        clearData();
    }

    private void clearData() {
        et_id.setText("");
        et_name.setText("");
        et_dept.setText("");
        et_desig.setText("");
        adapter.notifyDataSetChanged();
    }

    public void readData(View view){

        RealmResults<Employee> employeeRealmResults = realm.where(Employee.class).findAll();
        adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_single_choice,employeeRealmResults);
        listView.setAdapter(adapter);
        clearData();
    }


    public void updateData(View view){
        realm.beginTransaction();
        RealmResults<Employee> employeeRealmResults = realm.where(Employee.class).equalTo("id", et_id.getText().toString()).findAll();
        employeeRealmResults.get(0).setName(et_name.getText().toString());
        employeeRealmResults.get(0).setDepartment(et_dept.getText().toString());
        employeeRealmResults.get(0).setDesignation(et_desig.getText().toString());
        realm.commitTransaction();
        clearData();
    }


    public void deleteData(View view){
        realm.beginTransaction();
        RealmResults<Employee> employeeRealmResults = realm.where(Employee.class).equalTo("id", et_id.getText().toString()).findAll();
        employeeRealmResults.deleteAllFromRealm();
        realm.commitTransaction();
        clearData();
    }
}
