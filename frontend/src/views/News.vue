<template>
  <div class="container">
    <div class="grid">
      <NewsSidebar v-model="tab" />

      <section class="panel card">
        <!-- 头部 -->
        <div class="head">
          <h2 class="title">{{ tab==='road' ? '学习路上' : '通知消息' }}</h2>
        </div>
        <div class="underline"></div>

        <!-- 内容区 -->
        <div v-if="tab==='road'">
          <NewsItem v-for="a in pagedArticles" :key="a.id" :art="a" />
          <Pager v-if="articlePageCount>1"
                 :page="articlePage" :pages="articlePageCount"
                 @change="articlePage=$event" />
        </div>

        <div v-else>
          <NoticeItem v-for="n in pagedNotices" :key="n.id" :item="n" />
          <Pager v-if="noticePageCount>1"
                 :page="noticePage" :pages="noticePageCount"
                 @change="noticePage=$event" />
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import NewsSidebar from '@components/news/NewsSidebar.vue';
import NewsItem from '@components/news/NewsItem.vue';
import NoticeItem from '@components/news/NoticeItem.vue';
import { newsArticles, noticeList } from '@store/newsData.js';

export default {
  name: 'News',
  components: { NewsSidebar, NewsItem, NoticeItem, Pager: {
    // 简易分页组件（每页固定5条）
    name: 'Pager',
    props: { page: Number, pages: Number },
    methods: {
      set(p){ if(p>=1 && p<=this.pages) this.$emit('change', p); }
    },
    template: `
      <div class="pager">
        <button :disabled="page<=1" @click="set(page-1)">上一页</button>
        <span v-for="p in pages" :key="p" :class="['num', {on: p===page}]" @click="set(p)">{{p}}</span>
        <button :disabled="page>=pages" @click="set(page+1)">下一页</button>
        <span class="jump">跳转到 <input type="number" min="1" :max="pages" v-model.number="n" @keyup.enter="set(n)"> 页 <button @click="set(n)">GO</button></span>
      </div>
    `,
    data(){ return { n: 1 }; }
  } },
  data() {
    const tab = this.$route.query.tab === 'notice' ? 'notice' : 'road';
    return {
      tab,
      pageSize: 5,
      articles: newsArticles,  // 可替换为接口数据
      notices: noticeList,     // 与首页共用
      articlePage: 1,
      noticePage: 1
    };
  },
  watch: {
    tab() {
      // 切换栏目时各自回到第一页
      if (this.tab === 'road') this.articlePage = 1;
      else this.noticePage = 1;
      // 同步到路由（可刷新保持状态）
      this.$router.replace({ name: 'News', query: { tab: this.tab } });
    }
  },
  computed: {
    // 学习路上分页
    articlePageCount() { return Math.max(1, Math.ceil(this.articles.length / this.pageSize)); },
    pagedArticles() {
      const s = (this.articlePage - 1) * this.pageSize;
      return this.articles.slice(s, s + this.pageSize);
    },
    // 通知消息分页
    noticePageCount() { return Math.max(1, Math.ceil(this.notices.length / this.pageSize)); },
    pagedNotices() {
      const s = (this.noticePage - 1) * this.pageSize;
      return this.notices.slice(s, s + this.pageSize);
    }
  }
};
</script>

<!-- 省略上面的模板 -->
<style scoped>
.grid { display:grid; grid-template-columns: 300px 1fr; grid-column-gap: 24px; }
.panel { padding: 0; }
.head { display:flex; align-items:center; justify-content:space-between; padding:16px; background:#f7f7f7; border-bottom:1px solid var(--border); }
.title { margin:0; font-size:20px; }
.underline { height:2px; width:80px; background:#e53935; margin:0 16px; }

/* 分页条保持不变 */
.pager { display:flex; align-items:center; gap:8px; padding:16px 24px; } /* ← 左右 24px */
.pager button { padding:6px 10px; border:1px solid var(--border); background:#fff; border-radius:6px; cursor:pointer; }
.pager button:disabled { opacity:.5; cursor:not-allowed; }
.num { border:1px solid var(--border); padding:6px 10px; border-radius:6px; cursor:pointer; }
.num.on { background:#e53935; color:#fff; border-color:#e53935; }
.jump input { width:70px; height:32px; border:1px solid var(--border); border-radius:4px; padding:0 6px; }
@media (max-width: 1000px){ .grid { grid-template-columns: 1fr; } }
</style>

