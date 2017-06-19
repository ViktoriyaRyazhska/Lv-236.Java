<#import "header.ftl" as h>
<#import "calendar.ftl" as calendar>
<@h.header>

</@h.header>


<div id="header">
    <h1>Welcome ${model.currentPupil.firstName} ${model.currentPupil.lastName}</h1>
    <br>
</div>

<div class="row">
    <div class="col-md-6">
        <table class="table table-striped">
            <tr>
                <th colspan="5">My schedule</th>
            </tr>
            <tr>
                <th>Position</th>
                <th>Lesson</th>
                <th>Homework</th>
                <th>Classroom</th>
                <th>Teacher</th>
            </tr>
        <#list model["mySchedule"] as schedule>
            <tr>
                <td>${schedule.lessonPosition}</td>
                <td>${schedule.lessonName}</td>
                <td>${schedule.homework}</td>
                <td class="text"><span>${schedule.classroomName}</span></td>
                <td>${schedule.teacherFirstName} ${schedule.teacherLastName}</td>
            </tr>
        </#list>
        </table>
    </div>
    <div class="col-md-6">
    <@calendar.calendar></@calendar.calendar>
    </div>
</div>



<@h.footer>

</@h.footer>

