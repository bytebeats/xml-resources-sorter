package me.bytebeats.ipp.xmlres.dialog;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import me.bytebeats.ipp.xmlres.util.JComboBoxesKt;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionListener;

public class XmlResourcesOptionDialog extends DialogWrapper {

    private static final String[] INPUT_CASES = {"Snake Case", "Camel Case"};
    private static final String[] PREFIX_SPACE_LOCATIONS = {"1", "2", "3", "4"};
    private static final String[] INDENTS = {"2", "4", "8", "12"};

    private static final String TITLE = "Sort Xml Option";
    private static final String OK_BUTTON_TEXT = "Sort";

    public static final String PC_KEY_PREFIX_SPACE_LOCATION = "PC_KEY_PREFIX_SPACE_LOCATION";
    public static final String PC_KEY_SPACE_BETWEEN_PREFIX = "PC_KEY_SPACE_BETWEEN_PREFIX";
    public static final String PC_KEY_INSERT_XML_INFO = "PC_KEY_INSERT_XML_INFO";
    public static final String PC_KEY_DELETE_COMMENT = "PC_KEY_DELETE_COMMENT";
    public static final String PC_KEY_INPUT_CASE = "PC_KEY_INPUT_CASE";
    public static final String PC_KEY_INDENT = "PC_KEY_INDENT";
    public static final String PC_KEY_SEPARATE_NON_TRANSLATABLE = "PC_KEY_SEPARATE_NON_TRANSLATABLE";
    public static final String PC_KEY_CASE_SENSITIVE = "PC_KEY_CASE_SENSITIVE";

    private JPanel mMainPanel;
    private JCheckBox mInsertSpaceCheckBox;
    private JComboBox<String> mNamingStyleComboBox;
    private JComboBox<String> mPrefixSpaceLocationComboBox;
    private JCheckBox mInsertXmlInfoCheckBox;
    private JCheckBox mDeleteCommentsCheckBox;
    private JComboBox<String> mIndentComboBox;
    private JLabel mPrefixSpaceLocationLabel;
    private JCheckBox mSeparateNonTranslatableCheckBox;
    private JCheckBox mCaseSensitiveCheckBox;
    private JLabel mNamingStyleLabel;

    public XmlResourcesOptionDialog(@Nullable Project project) {
        super(project, true);
        setTitle(TITLE);
        setOKButtonText(OK_BUTTON_TEXT);
        initComponent();
        init();
    }

    private void initComponent() {
        JComboBoxesKt.alignItemCenter(mNamingStyleComboBox);
        JComboBoxesKt.alignItemCenter(mPrefixSpaceLocationComboBox);
        JComboBoxesKt.alignItemCenter(mIndentComboBox);
        PropertiesComponent pc = PropertiesComponent.getInstance();
        mInsertSpaceCheckBox.setSelected(pc.getBoolean(PC_KEY_SPACE_BETWEEN_PREFIX, true));
        mNamingStyleComboBox.setModel(new DefaultComboBoxModel<>(INPUT_CASES));
        mNamingStyleComboBox.setSelectedIndex(pc.getInt(PC_KEY_INPUT_CASE, 0));
        mPrefixSpaceLocationComboBox.setModel(new DefaultComboBoxModel<>(PREFIX_SPACE_LOCATIONS));
        mPrefixSpaceLocationComboBox.setSelectedIndex(pc.getInt(PC_KEY_PREFIX_SPACE_LOCATION, 0));
        mInsertXmlInfoCheckBox.setSelected(pc.getBoolean(PC_KEY_INSERT_XML_INFO, true));
        mDeleteCommentsCheckBox.setSelected(pc.getBoolean(PC_KEY_DELETE_COMMENT, false));
        mIndentComboBox.setModel(new DefaultComboBoxModel<>(INDENTS));
        mIndentComboBox.setSelectedIndex(pc.getInt(PC_KEY_INDENT, 1));
        mSeparateNonTranslatableCheckBox.setSelected(pc.getBoolean(PC_KEY_SEPARATE_NON_TRANSLATABLE, false));
        mCaseSensitiveCheckBox.setSelected(pc.getBoolean(PC_KEY_CASE_SENSITIVE, true));

        ActionListener actionListener = actionEvent -> {
            mNamingStyleComboBox.setEnabled(mInsertSpaceCheckBox.isSelected());
            mPrefixSpaceLocationLabel.setEnabled(mInsertSpaceCheckBox.isSelected());
            mNamingStyleLabel.setEnabled(mInsertSpaceCheckBox.isSelected());
            mPrefixSpaceLocationComboBox.setEnabled(mInsertSpaceCheckBox.isSelected());
        };
        mInsertSpaceCheckBox.addActionListener(actionListener);
        // initial invoke
        actionListener.actionPerformed(null);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mMainPanel;
    }

    public boolean isInsertSpaceEnabled() {
        return mInsertSpaceCheckBox.isSelected();
    }

    public boolean isSnakeCase() {
        return mNamingStyleComboBox.getSelectedIndex() == 0;
    }

    public int getPrefixSpaceLocation() {
        return getPrefixSpaceLocationValueAt(mPrefixSpaceLocationComboBox.getSelectedIndex());
    }


    public boolean isInsertXmlInfoEnabled() {
        return mInsertXmlInfoCheckBox.isSelected();
    }

    public boolean isDeleteCommentsEnabled() {
        return mDeleteCommentsCheckBox.isSelected();
    }

    public int getIndent() {
        return getIndentValueAt(mIndentComboBox.getSelectedIndex());
    }

    public boolean isSeparateNonTranslatableStringsEnabled() {
        return mSeparateNonTranslatableCheckBox.isSelected();
    }

    public boolean isCaseSensitive() {
        return mCaseSensitiveCheckBox.isSelected();
    }

    @Override
    protected void doOKAction() {
        save();
        super.doOKAction();
    }

    public void save() {
        PropertiesComponent pc = PropertiesComponent.getInstance();
        pc.setValue(PC_KEY_SPACE_BETWEEN_PREFIX, mInsertSpaceCheckBox.isSelected(), true);
        pc.setValue(PC_KEY_INPUT_CASE, mNamingStyleComboBox.getSelectedIndex(), 0);
        pc.setValue(PC_KEY_PREFIX_SPACE_LOCATION, mPrefixSpaceLocationComboBox.getSelectedIndex(), 0);
        pc.setValue(PC_KEY_INSERT_XML_INFO, mInsertXmlInfoCheckBox.isSelected(), true);
        pc.setValue(PC_KEY_DELETE_COMMENT, mDeleteCommentsCheckBox.isSelected(), false);
        pc.setValue(PC_KEY_INDENT, mIndentComboBox.getSelectedIndex(), 1);
        pc.setValue(PC_KEY_SEPARATE_NON_TRANSLATABLE, mSeparateNonTranslatableCheckBox.isSelected(), false);
        pc.setValue(PC_KEY_CASE_SENSITIVE, mCaseSensitiveCheckBox.isSelected(), true);
    }

    public static int getPrefixSpaceLocationValueAt(int index) {
        return Integer.parseInt(PREFIX_SPACE_LOCATIONS[index]);
    }

    public static int getIndentValueAt(int index) {
        return Integer.parseInt(INDENTS[index]);
    }

}