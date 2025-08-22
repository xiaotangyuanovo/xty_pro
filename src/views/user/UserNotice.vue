<template>
  <div class="card">
    <div class="head"><h3>通知消息</h3></div>
    <div class="list">
      <div class="row" v-for="n in paged" :key="n.id">
        <span class="pill">通知</span>
        <span class="title">{{ n.title }}</span>
        <span class="date">{{ n.date }}</span>
        <span class="state">【{{ n.read?'已阅':'未读' }}】</span>
      </div>
    </div>

    <div class="pager" v-if="pages>1">
      <button :disabled="page<=1" @click="page--">上一页</button>
      <span v-for="p in pages" :key="p" :class="['num',{on:p===page}]" @click="page=p">{{p}}</span>
      <button :disabled="page>=pages" @click="page++">下一页</button>
    </div>
  </div>
</template>

<script>
import { noticeList } from '@store/newsData.js';
export default {
  name:'UserNotice',
  data(){ return { list: noticeList, pageSize: 5, page: 1 }; },
  computed:{
    pages(){ return Math.max(1, Math.ceil(this.list.length/this.pageSize)); },
    paged(){ const s=(this.page-1)*this.pageSize; return this.list.slice(s, s+this.pageSize); }
  }
};
</script>

<style scoped>
.head{padding:14px 16px; border-bottom:1px solid var(--border)}
.list{padding: 0 16px}
.row{display:flex;align-items:center;gap:10px;padding:12px 8px;border-bottom:1px dotted var(--border)}
.pill{border:1px solid #e53935;color:#e53935;border-radius:6px;padding:2px 8px}
.title{flex:1}
.date{color:#666}
.state{width:66px;text-align:right;color:#666}
.pager{display:flex;gap:8px;align-items:center; padding:12px 16px}
.num{border:1px solid var(--border); padding:6px 10px; border-radius:6px; cursor:pointer}
.num.on{background:#e53935;color:#fff;border-color:#e53935}
</style>
