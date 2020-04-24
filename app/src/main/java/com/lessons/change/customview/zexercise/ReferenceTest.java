package com.lessons.change.customview.zexercise;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @date 2020/3/27.
 * GitHub：
 * email：
 * description：
 */
public class ReferenceTest {

    private static class MyBigObject {
        byte[] data = new byte[1024];  // 1kb
    }

    public static int removedSoftRefs = 0;
    public static int CACHE_INITIAL_CAPACITY = 100 * 1024;

    private static Set<SoftReference<MyBigObject>> cache = new HashSet<>(CACHE_INITIAL_CAPACITY);

    public static ReferenceQueue<MyBigObject> sReferenceQueue = new ReferenceQueue<>();


    public static void main(String[] args) {
        for (int i = 0; i < CACHE_INITIAL_CAPACITY; i++) {
            MyBigObject obj = new MyBigObject();
            cache.add(new SoftReference<>(obj, sReferenceQueue));
            clearUseLessReferences();
            if (i % 1000 == 0) {
                System.out.println("size of cache:  " + cache.size());
            }
        }
        System.out.println("size of cache end:  " + removedSoftRefs);
    }

    private static void clearUseLessReferences() {

        Reference<? extends MyBigObject> reference = sReferenceQueue.poll();

        while (reference != null) {
            if (cache.remove(reference)) {
                removedSoftRefs++;
            }
            reference = sReferenceQueue.poll();
        }
    }


}
