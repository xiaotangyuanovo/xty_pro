import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

// 可按模块拆分，这里给简单示例
export default new Vuex.Store({
  state: {
    user: null,
    courses: [
      { id: 1, title: '马克思主义的世界观和方法论', teacher: '王老师', cover: require('@assets/courses/course1.jpg') },
      { id: 2, title: '为什么要重新阅读马克思主义', teacher: '李老师', cover: require('@assets/courses/course2.jpg') },
      { id: 3, title: '习近平关于社会主义政治建设的重要论述', teacher: '张老师', cover: require('@assets/courses/course3.jpg') }
    ]
  },
  mutations: {
    setUser(state, payload) { state.user = payload; }
  },
  actions: {},
  getters: {
    courseList: s => s.courses
  }
});
