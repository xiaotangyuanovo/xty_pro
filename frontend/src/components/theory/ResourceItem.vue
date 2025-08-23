<template>
  <div class="res">
    <div class="thumb" @click="$emit('open', item)" role="button" tabindex="0">
      <img :src="coverUrl" :alt="item.title" />
      <span v-if="item.completed" class="badge">完成</span>
    </div>

    <div class="main">
      <h3 class="title" @click="$emit('open', item)">{{ item.title }}</h3>
      <p class="desc">{{ item.summary }}</p>

      <div class="meta">
        <span class="tag req" v-if="item.required">必修</span>
        <span class="hour">{{ item.hours }} 学时</span>
        <span class="like">
          <svg viewBox="0 0 24 24" width="16" height="16">
            <path d="M9 21H5a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h4m6-2V5a3 3 0 0 0-6 0v7l-2 4h10a2 2 0 0 0 2-2l-2-7h-4z" fill="#999"/>
          </svg>
          {{ item.likes }}
        </span>
      </div>

      <!-- 主讲人信息：每个资源都有 -->
      <div class="lecturer light">
        <div class="flag"></div>
        <strong>主讲人：</strong>{{ item.lecturer }}
        <div class="lecturer-desc">{{ item.lecturerDesc }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ResourceItem',
  props: { item: { type: Object, required: true } },
  computed: {
    coverUrl() {
      try { return require(`@/assets/courses/${this.item.cover}`); }
      catch { return ''; }
    }
  }
};
</script>

<style scoped>
.res {
  display: grid;
  grid-template-columns: 360px 1fr;
  grid-column-gap: 24px;
  padding: 24px 0;
  border-bottom: 1px solid var(--border);
}
.thumb { position: relative; cursor: pointer; }
.thumb img {
  width: 100%; height: 200px; object-fit: cover;
  border-radius: 6px; border: 1px solid var(--border);
}
.badge { position: absolute; top: 10px; right: 10px; background: #2e7d32; color: #fff; border-radius: 6px; padding: 4px 8px; font-size: 12px; }

.title { margin: 0 0 8px; font-size: 20px; font-weight: 600; cursor: pointer; }
.desc  { margin: 0 0 10px; color: var(--muted); line-height: 1.8; }
.meta  { display: flex; align-items: center; gap: 14px; color: #555; margin-bottom: 10px; }
.tag.req { border: 1.5px solid #d32f2f; color: #d32f2f; padding: 2px 8px; border-radius: 4px; font-size: 12px; }
.hour { color: #666; }
.like { display: inline-flex; align-items: center; gap: 4px; color: #777; }

.lecturer.light {
  background: #fafafa;
  border: 1px solid var(--border);
  border-radius: 8px;
  padding: 10px 12px;
}
.flag { display: inline-block; width: 6px; height: 16px; background: #d32f2f; margin-right: 8px; vertical-align: middle; }
.lecturer-desc { margin-top: 6px; color: var(--muted); }

@media (max-width: 900px) {
  .res { grid-template-columns: 1fr; }
  .thumb img { height: 220px; }
}
</style>
