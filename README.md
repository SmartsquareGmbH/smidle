# smidle :)

Smidle ist eine Anwendung zum Tracken der Dauer eines Pull Requests.

## Build Setup

1. Docker Image bauen: 

```bash
$ cd rest-api
$ ./gradlew jibDockerBuild
```

2. Docker Image pushen:

```bash
$ docker push docker.pkg.github.com/smartsquaregmbh/smidle/smidle-rest-api
```
 
3. Kubernetes Pods neustarten: 

```bash
$ kubectl rollout restart deployment/smidle
```
