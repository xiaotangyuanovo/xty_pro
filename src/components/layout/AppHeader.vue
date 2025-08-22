<template>
  <header class="header">
    <div class="container header__inner">
      <div class="brand" @click="$router.push('/')">
        <img class="logo" :src="logo" alt="Logo">
        <span class="title">CETC · 学习测评系统</span>
      </div>
      <nav class="nav">
        <a :class="{active: isActive('/')}"        @click.prevent="$router.push('/')">首页</a>
        <a :class="{active: isActive('/theory')}"  @click.prevent="$router.push('/theory')">理论学习</a>
        <a :class="{active: isActive('/exam')}"    @click.prevent="$router.push('/exam')">考试中心</a>
        <a :class="{active: isActive('/summary')}" @click.prevent="$router.push('/summary')">培训总结</a>
        <a :class="{active: isActive('/circle')}"  @click.prevent="$router.push('/circle')">学习圈</a>
        <a :class="{active: isActive('/news')}"    @click.prevent="$router.push('/news')">资讯</a>

        <!-- 新增：最右侧的个人中心按钮 -->
        <a
          class="user-btn"
          :class="{active: isActive('/user')}"
          @click.prevent="$router.push('/user')"
        >个人中心</a>
      </nav>
    </div>
  </header>
</template>

<script>
import logo from '@assets/logos/logo.png';
export default {
  name: 'AppHeader',
  data: () => ({ logo }),
  methods: {
    isActive(prefix) {
      return this.$route.path === prefix || this.$route.path.startsWith(prefix + '/');
    }
  }
};
</script>

<style scoped>
.header { background:#fff; border-bottom:1px solid var(--border); }
.header__inner { height:60px; display:flex; align-items:center; justify-content:space-between; }
.brand { display:flex; align-items:center; cursor:pointer; }
.logo { width:32px; height:32px; margin-right:10px; }
.title { font-weight:600; }

/* 导航使用 flex 布局，将“个人中心”按钮推到最右 */
.nav {
  display: flex;
  align-items: center;
  width: 100%;
  gap: 0; /* 由 a 的 margin 控制间距 */
}
.nav a {
  margin-left: 16px;
  padding: 6px 10px;
  border-radius: 6px;
  text-decoration: none;
  color: inherit;
}
.nav a:hover { background:#f6f6f6; }
.nav a.active { color: var(--primary); font-weight: 600; }

/* 个人中心按钮：常态描边，悬停/选中时实心 */
.user-btn {
  margin-left: auto;                 /* 关键：靠最右 */
  border: 1px solid var(--primary);
  color: var(--primary);
  border-radius: 6px;
  padding: 6px 12px;
}
.user-btn:hover,
.user-btn.active {
  background: var(--primary);
  color: #fff;
}
.user-btn.active {
  border-color: var(--primary);
  color: #fff !important;            /* 确保选中时文字可见 */
}

</style>
