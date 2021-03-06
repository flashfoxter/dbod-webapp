/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ui.model;

import ch.cern.dbod.db.entity.Instance;
import ch.cern.dbod.util.CommonConstants;
import ch.cern.dbod.util.TreeNodeComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.zkoss.zul.*;
import org.zkoss.zul.event.TreeDataEvent;
import org.zkoss.zul.ext.Sortable;

/**
 * Model for overview tree.
 * @author Daniel Gomez Blanco
 */
public class OverviewTreeModel extends AbstractTreeModel implements Sortable {
    
    /**
     * Tree where this is displayed
     */
    Tree tree;
    private Comparator _sorting;
    
    /**
     * Constructor of this class.
     * @param instances list of instances to make a tree of
     * @param tree component where instances are to be displayed
     */
    public OverviewTreeModel (List<Instance> instances, Tree tree) {
        //Call super-constructor
        super(new OverviewTreeNode(null, new ArrayList<OverviewTreeNode>()));
        this.tree = tree;
        _sorting = new TreeNodeComparator(true, "STATE");
        setInstances(instances);
    }
    
    /**
     * Gets a list of nodes representing the tree of instances
     * @param instances list of instances to make a tree of
     * @return list of nodes with master/slave relations
     */
    private ArrayList<OverviewTreeNode> getNodeList (List<Instance> instances) {
        ArrayList<OverviewTreeNode> nodeList = new ArrayList<>();
        ArrayList<Instance> masters = new ArrayList<>();
        ArrayList<Instance> slaves = new ArrayList<>();
        
        //Separate masters and slaves and create single and shared isntances
        for (Instance instance : instances) {
            //Masters and slaves are treated later
            if (instance.getSlave() != null) {
                masters.add(instance);
            }
            else if (instance.getMaster() != null) {
                slaves.add(instance);
            }
            else {
                //Only add instance to tree if it is filtered
                if (filterInstance(instance)) {
                    nodeList.add(new OverviewTreeNode(instance));
                }
            }
        }
        
        //Merge masters and slaves
        for (Instance master : masters) {
            ArrayList<OverviewTreeNode> slavesList =  new ArrayList<>();
            for (Instance slave : slaves) {
                if (slave.getMaster().equals(master.getDbName()) && filterInstance(slave)) {
                    slavesList.add(new OverviewTreeNode(slave));
                }
            }
            //If there are filtered slaves
            if (slavesList.size() > 0) {
                nodeList.add(new OverviewTreeNode(master, slavesList));
            }
            else {
                if (filterInstance(master)) {
                    nodeList.add(new OverviewTreeNode(master));
                }
            }
        }
        
        //Sort node list
        Collections.sort(nodeList, _sorting);
        
        return nodeList;
    }
    
    /**
     * Set the instances in the model (does not fire event to refresh view)
     * @param instances new list of instances
     */
    public final void setInstances (List<Instance> instances) {
        List<OverviewTreeNode> children = ((OverviewTreeNode)this.getRoot()).getChildren();
        List<OverviewTreeNode> nodeList = getNodeList(instances);
        children.removeAll(children);
        children.addAll(nodeList);
        fireEvent(TreeDataEvent.STRUCTURE_CHANGED, new int[0], 0, 0);
    }

    /**
     * Get if a given object is a leaf or not.
     * @param node Node to check.
     * @return true if node is a leaf, false otherwise.
     */
    @Override
    public boolean isLeaf(Object node) {
        return ((OverviewTreeNode)node).isLeaf();
    }

    /**
     * Gets a specific child of a node.
     * @param node Node to get the child from.
     * @param i Index of the child.
     * @return Node representing the selected child.
     */
    @Override
    public Object getChild(Object node, int i) {
        if (((OverviewTreeNode)node).getChildren() != null)
            return ((OverviewTreeNode)node).getChildAt(i);
        else
            return null;
    }

    /**
     * Gets the number of children a node has.
     * @param node Node to get the number of children from.
     * @return Number of children the node has.
     */
    @Override
    public int getChildCount(Object node) {
        if (((OverviewTreeNode)node).getChildren() != null)
            return ((OverviewTreeNode)node).getChildCount();
        else
            return 0;
    }
    
    /**
     * Filters an instance considering the information contained in the filter fields.
     * @param instance Instance to be filtered
     * @return true if the instance is filtered, false otherwise
     */
    private boolean filterInstance (Instance instance) {
        //Get field values
        String dbName = ((Textbox) tree.getFellow("dbNameFilter")).getValue().trim();
        String host = ((Textbox) tree.getFellow("hostFilter")).getValue().trim();
        String user = ((Textbox) tree.getFellow("usernameFilter")).getValue().trim();
        String eGroup = ((Textbox) tree.getFellow("eGroupFilter")).getValue().trim();
        String category = "";
        if (((Combobox)tree.getFellow("categoryFilter")).getSelectedItem() != null)
            category = ((String)((Combobox)tree.getFellow("categoryFilter")).getSelectedItem().getValue()).trim();
        String project = ((Textbox) tree.getFellow("projectFilter")).getValue().trim();
        String dbType = "";
        if (((Combobox) tree.getFellow("dbTypeFilter")).getSelectedItem() != null)
            dbType = ((String)((Combobox) tree.getFellow("dbTypeFilter")).getSelectedItem().getValue()).trim();
        String action = "";
        if (((Combobox) tree.getFellow("actionFilter")).getSelectedItem() != null)
            action = ((String)((Combobox) tree.getFellow("actionFilter")).getSelectedItem().getValue()).trim();
        
        if (instance.getDbName().toLowerCase().indexOf(dbName.trim().toLowerCase()) >= 0
                && instance.getHost().toLowerCase().indexOf(host.trim().toLowerCase()) >= 0
                && instance.getOwner().toLowerCase().indexOf(user.trim().toLowerCase()) >= 0
                && (eGroup.isEmpty() || (instance.getEGroup() != null && instance.getEGroup().toLowerCase().indexOf(eGroup.trim().toLowerCase()) >= 0))
                && (project.isEmpty() || (instance.getProject() != null && instance.getProject().toLowerCase().indexOf(project.trim().toLowerCase()) >= 0))
                && (category.isEmpty() || instance.getCategory().equals(category))
                && (dbType.isEmpty() || instance.getDbType().equals(dbType))) {
            if (action.isEmpty()) {
                return true;
            }
            else {
                //Check actions (a bit different behaviour)
                if ((action.equals(CommonConstants.JOB_STARTUP) && instance.getState().equals(CommonConstants.INSTANCE_STATE_STOPPED))
                        || (action.equals(CommonConstants.JOB_SHUTDOWN) && (instance.getState().equals(CommonConstants.INSTANCE_STATE_RUNNING)
                                                                        || instance.getState().equals(CommonConstants.INSTANCE_STATE_BUSY)
                                                                        || instance.getState().equals(CommonConstants.INSTANCE_STATE_UNKNOWN)))
                        || (action.equals(CommonConstants.JOB_UPGRADE) && instance.getUpgradeTo() != null && !instance.getUpgradeTo().isEmpty()))
                    return true;
            }
        }
        return false;
        
    }
    
    /**
     * Checks (or unchecks) all the instances in the model.
     * @param root root node to start checking.
     * @param checked true or false, depending if the action is checking or unchecking.
     */
    public void checkAll (OverviewTreeNode root, boolean checked) {
        if (root.getData() instanceof Instance) {
            ((Instance)root.getData()).setChecked(checked);
            //Rerender node
            int[] path = this.getPath(root.getParent());
            int index = root.getParent().getIndex(root);
            fireEvent(TreeDataEvent.CONTENTS_CHANGED, path, index, index);
        }
        for (int i=0; i < root.getChildCount(); i++)
            checkAll((OverviewTreeNode)root.getChildAt(i), checked);
}
    
    /**
     * Gets the list of checked nodes.
     * @param root root node to start.
     * @return list of checked nodes.
     */
    public List<OverviewTreeNode> getChecked (OverviewTreeNode root) {
        List<OverviewTreeNode> checked = new ArrayList<>();
        if (root.getData() instanceof Instance && ((Instance)root.getData()).isChecked())
            checked.add(root);
        for (int i=0; i < root.getChildCount(); i++)
            checked.addAll(getChecked((OverviewTreeNode)root.getChildAt(i)));
        return checked;
    }
    
    /**
     * Updates the node corresponding to the instance
     * @param instance instance to update
     */
    public void updateInstance (Instance instance) {
        OverviewTreeNode node = searchNode((OverviewTreeNode)this.getRoot(),instance);
        if (node != null) {
            node.setData(instance);
            updateNode(node);
        }
    }
    
    /**
     * Searches a node corresponding to a certain instance in the subtree
     * @param root node to start from
     * @param instance instance to search for
     * @return node corresponding to the given instance
     */
    public OverviewTreeNode searchNode (OverviewTreeNode root, Instance instance) {
        if (root.getData() instanceof Instance) {
            if (root.getData().equals(instance)) {
                //Rerender node
                int[] path = this.getPath(root.getParent());
                int index = root.getParent().getIndex(root);
                fireEvent(TreeDataEvent.CONTENTS_CHANGED, path, index, index);
                return root;
            }
        }
        
        for (int i=0; i < root.getChildCount(); i++) {
            OverviewTreeNode node = searchNode((OverviewTreeNode)root.getChildAt(i), instance);
            if (node != null) {
                return node;
            }
        }
        
        return null;
    }
    
    /**
     * Generates a CONTENTS_CHANGED event to refresh a specific node
     * @param node 
     */
    public void updateNode (OverviewTreeNode node) {
        int[] path = getPath(node.getParent());
        int index = node.getParent().getIndex(node);
        fireEvent(TreeDataEvent.CONTENTS_CHANGED, path, index, index);
    }

    @Override
    public void sort(Comparator cmprtr, boolean bln) {
        _sorting = cmprtr;
        
        //Sort node list
        List<OverviewTreeNode> children = ((OverviewTreeNode)this.getRoot()).getChildren();
        List<OverviewTreeNode> copy = new ArrayList(children);
        Collections.sort(copy, cmprtr);
        children.removeAll(children);
        children.addAll(copy);
        
        fireEvent(TreeDataEvent.CONTENTS_CHANGED, new int[0], 0, children.size()-1);
    }

    @Override
    public String getSortDirection(Comparator cmprtr) {
        if (cmprtr == _sorting && cmprtr != null)
            return ((TreeNodeComparator)cmprtr).isAsc() ? "ascending" : "descending";
        else
            return "natural";
    }
}
