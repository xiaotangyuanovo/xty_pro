<template>
  <div class="theory-card">
    <div class="thumb" @click="$emit('learn', module)" role="button" tabindex="0">
      <img :src="coverUrl" :alt="module.title" />
    </div>

    <div class="content">
      <h3 class="title" @click="$emit('learn', module)">{{ module.title }}</h3>
      <p class="desc">{{ module.summary }}</p>
      <p class="meta">
        【 本版块共 <span class="emph">{{ module.total }}</span> 课程，
        必修：<span class="emph">{{ module.required }}</span> 课，
        待选：<span class="emph">{{ module.pending }}</span> 课，
        已选：<span class="emph">{{ module.selected }}</span> 课，
        获得学时：<span class="emph">{{ module.hours }}</span> 】
      </p>
    </div>

    <div class="side">
      <button class="btn-outline" @click="$emit('learn', module)">我要学习</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TheoryCard',
  props: { module: { type: Object, required: true } },
  computed: {
    coverUrl() {
      try { return require(`@/assets/courses/${this.module.cover}`); }
      catch { return ''; }
    }
  }
};
</script>

<style scoped>
.theory-card {
  display: grid;
  grid-template-columns: 320px 1fr 140px;
  grid-column-gap: 24px;
  align-items: center;
  padding: 24px 0;
  border-bottom: 1px solid var(--border);
}
.thumb { cursor: pointer; }
.thumb img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid var(--border);
}
.title { margin: 0 0 8px; font-size: 18px; font-weight: 600; cursor: pointer; }
.desc  { margin: 0 0 10px; color: var(--muted); line-height: 1.8; }
.meta  { margin: 0; color: var(--text); }
.emph  { color: #d32f2f; font-weight: 600; }

.side { display: flex; justify-content: flex-end; }
.btn-outline {
  min-width: 110px; padding: 8px 14px; background: #fff;
  border: 1.5px solid #d32f2f; color: #d32f2f; border-radius: 6px; cursor: pointer;
}
.btn-outline:hover { background: #fff5f5; }

@media (max-width: 900px) {
  .theory-card { grid-template-columns: 1fr; }
  .thumb img { height: 200px; }
  .side { justify-content: flex-start; margin-top: 10px; }
}
</style>
