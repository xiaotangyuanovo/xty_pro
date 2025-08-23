<template>
  <div id="app">
    <AppHeader />
    <main class="container">
      <router-view />
    </main>
    <AppFooter />
  </div>
</template>

<script>
import AppHeader from '@components/layout/AppHeader.vue';
import AppFooter from '@components/layout/AppFooter.vue';

export default {
  name: 'App',
  components: { AppHeader, AppFooter }
};
</script>

<style scoped>
main {
  padding: 20px 0 40px;
}
</style>
