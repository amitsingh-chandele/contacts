**Assumptions**
1. Character set is only of english alphabet \[a-z\]. 
2. Case insensitive search happens.

**Prerequisite**:
1. This requires java8 installed locally
 

**Steps to run:**

1. Install maven
2. Run following command from root
`mvn clean compile assembly:single`
3. This will generate jar at 
`<root>/target/contacts-1.0-SNAPSHOT-jar-with-dependencies.jar`. 
Run this jar as 
`java -jar target/contacts-1.0-SNAPSHOT-jar-with-dependencies.jar`
