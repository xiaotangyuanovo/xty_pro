<template>
  <aside class="center card">
    <div class="center__title">课程中心</div>
    <ul class="menu">
      <li :class="{active: value==='video'}" @click="$emit('input','video')">
        <span class="micon">
          <svg viewBox="0 0 24 24" width="16" height="16"><path d="M4 6h11a1 1 0 0 1 1 1v3l4-2v8l-4-2v3a1 1 0 0 1-1 1H4a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2z" fill="#d32f2f"/></svg>
        </span>
        视频资料
        <span class="arrow">›</span>
      </li>
      <li :class="{active: value==='doc'}" @click="$emit('input','doc')">
        <span class="micon">
          <svg viewBox="0 0 24 24" width="16" height="16"><path d="M6 3h9l4 4v14a1 1 0 0 1-1 1H6a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1z" fill="#d32f2f"/></svg>
        </span>
        文档资料
        <span class="arrow">›</span>
      </li>
    </ul>
  </aside>
</template>

<script>
export default {
  name: 'CourseCenter',
  props: {
    value: { type: String, default: 'video' } // v-model
  }
};
</script>

<style scoped>
.center__title {
  background: #e53935;
  color: #fff;
  padding: 14px 16px;
  border-radius: 8px 8px 0 0;
  font-weight: 600;
}
.menu { padding: 0; margin: 0; list-style: none; }
.menu li {
  display: flex; align-items: center; justify-content: space-between;
  padding: 12px 14px; border-bottom: 1px solid var(--border); cursor: pointer;
}
.menu li:last-child { border-bottom: none; }
.menu li .micon { margin-right: 8px; }
.menu li.active { background: #fff5f5; color: #d32f2f; font-weight: 600; }
.arrow { color: #aaa; }
</style>
