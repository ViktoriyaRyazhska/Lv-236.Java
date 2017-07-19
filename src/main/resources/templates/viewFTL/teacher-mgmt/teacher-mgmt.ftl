<#import "../header.ftl" as h>
<@h.header>

</@h.header>
<link rel="stylesheet" type="text/css" href="/scripts/teacher-mgmt-popup.css">
<br>

<div id="header">
    <h2>
        <span><@spring.message "hello"/>, ${model.currentUser.firstName} ${model.currentUser.lastName}</span>
        <button id="create" class="btn btn-primary float-right create-teacher-management"
                onclick="window.location.href='/freemarker/teacher-mgmt/teacher-mgmt-create/'">
            <span class="fa fa-plus"></span>
            <span>
            <@spring.message "teacherm.createNew"/>
            </span>
        </button>
        <button id="lessons" class="btn btn-primary float-right lessons-teacher-management"
                onclick="window.location.href='/freemarker/teacher-mgmt/teacher-mgmt-lessons/'" style="margin-right: 10px">
            <span class="fa fa-plus"></span>
            <span>
            <@spring.message "teacherm.createNewLessons"/>
            </span>
        </button>
    </h2>
</div>
<div id="content">
    <table class="table table-striped">
        <thead>
        <tr>
            <th colspan="7"><@spring.message "teacherm.myTeachers"/></th>
        </tr>
        <tr>
            <th><@spring.message "firstname"/></th>
            <th><@spring.message "lastname"/></th>
            <th><@spring.message "teacherm.status"/></th>
            <th><@spring.message "teacherm.form"/></th>
            <th><@spring.message "teacherm.lessons"/></th>
            <th><@spring.message "email"/></th>
        </tr>
        </thead>
        <tbody id="teachersTable">
        <#list model["teachers"] as teacher>
        <tr>
            <td>${teacher.firstName}</td>
            <td>${teacher.lastName}</td>
            <td>
                <#if teacher.enabled!=true>
                    <button class="badge badge-danger hand"
                            onclick="window.location.href='/freemarker/teacher-mgmt-toggle/${teacher.id}'"><@spring.message "teacherm.deactivated"/>
                    </button></#if>
                <#if teacher.enabled=true>
                    <button class="badge badge-success hand"
                            onclick="window.location.href='/freemarker/teacher-mgmt-toggle/${teacher.id}'"><@spring.message "teacherm.activated"/>
                    </button></#if>
            </td>
            <td>${teacher.formName!""}</td>
            <td>
                <#list teacher.lessons as lesson>
                ${lesson.name}<#if lesson_has_next>,</#if>
                </#list>
            </td>
            <td>${teacher.email!""}</td>
            <td>
                <div class="btn-group flex-btn-group-container">
                    <button id="view" type="button" class="btn btn-info btn-sm"
                            onclick="showDetailModal(${teacher.id})">
                        <span class="fa fa-eye"></span>
                        <span class="hidden-md-down"><@spring.message "school.view"/></span>
                    </button>
                    <button id="edit" type="submit" class="btn btn-primary btn-sm"
                            onclick="editTeacherAjax(${teacher.id})">
                        <span class="fa fa-pencil"></span>
                        <span class="hidden-md-down"><@spring.message "school.edit"/></span>
                    </button>
                    <button id="delete" type="submit" class="btn btn-danger btn-sm"
                            onclick="showDeleteModal(${teacher.id})">
                        <span class="fa fa-remove"></span>
                        <span class="hidden-md-down"><@spring.message "school.delete"/></span>
                    </button>
                </div>
            </td>
            <div id="detail${teacher.id}" class="detailModal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2>${teacher.firstName} ${teacher.lastName}</h2>
                    </div>
                    <div class="modal-body">
                        <dl class="row-md jh-entity-details">
                            <dt><span><@spring.message "teacherm.form"/></span></dt>
                            <dd>
                                <div>
                                    <span>${teacher.formName!""}</span>
                                </div>
                            </dd>

                            <dt><span><@spring.message "email"/></span></dt>
                            <dd>
                                <div>
                                    <span>${teacher.email!""}</span>
                                </div>
                            </dd>

                            <dt><span><@spring.message "login.login"/></span></dt>
                            <dd>
                                <div>
                                    <span>${teacher.login}</span>
                                </div>
                            </dd>

                            <dt><span><@spring.message "teacherm.lessons"/></span></dt>
                            <dd>
            <span>
                <#list teacher.lessons as lesson>
                ${lesson.name}<#if lesson_has_next>,</#if>
                </#list>
            </span>
                            </dd>
                        </dl>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info"
                                onclick=removeDetailModal(${teacher.id})>
                            <span class="fa fa-arrow-left"></span>&nbsp;<span><@spring.message "back"/></span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="deleteModal" id="delete${teacher.id}">
                <div class="modal-content2">
                    <div class="modal-body">
                        <h2><@spring.message "teacherm.deleteconf"/> ${teacher.firstName} ${teacher.lastName}?</h2>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                onclick="removeDeleteModal(${teacher.id})">
                            <span class="fa fa-ban"></span>&nbsp;<span><@spring.message "school.cancel"/></span>
                        </button>
                        <button type="submit" class="btn btn-danger"
                                onclick="window.location.href='/freemarker/teacher-mgmt-delete/${teacher.id}'">
                            <span class="fa fa-remove"></span>&nbsp;<span><@spring.message "school.delete"/></span>
                        </button>
                    </div>
                </div>
            </div>
        </tr>
        </#list>
        </tbody>
        <div id="editPopup" class="editModal">
            <div class="modal-content3">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="form-control-label" for="firstName"><@spring.message "firstname"/></label>
                        <input type="text" class="form-control" id="firstNameEdit" name="firstName" minlength=3 maxlength=50 required>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" minlength=3 maxlength=50 required><@spring.message "lastname"/></label>
                        <input type="text" class="form-control" id="lastNameEdit" name="lastName" minlength=3 maxlength=50 required>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="email"><@spring.message "email"/></label>
                        <input type="email" class="form-control" id="emailEdit" name="email"
                               required minlength="7" maxlength="100">
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="formName"><@spring.message "teacherm.form"/><span id="currentForm">-</label>
                        <button id="assign" class="btn btn-secondary"
                                         onclick="showFormAssignModal()">
                        <span class="fa fa-plus"></span>
                        <span id="assignchange"><@spring.message "teacherm.assign"/></span>
                        </button>
                        <button id="remove" class="btn btn-secondary"
                                onclick="removeFormAssignment()">
                            <span class="fa fa-minus"></span>
                            <span id="assignchange"><@spring.message "teacherm.remove"/></span>
                        </button>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"
                            onclick="hideEditModal()">
                        <span class="fa fa-ban"></span>&nbsp;<span><@spring.message "school.cancel"/></span>
                    </button>
                    <button type="submit" class="btn btn-danger"
                            onclick="saveTeacher()">
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
                            onclick="assignFormToTeacher()">
                        <span class="fa fa-save"></span>&nbsp;<span><@spring.message "save"/></span>
                    </button>
                </div>
            </div>
        </div>
    </table>
</div>


<script src="/scripts/teachermgmt.js"></script>

<@h.footer>

</@h.footer>
