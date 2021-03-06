/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ui.controller;

import ch.cern.dbod.db.dao.InstanceDAO;
import ch.cern.dbod.db.entity.Instance;
import ch.cern.dbod.ui.model.OverviewTreeModel;
import ch.cern.dbod.util.CommonConstants;
import ch.cern.dbod.util.JobHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 * Controller for the shutdown window. Creates the window and all its components.
 * This window is basically a warning for the user to not shut down the wrong
 * database.
 * @author Daniel Gomez Blanco
 */
public class ShutdownController extends Window {

    /**
     * Instance being managed at the moment.
     */
    private Instance instance;
    /**
     * Helper to execute jobs.
     */
    private JobHelper jobHelper;
    /**
     * Instance DAO
     */
    private InstanceDAO instanceDAO;
    /**
     * User authenticated in the system at the moment.
     */
    private String username;
    /**
     * Model of the tree (null if we are in list view).
     */
    private OverviewTreeModel model;

    /**
     * Constructor for this window (coming from instance view)
     * @param inst instance to be managed.
     * @param user username for the authenticated user.
     * @param jobHelper helper to execute jobs.
     * @throws InterruptedException if the window cannot be created.
     */
    public ShutdownController (Instance inst, String user, JobHelper jobHelper) throws InterruptedException { 
        this (inst, user, jobHelper, null);
    }

    /**
     * Constructor for this window (coming from list view)
     * @param inst instance to be managed.
     * @param user username for the authenticated user.
     * @param jobHelper helper to execute jobs.
     * @param model model of the tree (null if we are in instance view).
     * @throws InterruptedException if the window cannot be created.
     */
    public ShutdownController (Instance inst, String user, JobHelper jobHelper, OverviewTreeModel model) throws InterruptedException {        
        //Call super constructor
        super();
        
        //Initialize instance and create job helper
        this.instance = inst;
        this.jobHelper = jobHelper;
        instanceDAO = new InstanceDAO();
        this.username = user;
        
        //Initialise model and node
        this.model = model;
        
        //Basic window properties
        this.setId("shutdownWindow");
        this.setTitle(Labels.getLabel(CommonConstants.LABEL_SHUTDOWN_TITLE) + " " + instance.getDbName());
        this.setBorder("normal");
        this.setMode(Window.OVERLAPPED);
        this.setPosition("center");
        this.setClosable(false);
        this.setWidth("350px");

        //Main box used to apply pading
        Vbox mainBox = new Vbox();
        mainBox.setStyle("padding-top:5px;padding-left:5px;padding-right:5px");
        this.appendChild(mainBox);

        //Box for message
        Hbox messageBox = new Hbox();
        messageBox.setAlign("center");
        messageBox.appendChild(new Image(CommonConstants.IMG_WARNING));
        //Main message
        Label message = new Label(Labels.getLabel(CommonConstants.LABEL_SHUTDOWN_WARNING));
        messageBox.appendChild(message);
        mainBox.appendChild(messageBox);

        //Div for accept and cancel buttons
        Div buttonsDiv = new Div();
        buttonsDiv.setWidth("100%");

        //Cancel button
        Hbox cancelBox = new Hbox();
        cancelBox.setHeight("24px");
        cancelBox.setAlign("bottom");
        cancelBox.setStyle("float:left;");
        Toolbarbutton cancelButton = new Toolbarbutton();
        cancelButton.setTooltiptext(Labels.getLabel(CommonConstants.LABEL_CANCEL));
        cancelButton.setZclass(CommonConstants.STYLE_BUTTON);
        cancelButton.setImage(CommonConstants.IMG_CANCEL);
        cancelButton.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) {
                doCancel();
            }
        });
        cancelBox.appendChild(cancelButton);
        Label cancelLabel = new Label(Labels.getLabel(CommonConstants.LABEL_CANCEL));
        cancelLabel.setSclass(CommonConstants.STYLE_TITLE);
        cancelLabel.setStyle("font-size:10pt !important;cursor:pointer;");
        cancelLabel.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) {
                doCancel();
            }
        });
        cancelBox.appendChild(cancelLabel);
        buttonsDiv.appendChild(cancelBox);

        //Accept button (only created when slave is upgraded or instance is not master)
        Hbox acceptBox = new Hbox();
        acceptBox.setHeight("24px");
        acceptBox.setAlign("bottom");
        acceptBox.setStyle("float:right;");
        Label acceptLabel = new Label(Labels.getLabel(CommonConstants.LABEL_ACCEPT));
        acceptLabel.setSclass(CommonConstants.STYLE_TITLE);
        acceptLabel.setStyle("font-size:10pt !important;cursor:pointer;");
        acceptLabel.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) {
                doAccept();
            }
        });
        acceptBox.appendChild(acceptLabel);
        Toolbarbutton acceptButton = new Toolbarbutton();
        acceptButton.setTooltiptext(Labels.getLabel(CommonConstants.LABEL_ACCEPT));
        acceptButton.setZclass(CommonConstants.STYLE_BUTTON);
        acceptButton.setImage(CommonConstants.IMG_ACCEPT);
        acceptButton.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) {
                doAccept();
            }
        });
        acceptBox.appendChild(acceptButton);
        buttonsDiv.appendChild(acceptBox);
        
        this.appendChild(buttonsDiv);
    }
    

    /**
     * Method executed when user accepts the form. A job is created and the window is detached.
     */
    private void doAccept() {
        ///Create new job and update instance status
        if (jobHelper.doShutdown(instance, username)) {
            Instance clone = instance.clone();
            clone.setAttribute("notifications", "false");

            if (instanceDAO.update(instance, clone, username) > 0) {
                instance = clone;
            }
            //If we are in the overview page
            if (model != null) {
                //Reload the node
                model.updateInstance(instance);
            } //If we are in the instance page
            else if (this.getRoot().getFellowIfAny("controller") != null && this.getRoot().getFellow("controller") instanceof InstanceController) {
                InstanceController controller = (InstanceController) this.getRoot().getFellow("controller");
                controller.afterCompose();
            }
        }
        else {
            showError(CommonConstants.ERROR_DISPATCHING_JOB);
        }
        this.detach();
    }

    /**
     * Method executed when the user cancels the form. The window is detached from the page.
     */
    private void doCancel() {
        this.detach();
    }

    /**
     * Displays an error window for the error code provided.
     * @param errorCode error code for the message to be displayed.
     */
    private void showError(String errorCode) {
        Window errorWindow = (Window) this.getParent().getFellow("errorWindow");
        Label errorMessage = (Label) errorWindow.getFellow("errorMessage");
        errorMessage.setValue(Labels.getLabel(errorCode));
        try {
            errorWindow.doModal();
        } catch (SuspendNotAllowedException ex) {
            Logger.getLogger(ShutdownController.class.getName()).log(Level.SEVERE, "ERROR SHOWING ERROR WINDOW", ex);
        }
    }
}
