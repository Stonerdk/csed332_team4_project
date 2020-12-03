package org.team4.team4_project.UI;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StructureTree extends JTree {
    public StructureTree(MetricsWindow parent) {

        File fileRoot = new File(Objects.requireNonNull(ProjectHandler.project.getBasePath()));
        System.out.println(fileRoot.toString());
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileRoot.getName());
        this.setModel(new DefaultTreeModel(node));
        this.setShowsRootHandles(true);
        this.scanDirectory(fileRoot, node);
    }

    public void scanDirectory(File dir, DefaultMutableTreeNode root) {
        File[] files = dir.listFiles();

        for(File file : files) {
            if (file == null) continue;
            DefaultTreeModel model = (DefaultTreeModel)getModel();
            DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(file.getName());
            if (file.isDirectory()) {
                if (file.listFiles() == null) {
                    continue;
                }
                root.add(newnode);
                model.reload();
                scanDirectory(file, newnode);
            } else {
                model.insertNodeInto(newnode, root, root.getChildCount());
                model.reload();
            }
        }
    }
}