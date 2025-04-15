document.addEventListener("DOMContentLoaded", () => {
  const albumBoxes = document.querySelectorAll('.album-box');

  // Function to check if an element is in the viewport
  function isInViewport(element) {
      const rect = element.getBoundingClientRect();
      console.log(rect.top); // Debugging: Check if the element's top is in the viewport
      return (
          rect.top >= 0 &&
          rect.left >= 0 &&
          rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
          rect.right <= (window.innerWidth || document.documentElement.clientWidth)
      );
  }

  // Add the 'show' class to album boxes as they come into the viewport
  function handleScroll() {
      console.log('Scroll event fired'); // Debugging: Ensure scroll event is firing
      albumBoxes.forEach((box) => {
          if (isInViewport(box)) {
              console.log(`Showing box: ${box}`); // Debugging: Check if the box is visible
              box.classList.add('show');
          }
      });
  }

  // Listen for the scroll event
  window.addEventListener('scroll', handleScroll);

  // Call handleScroll to check on initial load (in case the page is already scrolled)
  handleScroll();
});
