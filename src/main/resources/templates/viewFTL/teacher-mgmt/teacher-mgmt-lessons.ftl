<#import "../header.ftl" as h>
<@h.header>

</@h.header>
<link rel="stylesheet" type="text/css" href="/scripts/teacher-mgmt-popup.css">

<br>
<div id="header">
    <h2>
        <span><@spring.message "teacherm.list"/></span>
        <button id="create" class="btn btn-primary float-right lessons-teacher-management"
                onclick="window.location.href='/freemarker/teacher-mgmt/teacher-mgmt-add-lesson/'" style="margin-right: 10px">
            <span class="fa fa-plus"></span>
            <span>
            <@spring.message "teacherm.addLesson"/>
            </span>
        </button>
    </h2>
</div>

<div class="modal-body">
    <table class="table table-striped">
        <tr>
            <th colspan="7"><@spring.message "teacherm.list"/></th>
        </tr>
        <tr>
            <th><@spring.message "teacherm.lessonID"/></th>
            <th><@spring.message "teacherm.lessonName"/></th>
        </tr>
    <#list model["lessonList"] as lesson>
        <tr>
            <td>${lesson.id}</td>
            <td>${lesson.name}</a></td>
        </tr>
    </#list>
    </table>
    <span></span>

</div>
<script src="/scripts/teachermgmt.js"></script>

<@h.footer>

</@h.footer>
