package vn.edu.hcmuaf.fit.laptrinhweb2.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Menu.MainMenu;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.MenuService;

import java.io.IOException;
import java.util.List;
@WebFilter("/*")
public class MenuFilter implements Filter {

    private MenuService menuService;
    private boolean isStaticResource(String uri) {
        return uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(". png")
                || uri.endsWith(".jpg")
                || uri. endsWith(".jpeg")
                || uri.endsWith(". gif")
                || uri.endsWith(". ico")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/images/");
    }
    @Override
    public void init(FilterConfig filterConfig) {
        menuService = new MenuService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
        if (isStaticResource(uri)) {
            chain.doFilter(request, response);
            return;
        }
        List<MainMenu> menus = menuService.getAllMenus();
        req.setAttribute("listMenu", menus);

        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        System.out.println("MenuFilter: Đã hủy!");
    }

}

