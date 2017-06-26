<#import "header.ftl" as h>
<@h.header>

</@h.header>
<script>document.title = '<@spring.message "attendances.page.title"/>';</script>
<div id="subjectArea">
    <label for="lessons"><h3><@spring.message "attendances.page.subject"/></h3></label>
    <select class="form-control form-control-lg" id="lessons" name="subject" style="width: auto">
    <#list lessons as lesson>
        <option value="${lesson.id}">${lesson.name}</option>
    </#list>
    </select>
</div>
<div id="infoAttendance">

</div>
<script src="/scripts/AjaxGETAttandance.js"></script>
<@h.footer>

</@h.footer>
