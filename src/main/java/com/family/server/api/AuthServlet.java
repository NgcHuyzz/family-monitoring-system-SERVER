package com.family.server.api;

import com.family.server.controller.LoginController;
import com.family.server.security.JwtUtil;
import com.google.gson.Gson;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthServlet extends HttpServlet {

    private final LoginController loginController = new LoginController();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /login hoặc /register
        if (pathInfo == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        switch (pathInfo) {
            case "/login":
                handleLogin(req, resp);
                break;
            case "/register":
                handleRegister(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("[AuthServlet] handleLogin START");

        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, String> body = parseJsonBody(req);
            System.out.println("[AuthServlet] body = " + body);

            String username = body.get("username");
            String password = body.get("password");
            System.out.println("[AuthServlet] username = " + username);

            int check = loginController.checkLogin(username, password);
            System.out.println("[AuthServlet] checkLogin = " + check);

            if (check != 0) {
                result.put("success", false);
                result.put("code", check);
                writeJson(resp, result);
                System.out.println("[AuthServlet] handleLogin END (fail " + check + ")");
                return;
            }

            String userId = loginController.getUserID(username);
            System.out.println("[AuthServlet] userId = " + userId);

            System.out.println("[AuthServlet] generateToken START");
            String token = JwtUtil.generateToken(userId, username);
            System.out.println("[AuthServlet] generateToken OK");

            result.put("success", true);
            result.put("userId", userId);
            result.put("token", token);
            writeJson(resp, result);

            System.out.println("[AuthServlet] handleLogin END (success)");
        } catch (Exception e) {
            System.out.println("[AuthServlet] EXCEPTION: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("success", false);
            result.put("message", e.getMessage());
            writeJson(resp, result);
        }
    }



    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> body = parseJsonBody(req);

        String username = body.get("username");
        String password = body.get("password");
        String confirm  = body.get("confirmPassword");

        int check = loginController.checkRegister(username, password, confirm);

        if (check != 0) {
            result.put("success", false);
            result.put("code", check);
            writeJson(resp, result);
            return;
        }

        loginController.addUser(username, password);
        result.put("success", true);
        writeJson(resp, result);
    }

    private Map<String, String> parseJsonBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = req.getReader()) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }
        return gson.fromJson(sb.toString(), Map.class);
    }

    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(data));
    }
}
