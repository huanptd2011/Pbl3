<template>
    <div class="auth-modal">
      <div class="modal-content">
        <h2>Đăng nhập / Đăng ký</h2>
        <button @click="$emit('close')">Đóng</button>
      </div>
    </div>
  </template>
  
  <script setup>
  // có thể thêm logic đăng nhập ở đây
  </script>
  
  <style scoped>
  .auth-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.4);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .modal-content {
    background: white;
    padding: 2rem;
    border-radius: 8px;
    min-width: 300px;
  }
  </style>
  