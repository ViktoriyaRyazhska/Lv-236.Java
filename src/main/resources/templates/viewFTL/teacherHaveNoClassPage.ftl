<#import "header.ftl" as h>
<@h.header>

</@h.header>
<link rel="stylesheet" type="text/css" href="/scripts/admin-home-popup.css">
<br>
<div id="header">
    <br>
    <br>
    <div class="container">
        <div class="row">

            <div class="col align-self-center">
                <div class="alert alert-info" role="alert">
                    <strong> <span>${model.currentUser.firstName} ${model.currentUser.lastName}!</span></strong>

                You have no class
                </div>
            </div>

        </div>
    </div>


</div>


<@h.footer>

</@h.footer>
