package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Policy;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.PolicyServices;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PolicyController", value = "/Policy")
public class PolicyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PolicyServices policyServices = new PolicyServices();
        List<Policy> policies = policyServices.getAll();
        request.setAttribute("policies", policies);
        request.getRequestDispatcher("Policy.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}