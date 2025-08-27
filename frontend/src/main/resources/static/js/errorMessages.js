document.addEventListener("DOMContentLoaded", function() {

    "use strict";

    let alertBox = document.getElementById("alertBox");

    function messageBox(type, message) {
        alertBox.removeAttribute('hidden');
        alertBox.textContent = message;
        alertBox.classList.remove('alert-success', 'alert-danger');
        alertBox.classList.add(
            type === 'SUCCESS' ? 'alert-success' : 'alert-danger');
    }

    window.messageBox = messageBox;

});