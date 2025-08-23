<template>
  <article class="item">
    <h3 class="title">
      <router-link :to="{ name: 'NewsArticle', params: { id: art.id } }">{{ art.title }}</router-link>
    </h3>
    <p class="summary">{{ art.summary }}</p>
    <div class="meta">
      <span class="date">{{ art.date }}</span>
      <span class="tags">{{ (art.tags||[]).join('') }}</span>
      <span class="view">
        <svg width="18" height="18" viewBox="0 0 24 24"><path fill="#999" d="M12 5c5 0 9 4 10 7-1 3-5 7-10 7S3 15 2 12c1-3 5-7 10-7Zm0 2C8.5 7 5.5 9.4 4.4 12 5.5 14.6 8.5 17 12 17s6.5-2.4 7.6-5c-1.1-2.6-4.1-5-7.6-5Zm0 2.5A2.5 2.5 0 1 1 9.5 12 2.5 2.5 0 0 1 12 9.5Z"/></svg>
        阅读量：{{ art.views }}
      </span>
    </div>
  </article>
</template>

<script>
export default { name: 'NewsItem', props: { art: { type: Object, required: true } } };
</script>

<style scoped>
.item { padding: 18px 24px; border-bottom:1px solid var(--border); } /* ← 左右 24px 内边距 */
.title { margin: 0 0 6px; font-size: 22px; line-height: 1.4; }
.title a { color: inherit; text-decoration: none; }
.title a:hover { color: #e53935; }
.summary { margin: 0 0 8px; color:#666; line-height: 1.8; }
.meta { display:flex; align-items:center; gap:18px; color:#888; }
.view { margin-left:auto; display:inline-flex; align-items:center; gap:6px; }
</style>
