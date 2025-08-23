<template>
  <div class="theory container">
    <section class="card header">
      <div class="head-row">
        <div class="head-left">
          <div class="head-title">
            <span class="icon" aria-hidden="true">
              <svg viewBox="0 0 24 24" width="20" height="20">
                <path d="M4 3h13a1 1 0 0 1 1 1v12l-4 4H4a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1z" fill="#d32f2f"/>
                <path d="M14 20v-4h4" fill="#f2bdbd"/>
              </svg>
            </span>
            <h2>在线课程学习</h2>
          </div>

          <p class="intro">
            本理论学习环节学员共需要完成 <span class="emph">{{ overview.totalHours }}</span> 个学时的学习，
            其中 <span class="emph">{{ overview.requiredHours }}</span> 个学时为必修，
            <span class="emph">{{ overview.optionalHours }}</span> 个学时为选修。
            选修课程请通过对应课程板块的 <a class="link-red" @click.prevent="goWantStudy">我要学习</a> 进入课程中心进行选课。<br>
            你的理论学习环节已 <span class="emph">{{ overview.progressText }}</span>，
            可点击进入 <a class="link-red" @click.prevent="goMyCourses">[我的课程]</a> 查看完成状态。
          </p>
        </div>

        <div class="head-right">
          <button class="btn-red" @click="goMyCourses">我的课程</button>
        </div>
      </div>

      <div class="divider"></div>

      <div class="search-row">
        <div class="search">
          <input v-model.trim="keyword" type="text" placeholder="搜索关键词" @keyup.enter="goSearch" />
          <button class="btn-search" @click="goSearch" aria-label="搜索">
            <svg viewBox="0 0 24 24" width="18" height="18">
              <path d="M15.5 14h-.79l-.28-.27a6.5 6.5 0 1 0-.71.71l.27.28v.79l5 4.99L20.49 19l-4.99-5zM6.5 11a4.5 4.5 0 1 1 9 0a4.5 4.5 0 0 1-9 0z" fill="#fff"/>
            </svg>
          </button>
        </div>
      </div>
    </section>

    <section class="list">
      <TheoryCard v-for="m in paged" :key="m.id" :module="m" @learn="goLearn" />
    </section>

    <!-- 只有当总数 > pageSize 时才显示分页 -->
    <section class="pager-wrap" v-if="pageCount > 1">
      <ul class="pager">
        <li v-for="p in pages" :key="p" :class="{active: p === page}" @click="setPage(p)">{{ p }}</li>
        <li class="btn-next" :class="{disabled: page >= pageCount}" @click="nextPage">下一页</li>
        <li class="jump">
          跳转到
          <input v-model.number="jumpNum" type="number" min="1" :max="pageCount" /> 页
          <button class="btn-go" @click="jump">GO</button>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
import TheoryCard from '@components/theory/TheoryCard.vue';

export default {
  name: 'Theory',
  components: { TheoryCard },
  data() {
    return {
      overview: { totalHours: 32, requiredHours: 28.0, optionalHours: 4.0, progressText: '完成' },
      keyword: '',
      page: 1,
      pageSize: 5, // ← 改为 5/页
      jumpNum: 1,
      modules: [
        { id: 1, title: '深入学习马克思主义基本原理', summary: '……', cover: 'marx.jpg', total: 35, required: 3, pending: 30, selected: 2, hours: 8.5 },
        { id: 2, title: '用习近平新时代中国特色社会主义思想武装头脑', summary: '……', cover: 'xjp.jpg', total: 84, required: 2, pending: 82, selected: 0, hours: 3.5 },
        { id: 3, title: '发扬党的优良传统与作风', summary: '……', cover: 'tradition.jpg', total: 32, required: 1, pending: 31, selected: 0, hours: 3 }
      ]
    };
  },
  computed: {
    filtered() {
      if (!this.keyword) return this.modules;
      const k = this.keyword.toLowerCase();
      return this.modules.filter(m =>
        m.title.toLowerCase().includes(k) || m.summary.toLowerCase().includes(k)
      );
    },
    pageCount() { return Math.max(1, Math.ceil(this.filtered.length / this.pageSize)); },
    pages() { return Array.from({ length: this.pageCount }, (_, i) => i + 1); },
    paged() {
      const start = (this.page - 1) * this.pageSize;
      return this.filtered.slice(start, start + this.pageSize);
    }
  },
  methods: {
    goMyCourses() { this.$router.push({ name: 'Theory', query: { tab: 'mine' } }); },
    goWantStudy() { window.scrollTo({ top: 0, behavior: 'smooth' }); },
    goSearch() { this.page = 1; },
    setPage(p) { if (p >= 1 && p <= this.pageCount) this.page = p; },
    nextPage() { if (this.page < this.pageCount) this.page += 1; },
    jump() {
      if (!this.jumpNum) return;
      const p = Math.max(1, Math.min(this.pageCount, this.jumpNum));
      this.page = p;
    },
    goLearn(mod) {
      this.$router.push({ name: 'TheoryModule', params: { id: mod.id } }); // ← 跳详情
    }
  }
};
</script>

<style scoped>
/* 保持你之前的样式（略） */
.header { padding: 16px; }
.head-row { display: grid; grid-template-columns: 1fr auto; grid-column-gap: 24px; align-items: center; }
.head-title { display: flex; align-items: center; margin-bottom: 8px; }
.head-title h2 { margin: 0 0 0 8px; font-size: 20px; }
.icon { display: inline-flex; }
.intro { margin: 8px 0 0; color: var(--muted); line-height: 1.9; }
.link-red { color: #d32f2f; cursor: pointer; }
.btn-red { padding: 8px 14px; background: #d32f2f; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
.btn-red:hover { filter: brightness(0.95); }
.divider { height: 1px; background: var(--border); margin: 16px 0; }
.search-row { display: flex; justify-content: flex-end; }
.search { display: flex; align-items: center; }
.search input { width: 280px; height: 36px; border: 1px solid var(--border); border-right: none; border-radius: 18px 0 0 18px; padding: 0 12px; outline: none; }
.btn-search { width: 42px; height: 36px; border: none; border-radius: 0 18px 18px 0; background: #d32f2f; display: inline-flex; align-items: center; justify-content: center; cursor: pointer; }

.list { margin-top: 16px; }
.pager-wrap { margin: 18px 0 40px; }
.pager { display: flex; align-items: center; flex-wrap: wrap; gap: 8px; padding: 0; list-style: none; }
.pager li { border: 1px solid var(--border); border-radius: 6px; padding: 6px 10px; cursor: pointer; user-select: none; }
.pager li.active { background: #d32f2f; color: #fff; border-color: #d32f2f; }
.pager .btn-next { margin-left: 8px; }
.pager .btn-next.disabled { opacity: 0.5; pointer-events: none; }
.pager .jump { display: inline-flex; align-items: center; gap: 6px; border: none; }
.pager .jump input { width: 72px; height: 32px; border: 1px solid var(--border); border-radius: 4px; padding: 0 8px; }
.btn-go { height: 32px; padding: 0 10px; border: 1px solid var(--border); border-radius: 6px; background: #fff; cursor: pointer; }
.emph { color: #d32f2f; font-weight: 600; }
</style>
