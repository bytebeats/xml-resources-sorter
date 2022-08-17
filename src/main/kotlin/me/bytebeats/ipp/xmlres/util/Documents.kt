package me.bytebeats.ipp.xmlres

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.StringWriter
import java.nio.charset.Charset
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Throws(Exception::class)
fun String.toDocument(): Document {
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()
    return builder.parse(ByteArrayInputStream(this.toByteArray(Charset.forName("UTF-8"))))
}

@Throws(IOException::class)
fun Document.toPrettyString(indent: Int, insertEncoding: Boolean): String {
    return try {
        val factory = TransformerFactory.newInstance()
        val transformer = factory.newTransformer()
        transformer.apply {
            setOutputProperty(OutputKeys.INDENT, "yes")
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, if (!insertEncoding) "yes" else "no")
            setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent.toString())
        }
        val source = DOMSource(this)
        val writer = StringWriter()
        val result = StreamResult(writer)
        transformer.transform(source, result)
        writer.toString()
    } catch (e: TransformerConfigurationException) {
        throw IOException(e)
    } catch (e: TransformerException) {
        throw IOException(e)
    }
}

fun Document.toNodeList(): List<CommentedNode> {
    val list = mutableListOf<CommentedNode>()
    val childrenNode = this.documentElement.childNodes
    var comments: MutableList<Node>? = null
    for (i in 0 until childrenNode.length) {
        val node = childrenNode.item(i)
        // Add comment and eat-comment tag to comments list
        if (node.nodeType == Node.COMMENT_NODE || "eat-comment" == node.nodeName) {
            if (comments == null) {
                comments = mutableListOf()
            }
            comments.add(node)
        } else {
            list.add(CommentedNode(node, comments))
            comments = null
        }
    }
    return list.toList()
}

fun Document.deleteChildNodes() {
    val childNodes = this.documentElement.childNodes
    while (true) {
        val child = childNodes.item(0) ?: break
        this.documentElement.removeChild(child)
    }
}

fun Document.insertSpaceBetweenDiffPrefix(
    nodes: List<CommentedNode>,
    prefixLocation: Int,
    isSnakeCase: Boolean
): List<CommentedNode> {
    val insertedList = mutableListOf<CommentedNode>()
    var beforePrefix: String? = null
    for (commentedNode in nodes) {
        val node = commentedNode.node
        val nameValue = (node as Element).getAttribute("name")
        val prefix = try {
            if (isSnakeCase) {
                if (!nameValue.contains("_")) {
                    notifyError("namedItem \"$nameValue\" is not snake case")
                    nameValue
                } else {
                    nameValue.split("_")[prefixLocation - 1]
                }
            } else {
                val pattern = Pattern.compile("[A-Z]")
                pattern.split(nameValue)[prefixLocation - 1]
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
        if (nodes.indexOf(commentedNode) > 0) {
            if (beforePrefix.isNullOrEmpty() || prefix.isNullOrEmpty() || beforePrefix != prefix) {
                val spaceElement = createElement("space")
                insertedList.indexOf(CommentedNode(spaceElement, null))
            }
        }
        beforePrefix = prefix
        insertedList.add(commentedNode)
    }
    return insertedList
}