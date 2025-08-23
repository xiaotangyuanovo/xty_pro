<template>
  <div class="container">
    <div class="card head">
      <h2 class="title">{{ resourceSafe.title || '资源详情' }}</h2>
      <p class="muted">所属模块：{{ moduleTitle }}</p>
    </div>

    <div class="card body">
      <!-- 左侧：媒体/文档预览 -->
      <div class="media">
        <VideoPlayer
          v-if="resourceSafe.category === 'video' && mediaUrl"
          :src="mediaUrl"
          :poster="coverUrl"
          :resource-id="resourceSafe.id"
          :expected-hours="resourceSafe.hours"
          @complete="onVideoComplete"
        />
        <DocViewer
          v-else-if="resourceSafe.category === 'doc' && mediaUrl"
          :src="mediaUrl"
          :resource-id="resourceSafe.id"
        />
        <div v-else class="missing">未找到资源文件，请将文件放到 <code>src/assets/videos/</code> 或 <code>src/assets/docs/</code> 目录。</div>
      </div>

      <!-- 右侧：资源信息 -->
      <div class="info">
        <p>
          <span class="tag req" v-if="resourceSafe.required">必修</span>
          <span class="hour">{{ resourceSafe.hours }} 学时</span>
          <span class="like">👍 {{ resourceSafe.likes }}</span>
        </p>
        <p class="desc">{{ resourceSafe.summary }}</p>

        <div class="lecturer light" v-if="resource">
          <div class="flag"></div>
          <strong>主讲人：</strong>{{ resourceSafe.lecturer }}
          <div class="lecturer-desc">{{ resourceSafe.lecturerDesc }}</div>
        </div>

        <div class="actions">
          <button class="btn-red" v-if="resourceSafe.category==='video'" @click="play()">开始学习</button>
          <button class="btn-plain" @click="$router.back()">返回</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VideoPlayer from '@components/media/VideoPlayer.vue';
import DocViewer from '@components/media/DocViewer.vue';

export default {
  name: 'ResourceView',
  components: { VideoPlayer, DocViewer },
  data() {
    return {
      moduleMap: {
        1: '深入学习马克思主义基本原理',
        2: '用习近平新时代中国特色社会主义思想武装头脑',
        3: '发扬党的优良传统与作风'
      },
      // 演示数据：为视频/文档资源补充 file 字段
      allResources: [
        { id: 101, title: '马克思主义的世界观和方法论', summary: '“工欲善其事，必先利其器。”作为共产党员的世界观和方法论…', cover: 'res1.jpg', category: 'video', file: 'res1.mp4', required: true,  hours: 2.5, likes: 22, completed: true,  lecturer: '董振华', lecturerDesc: '中共中央党校（国家行政学院）哲学教研部副主任、教授' },
        { id: 102, title: '如何理解“重读马克思”在当代的价值', summary: '历史常常出现这样一种现象…',                            cover: 'res2.jpg', category: 'video', file: 'res2.mp4', required: true,  hours: 0.5, likes: 5,  completed: true,  lecturer: '晏锦芳', lecturerDesc: '北京大学马克思主义文献研究中心主任' },
        { id: 103, title: '《资本论》导读',                         summary: '从剩余价值、劳动价值论等核心概念入手…',                    cover: 'res3.jpg', category: 'video', file: 'res3.mp4', required: false, hours: 1.5, likes: 8,  completed: false, lecturer: '王某某', lecturerDesc: '——' },
        { id: 104, title: '哲学基本问题的两种对立',                   summary: '唯物主义与唯心主义的历史演进与现实意义…',                  cover: 'res4.jpg', category: 'doc',   file: 'res4.pdf', required: true,  hours: 1.0, likes: 12, completed: false, lecturer: '李某某', lecturerDesc: '——' },
        { id: 105, title: '历史唯物主义方法论阅读材料',               summary: '社会存在与社会意识、生产力与生产关系的辩证关系…',          cover: 'res5.jpg', category: 'doc',   file: 'res5.docx', required: false, hours: 0.8, likes: 3,  completed: false, lecturer: '张某某', lecturerDesc: '——' },
      ]
    };
  },
  computed: {
    moduleTitle() { return this.moduleMap[this.$route.params.id] || '课程模块'; },
    resource() {
      const rid = Number(this.$route.params.rid);
      return this.allResources.find(r => r.id === rid);
    },
    resourceSafe() { return this.resource || {}; },
    coverUrl() {
      if (!this.resourceSafe.cover) return '';
      try { return require(`@/assets/courses/${this.resourceSafe.cover}`); }
      catch (e) { return ''; }
    },
    mediaUrl() {
      if (!this.resourceSafe.file) return '';
      try {
        if (this.resourceSafe.category === 'video') {
          return require(`@/assets/videos/${this.resourceSafe.file}`);
        } else {
          return require(`@/assets/docs/${this.resourceSafe.file}`);
        }
      } catch (e) { return ''; }
    }
  },
  methods: {
    play() {
      // 聚焦 video 开始播放（可选）
      const v = this.$el.querySelector('video');
      if (v) v.play();
    },
    onVideoComplete(payload) {
      // 这里可以上报“完成学习”到后端，payload 含 watchedSeconds/duration
      // axios.post('/api/learned', { rid: this.resourceSafe.id, ...payload })
      // eslint-disable-next-line no-console
      console.log('Video complete:', payload);
    }
  }
};
</script>

<style scoped>
.head .title { margin: 0 0 6px; }
.muted { color: var(--muted); }

.body { display: grid; grid-template-columns: 1.4fr 1fr; grid-column-gap: 24px; }
.missing { border: 1px dashed var(--border); border-radius: 8px; padding: 16px; color: var(--muted); }
.desc { color: var(--muted); line-height: 1.8; margin-top: 10px; }

.tag.req { border: 1.5px solid #d32f2f; color: #d32f2f; padding: 2px 8px; border-radius: 4px; font-size: 12px; margin-right: 8px; }
.hour { color: #666; margin-right: 12px; }
.like { color: #777; }
.lecturer.light { margin-top: 12px; background: #fafafa; border: 1px solid var(--border); border-radius: 8px; padding: 10px 12px; }
.flag { display: inline-block; width: 6px; height: 16px; background: #d32f2f; margin-right: 8px; vertical-align: middle; }
.lecturer-desc { margin-top: 6px; color: var(--muted); }

.actions { margin-top: 16px; display: flex; gap: 12px; }
.btn-red { padding: 8px 14px; background: #d32f2f; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
.btn-plain { padding: 8px 14px; background: #fff; border: 1px solid var(--border); border-radius: 6px; cursor: pointer; }

@media (max-width: 900px) { .body { grid-template-columns: 1fr; } }
</style>
