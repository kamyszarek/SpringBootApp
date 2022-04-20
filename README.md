### Description
This application shows the basic functionalities for managing a simple company with administrator and user authorities. It was used Java 11, Spring Boot, MySQL database and Thymeleaf just to communicate with users. As Admin you can register new users, delete chosen ones, but also (as admin and user) change your password or show your personal data on the screen.

### Prepare environment
The application was coded in IntelliJ IDEA so it would be great to use that environment to run this project.<br>
To run this app you need XAMPP(or any other program that allows to manage local web server) and download database file "mycompany" from this repo.<br>
After that, place the database file in the right folder in xampp catalog (xampp->mysql->data).

### Change database source in app
Open "application.properties" (src->main->java->templates) and optionally change the line below for your current location of the downloaded database.
```ruby
spring.datasource.url=jdbc:mysql://127.0.0.1/mycompany?useUnicode=true&characterEncoding=utf-8
```

### Running app
Now you can run this project. To log in, you can use 2 example users with different authentications:<br>
ADMIN- Login:ArekAdmin, Password:AdminArek<br>
USER- Login:Janek99, Password:Janek99
