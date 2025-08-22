<template>
  <aside class="menu">
    <div class="head">资讯通知</div>
    <div class="item" :class="{active: value==='road'}" @click="$emit('input','road')">
      <span class="icon">📰</span><span>学习路上</span><span class="chev">›</span>
    </div>
    <div class="item" :class="{active: value==='notice'}" @click="$emit('input','notice')">
      <span class="icon">🔔</span><span>通知消息</span><span class="chev">›</span>
    </div>
  </aside>
</template>

<script>
export default {
  name: 'NewsSidebar',
  props: { value: { type: String, default: 'road' } }
};
</script>

<style scoped>
.menu { width: 280px; background:#fff; border:1px solid var(--border); border-radius:8px; overflow:hidden; }
.head { background:#e53935; color:#fff; padding:14px 16px; font-size:18px; }
.item { display:flex; align-items:center; gap:10px; padding:14px 16px; border-top:1px solid var(--border); cursor:pointer; }
.item:hover { background:#fafafa; }
.item.active { background:#fff5f5; color:#e53935; font-weight:600; }
.icon { width:20px; text-align:center; }
.chev { margin-left:auto; color:#999; }
</style>
