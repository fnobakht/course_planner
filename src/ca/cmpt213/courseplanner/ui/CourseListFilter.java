package ca.cmpt213.courseplanner.ui;

import ca.cmpt213.courseplanner.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

/**
 * @author  Faranak Nobakhtian
 * @version  2016-08-01.
 * This class displays subject list panel to show list of departments
 * It notifies Model when user changes a department
 */
public class CourseListFilter extends MyABCPanel {
    static String title = "Course List Filter";
    JPanel userContentsPanel;
    JComboBox departmentList;
    JCheckBox undergrad;
    JCheckBox grad;
    JLabel departmentLabel;

    public CourseListFilter(Model model, int width) {
        super(model, title);
        userContentsPanel = getUserContentsPanel();
        userContentsPanel.setLayout(new BoxLayout(userContentsPanel, BoxLayout.PAGE_AXIS));
        addDepartment();
        addCheckBoxes();
        addUpdateButton();
        userContentsPanel.setPreferredSize(new Dimension(width, userContentsPanel.getPreferredSize().height));
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.setPreferredSize(this.getPreferredSize());
    }

    public void addDepartment(){
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        departmentLabel = new JLabel("Department");
        topPanel.add(departmentLabel);
        departmentList = new JComboBox();
        Set<String> departments = getModel().getDepartments();
        String[] departmentsName = departments.toArray(new String[departments.size()]);
        Arrays.sort(departmentsName);
        for(String aDepartment : departmentsName){
            departmentList.addItem(aDepartment);
        }
        Dimension comboSize = departmentList.getPreferredSize();
        // need to be fixed
        comboSize.width += 140;
        departmentList.setMaximumSize(comboSize);
        topPanel.add(departmentList);
        userContentsPanel.add(topPanel);
    }

    public void addCheckBoxes(){
        JPanel checkBoxesPanel = new JPanel();
        checkBoxesPanel.setLayout(new GridLayout(2, 1));
        checkBoxesPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        addUnderGradCheckBoxs(checkBoxesPanel);
        addGradCheckBox(checkBoxesPanel);
        userContentsPanel.add(checkBoxesPanel);
    }

    public void addUnderGradCheckBoxs(JPanel parent){
        undergrad = new JCheckBox("Include undergrad courses");
        //undergrad.setAlignmentX(JCheckBox.LEFT);
        parent.add(undergrad);
    }

    public void addGradCheckBox(JPanel parent){
        grad = new JCheckBox("Include grad courses");
        //grad.setAlignmentX(JCheckBox.LEFT);
        parent.add(grad);
    }

    public void addUpdateButton(){
        userContentsPanel.add(makeUpdateButton());
    }

    public JButton makeUpdateButton(){
        JButton update = new JButton("Update Course List");
        update.addActionListener(e -> {
            updateCourses();
        });
        return update;
    }

    private void updateCourses(){
        getModel().setCourseListFilterData(getDepartmentName(), isGradSelected(), isUndergradSelected());
    }


    private boolean isUndergradSelected(){
        return undergrad.isSelected();
    }

    private boolean isGradSelected(){
        return grad.isSelected();
    }

    private String getDepartmentName(){
        String subjectName = (String)departmentList.getSelectedItem();
        return subjectName;
    }

}