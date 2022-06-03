package com.alex.musicart.controller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = {"/upload_image"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 25)
public class ImageController extends HttpServlet {
    private static final String CONTENT_TYPE = "image/png";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //var t = request.getParameter("img");
        String path = request.getParameter("image_path");
        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
        response.setContentType("image/png");
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //var t = request.getParameter("img");
        String path = request.getParameter("image_path");
        byte[] imageBytes = Files.readAllBytes(Paths.get(path));
        response.setContentType(CONTENT_TYPE);
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }
}