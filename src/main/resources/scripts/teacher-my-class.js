
var editedPupil;

function editPupilAjax(id){
    $.ajax({
        url : "teacher-my-class-edit",
        type : "POST",
        contentType : "application/json",
        data : JSON.stringify(id),
        success : function (response) {
            document.getElementById('emailEdit').value = response.email;
            document.getElementById('firstNameEdit').value = response.firstName;
            document.getElementById('lastNameEdit').value = response.lastName;
            document.getElementById('currentForm').innerHTML = response.formName;
            var editModal = document.getElementById('editPopup');
            editModal.style.display = "block";
            editedPupil = response;
        }
    });
}

function showFormAssignModal(){
    //get available forms in school
    $.ajax({
        url : "teacher-my-class-get-av-forms",
        type : "GET",
        contentType : "application/json",
        success : function (response) {
            var formAssign = document.getElementById('forms');
            var length = formAssign.options.length;
            for (i = 0; i < length; i++) {
                formAssign.options[i] = null;
            }
            $.each(response, function() {
                var opt = document.createElement('option');
                opt.value = this.id;
                opt.innerHTML = this.name;
                formAssign.appendChild(opt);
            });
            var formAssignModal = document.getElementById('formAssign');
            formAssignModal.style.display = "block";
        }
    });
}


function hideEditModal() {
    var editModal = document.getElementById('editPopup');
    editModal.style.display = "none";
}
function savePupil(){
    editedPupil.email = document.getElementById('emailEdit').value;
    editedPupil.firstName = document.getElementById('firstNameEdit').value;
    editedPupil.lastName = document.getElementById('lastNameEdit').value;
    $.ajax({
        url : "teacher-my-class-savePupil",
        type : "POST",
        contentType : "application/json",
        data : JSON.stringify(editedPupil),
        success : function (response) {
            if(response == "Success"){
                window.location.href='/freemarker/teacher-my-class';
            }
        }
    });
}

function hideFormAssignModal(){
    var formAssignModal = document.getElementById('formAssign');
    formAssignModal.style.display = "none";
}

function assignPupilToForm(){
    var formAssignSelect = document.getElementById('forms');
    var selected = formAssignSelect.options[ formAssignSelect.selectedIndex ].value;
    console.log(selected );
    if (!selected || selected == '') {
        return;
    }
    editedPupil.formId = selected;
    hideFormAssignModal();
}
