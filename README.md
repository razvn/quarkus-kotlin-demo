# Quarkus Kotlin Demo

A test repo of [Quarkus](https://quarkus.io) and its Kotlin support.

**Checkout the other branches:**
 - `master`: working simple demo
 - `constructor-injection`: constructor injection in Kotlin (**SOLVED**)
 - `rest-client`: Using the Rest Client in Kotlin (**SOLVED**)


Don't forget to add `@ApplicationScoped` to all-open plugin as by default only `@Path` is added.
Check [Kotlin Guide](https://quarkus.io/guides/kotlin) where it's specified as requirement (for now).

```xml
<option>all-open:annotation=javax.enterprise.context.ApplicationScoped</option>
``` 

Run the app:
```
mvn compile quarkus:dev
```

Test end point:
```
curl http://120.0.0.1:8080/infos
```

Project created with:
```
 mvn io.quarkus:quarkus-maven-plugin:0.11.0:create \
    -DprojectGroupId=quarkus.demo \
    -DprojectArtifactId=quarkus-kotlin \
    -DclassName="quarkus.demo.InfosResource" \
    -Dpath="/infos" \
    -Dextensions="kotlin, smallrye-rest-client, resteasy-jsonb"
```
