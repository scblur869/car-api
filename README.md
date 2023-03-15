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
## buiding the container
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
```console
docker build -f Dockerfile.two-stage --tag 2stage-myapi .
docker images

REPOSITORY     TAG       IMAGE ID       CREATED         SIZE
2stage-myapi   latest    4424f99b0240   2 days ago      144MB

```

### run the container interactive console
```console
  docker run -it -p 8080:8080 --name myapi mycarapi:latest
```
### run the container detatched
```console
  docker run -itd -p 8080:8080 --name myapi mycarapi:latest
```

## deploy to AWS ECR 
 * login to aws via:
 ```console
   $(aws ecr get-login --no-include-email --region us-east-1)
 ```
 * change the region above to match your own
 * create your ECR repo and follow the push commands to push your image to ECR
 * change the image path in the deployment.yaml to reflect your ecr repo url
 * change the rules - hosts: entry to match your route53 CNAME record
 * port 80 is the default port for accessing the url


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
### you will also need to create a route53 CNAME record and point that to your ingress controller A Record