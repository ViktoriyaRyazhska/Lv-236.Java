<#import "/spring.ftl" as locale/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>FreeMarker Spring MVC Hello World</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="/scripts/languageChanger.js"></script>
</head>
<body>

<div style="float: right">
    <select id="locales">
        <option value=""><@locale.message "lang.select"/></option>
        <option value="en"><@locale.message "lang.en"/></option>
        <option value="ru"><@locale.message "lang.ru"/></option>
        <option value="ua"><@locale.message "lang.ua"/></option>
    </select>
</div>

<div id="header">
    <h2><@locale.message "greeting"/></h2>
</div>
<div id="content">
<#--<script type="text/javascript" src="/temlates/test.js"></script>-->
    <fieldset>
        <legend><@locale.message "school.add"/></legend>
        <form name="schoolDTO" action="/freemarker/freemarkertest/add" method="post">
            <@locale.message "school.name"/>: <input type="text" name="name"/>
            <br/><@locale.message "school.enabled"/>:
                <select name="enabled">
                <option value="true"><@locale.message "true"/></option>
                <option value="false"><@locale.message "false"/></option>
            </select>
            <br/><input type="submit" value="<@locale.message "save"/>"/>
        </form>
    </fieldset>
    <br/>
    <a href="logout"><@locale.message "logout"/></a>
    <table>
        <tr>
            <th colspan="2"><@locale.message "school.list"/></th>
        </tr>
        <tr>
            <th><@locale.message "school.name"/></th>
            <th><@locale.message "school.id"/></th>
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
