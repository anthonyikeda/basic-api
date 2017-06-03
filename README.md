# basic-api
Demo API for testing concourse

# Starting Nexus
```bash
$ docker run -p 8081:8081 --rm sonatype/nexus3:latest
```
# Configuring Nexus
A user needs to be setup in Nexus to connect.

## First set up the role

<b>Settings > Roles > Create Role</b>

> <b>Role Id:</b> nx-deployment<br/>
> <b>Role Name:</b> nx-deployment<br/>
> <b>Role Description:</b> Role used for deploying artifacts to Nexus<br/>
> <b>Privileges:</b> nx-all (should .lower this to the right permissions)<br/>
> <b>Roles:</b> &lt;none><br/>

## Next setup the user

<b>Settings > Users > Create User</b>

> <b>Id:</b> deployment<br/>
> <b>First Name:</b> Deployment <br/>
> <b>Last Name:</b> Server<br/>
> <b>Email:</b> deployment@email.com<br/>
> <b>Status:</b> Active<br/>
> <b>Roles:</b> nx-deployment

You will need to then set up the password.

## Update the settings.xml file

Typically stored at ```$HOME/.m2/settings.xml```:

```xml
<servers>
    <server>
        <id>local-server-snapshots</id>
        <username>deployment</username>
        <password>password</password> <!-- The password supplied above -->
    </server>
</servers>
```

## Create the Snapshot and Release Repositories

<b>Settings > Repositories > Create Repository</b>

### Release
> <b>Recipe:</b> maven2 (hosted)<br/>
> <b>Name:</b> lib-release-local<br/>
> <b>Version Policy:</b> Release<br/>
> <b>Layout Policy:</b> Strict<br/>
> <b>Storage:</b> Default<br/>

### Snapshot
> <b>Recipe:</b> maven2 (hosted)<br/>
> <b>Name:</b> lib-snapshot-local<br/>
> <b>Version Policy:</b> Release<br/>
> <b>Layout Policy:</b> Strict<br/>
> <b>Storage:</b> Default<br/>


## Configure the project

Next we need to add the Nexus plugin and configure the repos

## Configure the plugins
We need to make sure the default maven-deploy-pliugin is disabled:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-deploy-plugin</artifactId>
            <version>${maven-deploy-plugin.version}</version>
            <configuration>
                <skip>true</skip> <!-- Disable this plugin -->
            </configuration>
        </plugin>
    </plugins>
</build>
```

And add in the Nexus plugin:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.sonatype.plugins</groupId>
            <artifactId>nexus-staging-maven-plugin</artifactId>
            <version>1.6.8</version>
            <executions>
                <execution>
                    <id>default-deploy</id>
                    <phase>deploy</phase>
                    <goals>
                        <goal>deploy</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <serverId>nexus-local</serverId>
                <nexusUrl>http://192.168.99.100:8081/</nexusUrl>
                <skipStaging>true</skipStaging>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Configure the repositories
```xml
<distributionManagement>
    <repository>
        <id>nexus-local</id>
        <url>http://192.168.99.100:8081/repository/local-releases/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-local</id>
        <url>http://192.168.99.100:8081/repository/local-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

> You can get the URL's from Nexus in Settings > URL (copy)

# Deploy Snapshot

```bash
$ ./mvnw deploy
[INFO] --- maven-deploy-plugin:2.8.2:deploy (default-deploy) @ basic-api ---
[INFO] Skipping artifact deployment
[INFO] 
[INFO] --- nexus-staging-maven-plugin:1.6.8:deploy (default-deploy) @ basic-api ---
[INFO] Performing deferred deploys (gathering into "/Users/anthonyikeda/work/git/basic-api/target/nexus-staging/deferred")...
[INFO] Installing /Users/anthonyikeda/work/git/basic-api/target/basic-api-0.0.1-SNAPSHOT.jar to /Users/anthonyikeda/work/git/basic-api/target/nexus-staging/deferred/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-SNAPSHOT.jar
[INFO] Installing /Users/anthonyikeda/work/git/basic-api/pom.xml to /Users/anthonyikeda/work/git/basic-api/target/nexus-staging/deferred/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-SNAPSHOT.pom
[INFO] Deploying remotely...
[INFO] Bulk deploying locally gathered artifacts from directory: 
[INFO]  * Bulk deploying locally gathered snapshot artifacts
Downloading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml
Downloading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-20170603.175023-1.jar
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-20170603.175023-1.jar (14 MB at 16 MB/s)
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-20170603.175023-1.pom
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/basic-api-0.0.1-20170603.175023-1.pom (2.5 kB at 12 kB/s)
Downloading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml
Downloading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml (770 B at 11 kB/s)
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/0.0.1-SNAPSHOT/maven-metadata.xml (770 B at 6.9 kB/s)
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml (280 B at 2.8 kB/s)
Uploading: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml
Uploaded: http://192.168.99.100:8081/repository/local-snapshots/com/example/basic-api/maven-metadata.xml (280 B at 2.3 kB/s)
[INFO]  * Bulk deploy of locally gathered snapshot artifacts finished.
[INFO] Remote deploy finished with success.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.918 s
[INFO] Finished at: 2017-06-03T10:50:24-07:00
[INFO] Final Memory: 28M/325M
[INFO] ------------------------------------------------------------------------
```

# Release the artifact
