package org.team4.team4_project.UI;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.GridBag;
import org.team4.team4_project.history.HistoryData;
import org.team4.team4_project.history.HistoryReader;
import org.team4.team4_project.metric_calculation.MetricMain;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class MetricsWindow extends JFrame {

    private static MetricsWindow instance = null;
    private JPanel graphStatusComposite;
    private GraphPanel graphPanel;
    private JPanel topPanel;
    private JPanel statusPanel;
    private JTextPane statusPane;
    private ComboBox<String> comboBox;
    private ArrayList<HistoryData> historyList;
    private final String[] comboStrings = {
            "Halstead Vocabulary",
            "Halstead Volume",
            "Cyclomatic Complexity",
            "Maintainability",
            "Code Churn"
    };

    private MetricsWindow () {

        try {
            historyList = (ArrayList<HistoryData>) new MetricMain().mcMain();
        } catch (ParseException | NullPointerException | IOException e) {
            e.printStackTrace();
            return;
        }

        setTitle("Software Metrics Graph");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);

        Container contentPane = getContentPane();

        topPanel = new JPanel();

        graphStatusComposite = new JPanel();

        graphPanel = new GraphPanel(historyList, this);
        graphPanel.setType(comboStrings[0]);
        graphPanel.repaint();

        statusPanel = new JPanel();
        statusPane = new JTextPane();
        statusPane.setText("");
        statusPane.setEditable(false);
        statusPanel.add(statusPane, BorderLayout.WEST);

        //graphStatusComposite.add(graphPanel, BorderLayout.WEST);
        //graphStatusComposite.add(statusPanel, BorderLayout.EAST);

        comboBox = new ComboBox<String>(comboStrings);
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

        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jsp.setLeftComponent(graphPanel);
        jsp.setRightComponent(statusPanel);
        contentPane.add(jsp, BorderLayout.CENTER);
    }

    public void setStatusHistory(@Nullable HistoryData h) {
        if (h == null) {
            statusPane.setText("");
        } else {
            statusPane.setText(h.toString());
        }
    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }
}
