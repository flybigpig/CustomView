// IPackageStatsObserver.aidl
package com.lessons.change.customview;

// Declare any non-default types here with import statements

oneway interface IPackageStatsObserver {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
