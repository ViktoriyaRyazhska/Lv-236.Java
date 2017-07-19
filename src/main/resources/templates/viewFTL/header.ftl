<#macro header pagetitle="" cssSources=[] jsSources=[]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/css/tether.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/scripts/navbar.css">
    <#list cssSources as source>
    <link rel="stylesheet" type="text/css" href="${source}">
    </#list>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"></script>
    <#list jsSources as source>
    <script type="text/javascript" src="${source}"></script>
    </#list>
    <title>${pagetitle}<#if pagetitle!=""> - </#if><@spring.message "schoolnet.title"/></title>
</head>
<body>
<div>
    <nav class="navbar navbar-inverse navbar-toggleable-md jh-navbar">
        <div class="jh-logo-container float-left">
            <a class="jh-navbar-toggler hidden-lg-up float-right" href="javascript:void(0);" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" (click)="toggleNavbar()">
                <i class="fa fa-bars"></i>
            </a>
            <span class="navbar-brand logo float-left">
                <span class="logo-img"></span><span class="navbar-title"><@spring.message "schoolnet.title"/></span>
            </span>
        </div>
        <div class="navbar-collapse collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">


                <@security.authorize  access="hasRole('ROLE_HEAD_TEACHER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/teacher-mgmt/teacher-mgmt">
                            <i class="fa fa-user" aria-hidden="true"></i>
                            <span><@spring.message "navbar.teachers"/></span>
                        </a>
                    </li>
                </@security.authorize>

                <@security.authorize access="hasRole('ROLE_TEACHER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/teacher/schedule">
                            <i class="fa fa-calendar-check-o" aria-hidden="true"></i>
                            <span><@spring.message "navbar.schedule"/></span>
                        </a>
                    </li>
                </@security.authorize>

                <@security.authorize  access="hasAnyRole('ROLE_TEACHER', 'ROLE_HEAD_TEACHER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/teacher-my-class">
                            <i class="fa fa-users" aria-hidden="true"></i>
                            <span><@spring.message "teacher.myClass"/></span>
                        </a>
                    </li>
                </@security.authorize>


                <@security.authorize  access="hasRole('ROLE_PUPIL')">
                <li class="nav-item dropdown pointer">
                    <a class="nav-link dropdown-toggle" href="/freemarker/pupil-home" id="pupil-menu" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
                        <span>
                            <i class="fa fa-home" aria-hidden="true"></i>
                            <span><@spring.message "navbar.home"/></span>
                            <b class="caret"></b>
                        </span>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="head-teacher-menu">
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/pupil-home">
                            <i class="fa fa-calendar-check-o" aria-hidden="true"></i>
                            <span><@spring.message "navbar.schedule"/></span>
                        </a>
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/pupil/attendances">
                            <i class="fa fa-graduation-cap" aria-hidden="true"></i>
                            <span><@spring.message "navbar.attendances"/></span>
                        </a>
                    </div>
                </li>
                </@security.authorize>

                <@security.authorize  access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/admin-home">
                            <i class="fa fa-home" aria-hidden="true"></i>
                            <span><@spring.message "navbar.home"/></span>
                        </a>
                    </li>
                </@security.authorize>

                <@security.authorize  access="hasRole('ROLE_PARENT')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/parent-home">
                            <i class="fa fa-home" aria-hidden="true"></i>
                            <span><@spring.message "navbar.home"/></span>
                        </a>
                    </li>
                </@security.authorize>

                <@security.authorize  access="hasAnyRole('ROLE_TEACHER', 'ROLE_HEAD_TEACHER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/freemarker/teacher-gradebook">
                            <i class="fa fa-book" aria-hidden="true"></i>
                            <span><@spring.message "navbar.gradebook"/></span>
                        </a>
                    </li>
                </@security.authorize>

                <li class="nav-item dropdown pointer">
                    <a class="nav-link dropdown-toggle" href="javascript:void(0);" id="languagesnavBarDropdown" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
                        <span>
                            <i class="fa fa-language" aria-hidden="true"></i>
                            <span><@spring.message "lang.select"/></span>
                            <b class="caret"></b>
                        </span>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="languagesnavBarDropdown" id="locales">
                        <a class="dropdown-item" onclick="change('en')" href="#">
                            <i class="fa fa-usd" aria-hidden="true"></i>
                            <span><@spring.message "lang.en"/></span>
                        </a>
                        <a class="dropdown-item" onclick="change('uk')" href="#">
                            <i class="fa fa-eur" aria-hidden="true"></i>
                            <span><@spring.message "lang.uk"/></span>
                        </a>
                        <a class="dropdown-item" onclick="change('ru')" href="#">
                            <i class="fa fa-rub" aria-hidden="true"></i>
                            <span><@spring.message "lang.ru"/></span>
                        </a>
                    </div>
                </li>

                <li class="nav-item dropdown pointer">
                    <a class="nav-link dropdown-toggle" href="javascript:void(0);" id="account-menu" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
                        <span>
                            <i class="fa fa-user-circle" aria-hidden="true"></i>
                            <span><@spring.message "navbar.account"/></span>
                            <b class="caret"></b>
                        </span>
                    </a>

                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="account-menu">

                        <#--<a class="dropdown-item" (click)="collapseNavbar()" href="javascript:void(0);">-->
                            <#--<i class="fa fa-fw fa-wrench" aria-hidden="true"></i>-->
                            <#--<span><@spring.message "navbar.settings"/></span>-->
                        <#--</a>-->
                        <#--<a class="dropdown-item" (click)="collapseNavbar()" href="javascript:void(0);">-->
                            <#--<i class="fa fa-fw fa-clock-o" aria-hidden="true"></i>-->
                            <#--<span><@spring.message "navbar.password"/></span>-->
                        <#--</a>-->
                        <@security.authorize access="isAuthenticated()">
                        <a class="dropdown-item" id="logout" href="/freemarker/logout">
                            <i class="fa fa-fw fa-sign-out" aria-hidden="true"></i>
                            <span><@spring.message "navbar.signout"/></span>
                        </a>
                        </@security.authorize>
                        <@security.authorize access="! isAuthenticated()">
                        <a class="dropdown-item" id="login" href="/freemarker/login">
                            <i class="fa fa-fw fa-sign-in" aria-hidden="true"></i>
                            <span><@spring.message "navbar.signin"/></span>
                        </a>
                        </@security.authorize>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>
<script src="/scripts/languageChanger.js"></script>
</#macro>

<#macro footer>
<div style="position: fixed; bottom: 0; width: 100%;"><@spring.message "footer.text"/></div>
</body>
</html>
</#macro>
