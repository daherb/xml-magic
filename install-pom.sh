./mvnw clean compile package install
./mvnw install:install-file -Dfile=target/xml-magic-1.0-SNAPSHOT.jar -DgroupId=de.ids-mannheim.lza -DartifactId=xml-magic -Dversion=1.0 -Dpackaging=jar  -DgeneratePom=true
