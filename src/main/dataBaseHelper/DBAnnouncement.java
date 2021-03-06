package main.dataBaseHelper;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import static main.dataBaseHelper.dataBaseConVars.*;
import static main.dataBaseHelper.dataBaseConVars.dBResult;

public class DBAnnouncement {
    public String announID = null, instructorID= null, examID= null, msgBody= null, msgHead= null;
    public String tableName = "Announcement";
    public String tableId = "announID";

    public DBAnnouncement() {
    }

    public DBAnnouncement(String announID, String instructorID, String examID, String msgBody, String msgHead) {
        this.announID = announID;
        this.instructorID = instructorID;
        this.examID = examID;
        this.msgBody = msgBody;
        this.msgHead = msgHead;
    }

    public DBAnnouncement getById(String id) {
        startConnection();
        DBAnnouncement announcement = new DBAnnouncement();
        try {
            String query = String.format("select * from %s where announID = %s",tableName, id);
            dBResult = stmt.executeQuery(query);
            while (dBResult.next()) {
                announcement.announID = dBResult.getString("announID");
                announcement.instructorID = dBResult.getString("instructorID");
                announcement.examID = dBResult.getString("examID");
                announcement.msgBody = dBResult.getString("msgBody");
                announcement.msgHead = dBResult.getString("msgHead");
            }
        } catch (SQLException ex) {
            System.out.println("query error " + new Throwable().getStackTrace()[0].getMethodName() + " " + ex);
        }finally {
            close();
        }
        return announcement;
    }

    public int deleteById(String id){
        try {
            if(getById(id).announID == null){
                return NOT_FOUNDED;
            }
            startConnection();
            String query = String.format("DELETE FROM %s where %s = '%s'",tableName,tableId,id);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            System.out.println("query error " + new Throwable().getStackTrace()[0].getMethodName() + " " + ex);
        }finally {
            close();
        }
        return OK;
    }

    public Vector<DBAnnouncement> getByExamId(String id){
        startConnection();
        Vector<DBAnnouncement> v = new Vector<>();
        try {
            String query = String.format("select * from %s where examID = '%s' ",tableName, id);
            dBResult = stmt.executeQuery(query);
            while (dBResult.next()) {
                DBAnnouncement tem = new DBAnnouncement();
                tem.announID = dBResult.getString("announID");
                tem.instructorID = dBResult.getString("instructorID");
                tem.examID = dBResult.getString("examID");
                tem.msgBody = dBResult.getString("msgBody");
                tem.msgHead = dBResult.getString("msgHead");
                v.add(tem);
            }
        } catch (SQLException ex) {
            System.out.println("query error " + new Throwable().getStackTrace()[0].getMethodName() + " " + ex );
        }finally {
            close();
        }
        return v;
    }

    public int add(DBAnnouncement ann) {
        try {
            startConnection();
            String query = String.format("insert into %s (announID, instructorID, examID, msgBody, msgHead)" +
                    "values ('%s','%s','%s','%s','%s')",tableName,ann.announID,ann.instructorID,ann.examID,ann.msgBody,ann.msgHead);
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            System.out.println("add error " + new Throwable().getStackTrace()[0].getMethodName() + " " + ex);
        }finally {
            close();
        }
        return OK;
    }

    public String getAnnounID() {
        return announID;
    }

    public void setAnnounID(String announID) {
        this.announID = announID;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgHead() {
        return msgHead;
    }

    public void setMsgHead(String msgHead) {
        this.msgHead = msgHead;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}