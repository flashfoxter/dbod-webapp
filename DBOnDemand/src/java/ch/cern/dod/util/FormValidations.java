package ch.cern.dod.util;

import ch.cern.dod.db.dao.DODInstanceDAO;
import ch.cern.dod.db.entity.DODInstance;
import ch.cern.dod.ws.authentication.UserInfo;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.rpc.ServiceException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

/**
 * Implements static functions to validate fields.
 * @author Daniel Gomez Blanco
 */
public class FormValidations {
    /**
     * Validates username.
     * @param username Textbox with the username to validate.
     * @param authenticationHelper Helper to use authentication web services.
     * @return true if username is valid, false otherwise.
     */
    public static UserInfo isUsernameValid(Textbox username, AuthenticationHelper authenticationHelper) {
        //If there are no previous errors
        if (username.getErrorMessage() == null || username.getErrorMessage().isEmpty()) {
            //Trim and lowercase
            username.setValue(username.getValue().trim().toLowerCase());
            //Check if user has entered a value
            if (username.getValue().isEmpty()) {
                username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_EMPTY));
                return null;
            }
            //Check dbName length
            if (username.getValue().length() > DODConstants.MAX_USERNAME_LENGTH) {
                username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_LENGTH));
                return null;
            }
            //ASCII digits and non-digits
            if (!Pattern.matches("[a-z]*", username.getValue())) {
                username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_CHARS));
                return null;
            }
            //Check if the user exists
            try {              
                UserInfo info = authenticationHelper.getUserInfo(username.getValue());
                if (info != null && info.getCcid() > 0) {
                    return info;
                }
                else {
                    username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_NOT_FOUND));
                    return null;
                }
            } catch (RemoteException ex) {
                username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_WS));
                Logger.getLogger(FormValidations.class.getName()).log(Level.SEVERE, "ERROR OBTAINING INFO FOR USER " + username.getValue(), ex);
                return null;
            } catch (ServiceException ex) {  
                username.setErrorMessage(Labels.getLabel(DODConstants.ERROR_USERNAME_WS));
                Logger.getLogger(FormValidations.class.getName()).log(Level.SEVERE, "ERROR OBTAINING INFO FOR USER " + username.getValue(), ex);
                return null;
            }
        }
        else
            return null;
    }
    
    /**
     * Validates DB name.
     * @param dbName Textbox with the DB name to validate.
     * @return true if DB name is valid, false otherwise.
     */
    public static boolean isDbNameValid(Textbox dbName) {
        //If there are no previous errors
        if (dbName.getErrorMessage() == null || dbName.getErrorMessage().isEmpty()) {
            //Trim and lowercase
            dbName.setValue(dbName.getValue().trim().toLowerCase());
            //Check if user has entered a value
            if (dbName.getValue().isEmpty()) {
                dbName.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_NAME_EMPTY));
                return false;
            }
            //Check dbName length
            if (dbName.getValue().length() > DODConstants.MAX_DB_NAME_LENGTH) {
                dbName.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_NAME_LENGTH));
                return false;
            }
            //ASCII digits and non-digits
            if (!Pattern.matches("[a-z][\\da-z_]*", dbName.getValue())) {
                dbName.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_NAME_CHARS));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates e-Group name.
     * @param eGroup Textbox with the e-group to validate.
     * @return true if e-group name is valid, false otherwise.
     */
    public static boolean isEGroupValid(Textbox eGroup) {
        //If there are no previous errors
        if (eGroup.getErrorMessage() == null || eGroup.getErrorMessage().isEmpty()) {
            if (eGroup.getText().length() > 0) {
                //Trim and lowercase
                eGroup.setValue(eGroup.getValue().trim().toLowerCase());
                //Check eGroup length
                if (eGroup.getText().length() > DODConstants.MAX_E_GROUP_LENGTH) {
                    eGroup.setErrorMessage(Labels.getLabel(DODConstants.ERROR_E_GROUP_LENGTH));
                    return false;
                }
                //Check if egroup name contains a dash
                if (!eGroup.getValue().contains("-")) {
                    eGroup.setErrorMessage(Labels.getLabel(DODConstants.ERROR_E_GROUP_DASH));
                    return false;
                }
                //Only upppercase and lowercase ASCII letters, numbers, dashes, dots and underscores are allowed
                if (!Pattern.matches("[\\da-z\\.\\-_]*", eGroup.getValue())) {
                    eGroup.setErrorMessage(Labels.getLabel(DODConstants.ERROR_E_GROUP_CHARS));
                    return false;
                }
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates category.
     * @param category Combobox with the category to validate.
     * @return true if category is valid, false otherwise.
     */
    public static boolean isCategoryValid(Combobox category) {
        //If there are no previous errors
        if (category.getErrorMessage() == null || category.getErrorMessage().isEmpty()) {
            //Check if user has selected a value
            if (category.getSelectedItem() == null) {
                category.setErrorMessage(Labels.getLabel(DODConstants.ERROR_CATEGORY_EMPTY));
                return false;
            }
            //Check dbtype Oracle or MySQL
            if (!category.getSelectedItem().getValue().equals(DODConstants.CATEGORY_OFFICIAL)
                    && !category.getSelectedItem().getValue().equals(DODConstants.CATEGORY_PERSONAL)
                    && !category.getSelectedItem().getValue().equals(DODConstants.CATEGORY_TEST)) {
                category.setErrorMessage(Labels.getLabel(DODConstants.ERROR_CATEGORY_LIST));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates expiry date.
     * @param expiryDate Datebox with the expiry date to validate.
     * @return true if expiry date is valid, false otherwise.
     */
    public static boolean isExpiryDateValid(Datebox expiryDate) {
        //If there are no previous errors
        if (expiryDate.getErrorMessage() == null || expiryDate.getErrorMessage().isEmpty()) {
            //If the user has entered a value
            if (!expiryDate.getText().isEmpty()) {
                //Check valid date
                if (expiryDate.getValue() == null) {
                    expiryDate.setErrorMessage(Labels.getLabel(DODConstants.ERROR_EXPIRY_DATE_FORMAT));
                    return false;
                }
                //Check if it is a future date
                Date now = new Date();
                if (expiryDate.getValue().compareTo(now) <= 0) {
                    expiryDate.setErrorMessage(Labels.getLabel(DODConstants.ERROR_EXPIRY_DATE_FUTURE));
                    return false;
                }
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates DB type.
     * @param dbType Combobox with the DB type to validate.
     * @return true if DB type is valid, false otherwise.
     */
    public static boolean isDbTypeValid(Combobox dbType) {
        //If there are no previous errors
        if (dbType.getErrorMessage() == null || dbType.getErrorMessage().isEmpty()) {
            //Check if user has selected a value
            if (dbType.getSelectedItem() == null) {
                dbType.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_TYPE_EMPTY));
                return false;
            }
            //Check dbtype Oracle or MySQL
            if (!dbType.getSelectedItem().getValue().equals(DODConstants.DB_TYPE_ORACLE)
                    && !dbType.getSelectedItem().getValue().equals(DODConstants.DB_TYPE_MYSQL)) {
                dbType.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_TYPE_LIST));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates DB size.
     * @param dbSize Textbox with the DB size to validate.
     * @return true if DB size is valid, false otherwise.
     */
    public static boolean isDbSizeValid(Textbox dbSize) {
        //If there are no previous errors
        if (dbSize.getErrorMessage() == null || dbSize.getErrorMessage().isEmpty()) {
            //Trim
            dbSize.setValue(dbSize.getValue().trim());
            //Check if user has entered a value
            if (dbSize.getValue().isEmpty()) {
                dbSize.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_SIZE_EMPTY));
                return false;
            }
            try {
                Integer size = Integer.valueOf(dbSize.getText());
                //Check dbName length
                if (size < 10 || size > DODConstants.MAX_DB_SIZE) {
                    dbSize.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_SIZE_RANGE));
                    return false;
                }
            } catch (NumberFormatException ex) {
                dbSize.setErrorMessage(Labels.getLabel(DODConstants.ERROR_INTEGER_FORMAT));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates NO Connections.
     * @param noConnections Textbox with the number of connections to validate.
     * @return true if NO Connections is valid, false otherwise
     */
    public static boolean isNOConnectionsValid(Textbox noConnections) {
        //If there are no previous errors
        if (noConnections.getErrorMessage() == null || noConnections.getErrorMessage().isEmpty()) {
            //Trim
            noConnections.setValue(noConnections.getValue().trim());
            //Check only if user has entered a value
            if (!noConnections.getValue().isEmpty()) {
                try {
                    int noConn = Integer.valueOf(noConnections.getText()).intValue();
                    //Check dbName length
                    if (noConn <= 0 || noConn > DODConstants.MAX_NO_CONNECTIONS) {
                        noConnections.setErrorMessage(Labels.getLabel(DODConstants.ERROR_NO_CONNECTIONS_RANGE));
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    noConnections.setErrorMessage(Labels.getLabel(DODConstants.ERROR_INTEGER_FORMAT));
                    return false;
                }
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates project
     * @param project Textbox with the project to validate.
     * @return true if project is valid, false otherwise
     */
    public static boolean isProjectValid(Textbox project) {
        //If there are no previous errors
        if (project.getErrorMessage() == null || project.getErrorMessage().isEmpty()) {
            //Trim
            project.setValue(project.getValue().trim());
            //Check project length
            if (project.getValue().length() > DODConstants.MAX_PROJECT_LENGTH) {
                project.setErrorMessage(Labels.getLabel(DODConstants.ERROR_PROJECT_LENGTH));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
     /**
     * Validates description.
     * @param description Textbox with the description to validate.
     * @return true if description is valid, false otherwise.
     */
    public static boolean isDescriptionValid(Textbox description) {
        //If there are no previous errors
        if (description.getErrorMessage() == null || description.getErrorMessage().isEmpty()) {
            //Trim
            description.setValue(description.getValue().trim());
            //Check description length
            if (description.getValue().length() > DODConstants.MAX_DESCRIPTION_LENGTH) {
                description.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DESCRIPTION_LENGTH));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates version.
     * @param version Textbox with the version to validate.
     * @return true if version is valid, false otherwise.
     */
    public static boolean isVersionValid(Textbox version) {
        //If there are no previous errors
        if (version.getErrorMessage() == null || version.getErrorMessage().isEmpty()) {
            //Trim
            version.setValue(version.getValue().trim());
            //Check if user has entered a value
            if (version.getValue().isEmpty()) {
                version.setErrorMessage(Labels.getLabel(DODConstants.ERROR_VERSION_EMPTY));
                return false;
            }
            //Check version length
            if (version.getValue().length() > DODConstants.MAX_VERSION_LENGTH) {
                version.setErrorMessage(Labels.getLabel(DODConstants.ERROR_VERSION_LENGTH));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates DB name.
     * @param master Textbox with the master to validate.
     * @param instanceDAO DAO to use in order to obtain the master with the given name.
     * @return true if DB name is valid, false otherwise
     */
    public static boolean isMasterValid(Textbox master, DODInstanceDAO instanceDAO) {
        //If there are no previous errors
        if (master.getErrorMessage() == null || master.getErrorMessage().isEmpty()) {
            //Trim and lowercase
            master.setValue(master.getValue().trim().toLowerCase());
            //If it's empty return true
            if (master.getValue() == null || master.getValue().isEmpty())
                return true;
            //Check dbName length
            if (master.getValue().length() > DODConstants.MAX_DB_NAME_LENGTH) {
                master.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_NAME_LENGTH));
                return false;
            }
            //ASCII digits and non-digits
            if (!Pattern.matches("[a-z][\\da-z_]*", master.getValue())) {
                master.setErrorMessage(Labels.getLabel(DODConstants.ERROR_DB_NAME_CHARS));
                return false;
            }
            DODInstance masterInstance = instanceDAO.selectByDbName(master.getValue(), null);
            //Check that master exists
            if (masterInstance == null) {
                master.setErrorMessage(Labels.getLabel(DODConstants.ERROR_MASTER_DOES_NOT_EXIST));
                return false;
            }
            //Check that master is not a shared instance
            if (masterInstance.getSharedInstance() != null && !masterInstance.getSharedInstance().isEmpty()){
                master.setErrorMessage(Labels.getLabel(DODConstants.ERROR_MASTER_SHARED_INSTANCE));
                return false;
            }
        }
        else
            return false;
        return true;
    }
    
    /**
     * Validates shared instance
     * @param sharedInstance Textbox with the shared instance to validate.
     * @param master Textbox with the master indicated by the user (shared instance is not permitted in master/slave).
     * @return true if shared instance is valid, false otherwise.
     */
    public static boolean isSharedInstanceValid(Textbox sharedInstance, Textbox master) {
        //If there are no previous errors
        if (sharedInstance.getErrorMessage() == null || sharedInstance.getErrorMessage().isEmpty()) {
            //Trim
            sharedInstance.setValue(sharedInstance.getValue().trim());
            //Check shared instance length
            if (sharedInstance.getValue().length() > DODConstants.MAX_SHARED_INSTANCE_LENGTH) {
                sharedInstance.setErrorMessage(Labels.getLabel(DODConstants.ERROR_SHARED_INSTANCE_LENGTH));
                return false;
            }
            //Check that theres is no master in case there is something in the field (slave can't be a shared instance)
            if (sharedInstance.getValue() != null && !sharedInstance.getValue().isEmpty()
                    && master != null && master.getValue() != null && !master.getValue().trim().isEmpty()) {
                sharedInstance.setErrorMessage(Labels.getLabel(DODConstants.ERROR_SLAVE_SHARED_INSTANCE));
                return false;
            }
        }
        else
            return false;
        return true;
    }
}
