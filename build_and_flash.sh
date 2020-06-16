#!/bin/bash
set -e
set -o pipefail

source build/envsetup.sh
lunch aosp_blueline-userdebug
m
adb reboot bootloader
fastboot flashall
spd-say -w "build and flash is done!"
