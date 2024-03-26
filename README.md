# caimito

To add the dependency to your Android project:

Add the Sonatype Maven repository to your repositories:

```
repositories {
    maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
}
```

Add the library to your dependencies:

```
dependencies {
  // Pick one:

  // 1. Use caimito in your implementation only:
  implementation("dev.gravitycode.caimito:caimito:2.0.1")

  // 2. Use caimito types in your public API:
  api("dev.gravitycode.caimito:caimito:2.0.1")
}
```
