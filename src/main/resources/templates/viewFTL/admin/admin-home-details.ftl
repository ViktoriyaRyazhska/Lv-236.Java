<#import "../header.ftl" as h>
<@h.header>

</@h.header>

<br>
<div id="header">

</div>
<div id="content">

    <br/>
    <table class="table table-striped">
        <h2>
        <@spring.message "teacher.list"/> ${schoolId}
        </h2>
        <tr>
            <th id="demo"><@spring.message "firstname"/></th>
            <th><@spring.message "lastname"/></th>
            <th><@spring.message "email"/></th>
            <th><@spring.message "school.headTeacher"/></th>
        </tr>
    <#list model["teachersList"] as teacher>
        <tr>
            <td>${teacher.firstName}</td>
            <td>${teacher.lastName}</td>
            <td>${teacher.email}</td>
            <td>
                <div class="btn-group flex-btn-group-container">
                    <button id="view" type="submit" class="btn btn-info btn-sm"
                        <span class="fa fa-check-square-o"></span>
                        <span class="hidden-md-down"><@spring.message "school.headTeacher"/></span>
                    </button>
                </div>
            </td>
        </tr>

    </#list>
    </table>
        <h2>

            <button id="create" class="btn btn-primary float-right create-teacher-management"
                    onclick="window.location.href='/freemarker/admin-home/createHeadTeacher/${schoolId}'">
                <span class="fa fa-plus"></span>
                <span>
                <@spring.message "newHeadTeacher"/>
            </span>
            </button>
        </h2>

    <br>
    <table class="table table-striped">
        <h2>
        <@spring.message "school.headTeachers"/>
        </h2>
        <tr>
            <th id="demo"><@spring.message "firstname"/></th>
            <th><@spring.message "lastname"/></th>
            <th><@spring.message "email"/></th>
            <th><@spring.message "school.headTeacher"/></th>
        </tr>
    <#list model["headTeachers"] as headTeacher>
        <tr>
            <td>${headTeacher.firstName}</td>
            <td>${headTeacher.lastName}</td>
            <td>${headTeacher.email}</td>
            <td>
                <div class="btn-group flex-btn-group-container">
                    <button id="delete" type="submit" class="btn btn-danger btn-sm">
                        <span class="fa fa-remove"></span>
                        <span class="hidden-md-down"><@spring.message "school.headTeacher"/></span>
                    </button>
                </div>
            </td>

        </tr>

    </#list>
    </table>

    <br>

</div>


<button type="submit" class="btn btn-info"
        onclick="window.location.href='/freemarker/admin-home/'">
    <span class="fa fa-arrow-left"></span>&nbsp;<span><@spring.message "back"/></span>
</button>
<@h.footer>

</@h.footer>
