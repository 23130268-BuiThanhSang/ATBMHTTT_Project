document.addEventListener("DOMContentLoaded", () => {

    // ====== TABS ======
    const tabButtons = document.querySelectorAll(".TabButton");
    const tabContents = document.querySelectorAll(".TabContent");

    tabButtons.forEach((btn, i) => {
        btn.addEventListener("click", () => {
            tabButtons.forEach(b => b.classList.remove("active"));
            tabContents.forEach(c => c.classList.remove("active"));

            btn.classList.add("active");
            tabContents[i]?.classList.add("active");
        });
    });

    ProductQuantityHandlers();

    const firstColor = document.querySelector(".ColorThumb.active");
    if (firstColor) {
        renderSizesByColor(firstColor.dataset.color);
    }

    const addBtn = document.querySelector(".AddToCart");
    if (addBtn) {
        addBtn.addEventListener("click", addToCartHandler);
    }
});

function addToCartHandler() {

    if (!IS_LOGGED_IN) {
        alert("Vui lòng đăng nhập để thêm vào giỏ hàng");
        return;
    }

    let quantity = parseInt(document.querySelector(".quantity").innerText);
    let color = document.getElementById("selectedColorText").innerText;
    let size = selectedSize;

    let variant = VARIANTS.find(v => v.color === color && v.size === size);

    if (!variant) {
        alert("Vui lòng chọn màu và size");
        return;
    }

    // 🔥 LẤY ẢNH ĐANG HIỂN THỊ
    let mainImage = document.getElementById("mainImage")?.src;

    if (!mainImage) {
        alert("Không xác định được ảnh sản phẩm");
        return;
    }

    fetch("add-to-cart", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            variantId: variant.id,
            productId: variant.productId,
            productName: variant.productName,
            color: variant.color,
            size: variant.size,
            price: variant.price,
            selectedImage: mainImage,
            quantity: quantity
        })
    })
        .then(res => res.text())
        .then(data => {
            alert("Đã thêm vào giỏ hàng");
        })
        .catch(err => {
            console.error(err);
            alert("Lỗi khi thêm vào giỏ");
        });
}



// ====== QUANTITY ======
function ProductQuantityHandlers() {
    const minus = document.querySelector(".qty-minus");
    const plus = document.querySelector(".qty-plus");
    const quantityEl = document.querySelector(".quantity");
    const unitPriceEl = document.getElementById("unitPrice");
    const totalPriceEl = document.getElementById("totalPrice");

    if (!minus || !plus || !quantityEl || !unitPriceEl || !totalPriceEl) return;
    let quantity = parseInt(quantityEl.innerText);

    function updateTotal() {
        const unitPrice = parseFloat(unitPriceEl.dataset.price);
        totalPriceEl.innerText = (unitPrice * quantity).toLocaleString("vi-VN");
    }
    plus.onclick = () => {
        quantity++;
        quantityEl.innerText = quantity;
        updateTotal();
    };
    minus.onclick = () => {
        if (quantity > 1) {
            quantity--;
            quantityEl.innerText = quantity;
            updateTotal();
        }
    };
}

// ====== IMAGE PREVIEW ======
function changeMainImage(el) {
    document.getElementById("mainImage").src = el.src;

    document.querySelectorAll(".GalleryThumb")
        .forEach(img => img.classList.remove("active"));
    el.classList.add("active");
}


function changeColor(el) {
    const selectedColor = el.dataset.color;

    console.log("Selected:", selectedColor);

    // active màu
    document.querySelectorAll(".ColorThumb")
        .forEach(c => c.classList.remove("active"));
    el.classList.add("active");

    const thumbs = document.querySelectorAll(".GalleryThumb");
    let firstVisible = null;

    const colorText = document.getElementById("selectedColorText");
    if (colorText) {
        colorText.innerText = selectedColor;
    }

    thumbs.forEach(img => {
        if (img.dataset.color === selectedColor) {
            img.style.display = "";
            if (!firstVisible) firstVisible = img;
        } else {
            img.style.display = "none";
        }
    });

    if (!firstVisible) {
        console.warn("No image for color:", selectedColor);
        thumbs.forEach(img => img.style.display = "");
        return;
    }
    changeMainImage(firstVisible);
    renderSizesByColor(selectedColor);
}

let selectedSize = null;

function selectSize(btn) {
    document.querySelectorAll(".SizeButton")
        .forEach(b => b.classList.remove("active"));
    btn.classList.add("active");

    selectedSize = parseInt(btn.dataset.size);

    const price = parseFloat(btn.dataset.price);
    const unitPriceEl = document.getElementById("unitPrice");
    const totalPriceEl = document.getElementById("totalPrice");
    const quantity = parseInt(document.querySelector(".quantity").innerText);

    unitPriceEl.dataset.price = price;
    unitPriceEl.innerText = price.toLocaleString("vi-VN");
    totalPriceEl.innerText = (price * quantity).toLocaleString("vi-VN");
}


function renderSizesByColor(color) {
    const sizeBox = document.querySelector(".SizeOptions");
    sizeBox.innerHTML = "";

    const list = VARIANTS.filter(v => v.color === color);

    list.forEach((v, index) => {
        const btn = document.createElement("button");
        btn.className = "SizeButton";
        btn.innerText = v.sizeText;
        btn.dataset.price = v.price;
        btn.dataset.stock = v.stock;
        btn.dataset.size = v.size;

        btn.addEventListener("click", () => selectSize(btn));

        sizeBox.appendChild(btn);

        if (index === 0) btn.click();
    });
}




