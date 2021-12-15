## Deploy Java Goof on Heroku

There are multiple ways to deploy an application to heroku. 
Currently deploying the WAR file works (best)

### Deploying WAR file with the Heroku CLI

1. Create Heroku account / Login to Heroku and create a new app
2. Install Heroku CLI on local machine
```
$ brew tap heroku/brew && brew install heroku
```
or
```
$ npm install -g heroku
```

3. login with Heroku CLI
```
$ heroku login
```

4. install Heroku Java plugin
  
```
$ heroku plugins:install java
```

5. build Java goof from root directory
```
$ mvn install
```

6. upload WAR file
```
$ heroku war:deploy todolist-web-struts/target/todolist.war --app <your_app_name>
```

The war file should be uploading and the app will start up. This may take some time.




