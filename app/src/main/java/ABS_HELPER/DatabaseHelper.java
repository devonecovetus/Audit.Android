package ABS_HELPER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ABS_GET_SET.Audit;

import static ABS_HELPER.StringUtils.audit_assign_by;
import static ABS_HELPER.StringUtils.audit_due_date;
import static ABS_HELPER.StringUtils.audit_id;
import static ABS_HELPER.StringUtils.audit_title;
import static ABS_HELPER.StringUtils.audit_work_status;
import static ABS_HELPER.StringUtils.database_name;
import static ABS_HELPER.StringUtils.mDBStoreAudit;
import static ABS_HELPER.StringUtils.tb_list_audit;
import static ABS_HELPER.StringUtils.user_id;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(tb_list_audit);
        sqLiteDatabase.execSQL(mDBStoreAudit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tb_list_audit);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + mDBStoreAudit);
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