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
import ABS_GET_SET.LayerList;
import ABS_GET_SET.MainLocationSubFolder;
import ABS_GET_SET.Message;
import ABS_GET_SET.MessageChat;
import ABS_GET_SET.SelectedLocation;

import static ABS_HELPER.StringUtils.audit_answer;
import static ABS_HELPER.StringUtils.audit_answer_id;
import static ABS_HELPER.StringUtils.audit_answer_type;
import static ABS_HELPER.StringUtils.audit_assign_by;
import static ABS_HELPER.StringUtils.audit_due_date;
import static ABS_HELPER.StringUtils.audit_id;
import static ABS_HELPER.StringUtils.audit_layer_desc;
import static ABS_HELPER.StringUtils.audit_layer_title;
import static ABS_HELPER.StringUtils.audit_location_desc;
import static ABS_HELPER.StringUtils.audit_location_server_id;
import static ABS_HELPER.StringUtils.audit_location_title;
import static ABS_HELPER.StringUtils.audit_main_location_count;
import static ABS_HELPER.StringUtils.audit_main_location_decs;
import static ABS_HELPER.StringUtils.audit_main_location_id;
import static ABS_HELPER.StringUtils.audit_main_location_server_id;
import static ABS_HELPER.StringUtils.audit_main_location_title;
import static ABS_HELPER.StringUtils.audit_main_question_id;
import static ABS_HELPER.StringUtils.audit_main_question_server_id;
import static ABS_HELPER.StringUtils.audit_question;
import static ABS_HELPER.StringUtils.audit_question_server_id;
import static ABS_HELPER.StringUtils.audit_question_type;
import static ABS_HELPER.StringUtils.audit_sub_folder_count;
import static ABS_HELPER.StringUtils.audit_sub_folder_id;
import static ABS_HELPER.StringUtils.audit_sub_folder_name;
import static ABS_HELPER.StringUtils.audit_sub_folder_title;
import static ABS_HELPER.StringUtils.audit_sub_location_id;
import static ABS_HELPER.StringUtils.audit_sub_location_server_id;
import static ABS_HELPER.StringUtils.audit_sub_location_title;
import static ABS_HELPER.StringUtils.audit_sub_question_condition;
import static ABS_HELPER.StringUtils.audit_sub_question_server_id;
import static ABS_HELPER.StringUtils.audit_title;
import static ABS_HELPER.StringUtils.audit_work_status;
import static ABS_HELPER.StringUtils.chat_from_id;
import static ABS_HELPER.StringUtils.chat_msg;
import static ABS_HELPER.StringUtils.chat_msg_time;
import static ABS_HELPER.StringUtils.chat_msg_type;
import static ABS_HELPER.StringUtils.chat_room_id;
import static ABS_HELPER.StringUtils.chat_to_id;
import static ABS_HELPER.StringUtils.chat_user_email;
import static ABS_HELPER.StringUtils.chat_user_id;
import static ABS_HELPER.StringUtils.chat_user_msg;
import static ABS_HELPER.StringUtils.chat_user_name;
import static ABS_HELPER.StringUtils.chat_user_phone;
import static ABS_HELPER.StringUtils.chat_user_photo;
import static ABS_HELPER.StringUtils.chat_user_role;
import static ABS_HELPER.StringUtils.chat_user_time;
import static ABS_HELPER.StringUtils.ct_tb_audit_main_location;
import static ABS_HELPER.StringUtils.ct_tb_audit_question;
import static ABS_HELPER.StringUtils.ct_tb_audit_sub_location;
import static ABS_HELPER.StringUtils.ct_tb_audit_sub_questions;
import static ABS_HELPER.StringUtils.ct_tb_chat_msg_list;
import static ABS_HELPER.StringUtils.ct_tb_chat_user_list;
import static ABS_HELPER.StringUtils.ct_tb_list_audit;
import static ABS_HELPER.StringUtils.ct_tb_location_sub_folder;
import static ABS_HELPER.StringUtils.ct_tb_selected_main_location;
import static ABS_HELPER.StringUtils.ct_tb_sub_folder_explation_list;
import static ABS_HELPER.StringUtils.database_name;
import static ABS_HELPER.StringUtils.id;
import static ABS_HELPER.StringUtils.tb_audit_main_location;
import static ABS_HELPER.StringUtils.tb_audit_question;
import static ABS_HELPER.StringUtils.tb_audit_sub_location;
import static ABS_HELPER.StringUtils.tb_audit_sub_questions;
import static ABS_HELPER.StringUtils.tb_chat_msg_list;
import static ABS_HELPER.StringUtils.tb_chat_user_list;
import static ABS_HELPER.StringUtils.tb_list_audit;
import static ABS_HELPER.StringUtils.tb_location_sub_folder;
import static ABS_HELPER.StringUtils.tb_selected_main_location;
import static ABS_HELPER.StringUtils.tb_sub_folder_explation_list;
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
        sqLiteDatabase.execSQL(ct_tb_location_sub_folder);
        sqLiteDatabase.execSQL(ct_tb_selected_main_location);
        sqLiteDatabase.execSQL(ct_tb_sub_folder_explation_list);
        sqLiteDatabase.execSQL(ct_tb_chat_user_list);
        sqLiteDatabase.execSQL(ct_tb_chat_msg_list);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ct_tb_list_audit);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tb_chat_user_list);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tb_chat_msg_list);
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

    public void update_tb_list_audit(String mId, String mStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(audit_work_status, mStatus);
        db.update(tb_list_audit, values, audit_id + "=" + mId, null);
    }


    //Insert tb_audit_main_location
    public int insert_tb_audit_main_location(AuditMainLocation auditMainLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, auditMainLocation.getmStrUserId());
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


    //Insert tb_sub_folder_explation_list
    public void insert_tb_sub_folder_explation_list(LayerList layerList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, layerList.getmStrUserId());
        values.put(audit_id, layerList.getmStrAuditId());
        values.put(audit_layer_desc, layerList.getmStrLayerDesc());
        values.put(audit_layer_title, layerList.getmStrLayerTitle());
        values.put(audit_sub_folder_title, layerList.getmStrSubFolderTitle());
        values.put(audit_sub_folder_id, layerList.getmStrSubFolderId());
        values.put(audit_main_location_title, layerList.getmStrMainLocationTitle());
        values.put(audit_main_location_id, layerList.getmStrMainLocationId());
        db.insert(tb_sub_folder_explation_list, null, values);
    }


    public ArrayList<LayerList> get_all_tb_sub_folder_explation_list(String mId) {
        ArrayList<LayerList> mAuditList = new ArrayList<LayerList>();
        String selectQuery = "SELECT  * FROM  " + tb_sub_folder_explation_list + " WHERE " + audit_sub_folder_id + " = '" + mId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                LayerList layerList = new LayerList();
                layerList.setmStrId(c.getString((c.getColumnIndex(id))));
                layerList.setmStrUserId(c.getString((c.getColumnIndex(user_id))));
                layerList.setmStrAuditId(c.getString((c.getColumnIndex(audit_id))));
                layerList.setmStrLayerTitle(c.getString((c.getColumnIndex(audit_layer_title))));
                layerList.setmStrLayerDesc(c.getString((c.getColumnIndex(audit_layer_desc))));
                layerList.setmStrSubFolderTitle(c.getString((c.getColumnIndex(audit_sub_folder_title))));
                layerList.setmStrSubFolderId(c.getString((c.getColumnIndex(audit_sub_folder_id))));
                layerList.setmStrMainLocationTitle(c.getString((c.getColumnIndex(audit_main_location_title))));
                layerList.setmStrMainLocationId(c.getString((c.getColumnIndex(audit_main_location_id))));
                mAuditList.add(layerList);
            } while (c.moveToNext());
        }
        return mAuditList;

    }


    public void update_tb_sub_folder_explation_list(LayerList layerList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(audit_layer_title, layerList.getmStrLayerTitle());
        values.put(audit_layer_desc, layerList.getmStrLayerDesc());
        db.update(tb_sub_folder_explation_list, values, "id=" + layerList.getmStrId(), null);
    }

    public int get_data_count_tb_list_audit_status(String status) {
        String selectQuery = "SELECT  * FROM  " + tb_list_audit + " WHERE " + audit_work_status + " = '" + status + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c.getCount();
    }

    public void update_tb_location_sub_folder_count(String mFolder) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM  " + tb_location_sub_folder + " WHERE " + id + " = '" + mFolder + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        String mStr = "0";
        if (c.moveToFirst()) {
            do {
                mStr = c.getString((c.getColumnIndex(audit_sub_folder_count)));
            } while (c.moveToNext());
        }
        ContentValues values = new ContentValues();
        values.put(audit_sub_folder_count, Integer.parseInt(mStr) + 1 + "");
        db.update(tb_location_sub_folder, values, "id=" + mFolder, null);
    }


    public void update_tb_selected_main_location_count(String mFolder) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM  " + tb_selected_main_location + " WHERE " + audit_main_location_id + " = '" + mFolder + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        String mStr = "0";
        if (c.moveToFirst()) {
            do {
                mStr = c.getString((c.getColumnIndex(audit_main_location_count)));
            } while (c.moveToNext());
        }
        ContentValues values = new ContentValues();
        values.put(audit_main_location_count, Integer.parseInt(mStr) + 1 + "");
        db.update(tb_selected_main_location, values, audit_main_location_id + "=" + mFolder, null);
    }

//###################################################################


    //Delete tb_sub_folder_explation_list
    public void delete_tb_sub_folder_explation_list(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_sub_folder_explation_list, audit_sub_folder_id + "=" + id, null);
    }


    //Delete tb_sub_folder_explation_list
    public void delete_tb_sub_folder_single_row(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_sub_folder_explation_list, id + "=" + id, null);
    }

    //Delete tb_selected_main_location
    public void delete_tb_selected_main_location(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_selected_main_location, audit_main_location_id + "=" + id, null);
    }

    //Delete tb_location_sub_folder
    public void delete_tb_location_sub_folder(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_location_sub_folder, audit_main_location_id + "=" + id, null);
    }

    //Delete tb_sub_folder_explation_list
    public void delete_tb_sub_folder_explation_list_all(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_sub_folder_explation_list, audit_main_location_id + "=" + id, null);
    }


    //Insert tb_audit_sub_location
    public int insert_tb_audit_sub_location(AuditSubLocation auditSubLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, auditSubLocation.getmStrUserId());
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
        values.put(user_id, auditQuestion.getmStrUserId());
        values.put(audit_id, auditQuestion.getmStrAuditId());
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
        values.put(user_id, auditSubQuestion.getmStrUserId());
        values.put(audit_id, auditSubQuestion.getmStrAuditId());
        values.put(audit_main_question_id, auditSubQuestion.getmStrMainQuestionId());
        values.put(audit_main_question_server_id, auditSubQuestion.getmStrMainQuestionServerId());
        values.put(audit_sub_question_server_id, auditSubQuestion.getmStrSubQuestionServerId());
        values.put(audit_question, auditSubQuestion.getmStrQuestion());
        values.put(audit_answer, auditSubQuestion.getmStrAnswer());
        values.put(audit_answer_id, auditSubQuestion.getmStrAnswerId());
        values.put(audit_answer_type, auditSubQuestion.getmStrAnswerType());
        values.put(audit_sub_question_condition, auditSubQuestion.getmStrQuestionCondition());
        db.insert(tb_audit_sub_questions, null, values);
    }


    //Insert tb_location_sub_folder
    public int insert_tb_location_sub_folder(MainLocationSubFolder mainLocationSubFolder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, mainLocationSubFolder.getmStrUserId());
        values.put(audit_id, mainLocationSubFolder.getmStrAuditId());
        values.put(audit_main_location_id, mainLocationSubFolder.getmStrMainLocationId());
        values.put(audit_sub_folder_name, mainLocationSubFolder.getmStrSubFolderName());
        values.put(audit_sub_folder_count, mainLocationSubFolder.getmStrSubFolderCont());
        db.insert(tb_location_sub_folder, null, values);
        Cursor cur = db.rawQuery("select last_insert_rowid()", null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        return ID;
    }


    //Insert tb_chat_user_list
    public int insert_tb_chat_user_list(Message messageChat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, messageChat.getmStrUserId());
        values.put(chat_from_id, messageChat.getmUserMsgFrom());
        values.put(chat_to_id, messageChat.getmUserMsgTo());
        values.put(chat_user_id, messageChat.getmChatUserId());
        values.put(chat_user_name, messageChat.getmUserName());
        values.put(chat_user_photo, messageChat.getmUserPhoto());
        values.put(chat_user_msg, messageChat.getmUserLastMsg());
        values.put(chat_user_time, messageChat.getmUserMsgDate());
        values.put(chat_user_role, messageChat.getmUserRole());
        values.put(chat_user_email, messageChat.getmUserEmail());
        values.put(chat_user_phone, messageChat.getmUserPhone());
        db.insert(tb_chat_user_list, null, values);
        Cursor cur = db.rawQuery("select last_insert_rowid()", null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        return ID;

    }


    //Insert tb_chat_msg_list
    public void insert_tb_chat_msg_list(MessageChat messageChat) {
        System.out.println("<><><dbinsert");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, messageChat.getmUserId());
        values.put(chat_user_id, messageChat.getmChatUserId());
        values.put(chat_room_id, messageChat.getmChatRoomId());
        values.put(chat_from_id, messageChat.getmUserMsgFrom());
        values.put(chat_msg, messageChat.getMessage());
        values.put(chat_msg_time, messageChat.getmCreatedAt());
        values.put(chat_msg_type, messageChat.getType());
        db.insert(tb_chat_msg_list, null, values);

    }


    //Update tb_location_sub_folder
    public void update_tb_location_sub_folder(MainLocationSubFolder mainLocationSubFolder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(audit_sub_folder_name, mainLocationSubFolder.getmStrSubFolderName());
        values.put(audit_sub_folder_count, mainLocationSubFolder.getmStrSubFolderCont());
        db.update(tb_location_sub_folder, values, "id=" + mainLocationSubFolder.getmStrId(), null);
    }


    //Insert tb_selected_main_location
    public void insert_tb_selected_main_location(SelectedLocation selectedLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user_id, selectedLocation.getmStrUserId());
        values.put(audit_id, selectedLocation.getmStrAuditId());
        values.put(audit_main_location_title, selectedLocation.getmStrMainLocationTitle());
        values.put(audit_main_location_count, selectedLocation.getmStrMainLocationCount());
        values.put(audit_main_location_server_id, selectedLocation.getmStrMainLocationServerId());
        values.put(audit_main_location_id, selectedLocation.getmStrMainLocationLocalId());
        values.put(audit_main_location_decs, selectedLocation.getmStrMainLocationDesc());
        db.insert(tb_selected_main_location, null, values);
    }

    //Check data tb_selected_main_location
    public int get_data_count_tb_selected_main_location(String mId) {
        String selectQuery = "SELECT  * FROM  " + tb_audit_main_location + " WHERE " + audit_id + " = '" + mId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c.getCount();
    }


    public void update_tb_selected_main_location(SelectedLocation selectedLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(audit_main_location_count, selectedLocation.getmStrMainLocationCount());
        db.update(tb_selected_main_location, values, audit_main_location_id + "=" + selectedLocation.getmStrMainLocationLocalId(), null);
    }


    public ArrayList<SelectedLocation> get_all_tb_selected_main_location(String mId) {
        ArrayList<SelectedLocation> mAuditList = new ArrayList<SelectedLocation>();
        String selectQuery = "SELECT  * FROM  " + tb_selected_main_location + " WHERE " + audit_id + " = '" + mId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                SelectedLocation selectedLocation = new SelectedLocation();
                selectedLocation.setmStrId(c.getString((c.getColumnIndex(id))));
                selectedLocation.setmStrAuditId(c.getString((c.getColumnIndex(audit_id))));
                selectedLocation.setmStrUserId(c.getString((c.getColumnIndex(user_id))));
                selectedLocation.setmStrMainLocationTitle(c.getString((c.getColumnIndex(audit_main_location_title))));
                selectedLocation.setmStrMainLocationCount(c.getString((c.getColumnIndex(audit_main_location_count))));
                selectedLocation.setmStrMainLocationServerId(c.getString((c.getColumnIndex(audit_main_location_server_id))));
                selectedLocation.setmStrMainLocationLocalId(c.getString((c.getColumnIndex(audit_main_location_id))));
                selectedLocation.setmStrMainLocationDesc(c.getString((c.getColumnIndex(audit_main_location_decs))));
                mAuditList.add(selectedLocation);
            } while (c.moveToNext());
        }
        return mAuditList;

    }


    public ArrayList<MainLocationSubFolder> get_all_tb_location_sub_folder(String mStrId) {
        ArrayList<MainLocationSubFolder> mAuditList = new ArrayList<MainLocationSubFolder>();
        String selectQuery = "SELECT * FROM " + tb_location_sub_folder + " WHERE " + audit_main_location_id + " = '" + mStrId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        ///
        if (c.moveToFirst()) {
            do {
                MainLocationSubFolder mainLocationSubFolder = new MainLocationSubFolder();
                mainLocationSubFolder.setmStrId(c.getString((c.getColumnIndex(id))));
                mainLocationSubFolder.setmStrAuditId(c.getString((c.getColumnIndex(audit_id))));
                mainLocationSubFolder.setmStrUserId(c.getString((c.getColumnIndex(user_id))));
                mainLocationSubFolder.setmStrMainLocationId(c.getString((c.getColumnIndex(audit_main_location_id))));
                mainLocationSubFolder.setmStrSubFolderName(c.getString((c.getColumnIndex(audit_sub_folder_name))));
                mainLocationSubFolder.setmStrSubFolderCont(c.getString((c.getColumnIndex(audit_sub_folder_count))));
                mAuditList.add(mainLocationSubFolder);
            } while (c.moveToNext());
        }

        return mAuditList;
    }


    public boolean isExistNotification(String mId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor clientCur = db.rawQuery("SELECT * FROM " + tb_selected_main_location + " WHERE " + audit_main_location_id + " = '" + mId + "'", null);
        boolean exist = (clientCur.getCount() > 0);
        clientCur.close();
        return exist;
    }


    public int get_existing_location_count(String mId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int a = 0;
        Cursor clientCur = db.rawQuery("SELECT * FROM " + tb_selected_main_location + " WHERE " + audit_main_location_id + " = '" + mId + "'", null);
        if (clientCur.moveToFirst()) {
            do {
                a = Integer.parseInt(clientCur.getString((clientCur.getColumnIndex(audit_main_location_count))));
            } while (clientCur.moveToNext());
        }
        return a;
    }


    //get all tb_audit_main_location
    public ArrayList<AuditMainLocation> get_all_tb_audit_main_location(String mId) {
        ArrayList<AuditMainLocation> mAuditList = new ArrayList<AuditMainLocation>();
        String selectQuery = "SELECT  * FROM  " + tb_audit_main_location + " where " + audit_id + " = '" + mId + "'";
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
    public ArrayList<Audit> get_all_tb_list_audit(String mUserId, String mStatus) {
        ArrayList<Audit> mAuditList = new ArrayList<Audit>();
        String selectQuery = "SELECT  * FROM  " + tb_list_audit + " where " + user_id + " = " + mUserId + " AND " + audit_work_status + " = " + mStatus;
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


    //get all tb_chat_user_list
    public ArrayList<Message> get_all_tb_chat_user_list(String mUserId) {
        ArrayList<Message> mMsgList = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM  " + tb_chat_user_list + " where " + user_id + " = '" + mUserId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Message messageChat = new Message();
                messageChat.setmStrUserId(c.getString((c.getColumnIndex(user_id))));
                messageChat.setmChatUserId(c.getString((c.getColumnIndex(chat_user_id))));
                messageChat.setmUserName(c.getString((c.getColumnIndex(chat_user_name))));
                messageChat.setmUserMsgFrom(c.getString((c.getColumnIndex(chat_from_id))));
                messageChat.setmUserMsgTo(c.getString((c.getColumnIndex(chat_to_id))));
                messageChat.setmUserLastMsg(c.getString((c.getColumnIndex(chat_user_msg))));
                messageChat.setmUserMsgDate(c.getString((c.getColumnIndex(chat_user_time))));
                messageChat.setmUserRole(c.getString((c.getColumnIndex(chat_user_role))));
                messageChat.setmUserEmail(c.getString((c.getColumnIndex(chat_user_email))));
                messageChat.setmUserPhone(c.getString((c.getColumnIndex(chat_user_phone))));
                messageChat.setmUserPhoto(c.getString((c.getColumnIndex(chat_user_photo))));
                mMsgList.add(messageChat);
            } while (c.moveToNext());
        }
        return mMsgList;
    }


    //get all tb_chat_msg_list
    public ArrayList<MessageChat> get_all_tb_chat_msg_list(String mUserId) {
        ArrayList<MessageChat> mMsgList = new ArrayList<MessageChat>();
        String selectQuery = "SELECT  * FROM  " + tb_chat_msg_list + " where " + chat_user_id + " = '" + mUserId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                MessageChat messageChat = new MessageChat();
                messageChat.setmUserId(c.getString((c.getColumnIndex(user_id))));
                messageChat.setmChatUserId(c.getString((c.getColumnIndex(chat_user_id))));
                messageChat.setmUserMsgFrom(c.getString((c.getColumnIndex(chat_from_id))));
                messageChat.setmMessage(c.getString((c.getColumnIndex(chat_msg))));
                messageChat.setmCreatedAt(c.getString((c.getColumnIndex(chat_msg_time))));
                messageChat.setmType(Integer.parseInt(c.getString((c.getColumnIndex(chat_msg_type)))));
                messageChat.setmChatRoomId(Integer.parseInt(c.getString((c.getColumnIndex(chat_room_id)))));
                mMsgList.add(messageChat);
            } while (c.moveToNext());
        }
        return mMsgList;
    }


    //delete_tb_chat_msg_list
    public void delete_tb_chat_msg_list() {
        System.out.println("<><><dbdelete");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_chat_msg_list, null, null);

    }  //delete_tb_chat_msg_list

    public void delete_tb_chat_user_list() {
        System.out.println("<><><mChatIddelete");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_chat_user_list, null, null);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}