<!DOCTYPE html>
<html lang="en">
<head><title>FreeMarker Spring MVC Hello World</title>
<body>
<div id="header">
    <h2>
        FreeMarker Spring MVC Hello World
    </h2>
</div>

<div id="content">
<#--<script type="text/javascript" src="/temlates/test.js"></script>-->
    <fieldset>
        <legend>Add School</legend>
        <form name="schoolDTO" action="/freemarker/freemarkertest/add" method="post">
            Name: <input type="text" name="name"/> <br/>
            Enabled: <select name="enabled">
            <option value="true">True</option>
            <option value="false">False</option>
        </select>
            <input type="submit" value="Save"/>
        </form>
    </fieldset>
    <br/>
    <a href="logout">Logout</a>
    <table>
        <tr>
            <th colspan="2">Schools List</th>
        </tr>
        <tr>
            <th>Name</th>
            <th>ID</th>
        </tr>
    <#list model["schoolList"] as school>
        <tr>
            <td><a href="/freemarker/teachers/${school.id}">${school.name}</a></td>
            <td>${school.id}</td>
        </tr>
    </#list>
    </table>
</div>
</body>
</html>
