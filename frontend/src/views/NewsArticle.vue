<template>
  <div class="container">
    <div class="card article">
      <h1 class="title">{{ art.title }}</h1>
      <div class="meta">
        <span>作者：{{ art.author || (art.tags && art.tags[0]) || '佚名' }}</span>
        <span>发布日期：{{ art.date }}</span>
      </div>
      <hr />

      <div class="content">
        <p v-for="(p,i) in contentParas" :key="i" class="para">{{ p }}</p>

        <figure v-for="(img,i) in filteredImages" :key="'img'+i" class="fig">
            <img :src="img" alt="配图" />
        </figure>

      </div>
    </div>
  </div>
</template>

<script>
import { newsArticles } from '@store/newsData.js';

export default {
  name: 'NewsArticle',
  data() {
    return { art: {} };
  },
  created() {
    const id = Number(this.$route.params.id);
    const found = newsArticles.find(a => a.id === id);
    this.art = found || {};
  },
  computed: {
    contentParas() {
      // 允许在数据里放 content（数组），否则就用 summary 占位
      if (this.art && this.art.content && this.art.content.length) return this.art.content;
      return [this.art.summary || ''];
    },
    imageUrls() {
      const list = (this.art && this.art.images) ? this.art.images : [];
      const urls = [];
      for (let i = 0; i < list.length; i++) {
        try {
          urls.push(require(`@/assets/news/${list[i]}`));
        } catch (e) {
          urls.push(''); // 缺图不报错
        }
      }
      return urls;
    },
    filteredImages() {
    return (this.imageUrls || []).filter(Boolean);
    }
  }
};
</script>

<style scoped>
.article { max-width: 920px; margin: 0 auto; padding: 24px 28px; } /* ← 舒适内边距 */
.title { text-align:center; font-size: 30px; margin: 6px 0 10px; }
.meta { text-align:center; color:#666; margin-bottom: 14px; }
.meta span + span { margin-left: 20px; }
.content { font-size: 16px; line-height: 1.9; color: #333; }
.para { text-indent: 2em; margin: 14px 0; }
.fig { text-align:center; margin: 18px 0; }
.fig img { max-width:100%; border-radius:8px; border:1px solid var(--border); }
</style>
