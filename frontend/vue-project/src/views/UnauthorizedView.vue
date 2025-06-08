<template>
  <div class="unauthorized-container">
    <div class="error-content">
      <!-- Icon lỗi -->
      <div class="error-icon">
        <svg
          width="120"
          height="120"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <circle cx="12" cy="12" r="10" stroke="#ef4444" stroke-width="2" />
          <path d="m15 9-6 6" stroke="#ef4444" stroke-width="2" />
          <path d="m9 9 6 6" stroke="#ef4444" stroke-width="2" />
        </svg>
      </div>

      <!-- Thông báo lỗi -->
      <div class="error-message">
        <h1 class="error-title">403 - Không có quyền truy cập</h1>
        <p class="error-description">
          Xin lỗi, bạn không có quyền truy cập vào trang này. Vui lòng liên hệ với quản trị viên nếu
          bạn cho rằng đây là lỗi.
        </p>
      </div>

      <!-- Các nút hành động -->
      <div class="error-actions">
        <button @click="goHome" class="btn btn-primary">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"
              stroke="currentColor"
              stroke-width="2"
            />
            <polyline points="9,22 9,12 15,12 15,22" stroke="currentColor" stroke-width="2" />
          </svg>
          Về trang chủ
        </button>

        <button @click="goBack" class="btn btn-secondary">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d="m12 19-7-7 7-7" stroke="currentColor" stroke-width="2" />
            <path d="M19 12H5" stroke="currentColor" stroke-width="2" />
          </svg>
          Quay lại
        </button>

        <button @click="goLogin" class="btn btn-outline">
          <svg
            width="20"
            height="20"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"
              stroke="currentColor"
              stroke-width="2"
            />
            <polyline points="10,17 15,12 10,7" stroke="currentColor" stroke-width="2" />
            <line x1="15" y1="12" x2="3" y2="12" stroke="currentColor" stroke-width="2" />
          </svg>
          Đăng nhập lại
        </button>
      </div>

      <!-- Thông tin thêm -->
      <div class="error-info">
        <p class="error-code">Mã lỗi: UNAUTHORIZED_ACCESS</p>
        <p class="error-timestamp">{{ currentTime }}</p>
      </div>
    </div>

    <!-- Trang trí nền -->
    <div class="background-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user'
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'UnauthorizedView',
  setup() {
    const router = useRouter()
    const currentTime = ref('')

    // Tham chiếu store người dùng
    const userStore = useUserStore()

    // Hàm quay về trang trước hoặc trang chủ nếu không có lịch sử
    const goBack = () => {
      if (window.history.length > 1) {
        router.go(-1)
      } else {
        router.push('/')
      }
    }

    // Hàm trở về trang chủ và đăng xuất
    const goHome = () => {
      userStore.logout()
      router.push('/')
    }

    // Hàm chuyển đến trang đăng nhập và đăng xuất
    const goLogin = () => {
      userStore.logout()
      router.push('/login')
    }

    // Cập nhật thời gian hiện tại theo định dạng Việt Nam
    const updateTime = () => {
      currentTime.value = new Date().toLocaleString('vi-VN')
    }

    // Biến để lưu ID interval để clear khi component unmount
    let intervalId = null

    onMounted(() => {
      updateTime()
      intervalId = setInterval(updateTime, 1000)
    })

    onBeforeUnmount(() => {
      clearInterval(intervalId)
    })

    return {
      goBack,
      goHome,
      goLogin,
      currentTime,
    }
  },
}
</script>

<style scoped>
/* Bạn có thể tùy chỉnh style theo ý muốn */

.unauthorized-container {
  position: relative;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f9fafb;
  padding: 20px;
  box-sizing: border-box;
}

.error-content {
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
  max-width: 480px;
  text-align: center;
  z-index: 10;
}

.error-icon {
  margin-bottom: 24px;
}

.error-title {
  margin: 0 0 12px;
  font-size: 28px;
  color: #ef4444;
  font-weight: 700;
}

.error-description {
  margin-bottom: 32px;
  color: #6b7280;
  font-size: 16px;
  line-height: 1.5;
}

.error-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  font-weight: 600;
  cursor: pointer;
  border-radius: 6px;
  border: 2px solid transparent;
  transition: background-color 0.3s, color 0.3s;
  user-select: none;
  background: transparent;
  color: #374151;
  font-size: 14px;
}

.btn svg {
  stroke-width: 2;
}

.btn-primary {
  background-color: #ef4444;
  color: white;
  border-color: #ef4444;
}

.btn-primary:hover {
  background-color: #dc2626;
  border-color: #dc2626;
}

.btn-secondary {
  background-color: #e5e7eb;
  color: #374151;
  border-color: #d1d5db;
}

.btn-secondary:hover {
  background-color: #d1d5db;
}

.btn-outline {
  border-color: #ef4444;
  color: #ef4444;
}

.btn-outline:hover {
  background-color: #ef4444;
  color: white;
}

.error-info {
  font-size: 12px;
  color: #9ca3af;
}

.background-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 1;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.15;
  background-color: #ef4444;
  animation: pulse 4s infinite ease-in-out;
}

.circle-1 {
  width: 180px;
  height: 180px;
  top: 10%;
  left: 15%;
  animation-delay: 0s;
}

.circle-2 {
  width: 250px;
  height: 250px;
  top: 50%;
  left: 60%;
  animation-delay: 1.5s;
}

.circle-3 {
  width: 150px;
  height: 150px;
  top: 75%;
  left: 30%;
  animation-delay: 3s;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.15;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.3;
  }
}
</style>
