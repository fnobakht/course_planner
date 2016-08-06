package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.model.*;
import javafx.scene.control.ComboBox;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Set;
import java.awt.*;

/**
 * Created by faranakpouya on 2016-07-26.
 */
public class CourseList extends MyABCPanel {
    static String title = "Course List";
    JPanel userContentsPanel;
    JList courseList;
    DefaultListModel listModel;
    String departmentName;

    public CourseList(Model model, int width){
        super(model, title);
        userContentsPanel = getUserContentsPanel();
        userContentsPanel.setLayout(new BoxLayout(userContentsPanel, BoxLayout.PAGE_AXIS));
//        Set<String> catalogNumbers = getModel().getCatalogNumbersInSpecificDepartment();
        listModel = new DefaultListModel();
        courseList = new JList(listModel);
        ListSelectionModel listSelectionModel;
        listSelectionModel = courseList.getSelectionModel();
        listSelectionModel.addListSelectionListener(e -> updateCatalogNumber(e));
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        courseList.setVisibleRowCount(-1);
        courseList.setPreferredSize(new Dimension(width, 50));
        courseList.setFixedCellWidth(width / 2 - 2);
        JScrollPane listScroller = new JScrollPane(courseList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        userContentsPanel.add(listScroller);
        model.registerCourseListListener(makeCourseListener());
    }

    public void updateCatalogNumber(ListSelectionEvent e){
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        //If no item is selected
        if (lsm.isSelectionEmpty()){
            return;
        }
        //Find the index of selected catalogNumber and set it to its function
        int index = lsm.getMinSelectionIndex();
        String selectedCatalogNum = (String)listModel.get(index);
        int indexCatalogNumber = selectedCatalogNum.indexOf(departmentName);
        if(indexCatalogNumber != -1){
            int start = indexCatalogNumber + departmentName.length() + 1;
            int end = selectedCatalogNum.length();
            String catalogNumber = selectedCatalogNum.substring(start, end);
            getModel().setCatalogNumber(catalogNumber);
        }

    }

    private CourseListListener makeCourseListener(){
        return new CourseListListener() {
            @Override
            public void updateCourseList() {
                Set<String> catalogNumbers = getModel().getCatalogNumbersInSpecificDepartment();
                String[] courseNumbers = catalogNumbers.toArray(new String[catalogNumbers.size()]);
                Arrays.sort(courseNumbers);
                departmentName = getModel().getSubject();
                listModel.clear();
                int index = 0;
                for(String catalogNum : courseNumbers){
                    listModel.add(index, departmentName + " " + catalogNum);
                    index++;
                }
            }
        };
    }
}