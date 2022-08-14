package me.bytebeats.ipg.xmlsorter

import org.w3c.dom.Node

data class CommentedNode(val node: Node, val comments: List<Node>) {
    internal class Comparator(private val separateNonTranslatable: Boolean) : kotlin.Comparator<CommentedNode> {
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
                    val transItem1 = attribute1.getNamedItem(TRANSLATABLE)
                    val transItem2 = attribute2.getNamedItem(TRANSLATABLE)
                    val isTranslatable1 = transItem1?.textContent?.equals(true.toString()) ?: false
                    val isTranslatable2 = transItem2?.textContent?.equals(true.toString()) ?: false
                    if (isTranslatable1 && !isTranslatable2) {
                        return -1
                    } else if (!isTranslatable1 && isTranslatable2) {
                        return 1
                    }

                }
                val attributeValue1 = attribute1.getNamedItem(NAME).textContent
                val attributeValue2 = attribute2.getNamedItem(NAME).textContent
                return attributeValue1.compareTo(attributeValue2)
            }
        }
    }

    companion object {
        private const val TRANSLATABLE = "translatable"
        private const val NAME = "name"
    }
}
