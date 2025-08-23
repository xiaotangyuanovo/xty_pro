<template>
  <footer class="footer">
    <div class="container footer__inner">
      <span>© {{ year }} CETC38</span>
      <span class="muted">BS架构 · Vue2 前端</span>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  computed: {
    year: () => new Date().getFullYear()
  }
};
</script>

<style scoped>
.footer {
  border-top: 1px solid var(--border);
  background: #fff;
}
.footer__inner {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.muted { color: var(--muted); }
</style>
