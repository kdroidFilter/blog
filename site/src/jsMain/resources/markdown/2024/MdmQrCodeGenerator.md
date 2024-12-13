---
root: .components.layouts.BlogLayout
title: "How to Create a QR Code for an APK as an MDM"
description: Learn how to create a QR code for provisioning an APK as a Mobile Device Management (MDM) application using OpenSSL and a QR code generator.
author: Elyahou Hadass
date: 2024-12-13
tags:
  - compose desktop
  - qr code
  - mdm
  - android
category: "KdroidFilter"
---

# How to Create a QR Code for an APK as an MDM

This guide explains how to create a QR code for provisioning an APK as a Mobile Device Management (MDM) application. Follow these steps to set up your APK as a device owner in Android Enterprise using a QR code.

## Prerequisites

1. **APK File**: Ensure you have the APK file for your application (e.g., `name-of-APK.apk`).
2. **OpenSSL**: Install OpenSSL to calculate the checksum.
3. **QR Code Generator**: Use a tool like [ZXing QR Code Generator](https://zxing.appspot.com/generator/).

## Steps to Create the QR Code

### 1. Calculate the APK Checksum

Run the following command to calculate the base64-encoded SHA-256 checksum of the APK:

```bash
cat name-of-APK.apk | openssl dgst -binary -sha256 | openssl base64 | tr '+/' '-_' | tr -d '='
```

This will generate a checksum similar to the following:

```
yU8yQt_wIndDnI8kmX-BRw1x55X6OPLJRUxaPuLbtvk
```

### 2. Prepare the QR Code Payload

Create a JSON payload with the necessary provisioning extras. Below is an example payload:

```javascript
{
    "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME":
    "com.kdroid.filter/.listener.AdminListener",

    "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM":
    "yU8yQt_wIndDnI8kmX-BRw1x55X6OPLJRUxaPuLbtvk",

    "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION":
    "https://yoursite.com/app.apk",

    "android.app.extra.PROVISIONING_SKIP_ENCRYPTION": true,

    "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED": true,

    "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE": {}
}
```

- Replace `com.kdroid.filter/.listener.AdminListener` with your app's package and admin component name.
- Replace `https://yoursite.com/app.apk` with the URL where your APK is hosted.

### 3. Generate the QR Code

For professional use, it is recommended to generate the QR code dynamically using a library such as [qrose](https://github.com/alexzhirkevich/qrose). This allows for greater flexibility and automation in scenarios requiring multiple or customized QR codes.

In this example, however, we will rely on the ZXing website:

1. Visit [ZXing QR Code Generator](https://zxing.appspot.com/generator/).
2. Select `Text` as the content type.
3. Paste the JSON payload into the text field.
4. Click on `Generate` to create the QR code.
5. Download and save the QR code image.

### 4. Test the QR Code

1. Factory reset an Android device.
2. On the setup screen, tap multiple times on the blank area to access the QR code scanning option.
3. Scan the generated QR code.
4. The device should automatically download and install the APK and configure it as the device owner.

## Additional Resources

- [Samsung Knox QR Code Enrollment](https://docs.samsungknox.com/dev/knox-sdk/kbas/how-to-create-a-qr-code-to-enroll-a-device-into-android-enterprise-device-owner-do-mode/)
- [Google Play EMM API - Provisioning Devices](https://developers.google.com/android/work/play/emm-api/prov-devices?hl=fr#create_a_qr_code)
