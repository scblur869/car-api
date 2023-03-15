# Car API based on spring-boot
  * uses h2 to store 1000 records of vehicle data
  * mock vehicle data is included /src/main/resources/cars.json

### endpoints
```java
 @GetMapping("/cars/list")
 @GetMapping("/cars/by-year/{year}")
 @GetMapping("/cars/by-model/{model}")
 @PostMapping(path = "/new-car", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
 ```

JSON post raw body for /new-car
 ```json
{
"vin": "32345234444",
"make": "honda",
"model": "pilot",
"color": "silver",
"year": "2019"
}
 ```
## Running the API as a container

## example single and two-stage build of the container
### clone this repo
```console
  git clone https://github.com/scblur869/car-api.git
  ```
## build the container locally using docker:
### (single stage) --single build, larger image size
```console
  cd car-api
  docker build --tag mycarapi . --no-cache
  ```
### view the image in docker
```console
docker images

REPOSITORY     TAG       IMAGE ID       CREATED         SIZE
mycarapi       latest    1ebb45f2e0b1   5 seconds ago   386MB
  ```

### (two stage) --smaller and more lean image size, enough to run the app only
### notice the image size difference as compared to the single stage above
```console
docker build -f Dockerfile.two-stage --tag 2stage-myapi . --no-cache
docker images

REPOSITORY     TAG       IMAGE ID       CREATED         SIZE
2stage-myapi   latest    4424f99b0240   2 days ago      144MB
  ```

### run the container as an interactive pseudo-TTY to validate image startup
- meant more for testing or interactive stdin session
```console
  docker run -it -p 8080:8080 --name myapi mycarapi:latest
  ```
### runs the container detatched and removes it when it terminates
- detached works well for normal deployments
```console
 docker run -d --rm -p 8080:8080 --name myapi mycarapi:latest
 ```

### viewing the container running status
```console
docker ps

CONTAINER ID   IMAGE      COMMAND                  CREATED         STATUS         PORTS                    NAMES
cf3e96211cc7   mycarapi   "sh -c 'java ${JAVA_…"   2 seconds ago   Up 2 seconds   0.0.0.0:8080->8080/tcp   myapi
  ```

### view the container stdout logs
```console
docker logs myapi

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.3.RELEASE)

2023-03-15 15:44:38.026  INFO 1 --- [           main] com....
...
...
...
cars loaded!
  ```


### to test the running container
http://localhost:8080/cars/list

### stopping the container
```console
docker stop myapi
  ```


## push to a registry and deploy to EKS / kubernetes
### deploy to AWS ECR (you must provide your AWS account id and repo name for the ecr endpoint!)
 * docker login to aws ecr via:
 ```console
  aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
  ```
### tag and push to ecr
```console
  docker tag myapi:latest aws_account_id.dkr.ecr.region.amazonaws.com/myapi:latest
  docker push aws_account_id.dkr.ecr.region.amazonaws.com/myapi:latest
  ```

### Kubernetes deployment requirements to deploy
- need to have access to kubernetes and kubectl
- need to update the deployment.yaml with a valid docker image path (ECR endpoint in previous step)
- need to have an ingress controller installed on EKS / Kubernetes (NGINX, HAPROXY, TRAEFIK, AWS)
- need to add an A ALIAS record (route53) that matches your ingress resource host  and the A record / dns name of the load balancer (nlb /alb)

 example install
 ```console
 kubectl create namespace car
 ```
 ```console
 kubectl -n car apply -f deployment.yaml
 ```
 ```console
 kubectl -n car apply -f service.yaml
 ```
 ```console
 kubectl -n car apply -f ingress.yaml
 ```
### the last line assumes you have a dedicated nginx-ingress controller listening for an ingress resource


## good reference on docker run
https://docs.docker.com/engine/reference/run/
