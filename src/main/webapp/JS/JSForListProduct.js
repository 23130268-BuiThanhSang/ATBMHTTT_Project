/**
 * thực hiện cho phép auto submit form lọc sản phẩm
 */
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('filterForm');

    // 🔁 SYNC dataset.checked với trạng thái checked từ server
    document.querySelectorAll('#filterForm input[type=radio]').forEach(radio => {
        radio.dataset.checked = radio.checked ? 'true' : 'false';
    });
    /**
     * RADIO: cho phép hủy chọn (uncheck) khi click lại vào radio đã chọn
     */
    document.querySelectorAll('#filterForm input[type=radio]').forEach(radio => {
        radio.addEventListener('click', function (e) {
            if (this.dataset.checked === 'true') {
                e.preventDefault();
                this.checked = false;
                this.dataset.checked = 'false';
                form.submit();
            } else {
                document
                    .querySelectorAll(`input[name="${this.name}"]`)
                    .forEach(r => r.dataset.checked = 'false');

                this.checked = true;
                this.dataset.checked = 'true';
                form.submit();
            }
        });
    });

});
/**
 * thực hiện auto submit search form khi mất focus hoặc nhấn Enter
 */
document.addEventListener('DOMContentLoaded', function () {
    const searchForm = document.getElementById('searchForm');
    const searchInput = searchForm?.querySelector('input[name="keyword"]');

    if (!searchForm || !searchInput) return;

    // Mất focus → submit searchForm
    searchInput.addEventListener('blur', function () {
        searchForm.submit();
    });

    // Nhấn Enter → submit searchForm
    searchInput.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            searchForm.submit();
        }
    });
});

