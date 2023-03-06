package com.thecodebarista.c196_studentscheduler.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Pojo representing the database table terms.
 */
@Entity (tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int termID;

    private String title;

    @ColumnInfo(name="start_date")
    private String startDt;

    @ColumnInfo(name="end_date")
    private String endDt;

    /**
     * Default empty Constructor
     */
    public Term() {
    }

    /**
     * All Term persisted member Constructor
     * @param termID Primary Key Term ID
     * @param title Term Title
     * @param startDt Term Start Date
     * @param endDt Term End Date
     */
    public Term(int termID, String title, String startDt, String endDt) {
        this.termID = termID;
        this.title = title;
        this.startDt = startDt;
        this.endDt = endDt;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
}
