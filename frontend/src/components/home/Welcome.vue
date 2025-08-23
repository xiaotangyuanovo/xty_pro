<template>
  <div class="card welcome">
    <div class="welcome__row">
      <div class="avatar" aria-hidden="true"></div>
      <div class="info">
        <h2 class="hello">
          {{ userName }}，您好！
        </h2>
        <p class="line">
          学习阶段：<strong>{{ role }}</strong>
        </p>
        <p class="line">
          所在班级：<strong>{{ organization }}</strong>
        </p>
      </div>
    </div>
    <p class="intro">
      欢迎进入CETC学习平台，您的学习共有 {{ stages }} 个环节，分别是理论学习、培训总结、综合测试。
      {{ stages }} 个环节均合格后可顺利结业。
    </p>
  </div>
</template>

<script>
export default {
  name: 'Welcome',
  props: {
    userName: { type: String, default: '同学' },
    role: { type: String, default: '学员' },
    organization: { type: String, default: '游客' },
    stages: { type: Number, default: 3 }
  }
};
</script>

<style scoped>
.welcome__row {
  display: flex;
  align-items: center;
  margin-bottom: 18px;
}

/* 用纯 CSS 做一个灰色占位头像（避免图片路径问题） */
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: #f1f1f1;
  border: 1px solid var(--border);
  box-shadow: 0 2px 6px rgba(0,0,0,.05) inset;
  margin-right: 24px;
  flex: 0 0 auto;
}

.info .hello {
  margin: 0 0 8px;
  font-size: 20px;
  font-weight: 600;
  color: var(--text);
}
.line { margin: 4px 0; color: var(--text); }
.intro { margin: 8px 0 0; color: var(--muted); line-height: 1.8; }
</style>
