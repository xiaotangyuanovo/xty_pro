<template>
  <div class="home">
    <!-- 顶部 Banner -->
    <Banner :imgUrl="bannerImg" alt="学习测评系统" />

    <!-- 欢迎 + 通知 -->
    <section class="two-col">
      <div class="col-left">
        <Welcome
          userName="张三"
          role="新学员"
          organization="XX一班"
          :stages="3"
        />
      </div>
      <div class="col-right">
        <Notification :items="['申请审核结果']" />
      </div>
    </section>

    <!-- 我的课程 -->
    <section>
      <h2 class="section-title">我的课程</h2>
      <div class="grid grid-3">
        <CourseCard
          v-for="c in courses"
          :key="c.id"
          :course="c"
          @click="goCourse(c)"
        />
      </div>
    </section>

    <!-- 🔥 热门课程推荐（新增） -->
    <section>
      <h2 class="section-title">热门课程推荐</h2>
      <div class="grid grid-3">
        <CourseCard
          v-for="c in hotCourses"
          :key="`hot-${c.id}`"
          :course="c"
          @click="goCourse(c)"
        />
      </div>
    </section>
  </div>
</template>

<script>
import Banner from '@components/common/Banner.vue';
import CourseCard from '@components/course/CourseCard.vue';
import Welcome from '@components/home/Welcome.vue';
import Notification from '@components/home/Notification.vue';
import { mapGetters } from 'vuex';
import bannerImg from '@assets/banners/banner1.jpg';

export default {
  name: 'Home',
  components: { Banner, CourseCard, Welcome, Notification },
  data: () => ({ bannerImg }),
  computed: {
    ...mapGetters(['courseList']),
    courses() { return this.courseList; },
    // 简单示例：把现有课程按一定规则作为热门（比如倒序/取前N个）
    hotCourses() {
      // 方案1：直接取前3个
      return this.courseList.slice(0, 3);
      // 方案2（可选）：根据后端“hot”字段过滤
      // return this.courseList.filter(c => c.hot);
    }
  },
  methods: {
    goCourse(c) {
      this.$router.push({ name: 'Courses', query: { id: c.id } });
    }
  }
};
</script>

<style scoped>
.section-title {
  margin: 18px 0 12px;
  font-size: 18px;
  font-weight: 600;
}

.two-col {
  display: grid;
  grid-template-columns: 2fr 1fr;
  grid-gap: 16px;
  margin: 8px 0 20px;
}
@media (max-width: 900px) {
  .two-col { grid-template-columns: 1fr; }
}
.col-left, .col-right { width: 100%; }
</style>
