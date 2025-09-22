// Simple lightbox for image gallery
function openLightbox(src) {
    const lightbox = document.createElement('div');
    lightbox.classList.add('lightbox');
    lightbox.innerHTML = `<img src="${src}" alt="Image">`;
    lightbox.addEventListener('click', () => lightbox.remove());
    document.body.appendChild(lightbox);
    lightbox.style.display = 'flex';
}

// Usage: Call openLightbox('/path/to/image') on image click
