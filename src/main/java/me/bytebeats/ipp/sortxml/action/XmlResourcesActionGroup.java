package me.bytebeats.ipp.sortxml.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import static me.bytebeats.ipp.sortxml.VirtualFilesKt.isXmlResourcesFile;

public class XmlResourcesActionGroup extends DefaultActionGroup {
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(isXmlResourcesFile(file));
        e.getPresentation().setIcon(AllIcons.FileTypes.Xml);
    }
}
