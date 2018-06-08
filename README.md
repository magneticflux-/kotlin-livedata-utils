# kotlin-livedata-utils

 [![Download](https://api.bintray.com/packages/magneticflux/kotlin-livedata-utils/kotlin-livedata-utils/images/download.svg) ](https://bintray.com/magneticflux/kotlin-livedata-utils/kotlin-livedata-utils/_latestVersion)
 [![GitHub tag](https://img.shields.io/github/tag/magneticflux-/kotlin-livedata-utils.svg)](https://github.com/magneticflux-/kotlin-livedata-utils/tags) 
[![Build Status](https://travis-ci.com/magneticflux-/kotlin-livedata-utils.svg?branch=master)](https://travis-ci.com/magneticflux-/kotlin-livedata-utils) 
[![GitHub issues](https://img.shields.io/github/issues/magneticflux-/kotlin-livedata-utils.svg)](https://github.com/magneticflux-/kotlin-livedata-utils/issues) 
[![GitHub PRs](https://img.shields.io/github/issues-pr/magneticflux-/kotlin-livedata-utils.svg)](https://github.com/magneticflux-/kotlin-livedata-utils/pulls) 
[![GitHub Contributors](https://img.shields.io/github/contributors/magneticflux-/kotlin-livedata-utils.svg)](https://github.com/magneticflux-/kotlin-livedata-utils/graphs/contributors) 
[![GitHub license](https://img.shields.io/github/license/magneticflux-/kotlin-livedata-utils.svg)](https://github.com/magneticflux-/kotlin-livedata-utils/blob/master/LICENSE) 

[![Get automatic notifications about new "kotlin-livedata-utils" versions](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/magneticflux/kotlin-livedata-utils/kotlin-livedata-utils?source=watch)

Usage:

```Gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.github.magneticflux:kotlin-livedata-utils:$latest_version'
}
```

```Kotlin
import com.github.magneticflux.livedata.*

// ...
{
    // ...
    return uuid.switchMap { itemRepository.getItemByUUID(it) }
}
```

---
Copyright 2018, Mitchell Skaggs

Licensed under the GNU Lesser General Public License v3.0
