<template>
  <div class="container">
    <div class="crumb">首页 &gt;&gt; <span>考试中心</span></div>

    <div class="card">
      <div class="bar">
        <span class="bar-icon">📝</span>
        <h2 class="bar-title">{{ exam.title }}</h2>
      </div>

      <div class="rows">
        <div class="row">
          <div class="label">考试名称</div><div class="val">{{ exam.title }}</div>
        </div>
        <div class="row">
          <div class="label">启用要求</div><div class="val">理论学习完成之后，方可进行测试</div>
        </div>
        <div class="row">
          <div class="label">考试时长</div><div class="val">{{ exam.duration }}分钟</div>
        </div>
        <div class="row">
          <div class="label">截止时间</div><div class="val">{{ exam.deadline }}</div>
        </div>
        <div class="row">
          <div class="label">考题类型</div><div class="val">单选 / 多选 / 判断 / 填空</div>
        </div>
        <div class="row">
          <div class="label">试题总分</div><div class="val">100分</div>
        </div>
        <div class="row">
          <div class="label">及格分数</div><div class="val">60分</div>
        </div>
        <div class="row">
          <div class="label">当前状态</div>
          <div class="val">
            可测试{{ exam.maxAttempts }}次，<span class="emph">已测试{{ exam.usedAttempts }}次</span>
            （最高分{{ exam.best }}，平均分{{ exam.avg }}）
            <a href="javascript:;" @click="placeholder('错题')" class="link">查看错题</a>
            <a href="javascript:;" @click="placeholder('记录')" class="link">查看记录</a>
          </div>
        </div>
        <div class="row">
          <div class="label">考试说明</div><div class="val muted">—</div>
        </div>
      </div>

      <div class="actions">
        <button class="btn-red" @click="start">开始考试</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExamCenter',
  data() {
    return {
      exam: {
        id: 1,
        title: '综合测试',
        duration: 35,
        deadline: '2025-08-31',
        maxAttempts: 5,
        usedAttempts: 1,
        best: 94,
        avg: 94
      }
    };
  },
  methods: {
    start() {
      this.$router.push({ name: 'ExamTaking', params: { id: this.exam.id } });
    },
    placeholder(name) { this.$toast ? this.$toast(`暂未实现${name}`) : alert(`暂未实现${name}`); }
  }
};
</script>

<style scoped>
.crumb { text-align: right; color: #777; margin: 8px 0; }
.bar { display: flex; align-items: center; border-bottom: 2px solid #e53935; padding-bottom: 8px; }
.bar-icon { color: #e53935; margin-right: 6px; }
.bar-title { margin: 0; font-size: 20px; }

.rows { margin-top: 16px; }
.row { display: grid; grid-template-columns: 180px 1fr; margin-bottom: 12px; }
.label { background: #f5f5f5; padding: 10px 12px; border-radius: 6px; color: #666; }
.val { background: #fafafa; padding: 10px 12px; border-radius: 6px; }
.muted { color: var(--muted); }
.emph { color: #e53935; }
.link { margin-left: 10px; }

.actions { display: flex; justify-content: flex-end; margin-top: 16px; }
.btn-red { padding: 10px 16px; background: #e53935; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
</style>
