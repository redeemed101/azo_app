package com.fov.core.files

import java.io.*

class FileProcessor constructor(){
    @Throws(Exception::class)
    fun readFile(filePath: String): ByteArray {
        val file = File(filePath)
        val fileContents = file.readBytes()
        val inputBuffer = BufferedInputStream(
            FileInputStream(file)
        )

        inputBuffer.read(fileContents)
        inputBuffer.close()

        return fileContents
    }
    @Throws(Exception::class)
    fun saveFile(fileData: ByteArray, path: String) :  File{
        val file = File(path)
        val bos = BufferedOutputStream(FileOutputStream(file, false))
        bos.write(fileData)
        bos.flush()
        bos.close()
        return file
    }
}