import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store';

Vue.use(VueRouter);

const Home         = () => import('@/views/Home.vue');
const Theory       = () => import('@/views/Theory.vue');
const TheoryModule = () => import('@/views/TheoryModule.vue');
const ResourceView = () => import('@/views/ResourceView.vue');
const Login        = () => import('@/views/auth/Login.vue');

const ExamCenter   = () => import('@/views/ExamCenter.vue');   // ← 详情+开始考试
const ExamTaking   = () => import('@/views/ExamTaking.vue');   // ← 作答页

const TrainSummary = () => import('@/views/TrainSummary.vue');
const StudyCircle  = () => import('@/views/StudyCircle.vue');
const News         = () => import('@/views/News.vue');
const NotFound     = () => import('@/views/NotFound.vue');

const NewsArticle  = () => import('@/views/NewsArticle.vue');   
const NoticeDetail = () => import('@/views/NoticeDetail.vue');  

// 顶部其它 import 保持不变
const UserLayout     = () => import('@/views/user/UserLayout.vue');  // 只有这一个父布局
const AccountInfo    = () => import('@/views/user/AccountInfo.vue');
const AccountAvatar  = () => import('@/views/user/AccountAvatar.vue');
const UserLesson     = () => import('@/views/user/UserLesson.vue');
const UserStudy      = () => import('@/views/user/UserStudy.vue');
const UserArchives   = () => import('@/views/user/UserArchives.vue');
const UserNotice     = () => import('@/views/user/UserNotice.vue');


const router = new VueRouter({
  mode: 'hash',
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/home', name: 'Home', component: Home },
    { path: '/theory', name: 'Theory', component: Theory },
    { path: '/theory/module/:id', name: 'TheoryModule', component: TheoryModule },
    { path: '/theory/module/:id/resource/:rid', name: 'ResourceView', component: ResourceView },

    { path: '/exam', name: 'ExamCenter', component: ExamCenter },
    { path: '/exam/:id/take', name: 'ExamTaking', component: ExamTaking },

    { path: '/summary', name: 'TrainSummary', component: TrainSummary },
    { path: '/circle',  name: 'StudyCircle',  component: StudyCircle },
    { path: '/news',    name: 'News',         component: News },

      { path: '/news/article/:id', name: 'NewsArticle', component: NewsArticle },
      { path: '/news/notice/:id',  name: 'NoticeDetail', component: NoticeDetail },

      { path: '/login', name: 'Login', component: Login },
    
    {
  path: '/user',
  component: UserLayout,          // ← 统一的父布局，只渲染一次侧栏
  children: [
    { path: '', redirect: 'account/info' },

    // 账号管理（不再有中间的 AccountLayout）
    { path: 'account/info',   name: 'UserAccountInfo',   component: AccountInfo },
    { path: 'account/avatar', name: 'UserAccountAvatar', component: AccountAvatar },

    // 其它栏目
    { path: 'lesson',   name: 'UserLesson',   component: UserLesson },
    { path: 'study',    name: 'UserStudy',    component: UserStudy },
    { path: 'archives', name: 'UserArchives', component: UserArchives },
    { path: 'notice',   name: 'UserNotice',   component: UserNotice }
    ]
  },
      { path: '*',        name: 'NotFound',     component: NotFound },
    // …404 等

  ],
  scrollBehavior() { return { x: 0, y: 0 }; }
});

router.beforeEach((to, from, next) => {
  if (!store.state.auth.token && to.path !== '/login') {
    next('/login');
  } else {
    next();
  }
});

export default router;
