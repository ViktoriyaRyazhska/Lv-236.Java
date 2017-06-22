$(function() {
    var pupilId = -1;
    var pupilFormId = -1;
    var selectedDate = new Date();
    var monday = getMonday(selectedDate);
    var minYear = selectedDate.getFullYear();
    var maxYear = selectedDate.getFullYear();
    var lang = $("#locale").val();

    if (selectedDate.getMonth() < 9 - 1) {
        minYear -= 1;
    } else {
        maxYear += 1;
    }

    $.datepicker.setDefaults($.datepicker.regional[lang]);

    $("#datepicker").datepicker({
        dateFormat: "dd.mm.yy",
        minDate: new Date(minYear, 9 - 1, 1),
        maxDate: new Date(maxYear, 8 - 1, 31)
        // showButtonPanel: true
        // defaultDate: selectedDate
    });

    $("#datepicker").change(function() {
        selectedDate = $(this).datepicker("getDate");
        var newMonday = getMonday(selectedDate);

        if (monday.getTime() === newMonday.getTime()) {
            return;
        }
        monday = newMonday;
        loadSchedule();
    });

    $("#attendance-link").click(function () {
        $("#datepicker").hide();
    });

    $("#attendance-link").trigger("click");

    $("#pupil-select a").click(function () {
        var newPupilFormId = $(this).data("pupil-form-id");
        var newPupilId = $(this).data("pupil-id");

        if(pupilId == newPupilId) {
            return;
        }
        pupilId = newPupilId;
        if(pupilFormId != newPupilFormId) {
            pupilFormId = newPupilFormId;
            loadSchedule();
            loadLessons();
        } else {
            loadAttendance();
        }
    });

    $("#pupil-select a:last").trigger("click");

    $("#week-schedule-link").click(function () {
        $("#datepicker").show();
    });

    $("#lessons").change(function() {
        loadAttendance();
    });

    function getMonday(date) {
        var newDate = new Date(date);
        var day = newDate.getDay();
        var diff = newDate.getDate() - day + (day == 0 ? -6 : 1);
        return new Date(newDate.setDate(diff));
    }

    function addDays(date, days) {
        var newDate = new Date(date);
        newDate.setDate(date.getDate() + days);
        return newDate;
    }

    function loadSchedule() {
        console.log("loadSchedule()");
        var searchParams = {
            pupilFormId: pupilFormId,
            date: selectedDate
        };
        $.ajax({
            url : "/freemarker/parent-home/schedule",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(searchParams),
            success : function (response) {
                var daysInWeek = 7;

                for(var i = 1; i <= daysInWeek; i++) {
                    $('#day' + i + ' thead tr th').eq(0).text($.datepicker.formatDate('DD, dd.mm.yy', addDays(monday, i - 1)));
                }

                $(".for-clear").empty();
                $.each(response, function(i, schedule) {
                    var dayOfWeek = new Date(schedule.date).getDay();
                    var selector = $('#day' + dayOfWeek + ' tbody tr').eq(schedule.lessonPosition);

                    selector.prop("title", schedule.homework);
                    selector.find("td").eq(1).text(schedule.lessonName);
                    selector.find("td").eq(2).text(schedule.classroomName);
                    selector.find("td").eq(3).text(schedule.teacherFirstName + " " + schedule.teacherLastName);
                });
            }
        });
    }

    function loadLessons() {
        console.log("loadLessons()");
        var searchParams = {
            pupilFormId: pupilFormId
        };
        $.ajax({
            url : "/freemarker/parent-home/lessons",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(searchParams),
            success : function (response) {
                $("#lessons").empty();
                $.each(response, function(i, lesson) {
                    $("#lessons").append($("<option></option>").attr("value", lesson.id).text(lesson.name));
                });
                loadAttendance();
            }
        });
    }

    function loadAttendance() {
        console.log("loadAttendance()");
        var searchParams = {
            pupilId: pupilId,
            lessonId: $("#lessons").val()
        };
        $.ajax({
            url : "/freemarker/parent-home/attendance",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(searchParams),
            success : function (response) {
                var avgGradeSum = 0;
                var avgGradeCount = 0;

                $('#attendanceTable tbody').find('tr:not(:last)').remove();

                $.each(response, function(i, attendance) {
                    if(attendance.grade > 0) {
                        avgGradeSum += attendance.grade;
                        avgGradeCount += 1;
                    }
                    $('#attendanceTable tbody').prepend('<tr><td>' + $.datepicker.formatDate('DD, dd.mm.yy', new Date(attendance.date)) + '</td><td>' + attendance.grade + '</td></tr>');
                });

                if(response.length == 0) {
                    $("#attendanceEmpty span:first").text($("#pupil-select li a.active").text());
                    $("#attendanceEmpty span:last").text($("#lessons option:selected").text());
                    $("#attendanceTable").hide();
                    $("#attendanceEmpty").show();
                } else {
                    $('#avg-grade').text(Number((avgGradeSum/avgGradeCount).toFixed(2)));
                    $("#attendanceEmpty").hide();
                    $("#attendanceTable").show();
                }
            }
        });
    }

});
