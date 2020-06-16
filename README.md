To build the CAGADroid image (CAGADroid is the name of our prototype implementation of GUS) and flash it on a Google Pixel 3 (only tested on Ubuntu 20.04):
0- Establish a linux AOSP build environment as described here: https://source.android.com/setup/build/initializing
1- Download and add "repo", "adb", and "fastboot" to your PATH environment as described here: https://source.android.com/setup/build/downloading
1- Create an empty folder on a drive with at least 100GB free space.
2- Copy the included "init.sh" to your created folder and execute it (a.k.a., chmod +x init.sh && sh init.sh) to download the AOSP 10 source tree.
3- Download and copy the propritery Pixel 3 binaries to your source tree, as described here: https://source.android.com/setup/build/downloading
3- Copy the files in the included "build", "frameworks", "packages" and "system" folders to their respective locations in your  source tree.
4- Connect the Pixel 3 to your computer using a USB cable and enable usb debugging on the phone.
5- Copy the included "build_and_flash.sh" to your source tree and execute it to build and flash the image.
6- Depending on which security policy you want to use ("ActivityLocker" or "PermissionLocker"), rename either "caga_policies_activity.xml" or "caga_policies_permission.xml" to "caga_policies.xml".
7- With the phone connected via USB, execute the included "push_policies.sh" to push the policies to the phone.
8- Reboot the phone to start using the new security policy.
9- Use the "CAGADroid_DummyIA_App" on the phone to manually change the confidence of authentication for each user and observe the results.

Side notes:
i- You can use debug tags "CAGA.PE", "CAGA.SM", "CAGA.SPD", "CAGA.CP", and "CAGA.RA" to view debug logs of various CAGADroid modules (use the commmand "adb logcat -s <debug tag>"
ii- You can also use debug tags "CAGA.ALLOW", "CAGA.DENY", and "CAGA.REAUTH" to see what access requests are being allowed or denied by CAGADroid.
iii- If you decide to change any of the SELinux policies (different than CAGADroid policies), you need to execute the included "selinux.sh" file before executing "build_and_flash.sh"
