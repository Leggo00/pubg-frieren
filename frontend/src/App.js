import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [zoom, setZoom] = useState(1);
  const [dragging, setDragging] = useState(false);
  const [dragStart, setDragStart] = useState({ x: 0, y: 0 });
  const [scrollStart, setScrollStart] = useState({ x: 0, y: 0 });

  const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedImage, setSelectedImage] = useState(null);


  useEffect(() => {
    fetch('http://localhost:8080/api/images/active')
        .then(response => response.json())
        .then(data => {
          setImages(data);
          setLoading(false);
        })
        .catch(error => {
          console.error('Error:', error);
          setLoading(false);
        });
  }, []);

  if (loading) return <div className="loading">로딩 중...</div>;

  return (
      <div className="App">
        <h1>PUBG 맵 이미지</h1>
        <div className="image-grid">
          {images.length === 0 ? (
              <p>등록된 이미지가 없습니다.</p>
          ) : (
              images.map(image => (
                  <div key={image.id} className="image-card" onClick={() => setSelectedImage(image)}>
                    <img
                        src={`http://localhost:8080/uploads/${image.fileName}`}
                        alt={image.title}
                    />
                    <div className="image-info">
                      <h3>{image.title}</h3>
                      <p>{image.description}</p>
                      <span className="badge">{image.mapName}</span>
                      <span className="badge size">{image.mapSize}</span>
                    </div>
                  </div>
              ))
          )}
        </div>
          {selectedImage && (
              <div className="modal-overlay" >
              {/*<div className="modal-overlay" onClick={() => { setSelectedImage(null); setZoom(1); }}>*/}
                  <div className="modal-content" onClick={e => e.stopPropagation()}>
                      <button className="modal-close" onClick={() => {
                          setSelectedImage(null);
                          setZoom(1);
                      }}>✕
                      </button>
                      <div className="modal-controls">
                          <h2>{selectedImage.title}</h2>
                          <div className="control-buttons">
                              <button onClick={() => setZoom(z => Math.max(z - 0.25, 0.5))}>−</button>
                              <span>{Math.round(zoom * 100)}%</span>
                              <button onClick={() => setZoom(z => Math.min(z + 0.25, 5))}>+</button>
                              <button onClick={() => setZoom(1)}>초기화</button>
                          </div>
                      </div>
                      <div
                          className="modal-image-wrapper"
                          onMouseDown={e => {
                              setDragging(true);
                              setDragStart({x: e.clientX, y: e.clientY});
                              setScrollStart({x: e.currentTarget.scrollLeft, y: e.currentTarget.scrollTop});
                          }}
                          onMouseMove={e => {
                              if (!dragging) return;
                              e.currentTarget.scrollLeft = scrollStart.x - (e.clientX - dragStart.x);
                              e.currentTarget.scrollTop = scrollStart.y - (e.clientY - dragStart.y);
                          }}
                          onMouseUp={() => setDragging(false)}
                          onMouseLeave={() => setDragging(false)}
                      >
                          <img
                              src={`http://localhost:8080/uploads/${selectedImage.fileName}`}
                              alt={selectedImage.title}
                              draggable={false}
                              style={{
                                  width: `${zoom * 100}%`,
                              }}
                          />
                      </div>
                  </div>
              </div>
          )}
      </div>
  );
}

export default App;