<template>
  <div class="card course-card" @click="$emit('click', course)">
    <img class="cover" :src="course.cover" :alt="course.title">
    <div class="meta">
      <h3 class="title">{{ course.title }}</h3>
      <p class="teacher">{{ course.teacher }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CourseCard',
  props: {
    course: { type: Object, required: true }
  }
};
</script>

<style scoped>
.course-card { cursor: pointer; transition: transform .1s ease; }
.course-card:hover { transform: translateY(-2px); }
.cover { width: 100%; height: 160px; object-fit: cover; border-radius: 8px; }
.meta { margin-top: 10px; }
.title { font-size: 16px; margin: 0 0 6px; }
.teacher { color: var(--muted); margin: 0; }
</style>
