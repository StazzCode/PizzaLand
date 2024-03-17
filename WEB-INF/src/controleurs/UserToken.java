package controleurs;

import dao.DS;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/token")
public class UserToken extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        Connection con = null;
        String token;

        try {
            con = DS.getConnection();

            String login = req.getParameter("login");
            String password = req.getParameter("password");

            String query = "SELECT * FROM users WHERE login = ? AND pwd = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                token = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
                out.println(login + " : " + token);
            } else {
                out.println("Vous êtes inconnu ici !");
                // res.sendRedirect("login1.html");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
