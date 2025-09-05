# 📱 A Simple Chat - Kotlin SMS App  

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-blueviolet?logo=kotlin&logoColor=white)](https://kotlinlang.org/) 
[![Android Compile](https://img.shields.io/badge/compileSdk-36-brightgreen?logo=android&logoColor=white)](https://developer.android.com/studio/releases/platforms)  
[![Android Min](https://img.shields.io/badge/minSdk-28-orange?logo=android&logoColor=white)](https://developer.android.com/studio/releases/platforms)

---

## 🌍 Overview | Resumen

**ENGLISH**  
This project was developed as part of an **internship examination**. The goal is to build a **simple SMS texting app** using **Kotlin** that can:  
- 📥 Load messages  
- 📤 Send messages  
- 📡 Receive new messages in real time  


**ESPAÑOL**  
Este proyecto fue desarrollado como parte de un **examen de pasantía**. El objetivo es construir una **aplicación de mensajería SMS** en **Kotlin** que pueda:  
- 📥 Cargar mensajes  
- 📤 Enviar mensajes  
- 📡 Recibir mensajes en tiempo real  

---

## ✨ Features | Características

- ✅ Load all SMS messages from the device  
- ✅ Send SMS to any contact  
- ✅ Receive incoming SMS with permissions handling  
- ✅ Simple and clean UI with **Jetpack Compose**  
- ✅ Uses **MVVM + Repository pattern** for clean architecture  
- ✅ Permission management at runtime (Android 12+)  

---

## 🏗️ Tech Stack | Tecnologías

- [Kotlin](https://kotlinlang.org/)  
- [Jetpack Compose](https://developer.android.com/jetpack/compose)  
- [Hilt (Dependency Injection)](https://dagger.dev/hilt/)  
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)  
- [Content Provider - SMS](https://developer.android.com/reference/android/provider/Telephony.Sms)  
- [Activity Result API - Permissions](https://developer.android.com/training/basics/intents/result)  

---

## 📸 Screenshots | Capturas

| Chat List | Chat Screen | Permissions |
|-----------|-------------|-------------|
| ![Chat List](docs/screenshots/chat_list.png) | ![Chat Screen](docs/screenshots/chat_screen.png) | ![Permissions](docs/screenshots/permissions.png) |

<img src="/Mockup/1.png" width="200"/>

---

## 🚀 Getting Started | Primeros Pasos

### Requirements | Requisitos
- Minimum SDK: **API 28 (Android 9.0)**  
- Target SDK: **API 34 (Android 14)**  
- Permissions:  
  - `READ_SMS`  
  - `SEND_SMS`  
  - `RECEIVE_SMS`  

### Installation | Instalación
1. Clone the repository:  
   ```bash
   git clone https://github.com/SaidTovar/ASimpleChat.git

2. Download the app  

Click the button to download:  

[![Download APK](https://img.shields.io/badge/📥%20Descargar-APK-brightgreen?style=for-the-badge)]([https://github.com/SaidTovar/AppMisDatosUniguajira/raw/refs/heads/main/App/app-release.apk](https://raw.githubusercontent.com/SaidTovar/ASimpleChat/refs/heads/master/app-release.apk))  
