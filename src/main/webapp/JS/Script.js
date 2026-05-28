const slides = document.getElementById("BannerSlides");
const total = slides.children.length;
let index = 0;
const bannerWidth = 1600;

let autoSlide = setInterval(nextSlide, 5000);

function showSlide() {
    slides.style.transform = `translateX(-${index * bannerWidth}px)`;
}
function nextSlide() {
    index = (index + 1) % total;
    showSlide();
}

function prevSlide() {
    index = (index - 1 + total) % total;
    showSlide();
}


document.getElementById("nextBtn").addEventListener("click", () => {
    nextSlide();
    resetTimer();
});

document.getElementById("prevBtn").addEventListener("click", () => {
    prevSlide();
    resetTimer();
});

// Reset auto-slide timer when manually clicked
function resetTimer() {
    clearInterval(autoSlide);
    autoSlide = setInterval(nextSlide, 5000);
}

// // display none for product card overFlow body of bodyListProductForFilter
// document.addEventListener("DOMContentLoaded", function() {
//     const loadMoreBtn = document.querySelector(".LoadMoreBtn");
//     const productContainer = document.querySelector(".bodyListProductForFilter");
//     const products = productContainer.querySelectorAll(".ProductCard");
//     let visibleCount = 6; // số sản phẩm hiển thị ban đầu
//     const increment = 6;   // số sản phẩm hiển thị thêm mỗi lần click
//
//     // ẩn tất cả sản phẩm vượt quá visibleCount
//     function updateProducts() {
//         products.forEach((product, index) => {
//             if(index < visibleCount) {
//                 product.style.display = "block"; // hiện sản phẩm
//             } else {
//                 product.style.display = "none";  // ẩn sản phẩm
//             }
//         });
//
//         // ẩn nút nếu tất cả sản phẩm đã hiển thị
//         if(visibleCount >= products.length) {
//             loadMoreBtn.style.display = "none";
//         }
//     }
//
//     // click nút xem thêm
//     loadMoreBtn.addEventListener("click", () => {
//         visibleCount += increment;
//         updateProducts();
//     });
//
//     updateProducts(); // khởi tạo hiển thị
// });
