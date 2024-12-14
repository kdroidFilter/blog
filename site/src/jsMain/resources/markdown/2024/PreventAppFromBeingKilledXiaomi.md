---
root: .components.layouts.BlogLayout
title: "Preventing Apps from Being Killed on Xiaomi Devices"
description: "Learn how to stop Xiaomi's system from killing your apps by adjusting system settings."
author: "Elyahou Hadass"
date: 2024-12-14
tags:
  - android
  - xiaomi
  - app management
  - system settings
category: "Android"
---

## Preventing Apps from Being Killed on Xiaomi Devices

On Xiaomi devices, aggressive system optimizations can cause background apps to be prematurely killed, disrupting functionality. To ensure your app runs reliably in the background, follow these steps to adjust Xiaomi’s system settings:

### Steps to Prevent an App from Being Killed

1. **Open Settings**:
    - Navigate to the device’s **Settings** app.

2. **Go to Applications or Apps**:
    - Depending on your device’s language and MIUI version, this may be labeled as **Applications** or **Apps**.

3. **Access System App Settings**:
    - Tap on **App settings** or **System app settings** (this option may vary slightly).

4. **Select Security**:
    - Look for the **Security** app under system app settings.

5. **Enable "Boost Speed" Options**:
    - Inside the **Security** settings, find and select the **Boost speed** option.

6. **Lock Your Desired App**:
    - In the **Boost speed** section, choose **Lock apps**.
    - From the list, select the app you want to protect from being killed by the system.

### Why Does Xiaomi Kill Background Apps?

Xiaomi devices run MIUI, a heavily customized version of Android that prioritizes battery efficiency and performance. To achieve this, MIUI aggressively terminates background processes. While this improves battery life, it can negatively impact apps that rely on running in the background, such as messaging, health trackers, or media players.

This method has been tested on devices running **MIUI** and **HyperOS**, and it effectively prevents apps from being killed by the system.

### Additional Tips

- **Disable Battery Optimization**: Go to **Settings > Battery & Performance > Battery Saver** and ensure that battery optimization is turned off for the specific app.
- **Add the App to Auto-Start**: In the **Permissions** or **Apps** section, enable auto-start for the app to ensure it launches at boot.
- **Update MIUI**: Sometimes, newer MIUI updates optimize app management policies. Keeping your device updated may reduce these issues.

### Conclusion

Xiaomi’s app-killing behavior can be frustrating, but with the right settings adjustments, you can ensure your app remains operational. These steps are particularly important for developers who want their apps to deliver consistent performance on Xiaomi devices. If you encounter additional challenges, consider reaching out to Xiaomi’s support or exploring their official [MIUI documentation](https://www.mi.com/miui/).

