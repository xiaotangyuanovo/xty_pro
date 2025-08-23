<template>
  <div>
    <h2>课程列表</h2>
    <div class="grid grid-3">
      <CourseCard
        v-for="c in courses"
        :key="c.id"
        :course="c"
        @click="goCourse(c)"
      />
    </div>
  </div>
</template>

<script>
import CourseCard from '@components/course/CourseCard.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'Courses',
  components: { CourseCard },
  computed: {
    ...mapGetters(['courseList']),
    courses() { return this.courseList; }
  },
  methods: {
    goCourse(c) {
      // TODO: 等你给我“课程详情页”需求后，我补 /courses/:id 路由与页面
      alert(`点击课程：${c.title}（后续跳转详情页）`);
    }
  }
};
</script>
