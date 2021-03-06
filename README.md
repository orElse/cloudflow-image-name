# cloudflow-image-name
Image name resolution test for ApplicationDescriptor


when switching the definition of the `sharedStreamlets` module different behaviour for creating the app descriptor can be obsereved:

using (which is not intended)
```
lazy val sharedStreamlets = (project in file("./sharedstreamlets"))
  //.enablePlugins(CloudflowLibraryPlugin)
  .settings(
    commonSettings
  )
  .settings(
    libraryDependencies ++= Seq(Dependencies.CloudflowAkkaUtil)
  )
  .dependsOn(dataModel)
```
the streamlets of the module will be available in all images generated by application projects having the module as dependency
* basically the image name decides from which app it gets used
* can change over time due to git revision in image name

using:
```
// own container -> streamlet comes from this
lazy val sharedStreamlets = (project in file("./sharedstreamlets"))
  .enablePlugins(CloudflowAkkaPlugin)
  .settings(
    commonSettings
  )
  .dependsOn(dataModel)
```
the streamlets inside the module get an own container 
* in all apps using a streamlet of the module the image of the sharedStreamlets will be used
* --> reproducible results
