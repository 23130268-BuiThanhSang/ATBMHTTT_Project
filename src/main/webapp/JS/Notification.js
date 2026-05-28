const icon = document.querySelector('.Announcement i');
const box = document.querySelector('.MegaBoxAnnouncement');


icon.addEventListener('click', (e) => {
    e.stopPropagation();

    icon.classList.toggle('active');
    box.classList.toggle('show');
});


document.addEventListener('click', () => {
    icon.classList.remove('active');
    box.classList.remove('show');
});




