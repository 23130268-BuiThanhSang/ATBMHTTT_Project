function showPage(stepId) {
    document.querySelectorAll('.page').forEach(p => {
        p.style.display = "none";
    });

    const step = document.getElementById(stepId);
    if (step) {
        step.style.display = "block";
    }
}

window.addEventListener("load", function () {
    const step = document.body.getAttribute("data-step");
    if (step) {
        showPage(step);
    } else {
        showPage("loginPage");
    }
});

document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll("form").forEach(form => {
        form.addEventListener("submit", function () {

            this.querySelectorAll(".error, .success").forEach(e => e.innerText = "");

            const loading = this.querySelector(".loading");
            if (loading) {
                loading.style.display = "inline";
            }
        });
    });
});
