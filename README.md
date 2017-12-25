This is a Hypermedia-Driven RESTful Web Service service based on Spring Boot platform using OAuth2 for protecting the endpoint and Mongo DB as a NoSQL storage.

### Technology stack POC based on Spring Boot
##### Spring projects used:
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Data Mongo DB](http://projects.spring.io/spring-data-mongodb/)
* [Spring Data REST](http://projects.spring.io/spring-data-rest/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/)
* [Spring HATEOAS](http://projects.spring.io/spring-hateoas/)
 
### Dev environment configuration

#### Local environment configuration

##### Install nginx in MacOS

_**Only for advance user, if you are not confident with this step please skip it.**_

* Install nginx in mac

```
brew install nginx
```
* nginx configuration
* Run ```brew info nginx``` you will see all the nginx information in your locale, you can get the following path according the above information.
  * nginx path: ```/Users/zhoule/.homebrew/etc/nginx```
  * nginx configuration file: ```/Users/zhoule/.homebrew/etc/nginx/nginx.conf```

* Config nginx to use sites-available and sites-enable
  * Create sites-available and sites-enable folders
    * ```cd /Users/zhoule/.homebrew/etc/nginx```
    * ```mkdir sites-{enabled,available}```
    * ```mkdir /var/log/nginx```
  * Go to sites-available folder
    * ```vim default```
    * Paste the following configuration to default, _**please remember change the root (/Users/zhoule/.homebrew/var/www) to you local path.**_
    
    ```
        server {
        	listen 80 default_server;
        	listen [::]:80 default_server;
        
        
        	root /Users/zhoule/.homebrew/var/www;
        
        	# Add index.php to the list if you are using PHP
        	index index.html index.htm index.nginx-debian.html;
        
        	server_name _;
        
        	location / {
        		# First attempt to serve request as file, then
        		# as directory, then fall back to displaying a 404.
        		try_files $uri $uri/ =404;
        	}
        }
    ```
    * create trackr configuraiton file
      * ```vim trackr```
      * Paste the following configuration to trackr
          
          ```
                server {
                    listen 80;
                    server_name localhost.soochow.co;
                
                    proxy_set_header X-Real-IP  $remote_addr; # pass on real client IP
                
                    location / {
                        proxy_pass http://localhost:8080;
                    }
                }
    
          ```
    
    * create symbolic links for `default` and `trackr`.
    
      * ```sudo ln -s /Users/zhoule/.homebrew/etc/nginx/sites-available/default /Users/zhoule/.homebrew/etc/nginx/sites-enable/default```
      * ```sudo ln -s /Users/zhoule/.homebrew/etc/nginx/sites-available/trackr /Users/zhoule/.homebrew/etc/nginx/sites-enable/trackr```
      
    * Use the following configuration to override all configuration in the nginx.conf, please remember update the include path to your local path ( ```include /Users/zhoule/.homebrew/etc/nginx/conf.d/*.conf;```, ```include /Users/zhoule/.homebrew/etc/nginx/sites-enabled/*;```)
        
        ```
        #user  nobody;
        worker_processes  1;
        
        #error_log  logs/error.log;
        #error_log  logs/error.log  notice;
        #error_log  logs/error.log  info;
        
        #pid        logs/nginx.pid;
        
        
        events {
            worker_connections  1024;
        }
        
        
        http {
            include       mime.types;
            default_type  application/octet-stream;
        
            log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                              '$status $body_bytes_sent "$http_referer" '
                              '"$http_user_agent" "$http_x_forwarded_for"';
        
            sendfile        on;
            keepalive_timeout  65;
        
            access_log /var/log/nginx/access.log;
            error_log /var/log/nginx/error.log;
        
        
            ##
            # Virtual Host Configs
            ##
        
            include /Users/zhoule/.homebrew/etc/nginx/conf.d/*.conf;
            include /Users/zhoule/.homebrew/etc/nginx/sites-enabled/*;
        }
        ```
        
     * Add a new host mapping in ```/etc/hosts``` by run ```sudo vim /etc/hosts```
           * 127.0.0.1 localhost.soochow.co
        
     * Reload and restart nginx configuration
        * ```sudo nginx -s stop && sudo nginx```
        * Open ```http://localhost``` to verify you nginx is started, if you can see "Welcome to nginx" means everything was config correctly.

##### Install mongodb

```brew install mongodb```

##### Admin user init script

Initialize admin user (regular users can be registered through UI) with admin password.
````javascript
db.user.insert({
  "userName": "admin",
  "password": "$2a$10$zqgKeEA.BG6aLea9Ph37oOrG4T3pTkV5i7TS.lIbAQnnZyanSuxAK",
  "phoneNumber": "18550082500",
  "chineseName": "周乐",
  "email": "jack@xplusz.com",
  "roles": [
    "ADMIN",
    "USER"
  ]
});
````
_Tips: Password is admin._

### Building

##### Using Gradle

````sh
gradle build
````

### Running 

##### Using Gradle

````sh
gradle bootRun
````
You can see [API documentation](http://localhost.soochow.co/swagger-ui.html)

##### Let's start with **ROOT** API resource. For the user who don't install nginx in you local please change localhost.soochow.co to localhost:8080.

The REST service strongly **Hypermedia-driven** and Content Type is **application/hal+json**.

````sh
curl -X GET http://localhost.soochow.co/api -k
````

You will received hal+json body containing public resource(s).
````json
{
    "_links": {
        "users": {
            "href": "http://localhost.soochow.co/api/users"
        }
    }
}
````
##### Now let's **Register** user.

````sh
curl -X POST http://localhost.soochow.co/api/users -k -d '{"userName":"jack.zhou","password":"zhoule", "phoneNumber":"18550082500", "chineseName": "周乐", "email":"jack@xplusz.com"}' -H 'Content-Type: application/json'
````

You will receive 201 status and Location headers.
````
HTTP/1.1 201 Created
Location: http://localhost.soochow.co/api/users/jack.zhou
````

##### Now let's read user you've created

````sh
curl -X GET http://localhost.soochow.co/api/users/jack.zhou/ -k -v
````

You will received **HTTP/1.1 401 Unauthorized** status and a following JSON body:

````json
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
````

##### It's time to authenticate with new user's credentials

````sh
curl -X POST -vu testclient:secret http://localhost.soochow.co/oauth/token -k -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read%20write&client_secret=secret&client_id=testclient"
````
You will received JSON containing access and refresh tokens.
````json
{
    "access_token": "7fb7353c-0b48-407c-9a18-65ed9754fea0",
    "token_type": "bearer",
    "refresh_token": "a4bb9adb-164d-4794-b181-fafd8458e4fa",
    "expires_in": 43199,
    "scope": "read write"
}
````

#### Get user's info authenticated
````sh
curl -X GET http://localhost.soochow.co/api/users/jack.zhou/ -k -v -H "Authorization: Bearer 7fb7353c-0b48-407c-9a18-65ed9754fea0"
````

You will received basic user info and hypermedia links pointing to user related resources.

````json
{
  "userName": "jack.zhou",
  "chineseName": "周乐",
  "email": "jack@xplusz.com",
  "password": null,
  "avatar": null,
  "phoneNumber": "18550082500",
  "school": null,
  "roles": [
    "USER"
  ],
  "_links": {
    "self": {
      "href": "http://localhost.soochow.co/api/users/jack.zhou"
    }
  }
}
````

#### Run against with heroku

You can see [API documentation](https://trackr-service.herokuapp.com/swagger-ui.html)

##### Let's start with **ROOT** API resource.

The REST service stronly **Hypermedia-driven** and Content Type is **application/hal+json**.

````sh
curl -X GET https://trackr-service.herokuapp.com/api -k
````

You will received hal+json body containing public resource(s).
````json
{
    "_links": {
        "users": {
            "href": "https://trackr-service.herokuapp.com/api/users"
        }
    }
}
````
##### Now let's **Register** user.

````sh
curl -X POST https://trackr-service.herokuapp.com/api/users -k -d '{"userName":"jack.zhou","password":"zhoule", "phoneNumber":"18550082500", "chineseName": "周乐", "email":"jack@xplusz.com"}' -H 'Content-Type: application/json'
````

You will receive 201 status and Location headers.
````
HTTP/1.1 201 Created
Location: https://trackr-service.herokuapp.com/api/users/jack.zhou
````

##### Now let's read user you've created

````sh
curl -X GET https://trackr-service.herokuapp.com/api/users/jack.zhou/ -k -v
````

You will received **HTTP/1.1 401 Unauthorized** status and a following JSON body:

````json
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
````

##### It's time to authenticate with new user's credentials

````sh
curl -X POST -vu testclient:secret https://trackr-service.herokuapp.com/oauth/token -k -H "Accept: application/json" -d "password=admin&username=admin&grant_type=password&scope=read%20write&client_secret=secret&client_id=testclient"
````
You will received JSON containing access and refresh tokens.
````json
{
    "access_token": "7fb7353c-0b48-407c-9a18-65ed9754fea0",
    "token_type": "bearer",
    "refresh_token": "a4bb9adb-164d-4794-b181-fafd8458e4fa",
    "expires_in": 43199,
    "scope": "read write"
}
````

#### Get user's info authenticated
````sh
curl -X GET https://trackr-service.herokuapp.com/api/users/jack.zhou/ -k -v -H "Authorization: Bearer 7fb7353c-0b48-407c-9a18-65ed9754fea0"
````

You will received basic user info and hypermedia links pointing to user related resources.

````json
    {
       "userName": "jack.zhou",
       "chineseName": "周乐",
       "email": "jack@xplusz.com",
       "password": null,
       "avatar": null,
       "phoneNumber": "18550082500",
       "school": null,
       "roles": [
         "USER"
       ],
       "_links": {
         "self": {
           "href": "https://trackr-service.herokuapp.com/api/users/jack.zhou"
         }
       }
     }
````

### Deploy the service to heroku

#### Precondition

* Install heroku ```brew install heroku```
* Register a heroku account
* Add your ssh key in heroku
* Heroku login in terminal by running ```heroku login```

##### Add heroku git remote

```heroku git:remote -a trackr-service ```

#### Deploy the changes to heroku
Commit the changes to you local or git server and then run the following command to deploy the changes to heroku.

```git push heroku master```

##### Other useful commands of heroku

* Check logs  
  * ```heroku logs --trial```
* Attach to the bash in heroku 
   * ```heroku ps bash```
