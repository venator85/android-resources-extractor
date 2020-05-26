package eu.alessiobianchi.resourcesextractor

import nu.xom.Builder
import nu.xom.Serializer
import xmlpulltreebuilder.TagNode
import xmlpulltreebuilder.TreeBuilder
import xmlpulltreebuilder.TreeToXML
import java.io.File
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {

	val targetFilePrefix = "downloadmanager_"
	val targetAttrPrefix = "downloadmanager_"

	val dirFilter: (String) -> Boolean = { dirName ->
		dirName.startsWith("values")
	}

	val res = listOf("notification_download_complete", "notification_download_failed", "storage_description")

	val dir = Paths.get("/Users/venator/Desktop/DownloadProvider/res")
	val out = Paths.get("/Users/venator/Desktop/DownloadProvider/resOut")

	out.toFile().deleteRecursively()
	out.toFile().mkdirs()

	Files.walk(dir)
		.filter {
			!it.toFile().isDirectory && it.fileName.toString().endsWith(".xml", true)
		}.forEach file@{
			val relPathChunks = dir.relativize(it).toList()
			val outDir = relPathChunks.first()

			if (!dirFilter(outDir.toString())) {
				return@file
			}

			it.toFile().bufferedReader().use { buf ->
				val root = TreeBuilder().parse(buf)
				var found = false

				var outTree = TagNode("resources")
				val outRoot = outTree

				if (root.name == "resources") {
					outTree.attrList = root.attrList

					root.childrenTagNodeIterator().forEach children@{ childNode ->
						val nameAttr = childNode.attrList.firstOrNull { attr ->
							attr.name == "name"
						} ?: return@children
						val attrMatches = res.contains(nameAttr.value)
						if (attrMatches) {
							nameAttr.value = targetAttrPrefix + nameAttr.value
							if (!found) {
								outTree.child = childNode
								found = true
							} else {
								outTree.sibling = childNode
							}
							outTree = childNode
						}
					}
				}

				if (found) {
					outTree.sibling = null

					val outName = targetFilePrefix + relPathChunks.last()
					val outPath = out.resolve(outDir).toFile()
					outPath.mkdirs()

					File(outPath, outName).outputStream().use { w ->
						val xml = TreeToXML(outRoot).toString()
						prettyFormatTo(xml, os = w)
					}
				}
			}
		}
}

fun prettyFormatTo(input: String, indentSpaces: Int = 4, os: OutputStream) {
	val serializer = Serializer(os, StandardCharsets.UTF_8.name())
	serializer.indent = indentSpaces
	serializer.write(Builder().build(input, ""))
}
