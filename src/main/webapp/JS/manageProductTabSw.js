document.addEventListener("DOMContentLoaded", function () {

    // --- VARIANT LOGIC ---
    const variantContainer = document.getElementById("VariantContainer");
    const btnAddVariant = document.getElementById("btnAddVariant");
    const productTypeSelect = document.querySelector("select[name='category']");

    let variantIndex = 0;

    // Define size options for different categories
    const SIZE_OPTIONS_BY_TYPE = {
        4: [35, 36, 37, 38, 39, 40, 41, 42, 43], // Shoes
        3: ["S", "M", "L", "XL", "XXL"],         // Clothes
        2: ["Free Size"],                        // Accessories
        1: ["Nhỏ", "Vừa", "Lớn"]                 // Gym Tools
    };

    // Initialize listeners
    if (btnAddVariant) {
        btnAddVariant.addEventListener("click", function () {
            addVariantCard();
        });
    }

    // When product category changes, update existing variant dropdowns
    if (productTypeSelect) {
        productTypeSelect.addEventListener("change", function () {
            if (!variantContainer) return;
            variantContainer.querySelectorAll(".VariantCard").forEach((card, i) => {
                const sizeField = card.querySelector(".VariantField:nth-child(2)");
                // Re-render the size field based on new category
                sizeField.innerHTML = `
                    <label>Kích cỡ:</label>
                    ${buildSizeField(i)}
                `;
            });
        });
    }

    // --- HELPER FUNCTIONS ---

    function getSizeOptions() {
        if (!productTypeSelect) return null;
        const type = productTypeSelect.value;
        return SIZE_OPTIONS_BY_TYPE[type];
    }

    function buildSizeField(index, selectedValue = "") {
        const sizes = getSizeOptions();

        // If no predefined sizes (or unknown type), return text input
        if (sizes == null) {
            return `
            <input type="text"
                   name="variants[${index}].size"
                   placeholder="Nhập kích thước"
                   value="${selectedValue || ""}"
                   required>
            `;
        }

        // Otherwise return select dropdown
        let html = `
        <select name="variants[${index}].size" required>
            <option value="">-- Chọn size --</option>
        `;

        sizes.forEach(size => {
            const selected = size == selectedValue ? "selected" : "";
            html += `<option value="${size}" ${selected}>${size}</option>`;
        });

        html += `</select>`;
        return html;
    }

    function getLastVariantData() {
        if (!variantContainer) return null;
        const cards = variantContainer.querySelectorAll(".VariantCard");
        if (cards.length === 0) return null;

        const last = cards[cards.length - 1];

        // Copy data from the last card to make entry easier
        return {
            color: last.querySelector("select[name*='.color']")?.value || "",
            size: last.querySelector("select[name*='.size']")?.value || "",
            price: last.querySelector("input[name*='.price']")?.value || "",
            stock: last.querySelector("input[name*='.stock']")?.value || ""
        };
    }

    function addVariantCard() {
        if (!variantContainer) return;

        const index = variantIndex++;
        const lastData = getLastVariantData();

        const card = document.createElement("div");
        card.className = "VariantCard";

        card.innerHTML = `
            <div class="VariantHeader">
                <span>Biến thể #${index + 1}</span>
                <button type="button" class="RemoveVariantBtn">✕</button>
            </div>
        
            <div class="VariantRow VariantRow-Top">
        
                <div class="VariantField">
                    <label>Màu sắc:</label>
                    <select name="variants[${index}].color" required>
                        <option value="">-- Chọn màu --</option>
                        ${["Tím", "Nâu", "Đen", "Trắng", "Bạc", "Hồng", "Xanh Navy", "Xanh Dương", "Xanh Lục", "Vàng", "Đỏ", "Cam"]
            .map(c => `<option value="${c}" ${lastData?.color === c ? "selected" : ""}>${c}</option>`)
            .join("")}
                    </select>
                </div>
        
                <div class="VariantField">
                    <label>Kích cỡ:</label>
                    ${buildSizeField(index, lastData?.size)}
                </div>

                <div class="VariantField">
                    <label>Giá:</label>
                    <input type="number"
                           name="variants[${index}].price"
                           min="0"
                           value="${lastData?.price || ""}"
                           required>
                </div>
        
                <div class="VariantField">
                    <label>Tồn kho:</label>
                    <input type="number"
                           name="variants[${index}].stock"
                           min="0"
                           value="${lastData?.stock || ""}"
                           required>
                </div>
        
            </div>
        
            <div class="VariantRow VariantRow-Bottom">
                <div class="VariantField">
                    <label>Hình ảnh:</label>
                    <input type="file"
                           name="variants[${index}].images"
                           multiple
                           accept="image/*">
                </div>
            </div>
        `;

        // Add delete functionality to the X button
        card.querySelector(".RemoveVariantBtn").addEventListener("click", function () {
            card.remove();
        });

        variantContainer.appendChild(card);
    }
});