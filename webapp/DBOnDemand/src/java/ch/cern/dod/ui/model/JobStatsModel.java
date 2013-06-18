package ch.cern.dod.ui.model;

import ch.cern.dod.db.entity.DODJobStat;
import ch.cern.dod.util.DODConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.ListDataEvent;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ext.Sortable;

/**
 * Represents a list of job stats. It implements sorting to save it from query to query.
 * @author Daniel Gomez Blanco
 */
public class JobStatsModel extends AbstractListModel implements Sortable {
    /**
     * Job stats in the model.
     */
    private List<DODJobStat> jobStats;
    /**
     * Filtered job stats in the model.
     */
    private List<DODJobStat> filtered;
    /**
     * Indicates if the order is ascending or descending.
     */
    private boolean ascending;
    /**
     * Comparator to sort the stats once they are reloaded.
     */
    private Comparator comparator;
    /**
     * Current dbName filtered
     */
    private String dbName = "";
    /**
     * Current command filtered
     */
    private String command = "";

    /**
     * Constructor for this class, passing the list of job stats as a parameter.
     * @param jobStats job stats to make the model of.
     */
    public JobStatsModel(List<DODJobStat> jobStats) {
        this.jobStats = jobStats;
        this.filtered = jobStats;
    }
    
    /**
     * Sets the list of job stats.
     * @param jobStats list of job stats.
     */
    public void setJobStats(List<DODJobStat> jobStats) {
        this.jobStats = jobStats;
        filterJobStats(dbName, command);
    }

    /**
     * Overrides the method to get the size of the model.
     * @return the number of stats in the model.
     */
    @Override
    public int getSize() {
        return filtered.size();
    }

    /**
     * Gets the stat at a certain position.
     * @param index index of the stat.
     * @return the stat.
     */
    @Override
    public Object getElementAt(int index) {
        return filtered.get(index);
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
        Collections.sort(filtered, comparator);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    }
    
    /**
     * Filters the stats.
     * @param dbName DB name to filter.
     * @param command command to filter.
     */
    public void filterJobStats (String dbName, String command) {
        //Store values
        this.dbName = dbName;
        this.command = command;
        //Filter stats
        filtered = new ArrayList<>();
        for (int i=0; i< jobStats.size(); i++) {
            DODJobStat stat = jobStats.get(i);
            if (stat.getDbName().toLowerCase().indexOf(dbName.trim().toLowerCase()) >= 0
                    && Labels.getLabel(DODConstants.LABEL_JOB + stat.getCommandName()).toLowerCase().indexOf(command.trim().toLowerCase()) >= 0) {
                filtered.add(stat);
            }
        }
        //Sort again
        sort(comparator, ascending);
    }
    
    @Override
    public String getSortDirection(Comparator cmprtr) {
        if (ascending)
            return "ascending";
        else
            return "descending";
    }
}