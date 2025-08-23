<template>
  <div class="container">
    <div class="card article">
      <h1 class="title">通知：{{ notice.title }}</h1>
      <div class="meta">
        <span>发布日期：{{ notice.date }}</span>
        <span>状态：{{ notice.read ? '已阅' : '未读' }}</span>
      </div>
      <hr />
      <div class="content">
        <p class="para">这是通知的详细内容示例。你可以把后端返回的正文填到 <code>notice.content</code> 字段里进行渲染。</p>
      </div>
    </div>
  </div>
</template>

<script>
import { noticeList } from '@store/newsData.js';

export default {
  name: 'NoticeDetail',
  data(){ return { notice: {} }; },
  created() {
    const id = Number(this.$route.params.id);
    this.notice = noticeList.find(n => n.id === id) || {};
  }
};
</script>

<style scoped>
.article { max-width: 920px; margin: 0 auto; padding: 24px 28px; }
.title { text-align:center; font-size: 28px; margin: 6px 0 10px; }
.meta { text-align:center; color:#666; margin-bottom: 14px; }
.meta span + span { margin-left: 20px; }
.content { font-size: 16px; line-height: 1.9; color: #333; }
.para { margin: 14px 0; }
</style>
