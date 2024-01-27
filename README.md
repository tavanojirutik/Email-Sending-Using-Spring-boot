# Email Sending Feature using Spring Boot 

## Step 1 :
Create Spring Boot Project After Creating Spring Boot Project go to pom.xml and add Dependency "spring-boot-starter-mail" and "jakarta.mail" you 
and use spring initializer and mvnrepository.com

## Step 2 :
You can create the given Static File ServiceImplSendMail.java for Demo purpose 

##  Step 3 : 
How to Set Username and Password in Email Sending Java File?  
this is the Most Important Step in This File 
goto Your Email Setting I you Create Gmail that Time follows Given steps: 
## Step : 
Go to Gmail Settings and enable two-factor authentication and then go to accouct Settings and go to the search box and Search App Password and select app give the name you want and generate the Password 
given generated Password is your password and username is your respective email ID set as a username and password 

## Step 4 :
Email Configuration is set 3 different methods like: Static, Dynamic and using Application.yml This is also a static method but configuration can be defined statically and its high priority 
means when you Define application.yml File then you convert  the Code Dynamic and Dynamic means your Configuration file is defined in a database that time email username and password get using application.yml 
if you want to define and get into the database that time you should Be comment-out the application.yml file 
