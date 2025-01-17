package View.ProfileUI;

import Controller.DataRequestHandler.ProfilesQueryController;
import Controller.DataUpdateHandler.ProfileUpdateController;
import View.MainUI.NavigateUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileEditPage extends JFrame {

    // Declare UI components and user data
    private ProfileUIData user;
    private JTextField usernameField, passwordField, firstNameField, lastNameField, ageField, heightField,
            weightField, specialPeriodField;
    private JCheckBox hasWeightScaleCheckBox;

    private JRadioButton maleButton, femaleButton;


    private JRadioButton kgRadioButton, lbsRadioButton, cmRadioButton, inchRadioButton, hasSpecialPeriod;

    public ProfileEditPage(ProfileUIData user) {
        this.user = user;

        createComponents();
    }

    // Method to create UI components
    private void createComponents() {
        setTitle("Profile Edit");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Define layout parameters
        int labelX = 50;
        int textFieldX = 200;
        int startY = 30;
        int ySpacing = 30;

        // Add various fields and input components
        addField("Username:", usernameField = new JTextField(user.getUsername()), labelX, textFieldX, startY, ySpacing);
        usernameField.setEnabled(false);
        addField("Password:", passwordField = new JPasswordField(user.getPassword()), labelX, textFieldX, startY + ySpacing, ySpacing);
        passwordField.setEnabled(false);
        addField("First Name:", firstNameField = new JTextField(user.getFirstName()), labelX, textFieldX, startY + 2 * ySpacing, ySpacing);
        addField("Last Name:", lastNameField = new JTextField(user.getLastName()), labelX, textFieldX, startY + 3 * ySpacing, ySpacing);
        addField("Age:", ageField = new JTextField("" + user.getAge()), labelX, textFieldX, startY + 4 * ySpacing, ySpacing);
        addField("Gender:", null, labelX, textFieldX, startY + 5 * ySpacing, ySpacing);
        maleButton = new JRadioButton("male");
        maleButton.setBounds(textFieldX, startY + 5 * ySpacing, 70, 20);
        add(maleButton);

        femaleButton = new JRadioButton("female");
        femaleButton.setBounds(textFieldX + 80, startY + 5 * ySpacing, 90, 20);
        add(femaleButton);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        if (user.getGender().equals("male")) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }

        addField("Height:", heightField = new JTextField("" + user.getHeight()), labelX, textFieldX, startY + 6 * ySpacing, ySpacing);

        cmRadioButton = new JRadioButton("cm");
        cmRadioButton.setBounds(textFieldX + 160, startY + 6 * ySpacing, 60, 20);
        add(cmRadioButton);


        inchRadioButton = new JRadioButton("inches");
        inchRadioButton.setBounds(textFieldX + 220, startY + 6 * ySpacing, 90, 20);
        add(inchRadioButton);


        ButtonGroup heightUnitGroup = new ButtonGroup();
        heightUnitGroup.add(cmRadioButton);
        heightUnitGroup.add(inchRadioButton);
        if (user.getHeightUnit().equals("cm")) {
            cmRadioButton.setSelected(true);
        } else {
            inchRadioButton.setSelected(true);
        }

        addField("Weight:", weightField = new JTextField("" + user.getWeight()), labelX, textFieldX, startY + 7 * ySpacing, ySpacing);

        kgRadioButton = new JRadioButton("kg");
        kgRadioButton.setBounds(textFieldX + 160, startY + 7 * ySpacing, 60, 20);
        add(kgRadioButton);

        lbsRadioButton = new JRadioButton("lbs");
        lbsRadioButton.setBounds(textFieldX + 220, startY + 7 * ySpacing, 90, 20);
        add(lbsRadioButton);

        ButtonGroup weightUnitGroup = new ButtonGroup();
        weightUnitGroup.add(kgRadioButton);
        weightUnitGroup.add(lbsRadioButton);
        if (user.getWeightUnit().equals("kg")) {
            kgRadioButton.setSelected(true);
        } else {
            lbsRadioButton.setSelected(true);
        }

        addField("Special Period:", specialPeriodField = new JTextField(user.getSpecialPeriod()), labelX, textFieldX, startY + 8 * ySpacing, ySpacing);

        hasSpecialPeriod = new JRadioButton("None");
        hasSpecialPeriod.setBounds(textFieldX + 160, startY + 8 * ySpacing, 90, 20);
        hasSpecialPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialPeriodField.setEnabled(!hasSpecialPeriod.isSelected());
            }
        });

        add(hasSpecialPeriod);

        hasWeightScaleCheckBox = new JCheckBox("Do you have a weight scale?");
        hasWeightScaleCheckBox.setBounds(labelX, startY + 9 * ySpacing, 300, 20);
        add(hasWeightScaleCheckBox);
        if (user.isHasWeightScale()) {
            hasWeightScaleCheckBox.setSelected(true);
        }

        // Add Save and Back buttons with action listeners
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(labelX, startY + 10 * ySpacing, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAction();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(labelX + 150, startY + 10 * ySpacing, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAction();
            }
        });
        add(backButton);

        add(saveButton);

        setVisible(true);
    }

    // Method to handle the "Back" button action
    public void backAction() {
        this.dispose();

        ProfileDetailsPage.launch(user);
    }

    // Method to handle the "Save" button action
    public void saveAction() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = maleButton.isSelected() ? "male" : "female";
        double height = Double.parseDouble(heightField.getText());
        String heightUnit = cmRadioButton.isSelected() ? "cm" : "inches";
        double weight = Double.parseDouble(weightField.getText());
        String weightUnit = kgRadioButton.isSelected() ? "kg" : "lbs";
        String specialPeriod = hasSpecialPeriod.isSelected() ? null : specialPeriodField.getText();
        boolean hasWeightScale = hasWeightScaleCheckBox.isSelected();

        // Update the user profile using the ProfileUpdateController
        ProfileUpdateController.getInstance().updateProfile(user.getId(), firstName, lastName, age, gender, height, heightUnit, weight, weightUnit, specialPeriod, hasWeightScale);

        JOptionPane.showMessageDialog(this, "Profile update successfully!\n");

        // Close the current JFrame and launch the main navigation page with updated user data
        this.dispose();
        NavigateUI.launch(ProfilesQueryController.getInstance().getProfile(user.getId()));
    }

    // Method to add a label and corresponding text field to the JFrame
    private void addField(String label, JTextField field, int labelX, int textFieldX, int y, int ySpacing) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(labelX, y, 150, 20);
        add(jLabel);
        if (field != null) {
            field.setBounds(textFieldX, y, 150, 20);
            add(field);
        }

    }

    // Static method to launch the ProfileEditPage
    public static void launch(ProfileUIData user) {
        new ProfileEditPage(user);
    }
}
