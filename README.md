ubuntu-packager-plugin
======================

A basic Ubuntu/Debian packager plugin for [Gradle](http://gradle.org).
It has some quirks but it should be possible to use it to create packages.

Modified by @m0hit to create debian packages for Java web projects using gradle. 

 - Remove the extract command, the war can be copied directly into the <build home>/ubuntu directory   
 - Change the path for looking up debian files from src/ubuntu/ to main/deb/   
   This is because deb packages work for more than ubuntu systems, and also to keep in accordance to 
   maven project conventions.

 - changed the name of the clean task to deb\_clean. 
   The clean task conflicted with clean task defined in the gradle 'java' plugin.

Example Project
---------------

The below examples come from the example project [helloworld-example](https://github.com/sgo/helloworld-example)

build.gradle example
--------------------

    buildscript {
        repositories {
            mavenRepo urls:'http://yourrepository.com'
        }
        dependencies {
            classpath "be.thinkerit.gradle:ubuntu-packager-plugin:0.1"
        }
    }
        
    apply plugin:'ubuntu'
    
    version = '0.1'
    
    ubuntu {
        archive = new File("helloworld.tar.gz").toURI()
        releaseNotes = "Example for the ubuntu packager plugin"
        author = 'me'
        email = 'me@example.org'
        homepage = 'http://example.org'
        depends {
            // uncomment to add dependencies to be installed
            // on 'mysql-server'
            // on 'jetty'
        }
        dirs {
            // you can add as many dir statements as you need
            dir '/usr/share/helloworld/bin'
        }
    
    }

dir structure
-------------

    helloworld-example
    ├── build.gradle
    ├── helloworld.war
    └── main  
        └── deb 
            └── debian
                ├── copyright
                └── helloworld-example.install

The following optional scripts can be added to `main/deb/debian`

* helloworld-example.default -- /etc/default/helloworld-example `holds environment variable defaults`
* helloworld-example.init -- /etc/init.d/helloworld-example `system start-stop script`
* helloworld-example.preinst -- `executed before installation`
* helloworld-example.postinst -- `executed after installation`
* helloworld-example.prerm -- `executed before removal`
* helloworld-example.postrm -- `executed after removal`

Finally you can still add any number of custom files to `main/deb/overrides` which are copied to the same level as the contents of the tgz so they are available for use in `helloworld-example.install`

See the [packaging guide](https://wiki.ubuntu.com/PackagingGuide/HandsOn) as for the format to use in these files.

Usage
-----

    ~/helloworld-example$ gradle deb_clean deb

Quirks
------

The package will be named based on the gradle name property which is the same as the directory in which the gradle project is located. As a result when using a CI system such as jenkins this name/dir will be called workspace and is probably not what you want. To work around this I have been putting my package scripts in their own subdir named the way I want the package to be named.

E.g:

* myapp/grails
* myapp/myapp
* myapp/myapp-data

So far the plugin will create debian packages based on the architecture on which the package is build. As I intend to use this for Java projects this should be changed to all instead in a future version. Or the target platform when using platform specific compilers.

