<template>
  <div class="notice">
    <span class="pill">通知</span>
    <router-link class="title" :to="{ name:'NoticeDetail', params:{ id: item.id } }">{{ item.title }}</router-link>
    <span class="date">{{ item.date }}</span>
    <span class="status">【{{ item.read ? '已阅' : '未读' }}】</span>
  </div>
</template>

<script>
export default { name: 'NoticeItem', props: { item: { type: Object, required: true } } };
</script>

<style scoped>
.notice { padding:16px 24px; border-bottom:1px dotted var(--border); display:flex; align-items:center; gap:12px; } /* ← 左右 24px */
.pill { border:1.5px solid #e53935; color:#e53935; border-radius:6px; padding:2px 10px; }
.title { font-size:18px; color:inherit; text-decoration:none; }
.title:hover { color:#e53935; }
.date { margin-left:auto; color:#666; }
.status { width:66px; text-align:right; color:#666; }
</style>
