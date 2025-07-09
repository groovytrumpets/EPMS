<%-- 
    Document   : uploadFileTest
    Created on : 9 thg 7, 2025, 13:22:57
    Author     : groovytrumpets <nguyennamkhanhnnk@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="file" name="file" required />
            <button type="submit">Upload</button>
        </form>
    </body>
</html>
