<#macro header>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="/scripts/navbar.css">
    <title>SchoolNet</title>
</head>
<body>
<div>
    <nav class="navbar navbar-inverse navbar-toggleable-md jh-navbar">
        <div class="jh-logo-container float-left">
            <a class="jh-navbar-toggler hidden-lg-up float-right" href="javascript:void(0);" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" (click)="toggleNavbar()">
                <i class="fa fa-bars"></i>
            </a>
            <a class="navbar-brand logo float-left" (click)="collapseNavbar()">
                <span class="logo-img"></span>
                <span class="navbar-title">SchoolNet</span>
            </a>
        </div>
        <div class="navbar-collapse collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">



    <@security.authorize  access="hasRole('ROLE_TEACHER')">
        <li class="nav-item">
            <a class="nav-link"  (click)="collapseNavbar()" href="/freemarker/teacher-schedule">
                <i class="fa fa-home" aria-hidden="true"></i>
                <span>Teacher Schedule</span>
            </a>
        </li>
    </@security.authorize>
                <@security.authorize  access="hasRole('ROLE_HEAD_TEACHER') or hasRole('ROLE_ADMIN')">
                <li class="nav-item">
                    <a class="nav-link"  (click)="collapseNavbar()" href="/freemarker/freemarkertest">
                        <i class="fa fa-home" aria-hidden="true"></i>
                        <span>Home</span>
                    </a>
                </li>
                <li class="nav-item dropdown pointer">
                    <a class="nav-link dropdown-toggle" href="javascript:void(0);" id="head-teacher-menu" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
                        <span>
                            <i class="fa fa-tasks" aria-hidden="true"></i>
                                <span>
                                    Management
                                </span>
                            <b class="caret"></b>
                        </span>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="head-teacher-menu">
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/teacher-mgmt/teacher-mgmt">
                            <i class="fa fa-user" aria-hidden="true"></i>
                            <span>Teachers</span>
                        </a>
                        <a class="dropdown-item" (click)="collapseNavbar()" href="javascript:void(0);">
                            <i class="fa fa-calendar" aria-hidden="true"></i>
                            <span>Classrooms</span>
                        </a>
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/freemarkertest">
                            <i class="fa fa-check" aria-hidden="true"></i>
                            <span>Freemarker</span>
                        </a>
                    </div>
                </li>
                </@security.authorize>

                <@security.authorize  access="hasRole('ROLE_PUPIL') or hasRole('ROLE_ADMIN')">
                <li class="nav-item dropdown pointer">
                    <a class="nav-link dropdown-toggle" href="/freemarker/pupil-home" id="pupil-menu" data-toggle="dropdown" aria-haspopup="false" aria-expanded="false">
                        <span>
                            <i class="fa fa-home" aria-hidden="true"></i>
                                <span>
                                    Home
                                </span>
                            <b class="caret"></b>
                        </span>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="head-teacher-menu">
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/pupil-home">
                            <i class="fa fa-calendar-check-o" aria-hidden="true"></i>
                            <span>Schedule</span>
                        </a>
                        <a class="dropdown-item" (click)="collapseNavbar()" href="/freemarker/pupil/attendances">
                            <i class="fa fa-graduation-cap" aria-hidden="true"></i>
                            <span>Attendances</span>
                        </a>
                    </div>
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
                        <a class="dropdown-item" onclick="change('ua')" href="#">
                            <i class="fa fa-eur" aria-hidden="true"></i>
                            <span><@spring.message "lang.ua"/></span>
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
                            <span>Account</span>
                            <b class="caret"></b>
                        </span>
                    </a>

                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="account-menu">

                        <a class="dropdown-item" (click)="collapseNavbar()" href="javascript:void(0);">
                            <i class="fa fa-fw fa-wrench" aria-hidden="true"></i>
                            <span>Settings</span>
                        </a>
                        <a class="dropdown-item" (click)="collapseNavbar()" href="javascript:void(0);">
                            <i class="fa fa-fw fa-clock-o" aria-hidden="true"></i>
                            <span>Password</span>
                        </a>
                        <@security.authorize access="isAuthenticated()">
                        <a class="dropdown-item" id="logout" href="/freemarker/logout">
                            <i class="fa fa-fw fa-sign-out" aria-hidden="true"></i>
                            <span>Sign out</span>
                        </a>
                        </@security.authorize>
                        <@security.authorize access="! isAuthenticated()">
                        <a class="dropdown-item" id="login" href="/freemarker/login">
                            <i class="fa fa-fw fa-sign-in" aria-hidden="true"></i>
                            <span>Sign in</span>
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
<div style="position: fixed; bottom: 0; width: 100%;">FOOTER</div>
</body>
</html>
</#macro>
