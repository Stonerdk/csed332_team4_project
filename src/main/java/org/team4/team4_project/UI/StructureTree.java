package org.team4.team4_project.UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class StructureTree extends JTree {
    GUIController guiC;

    /**
     * Construct structure tree based on current project
     *
     */
    public StructureTree() {
        guiC = GUIController.getInstance();

        File fileRoot = new File(guiC.getPath());
        System.out.println(fileRoot.toString());
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileRoot.getName());
        this.setModel(new DefaultTreeModel(node));
        this.setShowsRootHandles(true);
        this.scanDirectory(fileRoot, node);
    }

    /**
     * Scan contents of chosen directory
     *
     * @param dir File dir that is chosen
     * @param root tree node of dir in the structure tree
     */
    public void scanDirectory(File dir, DefaultMutableTreeNode root) {
        File[] files = dir.listFiles();

        for(File file : files) {
            if (file == null) continue;
            DefaultTreeModel model = (DefaultTreeModel)getModel();
            DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(file.getName());

            if (file.isDirectory()) {
                scanDirectory(file, newnode);

                if(newnode.getChildCount() == 0) continue;

                root.add(newnode);
                model.reload();
            }
            else {
                if(file.getName().indexOf(".java") == -1) continue;

                model.insertNodeInto(newnode, root, root.getChildCount());
                model.reload();
            }
        }
    }
}