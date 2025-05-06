<script setup>
import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user' // Đảm bảo đường dẫn đúng đến store của bạn
import appHeader from './components/appHeader.vue' // Đảm bảo đường dẫn đúng đến component appHeader
import headerUser from './components/headerUser.vue' // Đảm bảo đường dẫn đúng đến component headerUser

const userStore = useUserStore()

// !!! LƯU Ý: Đã bỏ dòng userStore.logout() ở đây.
// Nếu bạn muốn đăng xuất khi tải lại trang, hãy xem xét logic khác
// hoặc chỉ gọi nó ở một nút/event cụ thể.
// userStore.logout() // <--- Dòng này đã bị loại bỏ
</script>

<template>
  <div>
    <template v-if="!userStore.isAdmin">
      <headerUser v-if="userStore.isLoggedIn" />
      <appHeader v-else />
    </template>

    <RouterView />
  </div>
</template>
