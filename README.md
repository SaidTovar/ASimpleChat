# 📱 A Simple Chat - Kotlin SMS App  

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-blueviolet?logo=kotlin&logoColor=white)](https://kotlinlang.org/) 
[![Android Compile](https://img.shields.io/badge/compileSdk-36-brightgreen?logo=android&logoColor=white)](https://developer.android.com/studio/releases/platforms)  
[![Android Min](https://img.shields.io/badge/minSdk-28-orange?logo=android&logoColor=white)](https://developer.android.com/studio/releases/platforms)  
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)  

---

## 🌍 Overview | Resumen

**ENGLISH**  
This project was developed as part of an **internship examination**. The goal is to build a **simple SMS texting app** using **Kotlin** that can:  
- 📥 Load messages  
- 📤 Send messages  
- 📡 Receive new messages in real time  
- ⚡ Extend functionality with APIs and AI integration  

The project is supervised by the lead Kotlin developer and is designed to demonstrate clean architecture principles and practical mobile development skills.  

**ESPAÑOL**  
Este proyecto fue desarrollado como parte de un **examen de pasantía**. El objetivo es construir una **aplicación de mensajería SMS** en **Kotlin** que pueda:  
- 📥 Cargar mensajes  
- 📤 Enviar mensajes  
- 📡 Recibir mensajes en tiempo real  
- ⚡ Extender la funcionalidad con APIs e integración de IA  

El proyecto está supervisado por el desarrollador principal de Kotlin y está diseñado para demostrar principios de arquitectura limpia y habilidades prácticas en desarrollo móvil.  

---

## ✨ Features | Características

- ✅ Load all SMS messages from the device  
- ✅ Send SMS to any contact  
- ✅ Receive incoming SMS with permissions handling  
- ✅ Simple and clean UI with **Jetpack Compose**  
- ✅ Uses **MVVM + Repository pattern** for clean architecture  
- ✅ Permission management at runtime (Android 12+)  
- ✅ API-ready structure for AI integration  

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

---

## 🚀 Getting Started | Primeros Pasos

### Requirements | Requisitos
- Android Studio **Ladybug** (or newer)  
- Minimum SDK: **API 24 (Android 7.0)**  
- Target SDK: **API 34 (Android 14)**  
- Permissions:  
  - `READ_SMS`  
  - `SEND_SMS`  
  - `RECEIVE_SMS`  

### Installation | Instalación
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/asimplechat.git
