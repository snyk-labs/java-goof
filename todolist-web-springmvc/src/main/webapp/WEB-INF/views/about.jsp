<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="common/header.jspf"%>

<%--content--%>

<div class="container">
    <div class="row">
        <div class="span3">
            <%@ include file="common/sidebar.jspf"%>
        </div>
        <div class="span9">
            <div class="well">
                <div class="page-header">
                    <h1>About Online Todo List</h1>
                </div>
                <table class="table table-bordered table-striped">
                    <tbody>
                    <tr>
                        <td colspan="2"><strong>About</strong></td>
                    </tr>
                    <tr>
                        <td>License</td>
                        <td><a href="http://www.opensource.org/licenses/mit-license.php">The MIT License</a></td>
                    </tr>
                    <tr>
                        <td>Developed by</td>
                        <td><a href="mailto:md.benhassine@gmail.com">Benas</a></td>
                    </tr>
                    <tr>
                        <td colspan="2"><strong>Frameworks</strong></td>
                    </tr>
                    <tr>
                        <td><a href="http://www.springsource.org/about">Spring MVC</a></td>
                        <td>3.2.3</td>
                    </tr>
                    <tr>
                        <td><a href="http://www.springsource.org/about">Spring</a></td>
                        <td>3.2.3</td>
                    </tr>
                    <tr>
                        <td><a href="http://www.hibernate.org/">Hibernate</a></td>
                        <td>4.2.1</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<%--end content--%>
<%@ include file="common/footer.jspf"%>