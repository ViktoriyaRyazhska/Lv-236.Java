<#import "header.ftl" as h>
<@h.header>
</@h.header>

<link rel='stylesheet' href='/scripts/calendar/css/jquery-ui.css'/>
<link rel='stylesheet' href='/scripts/calendar/fc/fullcalendar.css'/>
<link rel='stylesheet' href='/scripts/calendar/css/style.css'/>

<div class="container-fluid">
    <div class="page-header">
        <h1><@spring.message "pupil.home.welcome"/> ${model.pupilFirstName} ${model.pupilLastName}</h1>
        <br>
        <h4><@spring.message "pupil.home.table.label"/><span id="label_date"></span></h4>

    </div>
    <div class="row">
        <div class="col-md-7" id="scheduleTable">
            <table class="table table-striped" >
                <tr>
                    <th><@spring.message "pupil.home.table.position"/></th>
                    <th><@spring.message "pupil.home.table.lesson"/></th>
                    <th><@spring.message "pupil.home.table.homework"/></th>
                    <th><@spring.message "pupil.home.table.classroom"/></th>
                    <th><@spring.message "pupil.home.table.teacher"/></th>
                    <th><@spring.message "pupil.home.table.attendance"/></th>
                </tr>
                <tbody>
                <#list 1..10 as table_row>
                <tr id="row">
                    <#list 0..5 as col>
                        <#if col == 0>
                            <td>${table_row}</td>
                        <#elseif col == 2>
                            <td class="homework">-</td>
                            <div class="container">
                                <div class="modal fade .modal-sm" id="modal-homework">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;
                                                </button>
                                                <h5 class="modal-title"><@spring.message "pupil.home.modal.homework.label"/></h5>
                                            </div>
                                            <div class="modal-body" id="setHomework"></div>
                                            <div class="modal-footer">
                                                <a href="" class="btn btn-default" data-dismiss="modal"><@spring.message "pupil.home.modal.close"/></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <#elseif col == 4>
                            <td class="teacher">-</td>
                            <div class="container">
                                <div class="modal fade .modal-sm" id="modal-teacher">
                                    <div class="modal-dialog modal-sm">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;
                                                </button>
                                                <h5 class="modal-title"><@spring.message "pupil.home.modal.teacher.label"/></h5>
                                            </div>
                                            <div class="modal-body" id="teacher-modal"></div>
                                            <div class="modal-footer">
                                                <a href="" class="btn btn-default" data-dismiss="modal"><@spring.message "pupil.home.modal.close"/></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <#else>
                            <td>-</td>
                        </#if>
                    </#list>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-5">
            <div id='calendar'></div>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="/scripts/calendar/js/jquery-ui.js"></script>
<script src="/scripts/calendar/fc/lib/moment.min.js"></script>
<script src="/scripts/calendar/fc/fullcalendar.js"></script>
<script src="/scripts/calendar/fc/locale-all.js"></script>
<script src="/scripts/calendar/js/main.js"></script>

<@h.footer></@h.footer>
