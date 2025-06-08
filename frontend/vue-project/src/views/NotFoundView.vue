<template>
  <div class="not-found-container">
    <div class="error-content">
      <!-- Error Icon -->
      <div class="error-icon">
        <svg width="120" height="120" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="12" cy="12" r="10" stroke="#f59e0b" stroke-width="2" />
          <text x="12" y="16" text-anchor="middle" font-size="10" fill="#f59e0b" font-weight="bold">404</text>
        </svg>
      </div>

      <!-- Error Message -->
      <div class="error-message">
        <h1 class="error-title">404 - Không tìm thấy trang</h1>
        <p class="error-description">
          Trang bạn yêu cầu không tồn tại. Có thể đường dẫn đã bị thay đổi hoặc sai chính tả.
        </p>
      </div>

      <!-- Action Buttons -->
      <div class="error-actions">
        <button @click="goHome" class="btn btn-primary">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" stroke="currentColor" stroke-width="2"/>
            <polyline points="9,22 9,12 15,12 15,22" stroke="currentColor" stroke-width="2"/>
          </svg>
          Về trang chủ
        </button>

        <button @click="goBack" class="btn btn-secondary">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="m12 19-7-7 7-7" stroke="currentColor" stroke-width="2"/>
            <path d="M19 12H5" stroke="currentColor" stroke-width="2"/>
          </svg>
          Quay lại
        </button>
      </div>

      <!-- Additional Info -->
      <div class="error-info">
        <p class="error-code">Mã lỗi: NOT_FOUND</p>
        <p class="error-timestamp">{{ currentTime }}</p>
      </div>
    </div>

    <!-- Decorative Background -->
    <div class="background-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
 const userStore = useUserStore()
export default {
  name: 'NotFound',
  setup() {
    const router = useRouter()
    const currentTime = ref('')

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
      setInterval(updateTime, 1000)
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
  padding: 40px 20px;
  background: #fffbe6;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.error-content {
  max-width: 600px;
  margin: auto;
  z-index: 1;
}

.error-icon {
  margin-bottom: 20px;
}

.error-title {
  font-size: 28px;
  font-weight: bold;
  color: #f59e0b;
  margin-bottom: 10px;
}

.error-description {
  color: #4b5563;
  margin-bottom: 30px;
}

.error-actions .btn {
  margin: 0 10px;
  padding: 10px 16px;
  font-weight: bold;
  border-radius: 6px;
  display: inline-flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
}

.btn-primary {
  background-color: #3b82f6;
  color: white;
  border: none;
}

.btn-secondary {
  background-color: #e5e7eb;
  color: #111827;
  border: none;
}

.btn:hover {
  opacity: 0.9;
}

.error-info {
  margin-top: 30px;
  font-size: 14px;
  color: #6b7280;
}

.background-decoration {
  position: absolute;
  inset: 0;
  z-index: 0;
  overflow: hidden;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.2;
}

.circle-1 {
  width: 200px;
  height: 200px;
  background: #facc15;
  top: -50px;
  left: -50px;
}

.circle-2 {
  width: 150px;
  height: 150px;
  background: #fbbf24;
  bottom: 80px;
  right: -40px;
}

.circle-3 {
  width: 100px;
  height: 100px;
  background: #fde68a;
  bottom: 0;
  left: 20%;
}
</style>
