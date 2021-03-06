/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.db.entity;

import java.util.Date;

/**
 * Represents a change to an instance attribute.
 * @author Daniel Gomez Blanco
 */

public class InstanceChange {
    
    /**
     * Username creator of this job (max. 32)
     */
    private String username;

    /**
     * DB name of the instance (max. 8)
     */
    private String dbName;

    /**
     * Creation time of this change
     */
    private Date changeDate;

    /**
     * Username requester of this change (max. 32)
     */
    private String requester;
    
    /**
     * Attribute changed (max. 32)
     */
    private String attribute;

    /**
     * Old value
     */
    private String oldValue;

    /**
     * New value
     */
    private String newValue;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}
