package me.bytebeats.ipp.sortxml.action.impl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import me.bytebeats.ipp.sortxml.action.AbstractSortXmlAction;
import me.bytebeats.ipp.sortxml.dialog.SortOptionDialog;
import org.jetbrains.annotations.NotNull;

public class SortXmlInstantAction extends AbstractSortXmlAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = getEventProject(e);
        final Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (project == null || editor == null) {
            return;
        }
        PropertiesComponent pc = PropertiesComponent.getInstance();
        execute(project,
                editor,
                pc.getInt(SortOptionDialog.PC_KEY_INPUT_CASE, 0) == 0,
                SortOptionDialog.getPrefixSpaceLocationValueAt(pc.getInt(SortOptionDialog.PC_KEY_PREFIX_SPACE_LOCATION, 0)),
                pc.getBoolean(SortOptionDialog.PC_KEY_SPACE_BETWEEN_PREFIX, true),
                pc.getBoolean(SortOptionDialog.PC_KEY_INSERT_XML_INFO, true),
                pc.getBoolean(SortOptionDialog.PC_KEY_DELETE_COMMENT, false),
                SortOptionDialog.getIndentValueAt(pc.getInt(SortOptionDialog.PC_KEY_INDENT, 1)),
                pc.getBoolean(SortOptionDialog.PC_KEY_SEPARATE_NON_TRANSLATABLE, false)
        );
    }
}
