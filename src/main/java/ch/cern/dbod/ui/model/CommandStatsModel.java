/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ui.model;

import ch.cern.dbod.db.entity.CommandStat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.ListDataEvent;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ext.Sortable;

/**
 * Represents a list of command stats. It implements sorting to save it from query to query.
 * @author Daniel Gomez Blanco
 */
public class CommandStatsModel extends AbstractListModel implements Sortable{
    /**
     * Command stats in the model.
     */
    private List<CommandStat> commandStats;
    /**
     * Indicates if the order is ascending or descending.
     */
    private boolean ascending;
    /**
     * Comparator to sort the stats once they are reloaded.
     */
    private Comparator comparator;

    /**
     * Constructor for this class, passing the list of command stats as a parameter.
     * @param commandStats command stats to make the model of.
     */
    public CommandStatsModel(List<CommandStat> commandStats) {
        this.commandStats = commandStats;
    }
    
    public void setCommandStats(List<CommandStat> commandStats) {
        this.commandStats = commandStats;
        sort(comparator, ascending);
    }

    /**
     * Overrides the method to get the size of the model.
     * @return the number of stats in the model.
     */
    @Override
    public int getSize() {
        return commandStats.size();
    }

    /**
     * Gets the stat at a certain position.
     * @param index index of the stat.
     * @return the stat.
     */
    @Override
    public Object getElementAt(int index) {
        return commandStats.get(index);
    }

    /**
     * Sort the stats in the model.
     * @param comparator comparator to use.
     * @param ascending indicates if the order is ascending or descending.
     */
    @Override
    public void sort(Comparator comparator, boolean ascending) {
        this.ascending = ascending;
        this.comparator = comparator;
        Collections.sort(commandStats, comparator);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    }

    /**
     * Obtains the sort direction.
     * @param cmpr comparator being used
     * @return ascending or descending
     */
    @Override
    public String getSortDirection(Comparator cmpr) {
        if (ascending) {
            return "ascending";
        }
        else {
            return "descending";
        }
    }
}
