<#import "../header.ftl" as h>
<@h.header>

</@h.header>
<link rel="stylesheet" type="text/css" href="/scripts/admin-home-popup.css">
<br>
<div id="header">
    <h2>
        <span><@spring.message "hello"/> ,</span>
        <button id="create" class="btn btn-primary float-right create-teacher-management"
                onclick="window.location.href='/freemarker/admin-home/createSchool'">
            <span class="fa fa-plus"></span>
            <span>
            <@spring.message "school.createNew"/>
            </span>
        </button>
    </h2>
</div>
<div id="content">
<table class="table table-striped">
    <tr>
        <th colspan="7"><@spring.message "school.list"/></th>
    </tr>
    <tr>
        <th><@spring.message "school.name"/></th>
        <th><@spring.message "school.id"/></th>
    </tr>
<#list model["schoolList"] as school>
<tr>
    <#if school.enabled=true>

        <td>${school.name}</a></td>
        <td>${school.id}</td>
        <td>
            <#if school.enabled!=true>
                <button class="badge badge-danger hand"
                        onclick="window.location.href='/freemarker/admin-home/school-toggle/${school.id}'"><@spring.message "disabled"/>
                </button></#if>
            <#if school.enabled=true>
                <button class="badge badge-success hand"
                        onclick="window.location.href='/freemarker/admin-home/school-toggle/${school.id}'"><@spring.message "enabled"/>
                </button></#if>
        </td>
        <td>
            <div class="btn-group flex-btn-group-container">
                <button id="view" type="button" class="btn btn-info btn-sm"
                        onclick="showDetailModal(${school.id})">
                    <span class="fa fa-eye"></span>
                    <span class="hidden-md-down"><@spring.message "school.view"/></span>
                </button>
                <button id="edit" type="submit" class="btn btn-primary btn-sm"
                        onclick="window.location.href='/freemarker/admin-home/details/${school.id}'">
                    <span class="fa fa-pencil"></span>
                    <span class="hidden-md-down"><@spring.message "school.edit"/></span>
                </button>
            </div>
        </td>
        <div id="detail${school.id}" class="detailModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>${school.name}</h2>
                </div>
                <div class="modal-body">
                    <dl class="row-md jh-entity-details">
                        <dt><span><@spring.message "school.name"/></span></dt>
                        <dd>
                            <div>
                                <span>${school.name}</span>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info"
                            onclick=removeDetailModal(${school.id})>
                        <span class="fa fa-arrow-left"></span>&nbsp;<span>Back</span>
                    </button>
                </div>
            </div>
        </div>
    </#if>
</tr>
</#list>
</table>
<span></span>
</div>


<script src="/scripts/admin-home.js"></script>

<@h.footer>

</@h.footer>

