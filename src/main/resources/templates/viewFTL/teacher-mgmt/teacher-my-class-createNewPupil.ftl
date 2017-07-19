<#import "../header.ftl" as h>
<@h.header>

</@h.header>

<link rel="stylesheet" type="text/css" href="/scripts/teacher-mgmt-popup.css">

<br>
<div id="header">
    <h2>
        <span>Create a new  pupil</span>
    </h2>
</div>

<form name="create" action="/freemarker/teacher-my-class/newPupil/${formId}" method="post">
    <div class="body">
        <div class="form-group">
            <label class="form-control-label" for="firstName"><@spring.message "firstname"/></label>
            <input type="text" class="form-control" id="firstName" name="firstName" minlength=3 maxlength=50 required>
        </div>
        <div class="form-group">
            <label class="form-control-label" minlength=3 maxlength=50 required><@spring.message "lastname"/></label>
            <input type="text" class="form-control" id="lastName" name="lastName" minlength=3 maxlength=50 required>
        </div>
        <div class="form-group">
            <label class="form-control-label" for="email"><@spring.message "email"/></label>
            <input type="email" class="form-control" id="email" name="email"
                   required minlength="7" maxlength="100">
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-info" data-dismiss="modal"
                onclick="window.location.href='/freemarker/teacher-mgmt/'">
            <span class="fa fa-arrow-left"></span>&nbsp;<span><@spring.message "back"/></span>
        </button>
        <button type="button" class="btn btn-default" data-dismiss="modal"
                onclick="reset()">
            <span class="fa fa-ban"></span>&nbsp;<span><@spring.message "school.clear"/></span>
        </button>
        <button type="submit" class="btn btn-primary">
            <span class="fa fa-save"></span>&nbsp;<span><@spring.message "save"/></span>
        </button>
    </div>
</form>
<div class="errorModal" id="errorPopup">
    <div class="modal-content3">
        <div class="modal-body">
            <h3>
                <span style="color:red">
                    <script>
                        <#if emailFail??>
                        document.getElementById('errorPopup').style.display = "block";
                        </#if>
                    </script>
                    Invalid e-mail. Please try another one.
                </span>
            </h3>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-info"
                    onclick=hideErrorModal()>
                <span class="fa fa-arrow-left"></span>&nbsp;<span>Back</span>
            </button>
        </div>
    </div>
</div>


<script src="/scripts/teachermgmt.js"></script>

<@h.footer>

</@h.footer>
