$(function() {

    $('[data-toggle="tooltip"]').tooltip();

    $("#sizeSelector").change(function () {
        $("#sizeChangerForm").submit();
    });

    var selectedTd = null;
    var backupValue = null;

    function hideDivShowInput() {
        console.log("hideDivShowInput()");
        selectedTd.find("div").hide();
        selectedTd.find("input").prop("type", "text");
        selectedTd.find("input").focus();
    }

    function hideInputShowDiv() {
        console.log("hideInputShowDiv()");
        selectedTd.find("div").show();
        selectedTd.find("input").prop("type", "hidden");
        selectedTd = null;
        backupValue = null;
    }

    function confirmDiscardChanges() {
        if (selectedTd.find("input").val() != backupValue) {
            if (confirm('Are you sure you want to discard changes?')) {
                selectedTd.find("input").val(backupValue);
                hideInputShowDiv();
            } else {
                return true;
            }
        } else {
            hideInputShowDiv();
        }
        return false;
    }

    $(".attendance").click(function () {
        if ($(this).is(selectedTd)) {
            return;
        }
        if (selectedTd != null) {
            if(confirmDiscardChanges()) {
                return;
            }
        }
        selectedTd = $(this);
        backupValue = $(this).find("input").val();
        hideDivShowInput();
    });

    $("input").bind("enterKey", function(e) {
        var string_value = $(this).val();
        var int_value = parseInt($(this).val(), 10);
        console.log("int_value: " + int_value);

        if (string_value) {
            if (int_value < 0 || int_value > 12 || isNaN(int_value)) {
                alert("Invalid grade!");
                return;
            }
        }

        var id = selectedTd.data("attendance-id");
        var grade = int_value;
        var enabled = true;
        var pupilId = selectedTd.data("pupil-id");
        var scheduleId = selectedTd.data("schedule-id");
        var attendanceDTO;

        if (id == -1) {
            attendanceDTO = {
                grade: grade,
                enabled: enabled,
                pupilId: pupilId,
                scheduleId: scheduleId
            };
        } else {
            attendanceDTO = {
                id : id,
                grade: grade,
                enabled: enabled,
                pupilId: pupilId,
                scheduleId: scheduleId
            };
        }
        $.ajax({
            url : "/freemarker/teacher-gradebook/update",
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(attendanceDTO),
            success : function (response) {
                selectedTd.data("attendance-id", response.id);
                selectedTd.find("input").attr("value", (!string_value ? "" : response.grade));
                selectedTd.find("div").text((!string_value ? "-" : response.grade));
                hideInputShowDiv();
            },
            error: function() {
                // add error handling
            }
        });
    });

    $("input").keyup(function(e){
        if (e.keyCode === 13) {
            $(this).trigger("enterKey");
        }
    });

    $(document).keyup(function(e){
        if (e.keyCode === 27) {
            selectedTd.find("input").val(backupValue);
            hideInputShowDiv();
        }
    });

});
