<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
</head>
<body>
    <div style="background-color:blue; height:300px; display:flex; flex-direction:column; justify-content:center; align-items:center">        
        <form action="CreateDeptPair" method="POST">
            <input type="text" name="dept-num" placeholder="Entrez un num�ro de d�partement"/>
            <br>
            <input type="text" name="dept-nom" placeholder="Entrez un nom de d�partement"/>
            <br>
            <input type="submit" value="Envoyer"/>
        </form>
    </div>
</body>
</html>