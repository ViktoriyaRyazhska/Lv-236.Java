<#import "../header.ftl" as h>
<@h.header
cssSources = [
"/scripts/bootstrap-datepiker/datepicker.css",
"/scripts/schedule.css"]
jsSources = [
"/scripts/bootstrap-datepiker/bootstrap-datepicker.js",
"/scripts/bootstrap-datepiker/bootstrap-datepicker.uk.js",
"/scripts/bootstrap-datepiker/bootstrap-datepicker.ru.js"
]/>

<div class="container-fluid">
    <div class="row">
        <div class="page-header">
            <h3><@spring.message "navbar.schedule"/></h3>
        </div>
    </div>

    <div class="row">

        <div class="col-md-3">
            <br>
            <div class="row">
                <div class="datepicker" style="margin: auto"></div>
            </div>
            <br>
            <div class="row">
                <select class="form-control" id="target">
                    <option value="BY_TEACHER"><@spring.message "pupil.home.table.teacher"/></option>
                    <option value="BY_CLASSROOM"><@spring.message "pupil.home.table.classroom"/></option>
                </select>
            </div>
            <br>
            <div class="row" id="target-element">
                <select title="select" class="form-control" type="teacher"></select>
            </div>
        </div>
        <div class="col-md-9" id="schedule">
            <table class="table table-bordered table-hover" style="margin: auto">
                <thead>
                <th># №</th>
                <#list 1..7 as week_day>
                <th id="${week_day}"></th>
                </#list>
                </thead>
                <tbody
                ">
            <#list 1..10 as row>
                <tr>
                    <td>${row}</td>
                    <#list 0..6 as col>
                        <td isEmpty="true" class="target_element" data-toggle="modal" data-target="#BY_TEACHER">-</td>
                    </#list>
                </tr>
            </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="/scripts/schedule.js"></script>

<@h.footer></@h.footer>
