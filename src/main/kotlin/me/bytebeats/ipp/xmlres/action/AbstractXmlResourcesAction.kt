package me.bytebeats.ipp.xmlres.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import me.bytebeats.ipp.xmlres.*
import org.w3c.dom.Document
import java.io.IOException

abstract class AbstractXmlResourcesAction : AnAction() {
    protected fun execute(
        project: Project,
        editor: Editor,
        isSnakeCase: Boolean,
        prefix1stNwords: Int,
        insertSpaceBetweenDiffPrefix: Boolean,
        insertXmlEncoding: Boolean,
        deleteComment: Boolean,
        indent: Int,
        separateNonTranslatable: Boolean,
        isCaseSensitive: Boolean
    ) {
        //content, remove \n between resources items
        val simplifiedContent = editor.document.text.replace(">\n*\\s+?<".toRegex(), "><")
        val document: Document
        try {
            document = simplifiedContent.toDocument()
        } catch (e: Exception) {
            notifyError(e.localizedMessage)
            return
        }
        //get node list from document object
        var commentedNodes = document.toNodeList()
        //sort
        commentedNodes = commentedNodes.sortedWith(CommentedNode.Comparator(separateNonTranslatable, isCaseSensitive))
        document.deleteChildNodes()

        //insert space if enabled
        if (insertSpaceBetweenDiffPrefix) {
            commentedNodes = document.insertSpaceBetweenDiffPrefix(commentedNodes, prefix1stNwords, isSnakeCase)
        }

        //append node into document with comments if enabled
        for (commentedNode in commentedNodes) {
            // don't write comment if `enableDeleteComment` is true
            if (!deleteComment) {
                val comments = commentedNode.comments
                comments?.let {
                    for (comment in it) {
                        document.documentElement.appendChild(comment)
                    }
                }
            }
            document.documentElement.appendChild(commentedNode.node)
        }

        //get pretty string from document object
        var prettyString: String
        try {
            prettyString = document.toPrettyString(indent, insertXmlEncoding)
            // IDEA uses '\n' for all their text editors internally, so we just use '\n' as our line separator
            // See: http://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview/documents.html
            val lineSeparator = System.getProperty("line.separator")
            if (lineSeparator != "\n") {
                prettyString = prettyString.replace(lineSeparator, "\n")
            }
        } catch (e: IOException) {
            notifyError(e.localizedMessage)
            return
        }

        //insert space between different prefix
        if (insertSpaceBetweenDiffPrefix) {
            prettyString = prettyString.replace("\n\\s+<space/>".toRegex(), "\n")
        }
        // eliminate line breaks before/after xliff declaration
        prettyString = prettyString.replace("\n\\s+<xliff:".toRegex(), "<xliff:")
        prettyString = prettyString.replace("(</xliff:\\w+>)\n\\s+".toRegex(), "$1")
        WriteCommandAction.runWriteCommandAction(project) {
            editor.document.setText(prettyString)
        }
    }
}