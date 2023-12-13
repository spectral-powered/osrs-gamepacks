package dev.kyleescobar.osrsgamepacks

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.file
import dev.kyleescobar.osrsgamepacks.OsrsGamepacksCLI.*
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class OsrsGamepacksCLI: NoOpCliktCommand(
    name = "OSRS Gamepacks Downloader",
    printHelpOnEmptyArgs = true,
    invokeWithoutSubcommand = false,
) {

    init {
        versionOption("1.0.0")
    }

    class DownloadCommand : CliktCommand(
        name = "download"
    ) {

        val outputFile by argument("output file", "Path to save the downloaded gamepack jar")
            .file(canBeDir = false)

        val url by option("-u", "--url", help = "The base jagex url to download from.")
            .default("https://oldschool.runescape.com")

        override fun run() {
            val javConfig = JavConfig("$url/jav_config.ws")
            val gamepack = RemoteGamepack(javConfig)
            gamepack.save(outputFile)
        }
    }

    class RevisionCommand : CliktCommand(
        name = "revision"
    ) {

        val saveFlag by option("-s", "--save", help = "Save to 'revision' file in current directory.").flag()

        val file by option("-f", "--file", help = "File path to save the result to.")
            .file(canBeDir = false)
            .default(File("revision"))

        override fun run() {
            val javConfig = JavConfig()
            val revision = javConfig["param_25"]
            println(revision)
            if(saveFlag) {
                Files.deleteIfExists(file.toPath())
                Files.writeString(file.toPath(), revision, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
            }
        }
    }
}

fun main(args: Array<String>) = OsrsGamepacksCLI()
    .subcommands(
        DownloadCommand(),
        RevisionCommand()
    )
    .main(args)