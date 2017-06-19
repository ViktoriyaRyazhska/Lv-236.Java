$(function () {
    $("#lessons").change(function () {
        var lessonDTO = {
            id : $("#lessons").val()
        };
        $.ajax({
            url : "att",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(lessonDTO),
            success : function (response) {
                $("#infoAttendance").empty();
                var html = "";
                var stylesTD = "style=\"width: 90px;padding-left: 3px; padding-right: 3px;\"";
                html += "<table class=\"table table-bordered\" style='width: auto'>";
                html += "<tr align=\"center\">";
                html += "<th align=\"center\" style='width:85px;'> Date </th>";
                $.each(response, function(i, date) {
                    html += "<td " + stylesTD +">" + date.date.substring(0,10) + "</td>";
                });
                html += "</tr>";
                html += "<tr align=\"center\">";
                html += "<th align=\"center\" style='width: 85px'> Attendance </th>";
                $.each(response, function(j, attendance) {
                    if (attendance.grade == 0) {
                        html += "<td " + stylesTD +">N/A</td>";
                    }else{
                        html += "<td " + stylesTD +">" + attendance.grade + "</td>";
                    }
                });
                html += "</tr>";
                html += "</table>";
                $("#infoAttendance").append(html);
            }
        });
    })
});
