// src/main.js

import { createApp } from 'vue'
import { createPinia } from 'pinia' // Import createPinia

import App from './App.vue'
import router from './router'
import { useUserStore } from '@/stores/user' // Import store người dùng của bạn

// Import CSS của Bootstrap và các file CSS tùy chỉnh
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import "../src/assets/base.css"
import './assets/main.css'


const app = createApp(App)
const pinia = createPinia() // 1. Tạo instance Pinia

app.use(pinia) // 2. Sử dụng Pinia trong ứng dụng

// 3. Tải dữ liệu người dùng vào store NGAY SAU KHI Pinia được dùng.
// Điều này đảm bảo store có dữ liệu từ localStorage (nếu có) trước khi router xử lý.
const userStore = useUserStore(); // Lấy instance của store người dùng
userStore.loadUserFromStorage(); // Gọi action để tải dữ liệu từ localStorage

app.use(router) // 4. Sử dụng router. Router guard (beforeEach) sẽ chạy sau khi store đã có lệnh tải data.

app.mount('#app') // 5. Mount ứng dụng vào DOM
