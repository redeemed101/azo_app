package com.fov.core.security.fileEncryption

import android.util.Log
import com.fov.core.files.FileProcessor
import com.fov.core.security.encryption.Encryption
import java.io.File

class FileEncryption constructor(
    private val encryption: Encryption,
    private val fileProcessor: FileProcessor
){
    fun encryptFile(filePath : String,destinationFilePath : String? = null, secretKey: String) : File? {
        var file : File? = null
        try {

            val fileData = fileProcessor.readFile(filePath)

            //encrypt file
            val encodedData = encryption.encrypt(secretKey, fileData)
            if(destinationFilePath == null) {
                file = fileProcessor.saveFile(encodedData, filePath)
            }
            else{
                file = fileProcessor.saveFile(encodedData, destinationFilePath)
            }

        } catch (e: Exception) {
            Log.d("FileEncryptionError", e.message!!)
        }
        return file
    }
   fun decryptEncryptedFile(sourceFilePath : String,destinationFilePath  :  String, secretKey: String): File {

        val fileData = fileProcessor.readFile(sourceFilePath)
        val decryptedFileData  =  encryption.decrypt(secretKey, fileData)
        return fileProcessor.saveFile(decryptedFileData,destinationFilePath);
    }
}