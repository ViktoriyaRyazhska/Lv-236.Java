$(document).ready(function () {
    var dateToSend = new Date().toISOString().slice(0, 19);

    getSchedule(dateToSend);
    lableDate.innerHTML = formatter.format(new Date());
});

var lableDate = document.getElementById("label_date");

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return 'en';
    }
    else {
        return decodeURI(results[1]) || 0;
    }
};

var selectedDate;
var schedules;

var formatter = new Intl.DateTimeFormat($.urlParam('lang'), {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric"
});

$(function () {
    $('#calendar').fullCalendar({
        dayClick: function (eventDate) {
            selectedDate = new Date(eventDate);
            var dateToSend = selectedDate.toISOString().slice(0, 19);
            lableDate.innerHTML = formatter.format(selectedDate);
            getSchedule(dateToSend)
        },
        theme: true,
        locale: $.urlParam('lang')
    });
});

function getSchedule(date) {
    var request = createRequest();
    if (!request) {
        return;
    }
    request.open('GET', 'pupil-home/mySchedule/' + date);
    request.onload = function () {
        schedules = JSON.parse(request.responseText);
        getAttendance(date);
        function getAttendance(date) {
            var request = createRequest();

            if (!request) {
                return;
            }
            request.open('GET', 'pupil-home/myAttendances/' + date, false);
            request.onload = function () {
                attendances = JSON.parse(request.responseText);
            };
            request.send();
        }
        var resetToDefoultVal = $("#row td:not(:first-child)");
        resetToDefoultVal.html("-");
        resetToDefoultVal.removeAttr('trgId', 'data-toggle', 'data-target');
        resetToDefoultVal.removeAttr('data-toggle');
        resetToDefoultVal.removeAttr('data-target');
        // $("#row td:not(:first-child)").removeAttr('trgId').then(removeAttr());
        schedules.forEach(function (el) {
            if (el.id) {
                var selector = $('table tr').eq(el.lessonPosition);
                selector.find("td").eq(1).html(el.lessonName);
                var element = selector.find("td").eq(2).attr('data-toggle', 'modal');
                element.attr('data-target', '#modal-homework');
                element.html(el.homework);
                selector.find("td").eq(3).html(el.classroomName);
                element = selector.find("td").eq(4).attr('data-toggle', 'modal');
                element.attr('data-target', '#modal-teacher');
                element.attr('trgId', el.teacherId);
                element.html(el.teacherFirstName + " " + el.teacherLastName);
                selector.find("td").eq(5).html(pickUpAttendance(el.id, attendances));
            }
        });
    };
    request.send();
}

function pickUpAttendance(id, attendances) {
    var grade = 'n/a';
    attendances.forEach(function (attendance) {
        if (attendance.scheduleId === id) {
            if (attendance.grade != 0) {
                grade = attendance.grade;
            }
        }
    });
    return grade;
}

function createRequest() {
    var request = false;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        try {
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch (CatchException) {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        }
    }
    if (!request) {
        alert("Cannot create XMLHttpRequest");
    }
    return request;
}

$('.homework').on('click', function () {
    $('#setHomework').html($(this).text())
});

$('.teacher').on('click', function () {
    var id = $(this).attr('trgId');
    if(id) {
        $.ajax({
            url: 'pupil-home/teacher',
            type: 'POST',
            data: JSON.stringify(id),
            contentType: 'application/json',
            success: function (response) {
                $('#teacher-modal').html('<p>' + response.firstName + ' ' + response.lastName + '</p>' +
                    '<p>e-mail: ' + response.email + '</p>')
            }
        });
    }
});
