// Initialize the scene
const scene = new THREE.Scene();

// Set up the camera
const camera = new THREE.PerspectiveCamera(
  75, 
  window.innerWidth / window.innerHeight, 
  0.1, 
  1000
);
camera.position.z = 5;

// Create the renderer
const renderer = new THREE.WebGLRenderer({ canvas: document.getElementById('three-canvas'), antialias: true });
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.setPixelRatio(window.devicePixelRatio);

// Handle window resize
window.addEventListener('resize', () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});

// Function to create an album cover plane
function createAlbumCover(textureURL, position) {
  const geometry = new THREE.PlaneGeometry(1, 1);
  const texture = new THREE.TextureLoader().load(textureURL);
  const material = new THREE.MeshBasicMaterial({ map: texture });
  const plane = new THREE.Mesh(geometry, material);
  plane.position.set(position.x, position.y, position.z);
  scene.add(plane);

  // Add hover animation using GSAP
  plane.userData = { hovered: false };
  plane.onHover = () => {
    if (!plane.userData.hovered) {
      plane.userData.hovered = true;
      gsap.to(plane.scale, { x: 1.2, y: 1.2, duration: 0.3 });
    }
  };
  plane.onHoverOut = () => {
    if (plane.userData.hovered) {
      plane.userData.hovered = false;
      gsap.to(plane.scale, { x: 1, y: 1, duration: 0.3 });
    }
  };

  return plane;
}

// Create a grid of album covers
const albumCovers = [];
const gridSize = 3;
const spacing = 1.5;
for (let i = -gridSize; i <= gridSize; i++) {
  for (let j = -gridSize; j <= gridSize; j++) {
    const textureURL = 'https://via.placeholder.com/256'; // Replace with your album cover URLs
    const position = { x: i * spacing, y: j * spacing, z: 0 };
    const album = createAlbumCover(textureURL, position);
    albumCovers.push(album);
  }
}

// Raycaster for detecting mouse interactions
const raycaster = new THREE.Raycaster();
const mouse = new THREE.Vector2();

// Track mouse movement
window.addEventListener('mousemove', (event) => {
  // Convert mouse position to normalized device coordinates (-1 to +1)
  mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
  mouse.y = - (event.clientY / window.innerHeight) * 2 + 1;

  // Update the picking ray with the camera and mouse position
  raycaster.setFromCamera(mouse, camera);

  // Calculate objects intersecting the picking ray
  const intersects = raycaster.intersectObjects(albumCovers);

  // Reset all album covers
  albumCovers.forEach(cover => cover.onHoverOut());

  // Highlight the first intersected album cover
  if (intersects.length > 0) {
    intersects[0].object.onHover();
  }
});

// Animation loop
function animate() {
  requestAnimationFrame(animate);
  renderer.render(scene, camera);
}
animate();
