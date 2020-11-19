package com.openpix.ophttp.upload

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class UploadRequestBody : RequestBody {
    private var mFile: File
    private var mMediaType: String
    private var mListener: UploadCallback
    private var mEachBufferSize = 1024
    private var tag: Any? = null

    interface UploadCallback {
        fun onProgressUpdate(tag: Any?, percentage: Int)
        fun onFinish()
        fun onError()
    }

    constructor(tag: Any?, file: File, mediaType: String, listener: UploadCallback) {
        this.tag = tag
        mFile = file
        mMediaType = mediaType
        mListener = listener
    }

    constructor(tag: Any?, file: File, mediaType: String, eachBufferSize: Int, listener: UploadCallback) {
        this.tag = tag
        mFile = file
        mMediaType = mediaType
        mEachBufferSize = eachBufferSize
        mListener = listener
    }

    override fun contentType(): MediaType? {
        return MediaType.parse(mMediaType)
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile.length()
        val buffer = ByteArray(mEachBufferSize)
        val fis = FileInputStream(mFile)
        var uploaded: Long = 0
        with(fis) {
            var readCount: Int
            while (read(buffer).also { readCount = it } != -1) {
                uploaded += readCount.toLong()
                var p = (100 * uploaded / fileLength).toInt()
                mListener.onProgressUpdate(tag,(100 * uploaded / fileLength).toInt())
                sink.write(buffer, 0, readCount)
                sink.flush()
            }
            if(uploaded == fileLength) {
                mListener.onFinish()
            }
        }
    }
}