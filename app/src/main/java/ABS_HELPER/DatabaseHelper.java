package ABS_HELPER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ABS_GET_SET.Audit;
import ABS_GET_SET.AuditMainLocation;
import ABS_GET_SET.AuditQuestion;
import ABS_GET_SET.AuditSubLocation;
import ABS_GET_SET.AuditSubQuestion;

import static ABS_HELPER.StringUtils.audit_answer;
import static ABS_HELPER.StringUtils.audit_answer_id;
import static ABS_HELPER.StringUtils.audit_answer_type;
import static ABS_HELPER.StringUtils.audit_assign_by;
import static ABS_HELPER.StringUtils.audit_due_date;
import static ABS_HELPER.StringUtils.audit_id;
import static ABS_HELPER.StringUtils.audit_location_desc;
import static ABS_HELPER.StringUtils.audit_location_id;
import static ABS_HELPER.StringUtils.audit_location_server_id;
import static ABS_HELPER.StringUtils.audit_location_title;
import static ABS_HELPER.StringUtils.audit_main_location_id;
import static ABS_HELPER.StringUtils.audit_main_question_id;
import static ABS_HELPER.StringUtils.audit_main_question_server_id;
import static ABS_HELPER.StringUtils.audit_question;
import static ABS_HELPER.StringUtils.audit_question_server_id;
import static ABS_HELPER.StringUtils.audit_question_type;
import static ABS_HELPER.StringUtils.audit_sub_location_id;
import static ABS_HELPER.StringUtils.audit_sub_location_server_id;
import static ABS_HELPER.StringUtils.audit_sub_location_title;
import static ABS_HELPER.StringUtils.audit_sub_question_condition;
import static ABS_HELPER.StringUtils.audit_sub_question_server_id;
import static ABS_HELPER.StringUtils.audit_title;
import static ABS_HELPER.StringUtils.audit_work_status;
import static ABS_HELPER.StringUtils.ct_tb_audit_main_location;
import static ABS_HELPER.StringUtils.ct_tb_audit_question;
import static ABS_HELPER.StringUtils.ct_tb_audit_sub_location;
import static ABS_HELPER.StringUtils.ct_tb_audit_sub_questions;
import static ABS_HELPER.StringUtils.ct_tb_list_audit;
import static ABS_HELPER.StringUtils.database_name;
import static ABS_HELPER.StringUtils.id;
import static ABS_HELPER.StringUtils.tb_audit_main_location;
import static ABS_HELPER.StringUtils.tb_audit_question;
import static ABS_HELPER.StringUtils.tb_audit_sub_location;
import static ABS_HELPER.StringUtils.tb_audit_sub_questions;
import static ABS_HELPER.StringUtils.tb_list_audit;
import static ABS_HELPER.StringUtils.user_id;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(tb_list_audit);
        sqLiteDatabase.execSQL(ct_tb_list_audit);
        sqLiteDatabase.execSQL(ct_tb_audit_main_location);
        sqLiteDatabase.execSQL(ct_tb_audit_sub_location);
        sqLiteDatabase.execSQL(ct_tb_audit_question);
        sqLiteDatabase.execSQL(ct_tb_audit_sub_questions);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ct_tb_list_audit);
        onCreate(sqLiteDatabase);
    }

    //Insert tb_list_audit
    public void insert_tb_list_audit(Audit audit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, audit.getmUserId());
        values.put(audit_id, audit.getmAuditId());
        values.put(audit_assign_by, audit.getmAssignBy());
        values.put(audit_title, audit.getmTitle());
        values.put(audit_due_date, audit.getmDueDate());
        values.put(audit_work_status, audit.getmStatus());
        db.insert(tb_list_audit, null, values);
    }


    //Insert tb_audit_main_location
    public int insert_tb_audit_main_location(AuditMainLocation auditMainLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id,auditMainLocation.getmStrUserId());
        values.put(audit_id, auditMainLocation.getmStrAuditId());
        values.put(audit_location_title, auditMainLocation.getmStrLocationTitle());
        values.put(audit_location_desc, auditMainLocation.getmStrLocationDesc());
        values.put(audit_location_server_id, auditMainLocation.getmStrLocationServerId());
        db.insert(tb_audit_main_location, null, values);
        Cursor cur = db.rawQuery("select last_insert_rowid()", null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        return ID;
    }



    //Insert tb_audit_sub_location
    public int insert_tb_audit_sub_location(AuditSubLocation auditSubLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id,auditSubLocation.getmStrUserId());
        values.put(audit_id, auditSubLocation.getmStrAuditId());
        values.put(audit_main_location_id, auditSubLocation.getmStrMainLocationId());
        values.put(audit_sub_location_server_id, auditSubLocation.getmStrSubLocationServerId());
        values.put(audit_sub_location_title, auditSubLocation.getmStrSubLocationTitle());
        db.insert(tb_audit_sub_location, null, values);
        Cursor cur = db.rawQuery("select last_insert_rowid()", null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        return ID;
    }


    //Insert tb_audit_question
    public int insert_tb_audit_question(AuditQuestion auditQuestion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id,auditQuestion.getmStrUserId());
        values.put(audit_id,auditQuestion.getmStrAuditId());
        values.put(audit_main_location_id, auditQuestion.getmStrMainLocationId());
        values.put(audit_sub_location_id, auditQuestion.getmStrSubLocationId());
        values.put(audit_question_server_id, auditQuestion.getmStrQuestionServerId());
        values.put(audit_question, auditQuestion.getmStrQuestion());
        values.put(audit_answer, auditQuestion.getmStrAnswer());
        values.put(audit_answer_id, auditQuestion.getmStrAnswerId());
        values.put(audit_answer_type, auditQuestion.getmStrQuestionType());
        values.put(audit_question_type, auditQuestion.getmStrQuestionType());
        db.insert(tb_audit_question, null, values);
        Cursor cur = db.rawQuery("select last_insert_rowid()", null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        return ID;
    }

    //Insert tb_audit_sub_questions
    public void insert_tb_audit_sub_questions(AuditSubQuestion auditSubQuestion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id,auditSubQuestion.getmStrUserId());
        values.put(audit_id,auditSubQuestion.getmStrAuditId());
        values.put(audit_main_question_id, auditSubQuestion.getmStrMainQuestionId());
        values.put(audit_main_question_server_id,auditSubQuestion.getmStrMainQuestionServerId());
        values.put(audit_sub_question_server_id, auditSubQuestion.getmStrSubQuestionServerId());
        values.put(audit_question,auditSubQuestion.getmStrQuestion());
        values.put(audit_answer, auditSubQuestion.getmStrAnswer());
        values.put(audit_answer_id, auditSubQuestion.getmStrAnswerId());
        values.put(audit_answer_type,auditSubQuestion.getmStrAnswerType());
        values.put(audit_sub_question_condition,auditSubQuestion.getmStrQuestionCondition());
        db.insert(tb_audit_sub_questions, null, values);
    }


    //get all tb_audit_main_location
    public ArrayList<AuditMainLocation> get_all_tb_audit_main_location() {
        ArrayList<AuditMainLocation> mAuditList = new ArrayList<AuditMainLocation>();
        String selectQuery = "SELECT  * FROM  " + tb_audit_main_location;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                AuditMainLocation auditMainLocation = new AuditMainLocation();
                auditMainLocation.setmStrId(c.getString((c.getColumnIndex(id))));
                auditMainLocation.setmStrAuditId(c.getString((c.getColumnIndex(audit_id))));
                auditMainLocation.setmStrUserId(c.getString((c.getColumnIndex(user_id))));
                auditMainLocation.setmStrLocationTitle(c.getString((c.getColumnIndex(audit_location_title))));
                auditMainLocation.setmStrLocationDesc(c.getString((c.getColumnIndex(audit_location_desc))));
                auditMainLocation.setmStrLocationServerId(c.getString((c.getColumnIndex(audit_location_server_id))));
                mAuditList.add(auditMainLocation);
            } while (c.moveToNext());
        }
        return mAuditList;
    }



    //get all tb_list_audit
    public ArrayList<Audit> get_all_tb_list_audit(String mUserId) {
        ArrayList<Audit> mAuditList = new ArrayList<Audit>();
        String selectQuery = "SELECT  * FROM  " + tb_list_audit + " where " + user_id + " = '" + mUserId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Audit audit = new Audit();
                audit.setmAuditId(c.getString((c.getColumnIndex(audit_id))));
                audit.setmAssignBy(c.getString((c.getColumnIndex(audit_assign_by))));
                audit.setmDueDate(c.getString((c.getColumnIndex(audit_due_date))));
                audit.setmTitle(c.getString((c.getColumnIndex(audit_title))));
                audit.setmStatus(c.getString((c.getColumnIndex(audit_work_status))));
                mAuditList.add(audit);
            } while (c.moveToNext());
        }
        return mAuditList;
    }






 /*   //get single data
    public String getNotificationName(String id) {
        Cursor cursor = null;
        String empName = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT " + KEY_NOTIFICATION_NAME + " FROM " + TABLE_NOTIFICATION + " WHERE " + KEY_NOTIFICATION_ID + "=?", new String[]{id + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                empName = cursor.getString(cursor.getColumnIndex(KEY_NOTIFICATION_NAME));
            }
            return empName;
        } finally {
            cursor.close();
        }
    }*/

    //for upadte table
  /*  public boolean isExistNotification(String notificationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor clientCur = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATION + " WHERE mAuditId = '" + notificationId + "'", null);
        boolean exist = (clientCur.getCount() > 0);
        clientCur.close();
        return exist;
    }*/

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}