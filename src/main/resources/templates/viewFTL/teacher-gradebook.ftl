<#import "header.ftl" as h>

<#assign pagetitle><@spring.message "teacher.gradebook.page.title"/> <#if model.form??>${model.form.name}, </#if><#if model.lesson??>${model.lesson.name}</#if></#assign>

<@h.header
pagetitle = "${pagetitle}"
cssSources = [
"/scripts/teacher-gradebook.css"
]
jsSources = [
"/scripts/teacher-gradebook.js"
]/>

<div class="container-fluid">

    <br>
    <h1>${pagetitle}</h1>
    <div><@spring.message "teacher.gradebook.logged.as"/> <strong>${model.teacher.firstName} ${model.teacher.lastName}</strong></div>
    <input id="teacher-id" type="hidden" value="${model.teacher.id}">

    <br>
    <p><@spring.message "teacher.gradebook.description"/></p>

    <form class="form-inline" id="formslessons">
        <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="forms">
        <#list model.forms as form>
            <option value="${form.id}" <#if model.form?? && form.id = model.form.id>selected</#if>>${form.name}</option>
        </#list>
        </select>
        <select class="custom-select mb-2 mr-sm-2 mb-sm-0" id="lessons">
            <option selected>Choose lesson...</option>
        </select>
    </form>

    <br>
<#if model.error??>

    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading"><@spring.message "teacher.gradebook.error"/></h4>
        <@spring.message "teacher.gradebook.error${model['error']}.description"/>
    </div>

<#elseif model.pupils??>

    <table class="table table-striped gradebook">
        <#list model.pupils>

            <tr>
                <th class="number"><@spring.message "teacher.gradebook.table.pupil.position"/></th>
                <th class="fullname"><@spring.message "teacher.gradebook.table.pupil.name"/></th>
                <#list model.schedules as schedule>
                    <th class="for-clear text-center date lesson-type-${schedule.lessonTypeId}" data-toggle="tooltip" data-placement="top" title="${schedule.lessonTypeName}" data-schedule-id="${schedule.id}">${schedule.date.format('dd/MM')}</th>
                </#list>
            </tr>

            <#items as pupil>
                <tr data-pupil-id="${pupil.id}">
                    <td class="number">${pupil?index + 1}</td>
                    <td class="fullname">${pupil.lastName} ${pupil.firstName}</td>
                    <#list model.schedules as schedule>

                        <#assign attendanceExists = false>
                        <#list model.attendances as attendance>
                            <#if attendance.pupilId == pupil.id && attendance.scheduleId == schedule.id>
                                <#assign attendanceExists = true>
                                <#assign att = attendance>
                                <#break>
                            </#if>
                        </#list>

                        <#if (schedule.teacherId == model.teacher.id) && (schedule.date.format('MMM d, yyyy')?date > model.minDateForEdit.format('MMM d, yyyy')?date)>
                            <#if attendanceExists == true>
                                <td class="for-clear text-center attendance" data-pupil-id="${pupil.id}" data-schedule-id="${schedule.id}" data-attendance-id="${att.id}">
                                    <div id="div-attendance-${pupil.id}-${schedule.id}"><#if !att.grade??>-<#elseif att.grade == 0>Н<#else>${att.grade}</#if></div>
                                    <input class="form-control" maxlength="2" id="input-attendance-${pupil.id}-${schedule.id}" value="<#if att.grade??>${att.grade}</#if>" type="hidden">
                                </td>
                            <#else>
                                <td class="for-clear text-center attendance" data-pupil-id="${pupil.id}" data-schedule-id="${schedule.id}" data-attendance-id="-1">
                                    <div id="div-attendance-${pupil.id}-${schedule.id}">-</div>
                                    <input class="form-control" maxlength="2" id="input-attendance-${pupil.id}-${schedule.id}" value="" type="hidden">
                                </td>
                            </#if>
                        <#else>
                            <td class="text-center"><div><#if attendanceExists == true><#if !att.grade??>-<#elseif att.grade == 0>Н<#else>${att.grade}</#if><#else>-</#if></div></td>
                        </#if>

                    </#list>
                </tr>
            </#items>

        </#list>
    </table>

    <form id="sizeChangerForm" action="" method="get">
        <div class="form-group float-right">
            <select class="form-control" name="size" id="sizeSelector">
                <#list [1, 5, 10, 15, 20] as s>
                    <#if model.sizes == s>
                        <option value="${s}" selected="selected">${s}</option>
                    <#else>
                        <option value="${s}">${s}</option>
                    </#if>
                </#list>
            </select>
        </div>
    </form>

    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item<#if model.current == 0> disabled</#if>"><a class="page-link" href="?page=0&size=${model.sizes}"><@spring.message "pagination.first"/></a></li>
            <li class="page-item<#if model.current == 0> disabled</#if>"><a class="page-link" href="?page=${model.current-1}&size=${model.sizes}"><@spring.message "pagination.prev"/></a></li>
            <#assign first = 0>
            <#assign last = model.longs - 1>
            <#assign width = 2>
            <#assign start = model.current - width>
            <#assign end = model.current + width>
            <#if (start < first)><#assign start = first></#if>
            <#if (end > last)><#assign end = last></#if>
            <#list start..end as i>
                <#if model.current != i>
                    <li class="page-item"><a class="page-link" href="?page=${i}&size=${model.sizes}">${i+1}</a></li>
                <#else>
                    <li class="page-item active"><span class="page-link">${i+1}</span></li>
                </#if>
            </#list>
            <li class="page-item<#if model.current == model.longs-1> disabled</#if>"><a class="page-link" href="?page=${model.current+1}&size=${model.sizes}"><@spring.message "pagination.next"/></a></li>
            <li class="page-item<#if model.current == model.longs-1> disabled</#if>"><a class="page-link" href="?page=${model.longs-1}&size=${model.sizes}"><@spring.message "pagination.last"/></a></li>
        </ul>
    </nav>

</#if>

</div>

<@h.footer/>
