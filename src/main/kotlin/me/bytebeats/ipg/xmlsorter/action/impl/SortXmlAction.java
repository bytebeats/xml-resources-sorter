package me.bytebeats.ipg.xmlsorter.action.impl;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import me.bytebeats.ipg.xmlsorter.action.AbstractSortXmlAction;
import org.jetbrains.annotations.NotNull;

import static me.bytebeats.ipg.xmlsorter.VirtualFilesKt.isResourceFile;

public class SortXmlAction extends AbstractSortXmlAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(isResourceFile(file));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = getEventProject(e);
        final Editor editor = e.getData(PlatformDataKeys.EDITOR);
        // TODO: 2022/8/14 pop up dialog here
    }
}
