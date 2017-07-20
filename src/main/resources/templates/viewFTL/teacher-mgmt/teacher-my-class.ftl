<#import "../header.ftl" as h>
<@h.header>

</@h.header>
<link rel="stylesheet" type="text/css" href="/scripts/admin-home-popup.css">
<br>
<div id="header">
    <h2>
        <div style=" position: absolute;">
            <p class="text-right" style="font-style: italic"><strong><@spring.message "hello"/>
                , ${model.currentUser.firstName} ${model.currentUser.lastName}</strong></p></div>
        <br>
    <@spring.message "teacher.class"/> ${model.formName}
        <button type="button" class="btn btn-primary btn-lg" style="margin-left: 84%;"
                onclick="window.location.href='/freemarker/teacher-my-class/newPupil/${model.currentUser.formId}'">
        <@spring.message "pupil.add"/>
        </button>

    </h2>
</div>
<table class="table table-hover">
    <thead>
    <tr>
        <th style="width: 20%"><@spring.message "teacher.gradebook.table.pupil.name"/></th>
        <th></th>
        <th style="width: 30%"><@spring.message "email"/></th>
        <th><@spring.message "parents"/></th>
        <th></th>

    </tr>
    </thead>

    <tbody>

    <#list model.pupils as i>
    <tr>
        <td>
            <p style="text-shadow:5px 5px 15px grey;"><strong>${i.lastName } ${i.firstName } </strong></p>
        </td>
        <td>
            <button id="edit" type="submit" class="btn btn-primary btn-sm"
                    onclick="editPupilAjax(${i.id})">
                <span class="fa fa-pencil"></span>
                <span class="hidden-md-down"><@spring.message "school.edit"/></span>
            </button>

            <div id="editPopup" class="editModal">
                <div class="modal-content3">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="form-control-label" for="firstName"><@spring.message "firstname"/></label>
                            <input type="text" class="form-control" id="firstNameEdit" name="firstName" minlength=3
                                   maxlength=50 required>
                        </div>
                        <div class="form-group">
                            <label class="form-control-label" minlength=3 maxlength=50
                                   required><@spring.message "lastname"/></label>
                            <input type="text" class="form-control" id="lastNameEdit" name="lastName" minlength=3
                                   maxlength=50 required>
                        </div>
                        <div class="form-group">
                            <label class="form-control-label" for="email"><@spring.message "email"/></label>
                            <input type="email" class="form-control" id="emailEdit" name="email"
                                   required minlength="7" maxlength="100">
                        </div>
                        <div class="form-group">
                            <label class="form-control-label" for="formName"><@spring.message "teacherm.form"/><span
                                id="currentForm">-</label>
                            <button id="assign" class="btn btn-secondary"
                                    onclick="showFormAssignModal()">
                                <span class="fa fa-plus"></span>
                                <span id="assignchange"><@spring.message "pupil.changeClass"/></span>
                            </button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                onclick="hideEditModal()">
                            <span class="fa fa-ban"></span>&nbsp;<span><@spring.message "school.cancel"/></span>
                        </button>
                        <button type="submit" class="btn btn-danger"
                                onclick="savePupil()">
                            <span class="fa fa-save"></span>&nbsp;<span><@spring.message "save"/></span>
                        </button>
                    </div>
                </div>
            </div>
            <div id="formAssign" class="editModal">
                <div class="modal-content3">
                    <div class="modal-body">
                        <select class="form-control form-control-lg" id="forms" style="width: auto">
                            <option value=""><@spring.message "teacherm.select"/></option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-info"
                                onclick="hideFormAssignModal()">
                            <span class="fa fa-arrow-left"></span>&nbsp;<span><@spring.message "back"/></span>
                        </button>
                        <button type="submit" class="btn btn-primary"
                                onclick="assignPupilToForm()">
                            <span class="fa fa-save"></span>&nbsp;<span><@spring.message "save"/></span>
                        </button>
                    </div>
                </div>
            </div>
        </td>
        </td>
        <td>
        ${i.email}
        </td>
        <td>
            <#if i.parents??>
                <#list i.parents as k>
                    <p style="text-shadow:5px 5px 10px grey;margin-bottom: 0px;margin-top: 10px;"> <strong>${k.firstName } ${k.lastName}
                        <a type="button" class="close" aria-label="Close" title="Delete">
                            <span aria-hidden="true">&times;</span>
                        </a>
                        <br></strong></p>
                    <i class="fa fa-envelope-open-o" aria-hidden="true">
                    ${k.email} </i><br>
                </#list>
            </#if>
        </td>
        <td>
            <button type="button" class="btn btn-outline-info btn-sm"
                    style="margin-top: 10px;"
                    onclick="window.location.href='/freemarker/teacher-my-class/newParent/${i.id}'"><@spring.message "addParent"/></button>
        </td>


    </tr>
    </#list>


    </tbody>


</table>
<script src="/scripts/teacher-my-class.js"></script>
<@h.footer>

</@h.footer>
