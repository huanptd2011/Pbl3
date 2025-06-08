<script setup>
import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user'
import appHeader from './components/appHeader.vue'
import headerUser from './components/headerUser.vue'
import AppFooter from './views/Footer.vue'

const userStore = useUserStore()
</script>

<template>
  <div id="app-container">
    <!-- Hiển thị header tùy theo trạng thái đăng nhập -->
    <headerUser v-if="userStore.isLoggedIn" />
    <appHeader v-else />

    <!-- Nội dung chính -->
    <div class="content-wrapper">
      <RouterView />
    </div>

    <!-- Hiển thị footer -->
    <AppFooter />
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
