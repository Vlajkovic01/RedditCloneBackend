# RedditCloneBackend
 
This project was created in 2nd year at the Faculty of Technical Science at Novi Sad as a part of a project for the BackEnd Development course. Later it has been expanded to 3rd year as a part of a "Manipulating e-documents" course. Developed by [@stefan](https://www.linkedin.com/in/stefanvlajkovic/)

## Getting started

To run this project you will need the following languages and libraries:
- [Java ](https://openjdk.org/) version 17
- [MySql](https://dev.mysql.com/doc/) version 8.0
- [ElasticSearch](https://www.elastic.co/downloads/past-releases/elasticsearch-7-4-0) version 7.4.0
- [Kibana](https://www.elastic.co/downloads/past-releases/kibana-7-4-0) version 7.4.0



The Easiest way to start the spring-boot app would be to open this project in some Java IDE and hit run. Then navigate to `http://localhost:8080/api` to test endpoints. You can also run frontend which I have written in this [AngularApp](https://github.com/Vlajkovic01/RedditCloneFrontend). 
To have some initial test data go to `application.properties` and check these two lines (**NOTE:** you will not have admin acc if you don't run this SQL script):
```
    spring.jpa.hibernate.ddl-auto = create-drop
    spring.sql.init.mode=always
    spring.jpa.defer-datasource-initialization=true
    
    //Demo passwords in this script all are 123456
```
Also, you can set your database parameters by editing the following lines in the same file
```
    spring.datasource.url=jdbc:mysql://localhost:3306/redditclone 
    spring.datasource.username= username
    spring.datasource.password= password
```
### Important:

Since I didn't containerize the whole app or create some script to run everything at once you will need to run an elastic search by yourself.
Just download the zip and run `./elasticsearch` from the bin folder.

You will need to install [SerbianAnalyzer](https://github.com/chenejac/udd06) for Serbian text analyzing and the **app will not run without it.** If you find repo instructions too much I also included built zip [on my drive](https://drive.google.com/drive/folders/1rsLx9DeyV5rPjYGl4Hda-RyO1AocWnsJ?usp=sharing),
and you can install it by running the command (in the bin folder also):
``` 
./elasticsearch-plugin install file:<absolute path of pugin .zip>
``` 

## Features
- Login/Register with JWT
- Multiple user roles(User,Mod,Admin)
- Create/Edit Community
- Create post(txt + img + pdf) with flairs
- Commenting(infinite comment replies)
- Edit/Delete comment
- Report post/comment
- React to post/comment
- Posts/Comment sorting(new,top,hot)
- User profile edit
- Community edit
- Banning users
- Community suspend
- Community/post search - Can search by two chosen parameters with AND or OR logic
  and multiple search styles(fuzzy,match,match phrase, range)
