## Todolist MVC

Todolist MVC is like [TodoMVC][] but for Java Web Frameworks instead of Javascript Frameworks.

The goal is to implement the same application using different technologies and compare them according to:

 * Framework complexity
 * Features set
 * Ease of use and developer productivity
 * Documentation
 * etc

Two types of frameworks are being compared:

 __1. Request based frameworks__

  1.1 Servlet 3 / JSP 2.2

  1.2 Spring MVC 3.2

  1.3 Struts 2.3

  1.4 Groovy/Grails 2.2 __(WIP)__

  1.5 Play Framework 2.1 __(WIP)__

 __2. Component based frameworks__

  2.1 Tapestry 5.3

  2.2 JSF 2.2 __(WIP)__

  2.3 Wicket 6.9 __(WIP)__

  2.4 Vaadin 7.1 __(WIP)__

In order to compare these frameworks objectively, the best implementation should be provided for each framework. If you think
you are an expert/ninja in some technology, please don't hesitate to fix/improve every single aspect of the current implementation.
If your favorite framework is not addressed, you are welcome to add it to the list by providing your own implementation of the application.

Please note that performance comparison is not addressed here, there are many excellent benchmarks on the web like [TechEmpower's Framework Benchmarks][].
We are comparing especially features set and developer productivity using each framework : form handling, request parameters binding, Ajax support, I18N support, etc.

The comparison table will be available once all implementations will be finished/provided.

## About Todolist MVC

Todolist MVC is a simple yet complete CRUD web application to store todo list online.
Some features like search todos, export todolist, user and session management go beyond CRUD operations to make the use case complete and allows covering most of the features of each framework.

The application backend is developed using Spring and JPA/Hibernate. Data is persisted in an in-memory HSQL database to make testing/running the application relatively easy.
This module, named `todolist-core`, is common to all web layers and is a good use case to see how web frameworks integrate with Spring.

Common web utilities (JSTL tags, Filters, Backing beans, etc) are packaged in a separate common web module named `todolist-web-common`.

Finally, for each web framework, a separate war module is created to implement ONLY the web layer of the application.

Todolist MVC uses [Twitter Bootstrap][] for presentation layer, here are some screenshots:

![Index page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-index.png)

![Sign-in page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-signin.png)

![Home page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-home.png)

![Search page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-search.png)

![User account page](https://github.com/benas/todolist-mvc/raw/master/src/site/screenshots/todolist-account.png)

Ideally, Todolist MVC URLs and actions should be designed as follows :

<table border="1">
<thead>
<tr>
    <th> </th>
    <th>Request</th>
    <th>Type</th>
    <th>Parameters</th>
    <th>Action</th>
    <th>Model</th>
    <th>View</th>
</tr>
</thead>
<tbody>
<tr>
    <td rowspan="6">public</td>
    <td>/index</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to welcome page</td>
    <td>N/A</td>
    <td>index.jsp</td>
</tr>
<tr>
    <td>/about</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to about page</td>
    <td>N/A</td>
    <td>about.jsp</td>
</tr>
<tr>
    <td>/login</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to login page</td>
    <td>N/A</td>
    <td>user/login.jsp</td>
</tr>
<tr>
    <td>/login</td>
    <td>POST</td>
    <td>login form bean</td>
    <td>process user login</td>
    <td>todo list</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td>/register</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to register page</td>
    <td>N/A</td>
    <td>user/register.jsp</td>
</tr>
<tr>
    <td>/register</td>
    <td>POST</td>
    <td>registration form bean</td>
    <td>process user registration</td>
    <td>todo list</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td rowspan="4">user</td>
    <td>/user/account</td>
    <td>GET</td>
    <td>N/A</td>
    <td>load user details</td>
    <td>user, todo stats</td>
    <td>user/account/details.jsp</td>
</tr>
<tr>
    <td>/user/account</td>
    <td>PUT</td>
    <td>user</td>
    <td>update account details</td>
    <td>user</td>
    <td>user/account/details.jsp</td>
</tr>
<tr>
    <td>/user/account</td>
    <td>DELETE</td>
    <td>user</td>
    <td>process account deletion</td>
    <td>N/A</td>
    <td>index.jsp</td>
</tr>
<tr>
    <td>/user/logout</td>
    <td>GET</td>
    <td>N/A</td>
    <td>process logout</td>
    <td>N/A</td>
    <td>index.jsp</td>
</tr>
<tr>
    <td rowspan="9">todo</td>
    <td>/todos</td>
    <td>GET</td>
    <td>N/A</td>
    <td>load user todos</td>
    <td>todolist</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td>/todos/new</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to todo creation page</td>
    <td>N/A</td>
    <td>todo/create.jsp</td>
</tr>
<tr>
    <td>/todos/new</td>
    <td>POST</td>
    <td>todo bean</td>
    <td>process todo creation</td>
    <td>todolist</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td>/todos/{todoId}</td>
    <td>GET</td>
    <td>todoId</td>
    <td>redirect to todo details/update page</td>
    <td>todo bean</td>
    <td>todo/update.jsp</td>
</tr>
<tr>
    <td>/todos/{todoId}</td>
    <td>PUT</td>
    <td>todo bean</td>
    <td>process todo update</td>
    <td>todolist</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td>/todos/{todoId}</td>
    <td>DELETE</td>
    <td>todoId</td>
    <td>process todo deletion</td>
    <td>todolist</td>
    <td>user/home.jsp</td>
</tr>
<tr>
    <td>/todos/search/{query}</td>
    <td>GET</td>
    <td>search query</td>
    <td>search todos</td>
    <td>todolist, query</td>
    <td>todo/search.jsp</td>
</tr>
<tr>
    <td>/todos/export</td>
    <td>GET</td>
    <td>N/A</td>
    <td>redirect to export page</td>
    <td>N/A</td>
    <td>todo/export.jsp</td>
</tr>
<tr>
    <td>/todos/export</td>
    <td>POST</td>
    <td>export form bean</td>
    <td>process todolist export</td>
    <td>N/A</td>
    <td>todo/export.jsp</td>
</tr>
</tbody>
</table>

URLs under `/user/*` and `/todos/*` must be accessible to only logged users. 
This requirement should be implemented using a servlet filter or equivalent (Struts interceptor, Spring MVC interceptor, etc)

Note that security is not addressed since not all frameworks provide security support.

Finally, view technology may vary for each framework. Here, JSP views are (re)used for most of current implementations.

## Build and run Todolist MVC

1.  Check out the project source code from github : `git clone https://github.com/benas/todolist-mvc.git`
2.  Open a terminal and run the following command from root directory : `mvn install`
3.  Choose a web framework to test and run it. For example : `cd todolist-web-springmvc && mvn tomcat7:run`
4.  Browse the following URL : `localhost:8080/`
5.  You can register a new account or login using the following credentials : foo@bar.org / foobar

## Contributions

Todolist MVC goal is to be community driven. Every single contribution is welcome!

## License
Todolist MVC is released under the [MIT License][].

[TodoMVC]: http://todomvc.com/
[TechEmpower's Framework Benchmarks]: https://github.com/TechEmpower/FrameworkBenchmarks
[Twitter Bootstrap]: http://twitter.github.io/bootstrap/
[MIT License]: http://opensource.org/licenses/mit-license.php/
