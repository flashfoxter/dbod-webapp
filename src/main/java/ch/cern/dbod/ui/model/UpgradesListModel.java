/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ui.model;

import ch.cern.dbod.db.entity.Upgrade;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.ListDataEvent;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ext.Sortable;

/**
 * Represents a list of upgrades. It implements sorting to save it from query to query.
 * @author Daniel Gomez Blanco
 */
public class UpgradesListModel extends AbstractListModel implements Sortable {
    /**
     * Instances in the model.
     */
    private List<Upgrade> upgrades;
    /**
     * Indicates if the order is ascending or descending.
     */
    private boolean ascending;
    /**
     * Comparator to sort the upgrades once they are reloaded.
     */
    private Comparator comparator;

    /**
     * Constructor for this class, passing the list of upgrades as a parameter.
     * @param upgrades upgrades to make the model of.
     */
    public UpgradesListModel(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    /**
     * Setter for the upgrades, passing the list of upgrades as a parameter.
     * @param upgrades upgrades to make the model of.
     */
    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
        sort(comparator, ascending);
    }

    /**
     * Overrides the method to get the size of the model.
     * @return the number of upgrades in the model.
     */
    @Override
    public int getSize() {
        return upgrades.size();
    }

    /**
     * Gets the upgrade at a certain position.
     * @param index index of the upgrade.
     * @return the upgrade.
     */
    @Override
    public Object getElementAt(int index) {
        return upgrades.get(index);
    }

    /**
     * Sort the upgrades in the model.
     * @param comparator comparator to use.
     * @param ascending indicates if the order is ascending or descending.
     */
    @Override
    public void sort(Comparator comparator, boolean ascending) {
        this.ascending = ascending;
        this.comparator = comparator;
        Collections.sort(upgrades, comparator);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    }
    
    /**
     * Obtains the sort direction.
     * @param cmprtr comparator being used
     * @return ascending or descending
     */
    @Override
    public String getSortDirection(Comparator cmprtr) {
        if (ascending)
            return "ascending";
        else
            return "descending";
    }
}
