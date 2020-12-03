package org.team4.team4_project.UI;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
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
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class MetricsWindow extends JFrame {

    private static MetricsWindow instance = null;
    private JPanel graphStatusComposite;
    private GraphPanel graphPanel;
    private JPanel topPanel;
    private JTable statusPanel;
    private JPanel GraStat;
    private JTextPane statusPane;
    private JPanel treePanel;
    private JPanel ScrollPanel;
    private JSplitPane jsp;
    private JSplitPane jsp2;
    private JSlider slider;
    private JScrollPane scroll;
    private ComboBox<String> comboBox;

    private JTree projectTree;
    private String[] commitStrings;

    private GUIController guiC;

    private final String[] comboStrings = {
            "Halstead Vocabulary",
            "Halstead Volume",
            "Cyclomatic Complexity",
            "Maintainability",
            "Code Churn"
    };

    private Project project;

    private MetricsWindow () {

        guiC = GUIController.getInstance();
        guiC.refreshController();

        setTitle("Software Metrics Graph");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 800);

        Container contentPane = getContentPane();

        topPanel = new JPanel();

        graphStatusComposite = new JPanel();

        graphPanel = new GraphPanel(this);
        graphPanel.setType(comboStrings[0]);
        graphPanel.repaint();
        //JScrollBar bar = new JScrollBar(graphPanel);
        //bar.setBounds(0, 0, 569, 206);

        statusPanel = new JTable();
        statusPane = new JTextPane();
        statusPane.setText("");
        statusPane.setEditable(false);
        treePanel = new JPanel();
        //statusPanel.add(statusPane, BorderLayout.WEST);
        //statusPanel.add(table, BorderLayout.WEST);

        //graphStatusComposite.add(graphPanel, BorderLayout.WEST);
        //graphStatusComposite.add(statusPanel, BorderLayout.EAST);

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

        slider = new JSlider(0, 60, 60);                            // Slider to Zoom Graph
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                graphPanel.Zoom(slider.getValue() + 20);
            }
        });
        scroll = new JScrollPane(graphPanel);

        projectTree = new StructureTree(this);
        treePanel.add(projectTree, BorderLayout.WEST);

        topPanel.add(slider);
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        jsp.setLeftComponent(scroll);
        jsp.setRightComponent(statusPanel);
        jsp.setResizeWeight(0.7);

        jsp2.setLeftComponent(treePanel);
        jsp2.setRightComponent(jsp);
        contentPane.add(jsp2, BorderLayout.CENTER);
    }

    public void setStatusHistory(@Nullable int idx) {
        statusPanel.removeAll();
        List<String> ValueList = guiC.getAllValue(idx);

        if(ValueList.size() == 13){
            String header[] = {"Metric", "Value"};
            String contents[][] = {{"Date", ValueList.get(0)}, {"Commit String", ValueList.get(1)}, {"Branch Name", ValueList.get(2)},
                    {"HalStead Vocabulary", ValueList.get(3)}, {"HalStead ProgLength", ValueList.get(4)},
                    {"HalStead CalProgLength", ValueList.get(5)}, {"HalStead Volume", ValueList.get(6)},
                    {"HalStead Difficulty", ValueList.get(7)}, {"HalStead Effort", ValueList.get(8)},
                    {"HalStead Time Required", ValueList.get(9)}, {"HalStead Num Del Bugs", ValueList.get(10)},
                    {"Cyclomatic Complexity", ValueList.get(11)}, {"Maintainability Index", ValueList.get(12)}};
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

    public void setProject(Project project) {
        this.project = project;
    }
}
