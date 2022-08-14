package me.bytebeats.ipg.xmlsorter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class DirectSortXmlAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final Editor editor = e.getData(PlatformDataKeys.EDITOR);
        // TODO: 2022/8/14 execute action here
    }
}
