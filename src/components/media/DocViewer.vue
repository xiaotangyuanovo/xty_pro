<template>
  <div class="doc">
    <div v-if="isPdf" class="pdf">
      <object :data="src" type="application/pdf" width="100%" height="720px">
        <embed :src="src" type="application/pdf" />
        <p>无法内嵌预览 PDF。<a :href="src" target="_blank" rel="noopener">下载查看</a></p>
      </object>
    </div>

    <div v-else class="docx">
      <div ref="holder" class="holder"></div>
      <p v-if="loadError" class="muted">
        DOCX 预览失败。<a :href="src" target="_blank" rel="noopener">下载查看</a>
      </p>
    </div>

    <div class="foot">
      <span>已阅读（本地统计）：{{ learnedMinutes }} 分钟</span>
      <button class="btn" @click="markDone">标记已读</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DocViewer',
  props: {
    src: { type: String, required: true },
    resourceId: { type: [String, Number], required: true }
  },
  data() {
    return {
      isPdf: false,
      loadError: false,
      timer: null,
      seconds: 0,
      visChangeHandler: null,
      // CDN 与本地回退
      CDN_JS: 'https://cdn.jsdelivr.net/npm/docx-preview@0.3.1/dist/docx-preview.min.js',
      CDN_CSS: 'https://cdn.jsdelivr.net/npm/docx-preview@0.3.1/dist/docx-preview.min.css',
      LOCAL_JS: '/libs/docx-preview.min.js',   // 可选：放到 public/libs/ 下
      LOCAL_CSS: '/libs/docx-preview.min.css'  // 可选：放到 public/libs/ 下
    };
  },
  async mounted() {
    this.isPdf = /\.pdf(\?|#|$)/i.test(this.src);

    const saved = localStorage.getItem(`progress:doc:${this.resourceId}`);
    if (saved) { try { this.seconds = JSON.parse(saved).seconds || 0; } catch (e) { void e; } }

    this.startTimer();

    if (!this.isPdf) {
      await this.renderDocx();
    }
  },
  beforeDestroy() {
    this.stopTimer();
    this.persist();
  },
  methods: {
    // ========= 进度统计 =========
    startTimer() {
      this.timer = setInterval(() => {
        if (!document.hidden) this.seconds += 1;
        if (this.seconds % 10 === 0) this.persist();
      }, 1000);
      this.visChangeHandler = () => this.persist();
      document.addEventListener('visibilitychange', this.visChangeHandler);
    },
    stopTimer() {
      if (this.timer) clearInterval(this.timer);
      if (this.visChangeHandler) {
        document.removeEventListener('visibilitychange', this.visChangeHandler);
        this.visChangeHandler = null;
      }
    },
    persist() {
      localStorage.setItem(
        `progress:doc:${this.resourceId}`,
        JSON.stringify({ seconds: this.seconds, updatedAt: Date.now() })
      );
    },

    // ========= DOCX 预览 =========
    async loadDocxLib() {
      // 已加载
      if (window.docx && window.docx.renderAsync) return;

      // 先插入 CSS
      const ensureCss = (href) => new Promise((resolve) => {
        if ([...document.styleSheets].some(s => (s.href || '').includes('docx-preview'))) return resolve();
        const link = document.createElement('link');
        link.rel = 'stylesheet'; link.href = href;
        link.onload = () => resolve();
        link.onerror = () => resolve(); // CSS 失败也不阻断
        document.head.appendChild(link);
      });

      // 动态加载脚本
      const loadScript = (src) => new Promise((resolve, reject) => {
        const existed = [...document.scripts].some(s => s.src.includes('docx-preview'));
        if (existed && window.docx && window.docx.renderAsync) return resolve();
        const s = document.createElement('script');
        s.src = src; s.async = true;
        s.onload = () => resolve();
        s.onerror = () => reject(new Error('load failed: ' + src));
        document.head.appendChild(s);
      });

      // 优先用 CDN，失败则尝试本地 /libs
      await ensureCss(this.CDN_CSS);
      try {
        await loadScript(this.CDN_JS);
      } catch (e) {
        await ensureCss(this.LOCAL_CSS);
        await loadScript(this.LOCAL_JS);
      }

      if (!(window.docx && window.docx.renderAsync)) {
        throw new Error('docx-preview 未可用');
      }
    },

    async renderDocx() {
      try {
        await this.loadDocxLib();
        const res = await fetch(this.src);
        const buf = await res.arrayBuffer();
        await window.docx.renderAsync(buf, this.$refs.holder, null, {
          inWrapper: false,
          className: 'docx',
          breakPages: true
        });
      } catch (e) {
        this.loadError = true;
        // eslint-disable-next-line no-console
        console.error('DOCX 渲染失败：', e);
      }
    },

    markDone() {
      if (this.seconds < 10 * 60) this.seconds = 10 * 60;
      this.persist();
    }
  },
  computed: {
    learnedMinutes() { return Math.floor(this.seconds / 60); }
  }
};
</script>

<style scoped>
.holder { border: 1px solid var(--border); border-radius: 8px; padding: 16px; max-height: 1000px; overflow: auto; background: #fff; }
.foot { margin-top: 12px; display: flex; gap: 12px; align-items: center; }
.btn { padding: 6px 10px; border: 1px solid var(--border); border-radius: 6px; background: #fff; cursor: pointer; }
.muted { color: var(--muted); }
</style>
