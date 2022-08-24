package me.bytebeats.ipp.xmlres

import me.bytebeats.ipp.xmlres.util.orFalse
import org.w3c.dom.Node

data class CommentedNode(val node: Node, val comments: List<Node>?) {
    internal class AttrNameComparator(
        private val separateNonTranslatable: Boolean,
        private val isCaseSensitive: Boolean
    ) :
        Comparator<CommentedNode> {
        override fun compare(o1: CommentedNode, o2: CommentedNode): Int {
            val node1 = o1.node
            val node2 = o2.node
            if (node1.nodeType == Node.COMMENT_NODE && node2.nodeType == Node.COMMENT_NODE) {
                return 0
            } else if (node1.nodeType == Node.COMMENT_NODE && node2.nodeType != Node.COMMENT_NODE) {
                return -1
            } else if (node1.nodeType != Node.COMMENT_NODE && node2.nodeType == Node.COMMENT_NODE) {
                return 1
            } else {
                val attribute1 = node1.attributes
                val attribute2 = node2.attributes
                if (separateNonTranslatable) {
                    val translatableItem1 = attribute1.getNamedItem(TRANSLATABLE)
                    val translatableItem2 = attribute2.getNamedItem(TRANSLATABLE)
                    val translatable1 = translatableItem1?.textContent?.equals(true.toString()).orFalse()
                    val translatable2 = translatableItem2?.textContent?.equals(true.toString()).orFalse()
                    if (translatable1 && !translatable2) {
                        return -1
                    } else if (!translatable1 && translatable2) {
                        return 1
                    }
                }
                val attrNameValue1 = attribute1.getNamedItem(NAME).textContent
                val attrNameValue2 = attribute2.getNamedItem(NAME).textContent
                return if (isCaseSensitive) attrNameValue1.compareTo(attrNameValue2)
                else attrNameValue1.lowercase().compareTo(attrNameValue2.lowercase())
            }
        }
    }

    companion object {
        private const val TRANSLATABLE = "translatable"
        private const val NAME = "name"
    }
}
