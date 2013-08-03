## Todolist MVC

Todolist MVC is like [TodoMVC][] but for Java Web Frameworks instead of Javascript Web Frameworks.

The goal is to implement the same application using different technologies and compare them according to:

 * Framework complexity
 * Features set
 * Ease of use and developer productivity
 * Documentation
 * etc

Two types of frameworks are being compared:

 1. __Request based frameworks__

  1.1 Servlet 3 / JSP 2.2

  1.2 Spring MVC 3.2

  1.3 Struts 2.3

  1.4 Groovy/Grails 2.2 (WIP)

  1.5 Play Framework 2.1 (WIP)

 2. __Component based frameworks__

  2.1 Tapestry 5.3

  2.2 JSF 2.2 (WIP)

  2.3 Wicket 6.9 (WIP)

In order to compare these frameworks objectively, the best implementation should be provided for each framework. If you think
you are an expert/ninja in some technology, please don't hesitate to fix/improve every single aspect of the current implementation.
If your favorite framework is not addressed, you are welcome to add it to the list by providing your own implementation of the application.

The comparison table will be available once all implementations will be finished/provided.

## About Todolist MVC

Todolist MVC is a simple yet complete CRUD web application to store todo list online.
Some features like search todos, export todolist, user and session management go beyond CRUD operations to make the use case complete and allows covering most of the features of each framework.

The application backend is developed using Spring and JPA/Hibernate. Data is persisted in an in-memory HSQL database to make testing/running the application relatively easy.
This module is common to all web layers and is a good use case to see how web frameworks integrate with Spring.

Common web utilities (JSTL tags, Filters, Backing beans, etc) are packaged in a separate common web module.

Finally, for each web framework, a separate war module is created to implement ONLY the web layer of the application.

Todolist MVC uses [Twitter Bootstrap][] for presentation layer, here are some screenshots:

![Index page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-index.png)

![Sign-in page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-signin.png)

![Home page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-home.png)

![Search page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-search.png)

![User account page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-account.png)

## Build and run Todolist MVC

1.  Check out the project source code from github : `git clone https://github.com/benas/todolist-mvc.git`
2.  Open a terminal and run the following command from root directory : `mvn install`
3.  Choose a web framework to test and run it. For example : `cd todolist-web-springmvc && mvn tomcat:run`
4.  Browse the following URL : `localhost:8080/`
5.  You can register a new account or login using the following credentials : foo@bar.org / foobar

## Contributions

Todolist MVC goal is to be community driven. Every single contribution is welcome!

## License
Todolist MVC is released under the [MIT License][].

[TodoMVC]: http://todomvc.com/
[Twitter Bootstrap]: http://twitter.github.io/bootstrap/
[MIT License]: http://opensource.org/licenses/mit-license.php/
