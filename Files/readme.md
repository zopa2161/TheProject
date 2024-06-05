# Activity, layout xml 파일 외에 수정해야 할 부분
---

## 1. build.gradle.kts(app)에 dependency 추가

```
implementation("com.naver.maps:map-sdk:3.18.0")
```

## 2. settings.gradle.kts에 maven("https://~) 추가

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://repository.map.naver.com/archive/maven")
    }
}
```

## 3. AndroidManifest.xml에 meta-data 추가

```
...
        android:supportsRtl="true"
        android:theme="@style/Theme.TermProject"
        tools:targetApi="31">

<meta-data
            android:name="com.naver.maps.map.CLIENT_ID"
            android:value="bb20t302y5" />

        <activity
            android:name=".MainActivity"
            android
...
```
