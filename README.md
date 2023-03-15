# Car API based on spring-boot
  * uses h2 to store 1000 records of vehicle data

endpoints

```java
 @GetMapping("/list")
 @GetMapping("/by-year/{year}")
 @GetMapping("/by-model/{model}")
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
## Running the API
 ### deploy to kubernetes via kubectl
 ### manifests are located in the '/k8s' folder
 ### read them for more info on the container as

## example single and two-stage build of the container
### clone the repo
```console
  git clone https://github.com/scblur869/car-api.git
```
### build the container
```console
  cd car-api
  docker build --tag mycarapi . --no-cache
```
### view the images in docker
```console
docker images

REPOSITORY     TAG       IMAGE ID       CREATED         SIZE
mycarapi       latest    1ebb45f2e0b1   5 seconds ago   386MB

```

### two stage build
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

## deploy to AWS ECR (you must provide your AWS account id and repo name for the ecr endpoint!)
 * login to aws via:
 ```console
  aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
```

  docker tag myapi:latest aws_account_id.dkr.ecr.region.amazonaws.com/myapi:latest
  docker push aws_account_id.dkr.ecr.region.amazonaws.com/myapi:latest

 ```
 * change the region above to match your own
 * create your ECR repo and follow the push commands to push your image to ECR
 * change the image path in the deployment.yaml to reflect your ecr repo url
 * change the rules - hosts: entry to match your route53 CNAME record
 * port 80 is the default port for accessing the url


### good reference
https://docs.docker.com/engine/reference/run/


## Kubernetes deployment requirements to deploy
- need to have access to kubernetes and kubectl
- need to update the deployment.yaml with a valid docker registry endpoint
- need to have an ingress controller installed on EKS / Kubernetes (NGINX, HAPROXY, TRAEFIK, AWS)
- need to add an A ALIAS record (route53) that matches your ingress resource host  and the A record / dns name of the loadbalancer

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
### the last line assumes you have a dedicated nginx-ingress controller listening for state changes
