<#import "header.ftl" as h>
<@h.header>

</@h.header>

<div id="subjectArea">
    <label for="lessons"><h2>Choose subject:</h2></label>
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
