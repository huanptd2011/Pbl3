<script setup>
import { RouterLink, RouterView } from 'vue-router'
import AppHeader from './components/appHeader.vue';
</script>

<template>
  
  <div>
    <AppHeader />
    <RouterView></RouterView>
  </div>

</template>

<style scoped>

</style>
