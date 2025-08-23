<template>
  <div class="card">
    <h2>页面走丢了 (404)</h2>
    <p>请检查链接或返回首页。</p>
    <button @click="$router.push('/')">返回首页</button>
  </div>
</template>

<script>
export default { name: 'NotFound' };
</script>

<style scoped>
button {
  margin-top: 12px;
  padding: 8px 12px;
  border: 1px solid var(--border);
  border-radius: 8px;
  background: #fff;
  cursor: pointer;
}
button:hover {
  background: #f6f6f6;
}
</style>
