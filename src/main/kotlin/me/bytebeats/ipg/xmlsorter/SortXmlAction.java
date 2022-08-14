package me.bytebeats.ipg.xmlsorter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.input.XmlStreamReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;

public class SortXmlAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        e.getPresentation().setEnabledAndVisible(isResourceFile(file));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final Editor editor = e.getData(PlatformDataKeys.EDITOR);
        // TODO: 2022/8/14 pop up dialog here
    }

    private static boolean isResourceFile(@Nullable VirtualFile file) {
        if (file == null || !file.getName().endsWith(".xml")) return false;
        XMLStreamReader reader = null;
        try {
            reader = XMLInputFactory.newFactory().createXMLStreamReader(file.getInputStream());
            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    return "resources".equals(reader.getLocalName());
                }
            }
        } catch (XMLStreamException | IOException e) {
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException ignore) {

                }
            }
        }
        return false;
    }
}
