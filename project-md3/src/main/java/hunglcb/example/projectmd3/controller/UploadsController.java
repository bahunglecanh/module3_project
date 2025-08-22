package hunglcb.example.projectmd3.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "UploadsController", urlPatterns = {"/uploads/*"})
public class UploadsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedPath = request.getPathInfo();
        if (requestedPath == null || requestedPath.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String baseUploads = request.getServletContext().getInitParameter("uploadBaseDir");
        if (baseUploads == null || baseUploads.trim().isEmpty()) {
            String catalinaBase = System.getProperty("catalina.base");
            if (catalinaBase != null && !catalinaBase.isEmpty()) {
                baseUploads = catalinaBase + File.separator + "uploads";
            } else {
                baseUploads = System.getProperty("user.home") + File.separator + "uploads";
            }
        }
        if (baseUploads == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        Path basePath = Paths.get(baseUploads).toAbsolutePath().normalize();
        Path filePath = basePath.resolve(requestedPath.substring(1)).normalize();

        if (!filePath.startsWith(basePath)) { // prevent path traversal
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        File file = filePath.toFile();
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mime = Files.probeContentType(filePath);
        if (mime == null) {
            mime = "application/octet-stream";
        }
        response.setContentType(mime);
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
    }
}


