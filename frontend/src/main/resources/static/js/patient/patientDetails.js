document.addEventListener("DOMContentLoaded", function() {

    "use strict";

    // fields
    let updateBtn = document.getElementById("updateBtn");
    let cancelBtn = document.getElementById("cancelBtn");
    let form = document.getElementById("patientForm");
    let inputs = form.querySelectorAll("input, select");

    let isEditing = false;

    // -------------------------
    // Toggle
    // -------------------------
    updateBtn.addEventListener("click", function() {
        isEditing ? saveChanges() : enableEditing();
    });

    cancelBtn.addEventListener("click", function() {
        cancelEditing();
    })

    // -------------------------
    // functions
    // -------------------------
    function enableEditing() {
        inputs.forEach(input => input.removeAttribute("disabled"));
        updateBtn.textContent = "Save";
        updateBtn.classList.add("save");
        cancelBtn.classList.remove("d-none");
        isEditing = true;
    }

    function saveChanges() {
        if (!form.firstName.value.trim()) {
            messageBox('FAIL', 'First name is required');
            return;
        }
        if (!form.lastName.value.trim()) {
            messageBox('FAIL', 'Last name is required');
            return;
        }
        if (!form.birthDate.value.trim()) {
            messageBox('FAIL', 'Date of birth is required');
            return;
        }
        form.submit();
    }

    function cancelEditing() {
        form.reset();
        inputs.forEach(input => input.setAttribute("disabled", "true"));
        updateBtn.textContent = "Update";
        updateBtn.classList.remove("save");
        cancelBtn.classList.add("d-none");
        isEditing = false;
    }

});