<#import "header.ftl" as h>

<#assign pagetitle><@spring.message "parent.page.title"/></#assign>

<@h.header
pagetitle = "${pagetitle}"
cssSources = [
"//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css",
"/scripts/parent-home.css"
]
jsSources = [
"https://code.jquery.com/ui/1.12.1/jquery-ui.js",
"/scripts/datepicker/datepicker-en.js",
"/scripts/datepicker/datepicker-ru.js",
"/scripts/datepicker/datepicker-uk.js",
"/scripts/parent-home.js"
]/>

<div class="container">
    <div class="row content">
        <div class="col-sm-12 text-left">

        <#if model["pupilList"]?size == 0>
            <h1><@spring.message "parent.page.havenopupils"/></h1>
        <#else>

            <br>
            <div id="datepicker" class="float-right"></div>
            <input type="hidden" id="locale" value="${.locale}">
            <h1>${pagetitle}</h1>

            <br>
            <p><@spring.message "parent.page.description"/></p>

            <br><br>
            <div class="font-weight-bold"><@spring.message "parent.page.selectpupil"/></div>
            <ul class="nav nav-pills" id="pupil-select" role="tablist">
            <#list model["pupilList"] as pupil>
                <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#pupil${pupil.id}" role="tab" aria-controls="pupil${pupil.id}" data-pupil-id="${pupil.id}" data-pupil-form-id="${pupil.formId}"
                       <#--data-pupil-form-school-enabled="${pupil.schoolEnabled?string('true', 'false')}-->
        ">${pupil.firstName} ${pupil.lastName} [${pupil.formName}]</a>
                </li>
            </#list>
            </ul>
            <br>
            <div class="clearfix"></div>
<#--<div id="message" style="width: 300px; height: 100px; background-color: chartreuse"> </div>-->
            <div class="card">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link" id="week-schedule-link" data-toggle="tab" href="#week-schedule" role="tab"><@spring.message "parent.page.schedule.title"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="attendance-link" data-toggle="tab" href="#attendance" role="tab"><@spring.message "parent.page.attendance.title"/></a>
                        </li>
                    </ul>
                </div>
                <div class="card-block">

                    <div class="tab-content">
                        <div class="tab-pane" id="week-schedule" role="tabpanel">

                            <table class="table table-striped">
                            <#list 0..10 as tableRow>

                                <tr>
                                    <#list 0..7 as tableCol>
                                        <#if tableCol == 0>
                                            <#if tableRow == 0>
                                                <td class="lesson-position">
                                                    <@spring.message "schedule.lesson.position"/>
                                                </td>
                                            <#else>
                                                <td class="lesson-position">${tableRow}</td>
                                            </#if>
                                        <#else>
                                            <#if tableRow == 0>
                                                <td class="day-of-week"></td>
                                            <#else>
                                                <td class="for-clear"></td>
                                            </#if>
                                        </#if>
                                    </#list>
                                </tr>

                            </#list>
                            </table>

                        </div>
                        <div class="tab-pane" id="attendance" role="tabpanel">
                            <div class="form-group row">
                                <label for="lessons" class="col-2 col-form-label font-weight-bold"><@spring.message "parent.page.select.subject"/></label>
                                <div class="col-10">
                                    <select class="form-control" id="lessons"></select>
                                </div>
                            </div>
                            <table id="attendanceTable" class="table table-striped">
                                <thead>
                                <tr>
                                    <th style="width: 50%;"><@spring.message "attendance.date"/></th>
                                    <th style="width: 50%;"><@spring.message "attendance.grade"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="font-weight-bold"><@spring.message "attendance.average"/></td>
                                    <td class="font-weight-bold" id="avg-grade"></td>
                                </tr>
                                </tbody>
                            </table>
                            <div id="attendanceEmpty">
                                <span></span> <@spring.message "parent.page.havenogrades"/> <span></span>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <br>

        </#if>

        </div>
    </div>
</div>

<@h.footer/>
