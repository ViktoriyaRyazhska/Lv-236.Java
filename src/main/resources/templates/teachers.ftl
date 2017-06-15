<!DOCTYPE html>
<html>
<head>
    <title>Teachers list</title>
    <script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</head>
<body>


<div id="content">

    <br/>
    <table width="200" border="1"
           cellpadding="4" cellspacing="0">
        <tr>
            <th colspan="3">Teachers list by school id # ${schoolId}</th>
        </tr>
        <tr>
            <th id="demo">First Name</th>  <th>Last Name</th> <th>E-mail</th>
        </tr>
        <#list model["teachersList"] as teacher>
            <tr>
                <td>${teacher.firstName}  </td> <td>${teacher.lastName}  </td> <td>${teacher.email}</td>
            </tr>
        </#list>
    </table>
    <br/>


    <script type="text/javascript" src="/scripts/test.js"></script>


    <input type="button" value="external function" id="externalJs" onclick="myFunction()">


</div>
</body>
</html>
