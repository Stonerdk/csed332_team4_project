package org.team4.team4_project.UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class StructureTree extends JTree {
    GUIController guiC;

    public StructureTree() {
        guiC = GUIController.getInstance();
        String init = guiC.getFilePath(0);

        init = init.substring(0, init.indexOf("/"));
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(init);
        this.setModel(new DefaultTreeModel(root));
        this.setShowsRootHandles(true);
        DefaultTreeModel model = (DefaultTreeModel) getModel();

        for (String dir : guiC.getAllPath()) {
            if (!dir.contains("/")) {
                continue;
            }
            List<String> dirList = new ArrayList<>();
            String cur, rest, fileName;
            int idx;

            rest = dir;
            while (true) {
                idx = rest.indexOf("/");
                cur = rest.substring(0, idx);
                rest = rest.substring(idx + 1);
                dirList.add(cur);
                if (!rest.contains("/")) {
                    fileName = rest;
                    break;
                }
            }
            DefaultMutableTreeNode temp = root;
            for (int i = 1; i < dirList.size(); i++) {
                boolean isChild = false;
                for (int j = 0; j < temp.getChildCount(); j++) {
                    if (((temp.getChildAt(j))).toString().equals(dirList.get(i))) {
                        isChild = true;
                        temp = (DefaultMutableTreeNode) temp.getChildAt(j);
                        break;
                    }
                }
                if (!isChild) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(dirList.get(i));
                    temp.add(newNode);
                    model.reload();

                    temp = newNode;
                }
            }
            DefaultMutableTreeNode newnode4 = new DefaultMutableTreeNode(fileName);
            model.insertNodeInto(newnode4, temp, temp.getChildCount());
            model.reload();
        }
    }
}