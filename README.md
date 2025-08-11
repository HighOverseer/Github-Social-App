# GitHub Social App

An advanced Android application for exploring GitHub profiles with enhanced security, modular architecture, and professional-grade development practices. Developed as the final submission for [Dicoding](https://www.dicoding.com)'s "Menjadi Android Developer Expert" course.

*Demonstrating expert-level Android development with enterprise-grade security and architecture*

<p align="center">
<img width="1920" height="1080" alt="Modern App Portfolio Mockup Presentation-5" src="https://github.com/user-attachments/assets/ed61bfaa-9c98-4adb-9151-dd183752c3e0" />
</p>

## 🎯 Overview

GitHub Social App transforms the way users discover and manage GitHub profiles through an intuitive mobile interface. Built with cutting-edge Android development practices, the app showcases advanced concepts including modularization, security hardening, performance optimization, and continuous integration.

## 🎓 Academic Excellence

This project represents the **capstone submission** for **Dicoding's "Menjadi Android Developer Expert"** course, demonstrating mastery of expert-level Android development concepts:

**Expert-Level Concepts Demonstrated:**
- Advanced modular architecture with dynamic feature modules
- Enterprise-grade security implementation
- Performance optimization and monitoring
- Continuous integration and automated testing
- Clean Architecture with multi-layer separation
- Advanced dependency injection patterns

**Course Achievement:**
- **Program**: Menjadi Android Developer Expert (Dicoding)
- **Level**: Expert
- **Project Type**: Final capstone submission
- **Focus**: Production-ready applications with enterprise standards

## ✨ Key Features

### 🔍 Advanced Profile Discovery
- **Intelligent Search**: Real-time GitHub profile search with username keyword matching
- **Rich Profile Details**: Comprehensive user information including avatar, name, and location
- **Interactive Navigation**: Seamless exploration of repositories, followers, and following lists
- **TabLayout Integration**: ViewPager + TabLayoutMediator for smooth content switching

### ⭐ Smart Favorites Management
- **Local Persistence**: Room Database for offline favorite profile storage
- **Encrypted Security**: SQLCipher database encryption for sensitive data protection
- **Advanced Search**: Dedicated search functionality within saved favorites

### 🎨 Professional User Experience
- **Adaptive Theming**: Automatic dark theme support following system preferences
- **Intuitive Navigation**: Jetpack Navigation Component for consistent user flows
- **Smooth Interactions**: Polished animations and transitions throughout the app

## 🏗️ Advanced Architecture

### Modular Design
- **Core Android Library**: Shared functionalities and common components
- **Dynamic Feature Module**: List User, Favorites, & Detail section with optimized delivery and installation
- **Separation of Concerns**: Clear boundaries between presentation, domain, and data layers

### Architecture Patterns
- **Clean Architecture**: Multi-layer architecture with strict dependency rules
- **MVVM (Model-View-ViewModel)**: Clear separation of UI and business logic
- **Repository Pattern**: Unified data access across local and remote sources
- **Use Case Pattern**: Encapsulated business logic with single responsibility

## 🔒 Enterprise Security

### Data Protection
- **Database Encryption**: SQLCipher integration for secure local storage
- **Certificate Pinning**: OkHttp3 Certificate Pinner preventing MITM attacks
- **Secure Configuration**: API keys and sensitive data stored in `gradle.properties`
- **Code Obfuscation**: ProGuard implementation for reverse-engineering protection

### Security Best Practices
- **Network Security**: Hardened HTTP client configuration
- **Data Validation**: Input sanitization and validation throughout the app
- **Permission Management**: Minimal permissions with clear user consent
- **Secure Storage**: Encrypted preferences using DataStore

## ⚡ Performance Excellence

### Optimization Strategies
- **Memory Management**: LeakCanary integration for proactive memory leak detection
- **Background Processing**: Kotlin Coroutines for smooth UI performance
- **Resource Optimization**: Efficient image loading and bitmap management

### Monitoring & Quality
- **Continuous Integration**: CircleCI for automated testing and builds
- **Code Quality**: Regular inspection and adherence to best practices

## 🛠️ Technical Stack

### Core Technologies
- **Language**: Kotlin with modern language features
- **UI Framework**: Traditional Android Views with Material Design
- **Architecture**: Clean Architecture + MVVM
- **Reactive Programming**: Kotlin Flow for responsive data handling

### Advanced Libraries & Tools
- **Dependency Injection**: 
  - Dagger Hilt for main application
  - Dagger 2 for dynamic feature modules
- **Networking**: Retrofit with OkHttp3 for GitHub API integration
- **Database**: Room with SQLCipher encryption
- **Navigation**: Jetpack Navigation Component
- **Security**: Certificate Pinning, ProGuard obfuscation
- **CI/CD**: CircleCI for automated builds and testing

### Development Tools
- **Memory Profiling**: LeakCanary for leak detection
- **Performance Monitoring**: Systematic performance tracking
- **Code Analysis**: Static analysis tools for code quality
- **Version Control**: Git with secure credential management

## 🚀 Getting Started

### Prerequisites
- Android Studio Flamingo or later
- Android SDK 24+
- GitHub API token (for extended rate limits)
- CircleCI account (for CI/CD setup)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/HighOverseer/Github-Social-App.git
cd GithubUserAppDicoding
```

2. **Configure secure properties**
```bash
# Add to gradle.properties (never commit this file)
API_TOKEN=your_github_personal_access_token
SERVER_HOST_NAME=api.github.com
```

3. **Open in Android Studio**
```bash
# Import project and sync Gradle files
```

4. **Build and run**
```bash
# Build with ProGuard obfuscation enabled
./gradlew assembleRelease
```

## 📂 Project Structure

```
GitHubUserAppDicoding/
├── .circleci/                    # CircleCI configuration
├── .gradle/                      # Gradle cache and wrapper files
├── .idea/                        # Android Studio IDE files
├── app/                          # Main application module
├── core/                         # Core shared library module
├── detail_user/                  # Dynamic feature: User detail module
├── list_user_and_user_favorite/  # Dynamic feature: User list & favorites
├── .gitignore                    # Git ignore configuration
├── build.gradle                  # Root project build configuration
├── gradle.properties             # Gradle properties and API keys
├── gradlew                       # Gradle wrapper script (Unix)
├── gradlew.bat                   # Gradle wrapper script (Windows)
├── local.properties              # Local SDK paths and sensitive data
├── settings.gradle               # Multi-module project settings
└── shared_dependencies.gradle    # Shared dependencies configuration
```

## 🎯 Expert-Level Achievements

### Advanced Development Practices
✅ **Modular Architecture**: Multi-module project with dynamic features  
✅ **Security Hardening**: Certificate pinning, encryption, obfuscation  
✅ **Performance Optimization**: Memory leak detection, efficient data handling  
✅ **CI/CD Implementation**: Automated testing and deployment pipeline  
✅ **Clean Architecture**: Strict separation of concerns across layers  
✅ **Dependency Injection**: Advanced DI patterns across modules  
✅ **Reactive Programming**: Flow-based reactive data management  

### Professional Standards
- **Code Quality**: Industry-standard practices and patterns
- **Security First**: Comprehensive security implementation
- **Performance Monitoring**: Proactive performance management
- **Scalable Design**: Architecture ready for enterprise-scale features

## 🙏 Acknowledgments

- **Dicoding Indonesia** for providing world-class Android development education
- **GitHub API Team** for comprehensive developer API access
- **Android Security Community** for security best practices and tools
- **Open Source Contributors** for excellent libraries and frameworks
- **CircleCI** for reliable continuous integration services

---
