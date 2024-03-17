package controleurs;

import dao.DS;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controleur extends HttpServlet {

    Connection con = null;

    public boolean verifToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");

        try {
            con = DS.getConnection();
            String authorization = req.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic"))
                return false;
            // on décode le token
            String token = authorization.substring("Basic".length()).trim();
            byte[] base64 = Base64.getDecoder().decode(token);
            String[] lm = (new String(base64)).split(":");

            if (lm.length < 2) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            String login = lm[0];
            String password = lm[1];

            String query = "SELECT * FROM users WHERE login = ? AND pwd = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getString("login").equals("admin") && rs.getString("pwd").equals("admin")) {
                return true;
            }

            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}