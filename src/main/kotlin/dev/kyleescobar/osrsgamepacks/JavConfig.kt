package dev.kyleescobar.osrsgamepacks

import org.jsoup.Jsoup
import java.util.Properties

class JavConfig(val url: String = "https://oldschool.runescape.com/jav_config.ws") {

    private val properties: Properties by lazy {
        Properties().also {
            it.load(
                Jsoup.connect(url)
                    .get()
                    .wholeText()
                    .replace("msg=", "msg_")
                    .replace("param=", "param_")
                    .byteInputStream()
            )
        }
    }

    operator fun get(key: String) = properties.getProperty(key) ?: error("No property '$key' defined in javconfig.")
}