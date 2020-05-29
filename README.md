# sautodoc
Compile:
  mvn clean install
  
  mvn net.bluetab:SAutoDoc:0.1:sautodoc

Use:
  1. Firts install plugin en your local maven repository
  2. Configure the plugin in your pom.xml file:
  
          <plugin>
                <groupId>net.bluetab</groupId>
                <artifactId>SAutoDoc</artifactId>
                <version>0.1</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>sautodoc</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                </configuration>
            </plugin>
  3. Run with: mvn clean compile
