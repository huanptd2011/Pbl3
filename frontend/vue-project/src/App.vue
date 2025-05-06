<script setup>
import { RouterView } from 'vue-router'
import { useUserStore } from './stores/user'
import appHeader from './components/appHeader.vue'
import headerUser from './components/headerUser.vue'

const userStore = useUserStore()
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
