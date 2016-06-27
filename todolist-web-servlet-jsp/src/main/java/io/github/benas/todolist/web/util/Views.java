package io.github.benas.todolist.web.util;

/**
 * Constants class for views names.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Views {
    
    private Views() {}

    public static final String PREFIX = "/WEB-INF/views";

    public static final String HOME_PAGE = PREFIX + "/user/home.jsp";

    public static final String LOGIN_PAGE = PREFIX + "/user/login.jsp";

    public static final String ACCOUNT_PAGE = PREFIX + "/user/account.jsp";

    public static final String REGISTER_PAGE = PREFIX + "/user/register.jsp";

    public static final String CREATE_TODO_PAGE = PREFIX + "/todo/create.jsp";

    public static final String SEARCH_PAGE = PREFIX + "/todo/search.jsp";

    public static final String UPDATE_TODO_PAGE = PREFIX + "/todo/update.jsp";

    public static final String ERROR_PAGE = PREFIX + "/error.jsp";

    public static final String ABOUT_PAGE = PREFIX + "/about.jsp";

    public static final String INDEX_PAGE = PREFIX + "/index.jsp";

}
