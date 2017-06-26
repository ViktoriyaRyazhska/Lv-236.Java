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
                var stylesTD = " align=\"center\" style=\"width: 90px;padding-left: 3px; padding-right: 3px;\"";
                html += "<table class=\"table table-striped\" style='width: auto'>";
                html += "<thead align=\"center\">";
                html += "<tr align=\"center\">";
                html += "<th align=\"center\" style='width:85px;'> Date </th>";
                html += "<th align=\"center\" style='width: 85px'> Attendance </th>";
                html += "</tr>";
                html += "</thead>";
                $.each(response, function(i, data) {
                    html += "<tr>";
                    html += "<td " + stylesTD +">" + data.date.substring(0,10) + "</td>";
                    if (data.grade == 0) {
                        html += "<td " + stylesTD +">N/A</td>";
                    }else{
                        html += "<td " + stylesTD +">" + data.grade + "</td>";
                    }
                    html += "</tr>";
                });
                html += "</table>";
                $("#infoAttendance").append(html);
            }
        });
    })
});
