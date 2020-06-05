package com.lessons.change.customview.utils;

/**
 * @author
 * @date 2020/6/5.
 * GitHub：
 * email：
 * description：
 */
public interface DownloadListener {

    void startDownload();

    void stopDownload();

    void finishDownload();

    void downloadProgress(long progress);
}
