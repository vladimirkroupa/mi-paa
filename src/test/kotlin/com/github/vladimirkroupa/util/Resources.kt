package com.github.vladimirkroupa.util

import java.io.File

class Resources {

    companion object {
        fun readLines(pathToResource: String): List<String> {
            val resUri = this.javaClass.getResource(pathToResource).toURI()
            val file = File(resUri)
            return file.readLines()
        }
    }

}