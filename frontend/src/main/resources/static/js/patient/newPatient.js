document.addEventListener("DOMContentLoaded", function() {

    "use strict";

    let createBtn = document.getElementById("createBtn");
    let createForm = document.getElementById("createPatientForm");

    createBtn.addEventListener("click", function(e) {
        e.preventDefault();
        if (!createForm.firstName.value.trim()) {
            messageBox('FAIL', 'First name is required');
            return;
        }
        if (!createForm.lastName.value.trim()) {
            messageBox('FAIL', 'Last name is required');
            return;
        }
        if (!createForm.birthDate.value.trim()) {
            messageBox('FAIL', 'Date of birth is required');
            return;
        }
        createForm.submit();
    });

});