package me.bytebeats.ipp.sortxml.action.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.bytebeats.ipp.sortxml.action.AbstractXmlResourcesAction;
import me.bytebeats.ipp.sortxml.dialog.XmlResourcesOptionDialog;
import org.jetbrains.annotations.NotNull;

import static me.bytebeats.ipp.sortxml.VirtualFilesKt.isXmlResourcesFile;

public class XmlResourcesAction extends AbstractXmlResourcesAction {

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
        if (project == null || editor == null) return;
        XmlResourcesOptionDialog dialog = new XmlResourcesOptionDialog(project);
        if (!dialog.showAndGet()) return;
        // options
        boolean insertSpaceEnabled = dialog.isInsertSpaceEnabled();
        boolean isSnakeCase = true;
        int prefixSpaceLocation = 0;
        if (insertSpaceEnabled) {
            isSnakeCase = dialog.isSnakeCase();
            prefixSpaceLocation = dialog.getPrefixSpaceLocation();
        }
        boolean insertXmlInfoEnabled = dialog.isInsertXmlInfoEnabled();
        boolean deleteCommentsEnabled = dialog.isDeleteCommentsEnabled();
        int indent = dialog.getIndent();
        boolean separateNonTranslatable = dialog.isSeparateNonTranslatableStringsEnabled();
        execute(project,
                editor,
                isSnakeCase,
                prefixSpaceLocation,
                insertSpaceEnabled,
                insertXmlInfoEnabled,
                deleteCommentsEnabled,
                indent,
                separateNonTranslatable);
    }
}
