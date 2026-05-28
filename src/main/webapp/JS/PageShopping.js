function toggleAllProducts() {
    const selectAll = document.getElementById('selectAllCheckbox');
    const itemCheckboxes = document.querySelectorAll('.item-checkbox');

    itemCheckboxes.forEach(cb => cb.checked = selectAll.checked);

    updateOrderSummary();
}

function CartQuantityHandlers() {
    document.querySelectorAll('.ItemProductInshoppingCart').forEach(function (item) {

        const buttons = item.querySelectorAll('.QuantityBoxForProduct button');
        if (buttons.length < 2) return;

        const minusBtn = buttons[0];
        const plusBtn = buttons[1];
        const quantitySpan = item.querySelector('.QuantityBoxForProduct .quantity');
        const deleteBtn = item.querySelector('.QuantityBoxForProduct .delProduct');

        // ===== NÚT + =====
        plusBtn.onclick = function () {
            let quantity = parseInt(quantitySpan.textContent);
            quantitySpan.textContent = quantity + 1;
            updateOrderSummary();
        };

        // ===== NÚT - =====
        minusBtn.onclick = async function () {
            let quantity = parseInt(quantitySpan.textContent);

            if (quantity > 1) {
                quantitySpan.textContent = quantity - 1;
                updateOrderSummary();
            } else {
                await confirmAndRemoveItem(item);
                updateOrderSummary();
            }
        };

        // ===== NÚT XÓA =====
        if (deleteBtn) {
            deleteBtn.onclick = async function (e) {
                e.preventDefault(); // ⭐ CHẶN LINK
                await confirmAndRemoveItem(item);
            };
        }
    });
}

function showConfirmModal(title, message, confirmText = 'Xóa', cancelText = 'Hủy') {
    return new Promise(function (resolve) {
        const modal = document.getElementById('deleteProductModal');
        const modalTitle = document.getElementById('modalTitle');
        const modalMessage = document.getElementById('modalMessage');
        const okBtn = document.getElementById('modalDeleteConfirm');
        const cancelBtn = document.getElementById('modalDeleteCancel');

        modalTitle.textContent = title;
        modalMessage.textContent = message;
        okBtn.textContent = confirmText;
        cancelBtn.textContent = cancelText;

        modal.style.display = 'flex';

        // Hủy sự kiện cũ trước khi gắn mới!
        okBtn.onclick = function () {
            modal.style.display = 'none';
            resolve(true);
        }
        cancelBtn.onclick = function () {
            modal.style.display = 'none';
            resolve(false);
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    CartQuantityHandlers();
    checkEmptyCart();
    const btn = document.getElementById('deleteCartBtn');
    if (btn) {
        btn.addEventListener('click', async function () {
            const ok = await showConfirmModal(
                'Xác nhận xóa giỏ hàng?',
                'Bạn có chắc muốn xóa tất cả sản phẩm trong giỏ hàng không?',
                'Xóa hết',
                'Hủy'
            );
            if (ok) {
                const products = document.querySelector('.ListProductInnerScroll');
                await fetch("delete-cart", { method: "POST" });
                window.location.reload();


                updateOrderSummary();
                checkEmptyCart();
            }
        });
    }
    // Nút Đặt hàng -> xóa các item đã được tích rồi hiện modal
    const placeBtn = document.getElementById('placeOrderBtn');




    if (placeBtn) {
        placeBtn.addEventListener('click', async function (e) {
            e.preventDefault();

            const checkedBoxes = document.querySelectorAll('.item-checkbox:checked');

            if (checkedBoxes.length === 0) {
                showConfirmModal(
                    "Chưa chọn sản phẩm",
                    "Vui lòng chọn ít nhất một sản phẩm để đặt hàng",
                    "OK",
                    "Đóng"
                );
                return;
            }

            // 2) Xóa từng item tương ứng
            checkedBoxes.forEach(cb => {
                // tìm wrapper .ItemProductInshoppingCart gần nhất và remove
                const item = cb.closest('.ItemProductInshoppingCart');
                if (item) item.remove();
            });
            checkEmptyCart();

            // 4) Hiện modal đặt hàng hoàn tất
            const res = await showOrderCompleteModal();
            if (res === 'view') {
                window.location.href = historyUrl;
            }

        });
    }
});

function showOrderCompleteModal(title, message, viewText, backText) {
    return new Promise(function (resolve) {
        const modal = document.getElementById('orderCompleteModal');
        const modalTitle = document.getElementById('orderModalTitle');
        const modalMessage = document.getElementById('orderModalMessage');
        const viewBtn = document.getElementById('orderViewBtn');
        const backBtn = document.getElementById('orderBackBtn');

        if (typeof title !== 'undefined') modalTitle.textContent = title;
        if (typeof message !== 'undefined') modalMessage.textContent = message;
        if (typeof viewText !== 'undefined') viewBtn.textContent = viewText;
        if (typeof backText !== 'undefined') backBtn.textContent = backText;

        modal.style.display = 'flex';

        viewBtn.onclick = function () { modal.style.display = 'none'; resolve('view'); };
        backBtn.onclick = function () { modal.style.display = 'none'; resolve('back'); };
    });
}

function checkEmptyCart() {
    const items = document.querySelectorAll(".ItemProductInshoppingCart");
    const emptyMsg = document.getElementById("emptyCartMessage");
    const orderBox = document.querySelector(".OrderSumary");
    const placeBtn = document.getElementById("placeOrderBtn");

    if (items.length === 0) {
        if (emptyMsg) emptyMsg.style.display = "block";
        if (orderBox) orderBox.style.opacity = "0.5";
        if (placeBtn) placeBtn.disabled = true;
    } else {
        if (emptyMsg) emptyMsg.style.display = "none";
        if (orderBox) orderBox.style.opacity = "1";
        if (placeBtn) placeBtn.disabled = false;
    }
}


async function confirmAndRemoveItem(item) {
    const ok = await showConfirmModal(
        "Xác nhận xóa sản phẩm?",
        "Bạn có chắc muốn xóa sản phẩm này không?",
        "Xóa",
        "Hủy"
    );

    if (!ok) return;

    const delBtn = item.querySelector('.delProduct');
    if (delBtn && delBtn.href) {
        fetch(delBtn.href).finally(() => {
            item.remove();
            checkEmptyCart();
        });
    }
}

function updateOrderSummary() {
    let totalQty = 0;
    let totalPrice = 0;

    document.querySelectorAll('.item-checkbox:checked').forEach(cb => {
        const item = cb.closest('.ItemProductInshoppingCart');
        const price = parseFloat(item.dataset.price);
        const qty = parseInt(item.querySelector('.quantity').textContent);

        totalQty += qty;
        totalPrice += price * qty;
    });

    document.getElementById('selectedCount').textContent =
        `Đã chọn: ${totalQty} sản phẩm`;

    document.getElementById('totalPrice').textContent =
        totalPrice.toLocaleString('vi-VN') + ' đ';
}


document.addEventListener('change', function (e) {
    if (e.target.classList.contains('item-checkbox')) {
        updateOrderSummary();
    }
});

document.addEventListener("DOMContentLoaded", function () {
    updateOrderSummary();
});



