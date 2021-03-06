/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.db.dao;

import ch.cern.dbod.db.entity.*;
import ch.cern.dbod.util.CommonConstants;
import ch.cern.dbod.util.RestHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.http.ParseException;

/**
 * DAO for Instance entity.
 * @author Daniel Gomez Blanco
 * @author Jose Andres Cordero Benitez
 */
public class InstanceDAO {

    /**
     * Obtains a new connection from the pool.
     * @return a connection to the database.
     * @throws NamingException if the context cannot be found.
     * @throws SQLException if the datasource cannot be created.
     */
    private Connection getConnection() throws NamingException, SQLException {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup(CommonConstants.ENVIRONMENT_CONTEXT);
        DataSource dataSource = (DataSource) envContext.lookup(CommonConstants.DATA_SOURCE_DBOD);
        return dataSource.getConnection();
    }

    /**
     * Selects all the instances in the database. It takes the list of available
     * upgrades in order to decide if an instance needs upgrading or not.
     * @param upgrades list of available upgrades.
     * @return List of all the instances in the database.
     */
    public List<Instance> selectAll(String username, Map<String, Upgrade> upgrades) {
        ArrayList<Instance> instances = null;
        try {
            JsonObject authHeader = new JsonObject();
            authHeader.addProperty("owner", username);
            authHeader.addProperty("groups", "[]");
            authHeader.addProperty("admin", true);
            instances = RestHelper.getObjectListFromRestApi("api/v1/instance", null, Instance.class, authHeader.toString(), "response");
            
            for (Instance instance : instances) {
                //Check if instance needs upgrade
                if (upgrades != null) {
                    String key = instance.getDbType() + "$" + instance.getCategory() + "$" + instance.getVersion();
                    Upgrade upgrade = upgrades.get(key);
                    if (upgrade != null) {
                        instance.setUpgradeTo(upgrade.getVersionTo());
                    }
                }
            }
        } catch (IOException | IllegalStateException | ParseException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SELECTING INSTANCES FOR ADMIN", ex);
        }
        
        return instances;
    }
    
    /**
     * Selects all the instances to be destroyed. These are the instances with a
     * status of 0.
     * @return List of all the instances to be destroyed.
     */
    public List<Instance> selectToDestroy(String username) {
        ArrayList<Instance> instances = null;
        try {
            JsonObject authHeader = new JsonObject();
            authHeader.addProperty("owner", username);
            authHeader.addProperty("groups", "[]");
            authHeader.addProperty("admin", true);
            HashMap<String, String> params = new HashMap<>();
            params.put("status", "eq.NON_ACTIVE");
            instances = RestHelper.getObjectListFromRestApi("api/v1/instance", params, Instance.class, authHeader.toString(), "response");
        } catch (IOException | IllegalStateException | ParseException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SELECTING INSTANCES FOR ADMIN", ex);
        }
        
        return instances;
    }

    /**
     * Select the instances belonging to a username or to any of their e-groups.
     * It takes the list of available upgrades in order to decide if an instance needs upgrading or not.
     * @param username username to obtain instances.
     * @param egroups e-groups that the user belongs to.
     * @param upgrades upgrades available.
     * @return List of instances belonging to a username or to any of their e-groups.
     */
    public List<Instance> selectByUserNameAndEGroups(String username, String egroups, Map<String, Upgrade> upgrades) {
        ArrayList<Instance> instances = null;
        try {
            JsonObject authHeader = new JsonObject();
            authHeader.addProperty("owner", username);
            JsonArray eg = new JsonArray();
            eg.add(egroups);
            authHeader.add("groups", eg);
            authHeader.addProperty("admin", false);
            instances = RestHelper.getObjectListFromRestApi("api/v1/instance", null, Instance.class, authHeader.toString(), "response");

            for (Instance instance : instances) {
                //Check if instance needs upgrade
                if (upgrades != null) {
                    String key = instance.getDbType() + "$" + instance.getCategory() + "$" + instance.getVersion();
                    Upgrade upgrade = upgrades.get(key);
                    if (upgrade != null) {
                        instance.setUpgradeTo(upgrade.getVersionTo());
                    }
                }
            }
        } catch (IOException | IllegalStateException | ParseException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SELECTING INSTANCES FOR USERNAME " + username, ex);
        }
        
        return instances;
    }
    
    /**
     * Select a specific instance by its DB name. It takes the list of available
     * upgrades in order to decide if an instance needs upgrading or not.
     * @param dbName DB name of the instance.
     * @param upgrades upgrades available.
     * @return instance for the username and DB name specified.
     */
    public Instance selectByDbName(String dbName, Map<String, Upgrade> upgrades) {
        Instance instance = null;
        try {
            JsonObject object = RestHelper.getJsonObjectFromRestApi("api/v1/instance/" + dbName);
            if (object == null)
                return null;
            
            instance = RestHelper.fromJson(object.getAsJsonArray("response").get(0).getAsJsonObject(), Instance.class);
            
            JsonObject attributes = object.getAsJsonArray("response").get(0).getAsJsonObject().getAsJsonObject("attributes");
            if (attributes != null) {
                for (Entry<String, JsonElement> entry : attributes.entrySet()) {
                    instance.setAttribute(entry.getKey(), entry.getValue().getAsString());
                }
            }
            
            //Check if instance needs upgrade
            if (upgrades != null) {
                String key = instance.getDbType() + "$" + instance.getCategory() + "$" + instance.getVersion();
                Upgrade upgrade = upgrades.get(key);
                if (upgrade != null) {
                    instance.setUpgradeTo(upgrade.getVersionTo());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SELECTING INSTANCE FOR DB NAME " + dbName ,ex);
        }
        return instance;
    }

    /**
     * Inserts a new instance in the database. It also updates the slave attribute
     * in the master in case this instance has a master.
     * @param instance instance to be inserted.
     * @return 1 if the operation was successful, 0 otherwise.
     */
    public int insert(Instance instance) {
        Connection connection = null;
        PreparedStatement instanceStatement = null;
        PreparedStatement masterStatement = null;
        int instanceResult;
        int masterResult = 0;
        try {
            //Get connection
            connection = getConnection();
            //Set autocommit to false to execute multiple queries and rollback in case something goes wrong
            connection.setAutoCommit(false);
            
            //Prepare query for the prepared statement (to avoid SQL injection)
            String instanceQuery = "INSERT INTO dod_instances (username, db_name, e_group, category, creation_date, expiry_date, db_type, db_size, no_connections, project, description, version, state, status, master, slave, host)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            instanceStatement = connection.prepareStatement(instanceQuery);
            //Assign values to variables
            instanceStatement.setString(1, instance.getOwner());
            instanceStatement.setString(2, instance.getDbName());
            instanceStatement.setString(3, instance.getEGroup());
            instanceStatement.setString(4, instance.getCategory());
            instanceStatement.setDate(5, new java.sql.Date(instance.getCreationDate().getTime()));
            if (instance.getExpiryDate() != null)
                instanceStatement.setDate(6, new java.sql.Date(instance.getExpiryDate().getTime()));
            else
                instanceStatement.setDate(6, null);
            instanceStatement.setString(7, instance.getDbType());
            instanceStatement.setInt(8, instance.getDbSize());
            //instanceStatement.setInt(9, instance.getNoConnections());
            instanceStatement.setString(10, instance.getProject());
            instanceStatement.setString(11, instance.getDescription());
            instanceStatement.setString(12, instance.getVersion());
            instanceStatement.setString(13, instance.getState());
            instanceStatement.setString(14, instance.getStatus());
            instanceStatement.setString(15, instance.getMaster());
            instanceStatement.setString(16, instance.getSlave());
            instanceStatement.setString(17, instance.getHost());
            //Execute query
            instanceResult = instanceStatement.executeUpdate();
            
            if (instance.getMaster() != null && !instance.getMaster().isEmpty()) {
                //Update master in case the insert was successful
                if (instanceResult > 0) {
                    //Prepare query for the prepared statement (to avoid SQL injection)
                    String masterQuery = "UPDATE dod_instances SET slave = ?, master = NULL "
                                            + "WHERE db_name = ?";
                    masterStatement = connection.prepareStatement(masterQuery);
                    //Set values
                    masterStatement.setString(1, instance.getDbName());
                    masterStatement.setString(2, instance.getMaster());
                    //Execute query
                    masterResult = masterStatement.executeUpdate();
                    
                    //Check result
                    if (masterResult <= 0) {
                        connection.rollback();
                        return 0;
                    }
                }
            }
            else {
                masterResult = 1;
            }
            
            //Commit queries
            connection.commit();
            
        } catch (NamingException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR INSERTING INSTANCE FOR USERNAME " + instance.getOwner() + " AND DB_NAME " + instance.getDbName(), ex);
        } catch (SQLException ex) {
            try {
                //Rollback updates
                connection.rollback();
            }
            catch (SQLException ex1) {
                Logger.getLogger(JobDAO.class.getName()).log(Level.SEVERE, "ERROR ROLLING BACK INSERTING INSTANCE FOR USERNAME " + instance.getOwner() + " AND DB_NAME " + instance.getDbName(), ex1);
            }
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR INSERTING INSTANCE FOR USERNAME " + instance.getOwner() + " AND DB_NAME " + instance.getDbName(), ex);
        } finally {
            try {
                instanceStatement.close();
            } catch (Exception e) {}
            try {
                masterStatement.close();
            } catch (Exception e) {}
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {}
            try {
                connection.close();
            } catch (Exception e) {}
        }
        return masterResult;
    }

    /**
     * Deletes an instance from the database.
     * @param instance instance to be deleted.
     * @return 1 if the operation was successful, 0 otherwise.
     */
    public int delete(Instance instance) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        int deleteResult = 0;
        try {
            //Get connection
            connection = getConnection();
            //Create statement
            String deleteQuery = "DELETE FROM dod_instances WHERE db_name = ? AND username = ?";
            deleteStatement = connection.prepareStatement(deleteQuery);
            //Set values
            deleteStatement.setString(1, instance.getDbName());
            deleteStatement.setString(2, instance.getOwner());

            deleteResult = deleteStatement.executeUpdate();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR DELETING INSTANCE FOR USERNAME " + instance.getOwner() + " AND DB_NAME " + instance.getDbName(), ex);
        } finally {
            try {
                deleteStatement.close();
            } catch (Exception e) {}
            try {
                connection.close();
            } catch (Exception e) {}
        }
        return deleteResult;
    }
    
    /**
     * Updates an instance with new values.
     * @param oldInstance old values of the instance.
     * @param newInstance new values of the instance.
     * @param requester username requesting this, in order to log it.
     * @return 1 if the operation was successful, 0 otherwise.
     */
    public int update(Instance oldInstance, Instance newInstance, String requester) {
        int updateResult = 0;
        /**
         * Insert or update attributes
         */
        for (Entry<String, String> entry : newInstance.getAttributes().entrySet()) {
            if (oldInstance.getAttribute(entry.getKey()) == null) {
                // The attribute is not present in old copy of the instance -> Create it
                JsonObject postJson = new JsonObject();
                postJson.addProperty(entry.getKey(), entry.getValue());
                RestHelper.postJsonToRestApi(postJson, "api/v1/instance/" + newInstance.getDbName() + "/attribute");
            } else if (!oldInstance.getAttribute(entry.getKey()).equals(newInstance.getAttribute(entry.getKey()))) {
                // The attribute existed -> Update it
                RestHelper.putValueToRestApi(entry.getValue(), "api/v1/instance/" + newInstance.getDbName() + "/attribute/" + entry.getKey());
            }
        }

        JsonObject json = RestHelper.parseObject(RestHelper.toJson(newInstance));
        boolean restResult = RestHelper.putJsonToRestApi(json, "api/v1/instance/" + newInstance.getId());
        if (!restResult) {
            //throw new NamingException("Error updating attributes.");
            updateResult = 0;
        } else {
            updateResult = 1;
        }
        
        return updateResult;
    }
    
    /**
     * Swaps master and slave in the database.
     * @param newMaster new master.
     * @param newSlave new slave.
     * @return number of updated instances if the operation was successful, 0 otherwise.
     */
    public int swapMasterSlave(String newMaster, String newSlave) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            if (newMaster != null && newSlave != null) {
                //Get connection
                connection = getConnection();
                connection.setAutoCommit(false);

                //Prepare query for the prepared statement (to avoid SQL injection)
                String query = "UPDATE dod_instances SET master = ?, slave = ? WHERE db_name = ?";
                statement = connection.prepareStatement(query);
                
                //Add master to batch
                statement.setString(1, null);
                statement.setString(2, newSlave);
                statement.setString(3, newMaster);
                statement.addBatch();
                
                //Add slave to batch
                statement.setString(1, newMaster);
                statement.setString(2, null);
                statement.setString(3, newSlave);
                statement.addBatch();
                
                int[] results = statement.executeBatch();
                result = results.length;
                for (int i=0; i<results.length; i++){
                    if (results[i] == PreparedStatement.EXECUTE_FAILED) {
                        result = 0;
                        connection.rollback();                        
                        break;
                    }
                }
                connection.commit();
            }

        } catch (NamingException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SWAPPING MASTER/SLAVE", ex);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (Exception e) {
                Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR ROLLING BACK MASTER/SLAVE SWAP", ex);
            }
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SWAPPING MASTER/SLAVE", ex);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {}
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {}
            try {
                connection.close();
            } catch (Exception e) {}
        }
        return result;
    }
    
    /**
     * Obtains the list of changes to attributes of an instance.
     * @param instance DBOD instance to get the changes from.
     * @return list of changes for the specified instance.
     */
    public List<InstanceChange> selectInstanceChanges(Instance instance) {
        return new ArrayList<>();
    }
    
    /**
     * Rescues an instance and enables it back again. It changes the status from
     * 0 to 1.
     * @param instance instance to be deleted.
     * @return 1 if the operation was successful, 0 otherwise.
     */
    public int rescue(Instance instance) {
        Connection connection = null;
        PreparedStatement rescueStatement = null;
        int rescueResult = 0;
        try {
            //Get connection
            connection = getConnection();
            //Create statement
            String rescueQuery = "UPDATE dod_instances SET status = '1' WHERE db_name = ? AND username = ?";
            rescueStatement = connection.prepareStatement(rescueQuery);
            //Set values
            rescueStatement.setString(1, instance.getDbName());
            rescueStatement.setString(2, instance.getOwner());

            rescueResult = rescueStatement.executeUpdate();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR RESCUING INSTANCE FOR USERNAME " + instance.getOwner() + " AND DB_NAME " + instance.getDbName(), ex);
        } finally {
            try {
                rescueStatement.close();
            } catch (Exception e) {}
            try {
                connection.close();
            } catch (Exception e) {}
        }
        return rescueResult;
    }
    
    /**
     * Checks if an instance is on FIM.
     * @param instance instance to check.
     * @return true if the instance is on FIM, false otherwise.
     */
    public boolean isInstanceOnFIM(Instance instance) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            //Get connection
            connection = getConnection();

            //Prepare query for the prepared statement (to avoid SQL injection)
            StringBuilder query = new StringBuilder();
            query.append("SELECT *"
                            + " FROM fim_ora_ma.dod_fim_objects WHERE db_name = ?");
            statement = connection.prepareStatement(query.toString());

            //Assign values to variables
            statement.setString(1, instance.getDbName());

            //Execute query
            result = statement.executeQuery();

            //If there is no result return false
            if (!result.next()) {
                return false;
            }
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(InstanceDAO.class.getName()).log(Level.SEVERE, "ERROR SELECTING FIM OBJECT FOR DB NAME " + instance.getDbName() ,ex);
        } finally {
            try {
                result.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        
        //Return true in any other case
        //This is required, because an instance cannot be deleted if it is still
        //in FIM. This is just to check that there were no errors and the instance
        //is really not in FIM when deleting it permantenly.
        return true;
    }
}
