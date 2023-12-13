package dev.kyleescobar.osrsgamepacks

import org.jsoup.Jsoup
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.BasicFileAttributeView
import java.util.jar.JarFile

sealed class Gamepack(private val bytes: ByteArray, private val initialClass: String = "client.class") {

    var revision: Int = 0

    fun save(file: File) {
        Files.deleteIfExists(file.toPath())
        Files.write(file.toPath(), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        val jar = JarFile(file)
        val time = jar.getJarEntry(initialClass).lastModifiedTime
        val attr = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView::class.java)
        attr.setTimes(time, time, time)
        jar.close()
    }
}

class RemoteGamepack(javConfig: JavConfig = JavConfig()) : Gamepack(
    Jsoup.connect(javConfig["codebase"] + javConfig["initial_jar"])
        .maxBodySize(0)
        .ignoreContentType(true)
        .execute()
        .bodyAsBytes(),
    javConfig["initial_class"]
)