document.addEventListener("DOMContentLoaded", function () {
    const variantContainer = document.getElementById("VariantContainer");
    const btnAddVariant = document.getElementById("btnAddVariant");
    const productTypeSelect = document.querySelector("select[name='category']");

    // --- 1. SIZE LOGIC CONFIGURATION ---
    const SIZE_OPTIONS_BY_TYPE = {
        4: [35, 36, 37, 38, 39, 40, 41, 42, 43], // Shoes
        3: ["S", "M", "L", "XL", "XXL"],         // Clothes
        2: ["Free Size"],                        // Accessories
        1: ["Nhỏ", "Vừa", "Lớn"]                 // Gym Tools
    };

    // --- 2. INITIALIZATION ---
    // Count current variants to ensure unique names for new ones
    let variantIndex = document.querySelectorAll('.VariantCard').length;

    // Listener for Add Button
    if (btnAddVariant) {
        btnAddVariant.addEventListener("click", function () {
            addNewVariantCard();
        });
    }

    // Listener for Existing Image Inputs
    document.querySelectorAll('.ImageInput').forEach(input => {
        setupImagePreview(input);
    });

    // Listener for Category Change (Updates all size fields dynamically)
    if (productTypeSelect) {
        productTypeSelect.addEventListener("change", function () {
            if (!variantContainer) return;

            variantContainer.querySelectorAll(".VariantCard").forEach((card, i) => {

                const sizeFieldContainer = card.querySelector(".VariantRow-Top .VariantField:nth-child(2)");
                const existingInput = sizeFieldContainer.querySelector("input, select");
                let currentIndex = i; // Fallback

                if (existingInput) {
                    const match = existingInput.name.match(/variants\[(\d+)\]/);
                    if (match) currentIndex = match[1];
                }

                // Re-render the HTML
                sizeFieldContainer.innerHTML = `
                    <label>Kích cỡ:</label>
                    ${buildSizeField(currentIndex)}
                `;
            });
        });
    }

    // --- 3. HELPER FUNCTIONS ---

    function getSizeOptions() {
        if (!productTypeSelect) return null;
        const type = productTypeSelect.value;
        return SIZE_OPTIONS_BY_TYPE[type];
    }

    function buildSizeField(index, selectedValue = "") {
        const sizes = getSizeOptions();

        // Case A: No specific sizes defined (Input Text)
        if (sizes == null) {
            return `
            <input type="text" 
                   name="variants[${index}].size" 
                   placeholder="Nhập size (VD: 40, XL)" 
                   value="${selectedValue}"
                   required>
            `;
        }

        // Case B: Dropdown Select
        let html = `
        <select name="variants[${index}].size" required>
            <option value="">-- Chọn size --</option>
        `;

        sizes.forEach(size => {
            const selected = (String(size) === String(selectedValue)) ? "selected" : "";
            html += `<option value="${size}" ${selected}>${size}</option>`;
        });

        html += `</select>`;
        return html;
    }

    // --- 4. ADD NEW VARIANT FUNCTION ---
    function addNewVariantCard() {
        if (!variantContainer) return;
        const index = variantIndex++;

        const card = document.createElement("div");
        card.className = "VariantCard NewVariant";

        card.innerHTML = `
            <div class="VariantHeader">
                <span>Biến thể Mới #${index + 1}</span>
                <button type="button" class="RemoveVariantBtn">✕</button>
            </div>
            
            <div class="VariantRow VariantRow-Top">
                <div class="VariantField">
                    <label>Màu sắc:</label>
                    <select name="variants[${index}].color" required>
                        <option value="">-- Chọn màu --</option>
                        ${["Tím", "Nâu", "Đen", "Trắng", "Bạc", "Hồng", "Xanh Navy", "Xanh Dương", "Xanh Lục", "Vàng", "Đỏ", "Cam"]
            .map(c => `<option value="${c}">${c}</option>`).join("")}
                    </select>
                </div>
                
                <div class="VariantField">
                    <label>Kích cỡ:</label>
                    ${buildSizeField(index)}
                </div>

                <div class="VariantField">
                    <label>Giá:</label>
                    <input type="number" name="variants[${index}].price" min="0" required>
                </div>
                
                <div class="VariantField">
                    <label>Tồn kho:</label>
                    <input type="number" name="variants[${index}].stock" min="0" required>
                </div>
            </div>
            
            <div class="VariantRow VariantRow-Bottom">
                <div class="VariantField" style="width: 100%;">
                    <label>Hình ảnh (Giữ Ctrl để chọn nhiều):</label>
                    <input type="file" class="ImageInput" name="variants[${index}].images" multiple accept="image/*">
                    <div class="ImagePreviewBox" style="display: flex; gap: 10px; margin-top: 10px; flex-wrap: wrap;"></div>
                </div>
            </div>
        `;

        // Remove Button Logic
        card.querySelector(".RemoveVariantBtn").addEventListener("click", function () {
            card.remove();
        });

        // Setup Preview
        setupImagePreview(card.querySelector(".ImageInput"));

        variantContainer.appendChild(card);
    }

    // --- 5. IMAGE PREVIEW HELPER ---
    function setupImagePreview(inputElement) {
        if (!inputElement) return;
        inputElement.addEventListener("change", function(e) {
            const previewBox = e.target.parentElement.querySelector(".ImagePreviewBox");
            if(!previewBox) return;

            previewBox.innerHTML = "";
            const files = e.target.files;

            if (files) {
                Array.from(files).forEach(file => {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const img = document.createElement("img");
                        img.src = e.target.result;
                        img.style.width = "60px";
                        img.style.height = "60px";
                        img.style.objectFit = "cover";
                        img.style.borderRadius = "5px";
                        img.style.border = "1px solid #ccc";
                        previewBox.appendChild(img);
                    }
                    reader.readAsDataURL(file);
                });
            }
        });
    }
});

function markImageDelete(imageId, btnElement) {
    if(!confirm("Bạn có chắc muốn xóa ảnh này?")) return;
    const input = document.getElementById("deletedImageIds");
    if(input.value) {
        input.value += "," + imageId;
    } else {
        input.value = imageId;
    }
    const container = btnElement.parentElement;
    container.style.display = "none";
}