---
root: .components.layouts.BlogLayout
title: "How to Create Guidelines for Blocking Applications"
description: Learn how to define effective guidelines for creating robust application blocking systems, focusing on both mobile and desktop environments.
author: Elyahou Hadass
date: 2024-09-20
tags:
  - application blocking
  - security
  - android
  - desktop
category: "KdroidFilter"
---

Hello everyone,

Today, I want to write a short article on the topic of creating blocking systems. I noticed that there are no clear guidelines for building a robust blocking system, unlike in other technology-related fields, such as kosher refrigerators for Shabbat. Therefore, I decided to outline some fundamental rules that should serve as the basis for a system worthy of being called a "hermetic block."

About a year and a half ago, I defined four "iron rules" for KdroidFilter, and today I’ll explain them and why they work. This article focuses on client-side blocking systems rather than server-side solutions, although the rules are applicable to any software that prevents users from accessing unauthorized networks.

### Purpose of the Blocking System

The idea is to create software that restricts user activity. The type of activity being restricted is less important; the principle is always the same.

### Rule 1: The software must have more privileges than the user

This rule ensures that under no circumstances can the user bypass the blocking mechanism, even if they know exactly how it works. To adhere to this rule, the software must be designed such that the user lacks the necessary permissions to disable the block.

In some systems, like Android with MDM, this is feasible, but on desktop systems, it’s less straightforward. A simple solution, which I believe is underutilized, is to use a non-administrator (or non-root) user account. This may suffice for a large portion of users. Additionally, one could envision a "kosher app store" that allows installation of administrator-required applications through a controlled interface.

An interesting question is whether it’s possible to limit a user who has root permissions. Fundamentally, root is not omnipotent; the system itself, especially the kernel, sets the boundaries for root privileges. Therefore, it’s possible to create a kernel driver to restrict the user. This solution requires a high level of programming expertise, as any error in the driver could crash the entire system.

### Rule 2: In the event of a failure, the device must remain blocked

No software is immune to errors or crashes. Therefore, it’s essential to ensure that, in the event of a crash, the device remains in a blocked state. The implementation of this rule depends on the system and what is being restricted, but using a kernel driver can inherently ensure compliance with this rule.

### Rule 3: Prevent device resets

On Android, Factory Reset Protection (FRP) makes resetting the device more challenging. On computers, a BIOS password can be used. While these methods are not unbreakable, they present significant obstacles for most users, particularly on laptops.

### Rule 4: Avoid punitive measures

Punishing users indicates a weakness in the blocking system’s structure. Punishment should only be applied in specific scenarios, such as preventing brute-force password attempts.

### Conclusion

I hope this article helps in developing stronger blocking systems. Perhaps in the future, an organization will certify blocking systems to ensure they adhere to these principles. Good luck!

