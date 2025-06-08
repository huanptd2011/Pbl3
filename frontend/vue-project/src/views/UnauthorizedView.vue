<template>
  <div class="unauthorized-container">
    <div class="error-content">
      <!-- Error Icon -->
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

      <!-- Error Message -->
      <div class="error-message">
        <h1 class="error-title">403 - Không có quyền truy cập</h1>
        <p class="error-description">
          Xin lỗi, bạn không có quyền truy cập vào trang này. Vui lòng liên hệ với quản trị viên nếu
          bạn cho rằng đây là lỗi.
        </p>
      </div>

      <!-- Action Buttons -->
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

        <router-link to="/login" class="btn btn-outline">
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
        </router-link>
      </div>

      <!-- Additional Info -->
      <div class="error-info">
        <p class="error-code">Mã lỗi: UNAUTHORIZED_ACCESS</p>
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
import { useUserStore } from '@/stores/user'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'UnauthorizedView',
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

    const userStore = useUserStore()

    const goHome = () => {
      userStore.logout()
      router.push('/')
    }

    const updateTime = () => {
      currentTime.value = new Date().toLocaleString('vi-VN')
    }

    onMounted(() => {
      updateTime()
      // Update time every second
      setInterval(updateTime, 1000)
    })

    return {
      goBack,
      goHome,
      currentTime,
    }
  },
}
</script>

<style scoped>
.unauthorized-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.error-content {
  text-align: center;
  background: white;
  padding: 3rem 2rem;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 100%;
  position: relative;
  z-index: 2;
}

.error-icon {
  margin-bottom: 2rem;
  animation: bounce 2s infinite;
}

@keyframes bounce {
  0%,
  20%,
  50%,
  80%,
  100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-10px);
  }
  60% {
    transform: translateY(-5px);
  }
}

.error-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.error-description {
  font-size: 1.1rem;
  color: #6b7280;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.error-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 12px 24px;
  border-radius: 10px;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
  font-size: 1rem;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
}

.btn-secondary {
  background: #f3f4f6;
  color: #374151;
  border: 2px solid #e5e7eb;
}

.btn-secondary:hover {
  background: #e5e7eb;
  transform: translateY(-2px);
}

.btn-outline {
  background: transparent;
  color: #667eea;
  border: 2px solid #667eea;
}

.btn-outline:hover {
  background: #667eea;
  color: white;
  transform: translateY(-2px);
}

.error-info {
  border-top: 1px solid #e5e7eb;
  padding-top: 1.5rem;
  font-size: 0.875rem;
  color: #9ca3af;
}

.error-code {
  margin-bottom: 0.5rem;
  font-family: 'Courier New', monospace;
}

.error-timestamp {
  margin: 0;
}

.background-decoration {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 100px;
  height: 100px;
  top: 10%;
  left: 10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 150px;
  height: 150px;
  top: 60%;
  right: 10%;
  animation-delay: 2s;
}

.circle-3 {
  width: 80px;
  height: 80px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
}

/* Responsive Design */
@media (max-width: 768px) {
  .error-content {
    padding: 2rem 1.5rem;
  }

  .error-title {
    font-size: 2rem;
  }

  .error-description {
    font-size: 1rem;
  }

  .btn {
    padding: 10px 20px;
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  .unauthorized-container {
    padding: 15px;
  }

  .error-content {
    padding: 1.5rem 1rem;
  }

  .error-title {
    font-size: 1.8rem;
  }

  .error-icon svg {
    width: 80px;
    height: 80px;
  }
}
</style>
