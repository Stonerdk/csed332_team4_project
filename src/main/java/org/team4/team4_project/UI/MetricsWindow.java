package org.team4.team4_project.UI;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.io.IntToIntBtree;
import com.intellij.util.ui.GridBag;
import org.team4.team4_project.history.HistoryData;
import org.team4.team4_project.history.HistoryReader;
import org.team4.team4_project.metric_calculation.CommitInfo;
import org.team4.team4_project.metric_calculation.FileInfo;
import org.team4.team4_project.metric_calculation.MetricMain;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class MetricsWindow extends JFrame {

    private static MetricsWindow instance = null;
    private final GraphPanel graphPanel;
    private JTable statusPanel;
    private final JSplitPane jsp;
    private final JSlider slider;
    private final JScrollPane scroll;
    private final JScrollPane scroll2;

    private ComboBox<String> comboBox;

    private JTree projectTree;

    private final GUIController guiC;

    private MetricsWindow () {

        guiC = GUIController.getInstance();
        guiC.refreshController();

        setTitle("Software Metrics Graph");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 800);

        Container contentPane = getContentPane();

        JPanel topPanel = new JPanel();

        graphPanel = new GraphPanel(this);
        String[] comboStrings = {
                "Code Length",
                "Halstead Vocabulary",
                "Halstead Program Length",
                "Halstead Cal Prog Length",
                "Halstead Volume",
                "Halstead Difficulty",
                "Halstead Effort",
                "Halstead Time Required",
                "Halstead Num Del Bugs",
                "Cyclomatic Complexity",
                "Maintainability",
                "Code Churn",
                "Number of Methods",
                "Number of Loops",
                "Number of Imports"
        };
        graphPanel.setType(comboStrings[0]);
        graphPanel.repaint();

        statusPanel = new JTable();
        JTextPane statusPane = new JTextPane();
        statusPane.setText("");
        statusPane.setEditable(false);
        JPanel treePanel = new JPanel(new BorderLayout());

        comboBox = new ComboBox<String>(comboStrings);                              // ComboBox for Metrics
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    graphPanel.setType(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                    graphPanel.repaint();
                } catch (NumberFormatException | NullPointerException e2){
                    JOptionPane.showMessageDialog(graphPanel,"Invalid Number","Error Message",JOptionPane.ERROR_MESSAGE);
                    e2.printStackTrace();
                }
            }
        });

        topPanel.add(comboBox);

        contentPane.add(topPanel, BorderLayout.NORTH);

        slider = new JSlider(20, 100, 60);                            // Slider to Zoom Graph
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                graphPanel.Zoom(slider.getValue() + 20);
                scroll.revalidate();
            }
        });
        scroll = new JScrollPane(graphPanel);

        JButton projButton = new JButton("Entire Project");
        projButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiC.selectProj();
                projectTree.clearSelection();
                graphPanel.repaint();
            }
        });

        setTree();

        JPanel treeGridContainer = new JPanel(new GridLayout());
        treeGridContainer.add(projectTree);

        JButton resetButton = new JButton("Reset Project");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiC.refreshController();
                treeGridContainer.removeAll();

                setTree();

                treeGridContainer.add(projectTree);

                scroll2.revalidate();
                scroll2.repaint();

                scroll.revalidate();
                scroll.repaint();
            }
        });

        treePanel.add(projButton, BorderLayout.NORTH);
        treePanel.add(resetButton, BorderLayout.SOUTH);

        treePanel.add(treeGridContainer, BorderLayout.CENTER);
        //treePanel.setPreferredSize(new Dimension(130, 300));

        scroll2 = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        topPanel.add(slider);
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        jsp.setLeftComponent(scroll);
        jsp.setRightComponent(statusPanel);
        jsp.setResizeWeight(0.7);

        jsp2.setLeftComponent(scroll2);
        jsp2.setRightComponent(jsp);
        jsp2.setResizeWeight(0.2);
        contentPane.add(jsp2, BorderLayout.CENTER);
    }

    public void setStatusHistory(int idx) {
        statusPanel.removeAll();
        List<String> ValueList = guiC.getAllValue(idx);

        if(ValueList.size() == 19){
            String[] header = {"Metric", "Value"};
            String[][] contents = {{"Date", ValueList.get(0)},
                    {"Commit String", ValueList.get(1)},
                    {"Branch Name", ValueList.get(2)},
                    {"Code Length", ValueList.get(3)},
                    {"HalStead Vocabulary", ValueList.get(4)},
                    {"HalStead ProgLength", ValueList.get(5)},
                    {"HalStead CalProgLength", ValueList.get(6)},
                    {"HalStead Volume", ValueList.get(7)},
                    {"HalStead Difficulty", ValueList.get(8)},
                    {"HalStead Effort", ValueList.get(9)},
                    {"HalStead Time Required", ValueList.get(10)},
                    {"HalStead Num Del Bugs", ValueList.get(11)},
                    {"Cyclomatic Complexity", ValueList.get(12)},
                    {"Maintainability Index", ValueList.get(13)},
                    {"Code Churn Added", ValueList.get(14)},
                    {"Code Churn Deleted", ValueList.get(15)},
                    {"Number of Methods", ValueList.get(16)},
                    {"Number of Loops", ValueList.get(17)},
                    {"Number of Imports", ValueList.get(18)}
            };
            statusPanel = new JTable(contents, header);
            jsp.setRightComponent(statusPanel);

            repaint();
        }

        /*if (h == null) {
            statusPane.setText("");
        } else {
            statusPane.setText(h.toString());
        }*/
    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }

    public void setTree(){
        projectTree = new StructureTree();
        projectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                String path = projectTree.getSelectionPath().toString();
                path = path.substring(1, path.length()-1);
                path = path.replaceAll(", ", "/");
                guiC.selectFile(projectTree.getLastSelectedPathComponent().toString(), path);
                graphPanel.repaint();
            }
        });
    }

    public void setProject(Project project) {
    }
}
