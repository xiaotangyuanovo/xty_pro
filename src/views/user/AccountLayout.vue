<template>
  <div class="container user-wrap">
    <!-- 左侧菜单（唯一的一份） -->
    <aside class="side card">
      <div class="side-head">个人中心</div>

      <div class="group">
        <div class="group-title">账号管理</div>
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/account/info')}"
          to="/user/account/info">基本信息</router-link>
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/account/avatar')}"
          to="/user/account/avatar">头像修改</router-link>
      </div>

      <div class="group">
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/lesson')}"
          to="/user/lesson">我的课程</router-link>
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/study')}"
          to="/user/study">学习统计</router-link>
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/archives')}"
          to="/user/archives">我的成绩单</router-link>
        <router-link class="item"
          :class="{on: $route.path.startsWith('/user/notice')}"
          to="/user/notice">通知消息</router-link>
      </div>

      <div class="actions">
        <button class="btn-danger" @click="logout">安全退出</button>
      </div>
    </aside>

    <!-- 右侧内容区 -->
    <section class="main">
      <router-view />
    </section>
  </div>
</template>

<script>
export default {
  name: 'UserLayout',
  methods: {
    logout() {
      if (confirm('确认退出当前账号？')) {
        try { localStorage.clear(); } catch (e) { void e; }
        this.$router.replace('/');
      }
    }
  }
};
</script>

<style scoped>
.user-wrap { display: grid; grid-template-columns: 280px 1fr; grid-column-gap: 20px; }

/* 侧栏 */
.side { padding: 0; }
.side-head { background: #e53935; color: #fff; padding: 14px 16px; font-size: 18px; }
.group { border-top: 1px solid var(--border); }
.group-title { color: #999; padding: 10px 16px; }
.item { display: block; padding: 12px 16px; color: #333; text-decoration: none; }
.item:hover { background: #fafafa; }
.item.on { background: #fff5f5; color: #e53935; font-weight: 600; }
.actions { padding: 14px 16px; border-top: 1px solid var(--border); }
.btn-danger { width: 100%; height: 38px; background: #fff; border: 1px solid #e53935; color: #e53935; border-radius: 6px; cursor: pointer; }

/* 右侧内容 */
.main { min-height: 560px; }
</style>
