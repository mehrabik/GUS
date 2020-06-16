#! /bin/sh

repo init -u https://android.googlesource.com/platform/manifest -b android-10.0.0_r27
repo sync -j8 -qc

# You should download proprietary binaries for your specific device next
