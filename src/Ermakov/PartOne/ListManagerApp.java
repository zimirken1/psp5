package Ermakov.PartOne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ListManagerApp extends JFrame {
    private DefaultListModel<String> list1Model;
    private DefaultListModel<String> list2Model;
    private DefaultListModel<String> list3Model;
    private JList<String> list1;
    private JList<String> list2;
    private JList<String> list3;
    private JButton moveLeftToRightButton;
    private JButton moveRightToLeftButton;
    private JButton fillButton;
    private JButton clearButton;
    private Checkbox list1Checkbox;
    private Checkbox list2Checkbox;
    private Checkbox list3Checkbox;

    static int j = 1;
    public ListManagerApp() {
        setTitle("List Manager");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
    }

    private void initializeComponents() {
        list1Model = new DefaultListModel<>();
        list2Model = new DefaultListModel<>();
        list3Model = new DefaultListModel<>();

        list1 = new JList<>(list1Model);
        list2 = new JList<>(list2Model);
        list3 = new JList<>(list3Model);

        moveLeftToRightButton = new JButton("Move Left to Center");
        moveRightToLeftButton = new JButton("Move Center to Right");
        fillButton = new JButton("Fill");
        clearButton = new JButton("Clear");

        list1Checkbox = new Checkbox("List 1");
        list2Checkbox = new Checkbox("List 2");
        list3Checkbox = new Checkbox("List 3");

        JPanel listsPanel = new JPanel();
        listsPanel.setLayout(new GridLayout(1, 3));
        listsPanel.add(new JScrollPane(list1));
        listsPanel.add(new JScrollPane(list2));
        listsPanel.add(new JScrollPane(list3));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(moveLeftToRightButton);
        buttonsPanel.add(moveRightToLeftButton);
        buttonsPanel.add(fillButton);
        buttonsPanel.add(clearButton);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new FlowLayout());
        checkboxPanel.add(list1Checkbox);
        checkboxPanel.add(list2Checkbox);
        checkboxPanel.add(list3Checkbox);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(listsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainPanel.add(checkboxPanel, BorderLayout.NORTH);

        add(mainPanel);

        moveLeftToRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveItems(list1Model, list2Model, list1);
            }
        });

        moveRightToLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveItems(list2Model, list3Model, list2);
            }
        });

        fillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillList();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelectedItems();
            }
        });
    }

    private void moveItems(DefaultListModel<String> sourceModel, DefaultListModel<String> destinationModel, JList<String> sourceList) {
        List<String> selectedItems = sourceList.getSelectedValuesList();
        for (String selectedItem : selectedItems) {
            sourceModel.removeElement(selectedItem);
            destinationModel.addElement(selectedItem);
        }
    }

    private void fillList() {
        DefaultListModel<String> selectedModel = getSelectedModel();
        if (selectedModel == null) {
            return;
        }

        selectedModel.clear();
        for (int i = 1; i <= 5; i++) {
            selectedModel.addElement("Item " + j);
            j++;
        }
    }

    private void clearSelectedItems() {
        DefaultListModel<String> selectedModel = getSelectedModel();
        if (selectedModel == null) {
            return;
        }

        /*JList<String> sourceList = getSelectedList();
        List<String> selectedItems = sourceList.getSelectedValuesList();
        for (String selectedItem : selectedItems) {
            selectedModel.removeElement(selectedItem);
        }*/

        selectedModel.clear();
    }

    private JList<String> getSelectedList() {
        if (list1Checkbox.getState()) {
            return list1;
        } else if (list2Checkbox.getState()) {
            return list2;
        } else if (list3Checkbox.getState()) {
            return list3;
        }

        return null;
    }

    private DefaultListModel<String> getSelectedModel() {
        if (list1Checkbox.getState()) {
            return list1Model;
        } else if (list2Checkbox.getState()) {
            return list2Model;
        } else if (list3Checkbox.getState()) {
            return list3Model;
        }

        return null;
    }
}