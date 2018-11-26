package ABS_HELPER;

/**
 * Created by admin1 on 22/11/18.
 */

public class StringUtils {

    //CommonKeys For DataBase
    public static String database_name = "big_data_audit";




    //Keys For tb_list_audit

    public static String tb_list_audit = "tb_list_audit";
    public static String id = "id";
    public static String user_id = "user_id";
    public static String audit_id = "audit_id";
    public static String audit_assign_by = "audit_assign_by";
    public static String audit_title = "audit_title";
    public static String audit_due_date = "audit_due_date";
    public static String audit_work_status = "audit_work_status";


    //Queries For tb_list_audit

    public static String mDBStoreAudit = "CREATE TABLE "+tb_list_audit+" ("+id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+user_id+ " TEXT NOT NULL, "+audit_id+ " TEXT NOT NULL, "+audit_assign_by+ " TEXT NOT NULL, "+audit_title+ " TEXT NOT NULL, "+audit_due_date+ " TEXT NOT NULL, "+audit_work_status+ " TEXT NOT NULL " +");";




}
