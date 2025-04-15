document.addEventListener("DOMContentLoaded", () => {
    const albumBoxes = document.querySelectorAll('.album-box');

    // Function to check if an element is in the viewport
    function isInViewport(element) {
        const rect = element.getBoundingClientRect();
        return (
            rect.top >= 0 &&
            rect.left >= 0 &&
            rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.right <= (window.innerWidth || document.documentElement.clientWidth)
        );
    }

    // Add the 'show' class to album boxes as they come into the viewport and remove it when they leave
    function handleScroll() {
        albumBoxes.forEach((box) => {
            if (isInViewport(box)) {
                box.classList.add('show');
                box.classList.remove('hide'); // Remove hide class when element is in viewport
            } else {
                box.classList.add('hide');
                box.classList.remove('show'); // Remove show class when element is out of view
            }
        });
    }

    // Listen for the scroll event
    window.addEventListener('scroll', handleScroll);

    // Call handleScroll to check on initial load (in case the page is already scrolled)
    handleScroll();
});
