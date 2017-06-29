<#import "../header.ftl" as h>
<@h.header>

</@h.header>

<br>
<div id="header">
    <h2>
    <@spring.message "teacherm.addLesson"/>
    </h2>
</div>
<form name="editForm" action="/freemarker/teacher-mgmt/addLesson" method="post">
    <div class="body">
        <div class="form-group">
            <label class="form-control-label" for="name"><@spring.message "teacherm.createNewLessons"/></label>
            <input type="text" class="form-control" id="lessonName" name="name" minlength=3 maxlength=50 required>
        </div>
    <@spring.message "school.enabled"/>
        <select name="enabled">
            <option value="true"><@spring.message "true"/></option>
            <option value="false"><@spring.message "false"/></option>
        </select>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-info"
                onclick="window.location.href='/freemarker/teacher-mgmt/teacher-mgmt-lessons/'">
            <span class="fa fa-arrow-left"></span>&nbsp;<span><@spring.message "back"/></span>
        </button>
        <button type="button" class="btn btn-default" data-dismiss="modal"
                onclick="reset()">
            <span class="fa fa-ban"></span>&nbsp;<span><@spring.message "school.cancel"/></span>
        </button>
        <button type="submit" class="btn btn-primary">
            <span class="fa fa-save"></span>&nbsp;<span><@spring.message "school.save"/></span>
        </button>
    </div>
</form>


<@h.footer>

</@h.footer>
