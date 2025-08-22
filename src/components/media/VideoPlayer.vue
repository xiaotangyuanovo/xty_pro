<template>
  <div class="vp">
    <video
      ref="video"
      :src="src"
      :poster="poster"
      controls
      playsinline
      @loadedmetadata="onLoadedMeta"
      @timeupdate="onTimeUpdate"
      @ended="onEnded"
    ></video>

    <div class="bar"><div class="bar__fill" :style="{ width: progressPct + '%' }"></div></div>

    <div class="stat">
      <span>进度：{{ progressPct }}%</span>
      <span>已观看（本地统计）：{{ learnedHours }} 小时</span>
      <span v-if="expectedHours >= 0">课程学时：{{ expectedHours.toFixed(1) }} 小时</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VideoPlayer',
  props: {
    src: { type: String, required: true },
    poster: { type: String, default: '' },
    resourceId: { type: [String, Number], required: true },
    expectedHours: { type: Number, default: -1 }
  },
  data() {
    return { position: 0, duration: 0, watched: 0 };
  },
  mounted() {
    const saved = localStorage.getItem(`progress:video:${this.resourceId}`);
    if (saved) {
      try {
        const p = JSON.parse(saved);
        this.position = p.positionSeconds || 0;
        this.watched  = p.watchedSeconds || 0;
      } catch (e) { void e; } // 避免 no-empty
    }
  },
  methods: {
    onLoadedMeta(e) {
      this.duration = e.target.duration || 0;
      if (this.position > 0 && this.position < this.duration) {
        e.target.currentTime = this.position; // 断点续播
      }
    },
    onTimeUpdate(e) {
      const t = e.target.currentTime || 0;
      if (t > this.position) {
        this.watched += (t - this.position);
        this.position = t;
      }
      this.persist();
    },
    onEnded() {
      this.persist(true);
      this.$emit('complete', {
        resourceId: this.resourceId,
        watchedSeconds: this.watched,
        duration: this.duration
      });
    },
    persist(ended = false) {
      localStorage.setItem(
        `progress:video:${this.resourceId}`,
        JSON.stringify({
          positionSeconds: this.position,
          watchedSeconds:  this.watched,
          duration:        this.duration,
          ended,
          updatedAt: Date.now()
        })
      );
    }
  },
  computed: {
    progressPct() {
      return this.duration ? Math.min(100, Math.round((this.position / this.duration) * 100)) : 0;
    },
    learnedHours() {
      return +(this.watched / 3600).toFixed(2);
    }
  }
};
</script>

<style scoped>
.vp video { width: 100%; border: 1px solid var(--border); border-radius: 8px; }
.bar { height: 6px; background: #f2f2f2; border-radius: 999px; margin: 8px 0; overflow: hidden; }
.bar__fill { height: 100%; background: #d32f2f; width: 0%; transition: width .2s; }
.stat { display: flex; gap: 16px; color: #666; font-size: 14px; }
</style>
