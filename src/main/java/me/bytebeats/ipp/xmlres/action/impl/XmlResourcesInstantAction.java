package me.bytebeats.ipp.xmlres.action.impl;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.bytebeats.ipp.xmlres.action.AbstractXmlResourcesAction;
import me.bytebeats.ipp.xmlres.dialog.XmlResourcesOptionDialog;
import org.jetbrains.annotations.NotNull;

import static me.bytebeats.ipp.xmlres.VirtualFilesKt.isXmlResourcesFile;

public class XmlResourcesInstantAction extends AbstractXmlResourcesAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(isXmlResourcesFile(file));
    }

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
                pc.getInt(XmlResourcesOptionDialog.PC_KEY_INPUT_CASE, 0) == 0,
                XmlResourcesOptionDialog.getPrefixSpaceLocationValueAt(pc.getInt(XmlResourcesOptionDialog.PC_KEY_PREFIX_SPACE_LOCATION, 0)),
                pc.getBoolean(XmlResourcesOptionDialog.PC_KEY_SPACE_BETWEEN_PREFIX, true),
                pc.getBoolean(XmlResourcesOptionDialog.PC_KEY_INSERT_XML_INFO, true),
                pc.getBoolean(XmlResourcesOptionDialog.PC_KEY_DELETE_COMMENT, false),
                XmlResourcesOptionDialog.getIndentValueAt(pc.getInt(XmlResourcesOptionDialog.PC_KEY_INDENT, 1)),
                pc.getBoolean(XmlResourcesOptionDialog.PC_KEY_SEPARATE_NON_TRANSLATABLE, false)
        );
    }
}
