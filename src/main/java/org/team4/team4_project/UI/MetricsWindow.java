package org.team4.team4_project.UI;

import com.intellij.openapi.ui.ComboBox;
import org.team4.team4_project.history.HistoryData;
import org.team4.team4_project.history.HistoryReader;
import org.team4.team4_project.metric_calculation.MetricMain;

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
    private GraphPanel graphPanel;
    private JPanel topPanel;
    private ComboBox<String> comboBox;
    private ArrayList<HistoryData> historyList;
    private String[] comboStrings = {"Halstead Complexity", "Cyclomatic Complexity", "Maintainability", "Code Churn +", "Code Churn -"};

    private MetricsWindow () {

        try {
            historyList = (ArrayList<HistoryData>) new MetricMain().mcMain();
        } catch (ParseException | NullPointerException | IOException e) {
            e.printStackTrace();
            return;
        }

        setTitle("Software Metrics Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);

        Container contentPane = getContentPane();

        topPanel = new JPanel();
        graphPanel = new GraphPanel(historyList);
        graphPanel.setType(comboStrings[0]);
        graphPanel.repaint();

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
        contentPane.add(graphPanel, BorderLayout.CENTER);
    }

    public static MetricsWindow getInstance() {
        if (instance == null)
            instance = new MetricsWindow();
        return instance;
    }
}
