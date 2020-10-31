### 将jar包打包生成exe可执行文件
基于maven的Java项目，通常会打包成jar， 如果要把jar文件生成exe文件，仅需要在pom.xml配置文件中增加一个插件即可

这里用 [launch4j](http://launch4j.sourceforge.net/docs.html)

同时在项目根目录新建一个`assembly.xml`文件


### 打包
        mvn clean
        mvn compile
        mvn package
