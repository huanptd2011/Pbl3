<script setup>


import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user'
import appHeader from './components/appHeader.vue'
import headerUser from './components/headerUser.vue'

const userStore = useUserStore()
userStore.logout()
</script>

<template>
  <div>
    <headerUser v-if="userStore.isLoggedIn" />
    <appHeader v-else /> 
    <RouterView />
  </div>
</template>
