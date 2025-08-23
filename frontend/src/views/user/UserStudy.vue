<template>
  <div class="card pad">
    <h3>学习统计</h3>
    <div class="stats">
      <div class="s"><div class="n">{{ hours }}</div><div class="t">累计学时</div></div>
      <div class="s"><div class="n">{{ finished }}</div><div class="t">已完成课程</div></div>
      <div class="s"><div class="n">{{ passed }}/{{ total }}</div><div class="t">合格环节/总环节</div></div>
    </div>
  </div>
</template>
<script>
export default {
  name:'UserStudy',
  data(){ return { hours: 28.5, finished: 12, passed: 3, total: 3 }; }
};
</script>
<style scoped>
.pad{padding:18px 24px}
.stats{display:grid;grid-template-columns:repeat(3,1fr);gap:16px;margin-top:12px}
.s{border:1px solid var(--border);border-radius:8px;padding:14px;text-align:center}
.n{font-size:24px;font-weight:700}
.t{color:#666;margin-top:6px}
</style>
