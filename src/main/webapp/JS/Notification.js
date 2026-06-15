const icon = document.querySelector('.Announcement i');
const box = document.querySelector('.MegaBoxAnnouncement');

if (icon && box) {
    icon.addEventListener('click', (e) => {
        e.stopPropagation();
        icon.classList.toggle('active');
        box.classList.toggle('show');
    });
    document.addEventListener('click', () => {
        icon.classList.remove('active');
        box.classList.remove('show');
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById('filePublicKey');
    const keyPathInput = document.getElementById('publicKeyPath');
    const btnReportLostKey = document.getElementById('btnReportLostKey');

    const otpModal = document.getElementById('otpModal');
    const modalOtpInput = document.getElementById('modalOtpInput');
    const btnSubmitOtp = document.getElementById('btnSubmitOtp');
    const btnCancelOtp = document.getElementById('btnCancelOtp');

    const lostKeyConfirmModal = document.getElementById('lostKeyConfirmModal');
    const btnLostKeyConfirmYes = document.getElementById('btnLostKeyConfirmYes');
    const btnLostKeyConfirmNo = document.getElementById('btnLostKeyConfirmNo');

    // Lấy 2 vùng hiển thị thông báo màu của Form
    const jsSuccessMsg = document.getElementById('jsSuccessMsg');
    const jsErrorMsg = document.getElementById('jsErrorMsg');

    let currentAction = '';
    let originalValue = '';

    // Hàm tiện ích hiển thị thông báo động dạng msg-success / msg-error
    function showStatus(type, text) {
        // Dọn sạch trạng thái cũ
        if (jsSuccessMsg) jsSuccessMsg.style.display = 'none';
        if (jsErrorMsg) jsErrorMsg.style.display = 'none';

        if (type === 'success' && jsSuccessMsg) {
            jsSuccessMsg.innerText = text;
            jsSuccessMsg.style.display = 'block';
        } else if (type === 'error' && jsErrorMsg) {
            jsErrorMsg.innerText = text;
            jsErrorMsg.style.display = 'block';
        }
    }

    function updateKeyPathStatus(statusText) {
        if (keyPathInput) {
            keyPathInput.disabled = false;
            keyPathInput.value = statusText;
            keyPathInput.disabled = true;
        }
    }

    // --- XỬ LÝ KHI CHỌN FILE (UPLOAD KEY) ---
    if (fileInput) {
        fileInput.addEventListener('change', function (e) {
            const file = e.target.files[0];
            if (!file) return;

            showStatus('clear', ''); // Xóa thông báo cũ
            originalValue = keyPathInput.value;
            currentAction = 'UPLOAD';

            updateKeyPathStatus("Đang gửi OTP upload...");

            const formData = new FormData();
            formData.append("publicKeyFile", file);

            fetch(`${window.contextPath || ''}/UploadPublicKeyController`, {
                method: 'POST',
                body: formData
            })
                .then(res => {
                    if (!res.ok) throw new Error("HTTP error " + res.status);
                    return res.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        openOtpModal();
                    } else {
                        updateKeyPathStatus(originalValue);
                        showStatus('error', data.message || "Tệp key không đúng định dạng!");
                    }
                })
                .catch((err) => {
                    updateKeyPathStatus(originalValue);
                    showStatus('error', "Lỗi hệ thống: Không thể gửi OTP upload!");
                    console.error(err);
                });
        });
    }

    // --- XỬ LÝ KHI BẤM NÚT BÁO MẤT KHÓA BAN ĐẦU ---
    if (btnReportLostKey) {
        btnReportLostKey.addEventListener('click', function () {
            showStatus('clear', '');
            if (lostKeyConfirmModal) {
                lostKeyConfirmModal.style.display = 'flex';
            }
        });
    }

    // --- XÁC NHẬN BÁO MẤT TRÊN POPUP XÁC NHẬN ---
    if (btnLostKeyConfirmYes) {
        btnLostKeyConfirmYes.addEventListener('click', function () {
            if (lostKeyConfirmModal) lostKeyConfirmModal.style.display = 'none';

            originalValue = keyPathInput.value;
            currentAction = 'LOST';

            updateKeyPathStatus("Đang gửi OTP báo mất khóa...");

            fetch(`${window.contextPath || ''}/ReportLostKeyOTPController`, {
                method: 'POST'
            })
                .then(res => {
                    if (!res.ok) throw new Error("HTTP error " + res.status);
                    return res.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        openOtpModal();
                    } else {
                        updateKeyPathStatus(originalValue);
                        showStatus('error', data.message || "Không thể yêu cầu báo mất khóa!");
                    }
                })
                .catch((err) => {
                    updateKeyPathStatus(originalValue);
                    showStatus('error', "Lỗi kết nối: Không thể gửi OTP báo mất!");
                    console.error(err);
                });
        });
    }

    if (btnLostKeyConfirmNo) {
        btnLostKeyConfirmNo.addEventListener('click', function () {
            if (lostKeyConfirmModal) lostKeyConfirmModal.style.display = 'none';
        });
    }

    // --- SỰ KIỆN XÁC NHẬN MÃ OTP (DÙNG CHUNG POPUP) ---
    if (btnSubmitOtp) {
        btnSubmitOtp.addEventListener('click', function () {
            const otpValue = modalOtpInput.value.trim();
            if (otpValue.length !== 6) {
                closeOtpModal();
                updateKeyPathStatus(originalValue);
                showStatus('error', "Mã định danh OTP bắt buộc phải nhập đủ 6 chữ số!");
                return;
            }

            if (currentAction === 'UPLOAD') {
                fetch(`${window.contextPath || ''}/VerifyKeyOTPController?otp=${otpValue}`, { method: 'POST' })
                    .then(res => {
                        if (!res.ok) throw new Error("HTTP error " + res.status);
                        return res.json();
                    })
                    .then(result => {
                        closeOtpModal();
                        if (result.status === "verified") {
                            updateKeyPathStatus(result.maskedKey);
                            showStatus('success', "Cập nhật Public Key mới vào hệ thống thành công!");
                        } else {
                            updateKeyPathStatus(originalValue);
                            showStatus('error', result.message || "Mã OTP xác thực tải khóa lên không chính xác!");
                        }
                    })
                    .catch((err) => {
                        closeOtpModal();
                        updateKeyPathStatus(originalValue);
                        showStatus('error', "Lỗi kết nối xác thực OTP Upload!");
                        console.error(err);
                    });
            } else if (currentAction === 'LOST') {
                fetch(`${window.contextPath || ''}/VerifyLostKeyOTPController?otp=${otpValue}`, { method: 'POST' })
                    .then(res => {
                        if (!res.ok) throw new Error("HTTP error " + res.status);
                        return res.text(); // Đọc dạng text trước để tránh crash SyntaxError
                    })
                    .then(text => {
                        // Thử phân tích chuỗi text nhận được thành JSON
                        let result;
                        try {
                            result = JSON.parse(text);
                        } catch (e) {
                            throw new Error("Server trả về dữ liệu lỗi, không phải định dạng JSON! Chi tiết: " + text);
                        }

                        closeOtpModal();
                        if (result.status === "verified") {
                            updateKeyPathStatus("Chưa tải lên Public Key");
                            showStatus('success', "Hệ thống đã hủy kích hoạt và báo mất khóa thành công!");
                        } else {
                            updateKeyPathStatus(originalValue);
                            showStatus('error', result.message || "Mã OTP xác nhận báo mất khóa không chính xác!");
                        }
                    })
                    .catch((err) => {
                        closeOtpModal();
                        updateKeyPathStatus(originalValue);
                        showStatus('error', "Lỗi kết nối xác thực OTP Báo mất khóa!");
                        console.error("Chi tiết lỗi hệ thống:", err);
                    });
            }
        });
    }

    function openOtpModal() {
        if (otpModal) otpModal.style.display = 'flex';
        if (modalOtpInput) {
            modalOtpInput.value = '';
            modalOtpInput.focus();
        }
    }

    function closeOtpModal() {
        if (otpModal) otpModal.style.display = 'none';
    }

    if (btnCancelOtp) {
        btnCancelOtp.onclick = function() {
            closeOtpModal();
            updateKeyPathStatus(originalValue);
        };
    }
});