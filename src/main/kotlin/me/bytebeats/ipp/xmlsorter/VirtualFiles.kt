package me.bytebeats.ipp.xmlsorter

import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamException
import javax.xml.stream.XMLStreamReader

internal fun VirtualFile?.isResourceFile(): Boolean {
    if (this?.name?.endsWith(".xml") != true) return false
    var reader: XMLStreamReader? = null
    try {
        reader = XMLInputFactory.newFactory().createXMLStreamReader(this.inputStream)
        while (reader.hasNext()) {
            if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                return "resources" == reader.localName
            }
        }
    } catch (e: XMLStreamException) {
        return false
    } catch (e: IOException) {
        return false
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (ignore: XMLStreamException) {
            }
        }
    }
    return false
}