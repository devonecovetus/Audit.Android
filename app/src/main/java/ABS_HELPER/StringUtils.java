package ABS_HELPER;

/**
 * Created by admin1 on 22/11/18.
 */

public class StringUtils {

    //CommonKeys For DataBase
    public static String database_name = "big_data_audit";

    //CommonKeys For All Table
    public static String id = "id";
    public static String user_id = "user_id";
    public static String audit_id = "audit_id";


    //Keys For tb_list_audit
    public static String tb_list_audit = "tb_list_audit";
    public static String audit_assign_by = "audit_assign_by";
    public static String audit_title = "audit_title";
    public static String audit_due_date = "audit_due_date";
    public static String audit_work_status = "audit_work_status";


    //Keys For tb_audit_main_location
    public static String tb_audit_main_location = "tb_audit_main_location";
    public static String audit_location_title = "location_title";
    public static String audit_location_id = "location_id";
    public static String audit_location_desc = "location_desc";
    public static String audit_location_server_id = "location_server_id";

    //Keys For tb_audit_sub_location
    public static String tb_audit_sub_location = "tb_audit_sub_location";
    public static String audit_main_location_id = "main_location_id";
    public static String audit_sub_location_server_id = "sub_location_server_id";
    public static String audit_sub_location_title = "sub_location_title";

    //Keys For tb_audit_question
    public static String tb_audit_question = "tb_audit_question";
    public static String audit_question_server_id = "question_server_id";
    public static String audit_question_type = "question_type";
    public static String audit_sub_location_id = "sub_location_id";

    //Common Keys For tb_audit_question And tb_audit_sub_questions
    public static String audit_question = "question";
    public static String audit_answer = "answer";
    public static String audit_answer_id = "answer_id";
    public static String audit_answer_type = "answer_type";

    //Keys For tb_audit_sub_questions
    public static String tb_audit_sub_questions = "tb_audit_sub_questions";
    public static String audit_main_question_id = "main_question_id";
    public static String audit_main_question_server_id = "main_question_server_id";
    public static String audit_sub_question_server_id = "question_id";
    public static String audit_sub_question_condition = "question_condition";

    //Keys For tb_location_sub_folder
    public static String audit_sub_folder_count = "sub_folder_count";
    public static String audit_sub_folder_name = "sub_folder_name";
    public static String tb_location_sub_folder = "tb_location_sub_folder";




    //Keys For tb_selected_main_location
    public static String tb_selected_main_location = "tb_selected_main_location";
    public static String audit_main_location_title = "main_location_title";
    public static String audit_main_location_count = "main_location_count";
    public static String audit_main_location_server_id = "main_location_server_id";
    public static String audit_main_location_decs = "main_location_decs";


    //Keys For tb_sub_folder_explation_list
    public static String tb_sub_folder_explation_list = "tb_sub_folder_explation_list";
    public static String audit_sub_folder_id = "sub_folder_id";
    public static String audit_sub_folder_title = "sub_folder_title";
    public static String audit_layer_title = "layer_title";
    public static String audit_layer_desc = "layer_desc";







    //CREATE TABLE Queries
    public static String ct_tb_list_audit = "CREATE TABLE "+tb_list_audit+" ("+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+user_id+ " TEXT NOT NULL, "+audit_id+ " TEXT NOT NULL, "+audit_assign_by+ " TEXT NOT NULL, "+audit_title+ " TEXT NOT NULL, "+audit_due_date+ " TEXT NOT NULL, "+audit_work_status+ " TEXT NOT NULL )";
    public static String ct_tb_audit_main_location = "CREATE TABLE "+tb_audit_main_location+" ("+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+audit_id+" TEXT NOT NULL, "+user_id+" TEXT NOT NULL, "+audit_location_title+" TEXT NOT NULL, "+audit_location_desc+" TEXT NOT NULL, "+audit_location_server_id+" TEXT NOT NULL )";
    public static String ct_tb_audit_sub_location = "CREATE TABLE "+tb_audit_sub_location+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+user_id+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL, "+audit_main_location_id+" TEXT NOT NULL, "+audit_sub_location_server_id+" TEXT NOT NULL, "+audit_sub_location_title+" TEXT NOT NULL )";
    public static String ct_tb_audit_question = "CREATE TABLE "+tb_audit_question+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+audit_question_server_id+" TEXT NOT NULL, "+audit_question+" TEXT NOT NULL, "+audit_answer+" TEXT NOT NULL, "+audit_main_location_id+" TEXT NOT NULL, "+audit_answer_id+" TEXT NOT NULL, "+audit_sub_location_id+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL, "+user_id+" TEXT NOT NULL, "+audit_answer_type+" TEXT NOT NULL, "+audit_question_type+" TEXT NOT NULL )";
    public static String ct_tb_audit_sub_questions = "CREATE TABLE "+tb_audit_sub_questions+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+user_id+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL, "+audit_main_question_id+" TEXT NOT NULL, "+audit_main_question_server_id+" TEXT NOT NULL, "+audit_sub_question_server_id+" TEXT NOT NULL, "+audit_question+" TEXT NOT NULL, 'answer' TEXT NOT NULL, "+audit_answer_id+" TEXT NOT NULL, "+audit_answer_type+" TEXT NOT NULL, "+audit_sub_question_condition+" TEXT NOT NULL )";
    public static String ct_tb_location_sub_folder = "CREATE TABLE "+tb_location_sub_folder+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+audit_main_location_id+" TEXT NOT NULL, "+audit_sub_folder_name+" TEXT NOT NULL, "+audit_sub_folder_count+" TEXT NOT NULL, "+user_id+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL )";
    public static String ct_tb_selected_main_location = "CREATE TABLE "+tb_selected_main_location+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+audit_main_location_id+" TEXT NOT NULL, "+audit_main_location_title+" TEXT NOT NULL, "+audit_main_location_server_id+" TEXT NOT NULL, "+audit_main_location_count+" TEXT NOT NULL, "+user_id+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL, "+audit_main_location_decs+" TEXT NOT NULL )";
    public static String ct_tb_sub_folder_explation_list = "CREATE TABLE "+tb_sub_folder_explation_list+" ( "+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+audit_main_location_id+" TEXT NOT NULL, "+audit_main_location_title+" TEXT NOT NULL, "+audit_sub_folder_id+" TEXT NOT NULL, "+audit_sub_folder_title+" TEXT NOT NULL, "+audit_layer_title+" TEXT NOT NULL, "+audit_layer_desc+" TEXT NOT NULL, "+audit_id+" TEXT NOT NULL, "+user_id+" TEXT NOT NULL )";







            


            


}
