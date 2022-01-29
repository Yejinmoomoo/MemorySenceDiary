package com.foreknowledge.memorydiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    private final String dbName = "iotdb";
    private final String tableName = "member";

    LoginActivity.myDBHelper myHelper;
    SQLiteDatabase sqlDB;//쿼리문 수행용

    EditText idText;
    EditText passText;
    String Id;
    String Pass;

    SQLiteDatabase iotdb;
    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Button btn_cancel3=(Button) findViewById(R.id.btn_cancel3);
        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);

        myHelper=new LoginActivity.myDBHelper(this);
        btn_cancel3 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private static final String TAG = "JoinActivity";
    public void members(View v) {


        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("SELECT * FROM "+tableName+" WHERE id='"+idText.getText().toString()+"'   ;",null);
        //가입할려는 아이디가 디비에 이미 존재하는지 select문으로 조회, 조회가 되면 이미 존재하는 아이디이고 없을경우 회원가입 가능
        Log.d(TAG,"회원검색 카운트 "+cursor.getCount());
        if(cursor.getCount() == 0) {// 0이면 일치하는 정보없음 회원가입 가능
            Log.d(TAG, "아이디 없다 ");
            sqlDB.close();
            cursor.close();

            sqlDB = myHelper.getWritableDatabase();//쓰기전용db열기
            sqlDB.execSQL("INSERT INTO " + tableName + " VALUES(null,'" + idText.getText().toString() + "'," + passText.getText().toString() + ");");
            //insert문으로 회원 추가
            Toast.makeText(getApplicationContext(), "회원가입완료 ", Toast.LENGTH_LONG).show();
            sqlDB.close();
            cursor.close();
            /*로그인페이지로 이동 */
            Intent i1;
            i1 = new Intent(this, LoginActivity.class);
            startActivity(i1);
        }else{//중복되는 아이디가 존재 회원가입 실패
            sqlDB.close();
            cursor.close();
            Toast.makeText(getApplicationContext(),"중복된 아이디가 존재합니다. ",Toast.LENGTH_LONG).show();
        }



    }


    public class myDBHelper extends SQLiteOpenHelper {
        //테이블 생성
        public myDBHelper(Context context){
            super(context, "groupDB",null,1);
        }
        //onCreate()-테이블생성
        //onUpgrade()-테이블 초기화 후 생성


        @Override
        public void onCreate(SQLiteDatabase db) {//쿼리문 수행
            db.execSQL("CREATE TABLE IF NOT EXISTS member "
                    + " (idx INTEGER PRIMARY KEY AUTOINCREMENT , id CHAR(20) , pw CHAR(20) );");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+tableName);//groupTBL존재시 테이블 지움
            onCreate(db);//테이블 다시 생성

        }
    }



}