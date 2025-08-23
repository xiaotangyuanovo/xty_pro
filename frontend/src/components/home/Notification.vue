<template>
  <div class="card notice">
    <div class="notice__head">
      <span class="icon" aria-hidden="true">
        <!-- 简单房子图标 -->
        <svg viewBox="0 0 24 24" width="18" height="18">
          <path d="M3 10.5L12 3l9 7.5v9a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1v-9z" fill="#e64a19"/>
        </svg>
      </span>
      <span class="title">通知消息</span>
    </div>
    <div class="notice__underline"></div>

    <ul class="notice__list">
      <li v-for="(item, idx) in items" :key="idx">
        <span class="dot">•</span>
        <span class="text">{{ item }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Notification',
  props: {
    items: {
      type: Array,
      default: () => ['申请审核结果']
    }
  }
};
</script>

<style scoped>
.notice__head {
  display: flex;
  align-items: center;
  height: 48px;
  border-bottom: 1px solid var(--border);
}
.icon { display: inline-flex; margin-right: 8px; }
.title { font-weight: 600; }

.notice__underline {
  height: 2px;
  width: 60px;
  background: #e64a19; /* 红色下划线效果 */
  margin-top: -2px;
}

.notice__list { padding: 12px 2px 4px; }
.notice__list li {
  display: flex;
  align-items: baseline;
  padding: 6px 0;
}
.dot { width: 16px; display: inline-block; color: #999; }
.text { color: var(--text); }
</style>
