<script>
import CourseCenter from '@components/theory/CourseCenter.vue';
import ResourceItem from '@components/theory/ResourceItem.vue';

export default {
  name: 'TheoryModule',
  components: { CourseCenter, ResourceItem },
  data() {
    return {
      moduleMap: {
        1: '深入学习马克思主义基本原理',
        2: '用习近平新时代中国特色社会主义思想武装头脑',
        3: '发扬党的优良传统与作风'
      },
      leftTab: 'video',
      tab: 'all',
      page: 1,
      pageSize: 5,
      jumpNum: 1,
      resources: [
        { id: 101, title: '马克思主义的世界观和方法论', summary: '“工欲善其事，必先利其器。”作为共产党员的世界观和方法论…', cover: 'res1.jpg', category: 'video', required: true,  hours: 2.5, likes: 22, completed: true,  lecturer: '董振华', lecturerDesc: '中共中央党校（国家行政学院）哲学教研部副主任、教授' },
        { id: 102, title: '如何理解“重读马克思”在当代的价值', summary: '历史常常出现这样一种现象…',                            cover: 'res2.jpg', category: 'video', required: true,  hours: 0.5, likes: 5,  completed: true,  lecturer: '晏锦芳', lecturerDesc: '北京大学马克思主义文献研究中心主任' },
        { id: 103, title: '《资本论》导读',                         summary: '从剩余价值、劳动价值论等核心概念入手…',                    cover: 'res3.jpg', category: 'video', required: false, hours: 1.5, likes: 8,  completed: false, lecturer: '王某某', lecturerDesc: '——' },
        { id: 104, title: '哲学基本问题的两种对立',                   summary: '唯物主义与唯心主义的历史演进与现实意义…',                  cover: 'res4.jpg', category: 'doc',   required: true,  hours: 1.0, likes: 12, completed: false, lecturer: '李某某', lecturerDesc: '——' },
        { id: 105, title: '历史唯物主义方法论阅读材料',               summary: '社会存在与社会意识、生产力与生产关系的辩证关系…',          cover: 'res5.jpg', category: 'doc',   required: false, hours: 0.8, likes: 3,  completed: false, lecturer: '张某某', lecturerDesc: '——' },
      ]
    };
  },
  computed: {
    moduleTitle() { const id = this.$route.params.id; return this.moduleMap[id] || '课程模块'; },
    filteredByLeft() { return this.resources.filter(r => r.category === this.leftTab); },
    filtered() {
      if (this.tab === 'required') return this.filteredByLeft.filter(r => r.required);
      if (this.tab === 'optional') return this.filteredByLeft.filter(r => !r.required);
      return this.filteredByLeft;
    },
    pageCount() { return Math.max(1, Math.ceil(this.filtered.length / this.pageSize)); },
    pages() { return Array.from({ length: this.pageCount }, (_, i) => i + 1); },
    paged() { const s = (this.page - 1) * this.pageSize; return this.filtered.slice(s, s + this.pageSize); }
  },
  watch: { leftTab() { this.page = 1; }, tab() { this.page = 1; } },
  methods: {
    setPage(p) { if (p >= 1 && p <= this.pageCount) this.page = p; },
    nextPage() { if (this.page < this.pageCount) this.page += 1; },
    jump() { if (!this.jumpNum) return; const p = Math.max(1, Math.min(this.pageCount, this.jumpNum)); this.page = p; },
    openResource(r) {
      this.$router.push({ name: 'ResourceView', params: { id: this.$route.params.id, rid: r.id } });
    }
  }
};
</script>

<template>
  <div class="container">
    <div class="card topbar">
      <h2 class="mod-title">{{ moduleTitle }}</h2>
    </div>

    <div class="grid-wrap">
      <CourseCenter v-model="leftTab" />

      <section class="card content">
        <div class="section-head">
          <h3>精品课程</h3>
          <div class="tabs">
            <button :class="{active: tab==='all'}" @click="tab='all'">全部</button>
            <button :class="{active: tab==='required'}" @click="tab='required'">必读</button>
            <button :class="{active: tab==='optional'}" @click="tab='optional'">选读</button>
          </div>
        </div>
        <div class="red-underline"></div>

        <div>
          <ResourceItem v-for="r in paged" :key="r.id" :item="r" @open="openResource" />
        </div>

        <!-- 分页（仅总数>5显示） -->
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
      </section>
    </div>
  </div>
</template>

<style scoped>
.topbar { margin-bottom: 12px; }
.mod-title { margin: 0; font-size: 20px; font-weight: 600; }
.grid-wrap { display: grid; grid-template-columns: 260px 1fr; grid-column-gap: 20px; }
.content { padding: 0 0 16px; }
.section-head { display: flex; align-items: flex-end; justify-content: space-between; padding: 16px; border-bottom: 1px solid var(--border); }
.section-head h3 { margin: 0; font-size: 18px; font-weight: 600; }
.tabs button { background: #fff; border: 1px solid var(--border); border-radius: 6px; padding: 6px 12px; margin-left: 8px; cursor: pointer; }
.tabs button.active { border-color: #d32f2f; color: #d32f2f; font-weight: 600; }
.red-underline { height: 2px; width: 60px; background: #d32f2f; margin: 0 16px 8px; }
.pager-wrap { margin: 18px 0 24px; }
.pager { display: flex; align-items: center; flex-wrap: wrap; gap: 8px; padding: 0; list-style: none; }
.pager li { border: 1px solid var(--border); border-radius: 6px; padding: 6px 10px; cursor: pointer; user-select: none; }
.pager li.active { background: #d32f2f; color: #fff; border-color: #d32f2f; }
.pager .btn-next { margin-left: 8px; }
.pager .btn-next.disabled { opacity: .5; pointer-events: none; }
.pager .jump { display: inline-flex; align-items: center; gap: 6px; border: none; }
.pager .jump input { width: 72px; height: 32px; border: 1px solid var(--border); border-radius: 4px; padding: 0 8px; }
@media (max-width: 1000px) { .grid-wrap { grid-template-columns: 1fr; } }
</style>
