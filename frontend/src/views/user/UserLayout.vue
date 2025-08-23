<template>
  <div class="container user-wrap">
    <aside class="side card">
      <div class="side-head">个人中心</div>

      <div class="group">
        <div class="group-title">账号管理</div>
        <router-link :to="{name:'UserAccountInfo'}"   class="item" :class="{on: isRoute('UserAccountInfo')}">基本信息</router-link>
        <router-link :to="{name:'UserAccountAvatar'}" class="item" :class="{on: isRoute('UserAccountAvatar')}">头像修改</router-link>
      </div>

      <div class="group">
        <router-link :to="{name:'UserLesson'}"   class="item" :class="{on: isRoute('UserLesson')}">我的课程</router-link>
        <router-link :to="{name:'UserStudy'}"    class="item" :class="{on: isRoute('UserStudy')}">学习统计</router-link>
        <router-link :to="{name:'UserArchives'}" class="item" :class="{on: isRoute('UserArchives')}">我的成绩单</router-link>
        <router-link :to="{name:'UserNotice'}"   class="item" :class="{on: isRoute('UserNotice')}">通知消息</router-link>
      </div>

      <div class="actions">
        <button class="btn-danger" @click="logout">安全退出</button>
      </div>
    </aside>

    <section class="main">
      <router-view />
    </section>
  </div>
</template>

<script>
export default {
  name: 'UserLayout',
  methods: {
    isRoute(name){ return this.$route.name === name; },
    logout(){
      if (confirm('确认退出当前账号？')) {
        try { localStorage.clear(); } catch(e) {void e}
        this.$router.replace('/');
      }
    }
  }
};
</script>

<style scoped>
.user-wrap { display:grid; grid-template-columns: 280px 1fr; grid-column-gap: 32px; }
.side { padding:0; }
.side-head { background:#e53935; color:#fff; padding:14px 16px; font-size:18px; }
.group { border-top:1px solid var(--border); padding:10px 0; }
.group-title { color:#999; padding:8px 16px; }
.item { display:block; padding:12px 16px; color:#333; text-decoration:none; }
.item:hover { background:#fafafa; }
.item.on { background:#fff5f5; color:#e53935; font-weight:600; }
.actions { padding:12px 16px; border-top:1px solid var(--border); }
.btn-danger { width:100%; height:38px; background:#fff; border:1px solid #e53935; color:#e53935; border-radius:6px; cursor:pointer; }
.main { min-height:560px; padding: 12px 14px; }
</style>
