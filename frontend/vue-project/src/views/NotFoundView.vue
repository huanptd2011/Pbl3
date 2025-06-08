<template>
  <div class="not-found-container">
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
          <text
            x="12"
            y="16"
            text-anchor="middle"
            font-size="10"
            fill="#ef4444"
            font-weight="bold"
          >
            404
          </text>
        </svg>
      </div>

      <!-- Thông báo lỗi -->
      <div class="error-message">
        <h1 class="error-title">404 - Không tìm thấy trang</h1>
        <p class="error-description">
          Trang bạn yêu cầu không tồn tại. Có thể đường dẫn đã bị thay đổi hoặc sai chính tả.
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
      </div>

      <!-- Thông tin thêm -->
      <div class="error-info">
        <p class="error-code">Mã lỗi: NOT_FOUND</p>
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
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

export default {
  name: 'NotFound',
  setup() {
    const router = useRouter()
    const userStore = useUserStore()
    const currentTime = ref('')
    let intervalId = null

    const goBack = () => {
      if (window.history.length > 1) {
        router.go(-1)
      } else {
        router.push('/')
      }
    }

    const goHome = () => {
      userStore.logout()
      router.push('/')
    }

    const updateTime = () => {
      currentTime.value = new Date().toLocaleString('vi-VN')
    }

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
      currentTime
    }
  }
}
</script>

<style scoped>
.not-found-container {
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
