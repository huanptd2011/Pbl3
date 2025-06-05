<script setup>
import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user' // Đảm bảo đường dẫn đúng đến store của bạn
import appHeader from './components/appHeader.vue' // Đảm bảo đường dẫn đúng đến component appHeader
import headerUser from './components/headerUser.vue' // Đảm bảo đường dẫn đúng đến component headerUser
import AppFooter from './views/Footer.vue' // Đảm bảo đường dẫn đúng đến component Footer

const userStore = useUserStore()

// !!! LƯU Ý: Đã bỏ dòng userStore.logout() ở đây.
// Nếu bạn muốn đăng xuất khi tải lại trang, hãy xem xét logic khác
// hoặc chỉ gọi nó ở một nút/event cụ thể.
// userStore.logout() // <--- Dòng này đã bị loại bỏ
</script>

<!-- <template>
  <div>
    <template v-if="!userStore.isAdmin">
      <headerUser v-if="userStore.isLoggedIn" />
      <appHeader v-else />
    </template>

    <RouterView />
     
    <template v-if="!userStore.isAdmin">
      <AppFooter />
    </template>
  </div>
</template> -->

<template>
  <div id="app-container"> 
    <div class="content-wrapper">
      <template v-if="!userStore.isAdmin">
        <headerUser v-if="userStore.isLoggedIn" />
        <appHeader v-else />
      </template>

      <RouterView /> 
    </div>
      
    <template v-if="!userStore.isAdmin">
      <AppFooter />
    </template>
  </div>
</template>

<style>
  #app-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }
  .content-wrapper {
    flex: 1;
    overflow-y: auto;
  }

  
</style>
