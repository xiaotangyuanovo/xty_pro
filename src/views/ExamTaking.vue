<template>
  <div class="container exam-wrap">
    <!-- 标题与操作 -->
    <div class="top">
      <h2 class="title">{{ exam.title }}</h2>
      <div class="ops">
        <button class="btn-red" @click="submitPaper">交卷</button>
        <button class="btn" @click="quit">退出考试</button>
        <div class="timer">⏱ 倒计时：<span class="time">{{ remainText }}</span></div>
      </div>
    </div>

    <div class="grid">
      <!-- 左侧：题目区 -->
      <section class="card q-area">
        <div class="q-type-tip">{{ typeTip }}</div>

        <div class="q">
          <div class="stem">
            <span class="num">{{ currentIndex + 1 }}.</span>
            <span v-html="current.stem"></span>
            <button class="mark" :class="{on: isMarked(current.id)}" @click="toggleMark(current.id)">☆ 标记</button>
          </div>

          <!-- 单选 -->
          <ul v-if="current.type==='single'" class="opts">
            <li v-for="opt in current.options" :key="opt.value">
              <label>
                <input type="radio" :name="'s_'+current.id" :value="opt.value" v-model="answers[current.id]" />
                <span class="o-text">{{ opt.value }}. {{ opt.text }}</span>
              </label>
            </li>
          </ul>

          <!-- 多选 -->
          <ul v-else-if="current.type==='multiple'" class="opts">
            <li v-for="opt in current.options" :key="opt.value">
              <label>
                <input type="checkbox" :value="opt.value" v-model="answersMulti[current.id]" />
                <span class="o-text">{{ opt.value }}. {{ opt.text }}</span>
              </label>
            </li>
          </ul>

          <!-- 判断 -->
          <ul v-else-if="current.type==='judge'" class="opts">
            <li>
              <label><input type="radio" :name="'j_'+current.id" value="T" v-model="answers[current.id]" /> 正确</label>
            </li>
            <li>
              <label><input type="radio" :name="'j_'+current.id" value="F" v-model="answers[current.id]" /> 错误</label>
            </li>
          </ul>

          <!-- 填空 -->
          <div v-else class="fill">
            <input class="fill-input" v-model.trim="answersFill[current.id]" placeholder="请输入答案" />
          </div>

          <div class="divider"></div>

          <div class="nav">
            <button class="btn-orange" :disabled="currentIndex===0" @click="prev">上一题</button>
            <span class="progress">{{ currentIndex + 1 }}/{{ questions.length }}</span>
            <button class="btn-orange" :disabled="currentIndex===questions.length-1" @click="next">下一题</button>
          </div>
        </div>
      </section>

      <!-- 右侧：答题卡 -->
      <aside class="card side">
        <div class="legend">
          <label><input type="checkbox" disabled /> 未作答</label>
          <label><input type="checkbox" checked disabled /> 已作答</label>
          <span class="flag">■ 标记</span>
        </div>

        <div class="group">
          <div class="g-title">单选题</div>
          <div class="cards">
            <span
              v-for="id in groups.single"
              :key="'s'+id"
              class="cell"
              :class="cellClass(id)"
              @click="jumpTo(id)"
            >{{ indexOf(id)+1 }}</span>
          </div>
        </div>

        <div class="group">
          <div class="g-title">多选题</div>
          <div class="cards">
            <span v-for="id in groups.multiple" :key="'m'+id" class="cell" :class="cellClass(id)" @click="jumpTo(id)">{{ indexOf(id)+1 }}</span>
          </div>
        </div>

        <div class="group">
          <div class="g-title">判断题</div>
          <div class="cards">
            <span v-for="id in groups.judge" :key="'j'+id" class="cell" :class="cellClass(id)" @click="jumpTo(id)">{{ indexOf(id)+1 }}</span>
          </div>
        </div>

        <div class="group">
          <div class="g-title">填空题</div>
          <div class="cards">
            <span v-for="id in groups.fill" :key="'f'+id" class="cell" :class="cellClass(id)" @click="jumpTo(id)">{{ indexOf(id)+1 }}</span>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExamTaking',
  data() {
  return {
    exam: {
      id: Number(this.$route.params.id) || 1,
      title: '综合测试',
      duration: 35 // 分钟
    },
    startAt: 0,   // ← 进入页面时再设置
    now: 0,       // ← 响应式“当前时间”，每秒更新一次
    tick: null,
    currentIndex: 0,

    questions: [],

    answers: {},
    answersMulti: {},
    answersFill: {},
    marks: []
        };
},

    created() {
  // 开始时间 & 当前时间
  this.startAt = Date.now();
  this.now = this.startAt;

  // 生成试卷
  this.questions = this.buildPaper();

  // ✅ 初始化答案（关键：多选必须初始化为 []）
  this.questions.forEach(q => {
    if (q.type === 'multiple') {
      if (!Array.isArray(this.answersMulti[q.id])) this.$set(this.answersMulti, q.id, []);
    } else if (q.type === 'single' || q.type === 'judge') {
      if (typeof this.answers[q.id] === 'undefined') this.$set(this.answers, q.id, '');
    } else if (q.type === 'fill') {
      if (typeof this.answersFill[q.id] === 'undefined') this.$set(this.answersFill, q.id, '');
    }
  });

  // ⏱ 启动倒计时：每秒更新一次 now（响应式），不用 $forceUpdate
  this.tick = setInterval(() => {
    this.now = Date.now();
    if (this.remainMs <= 0) {
      clearInterval(this.tick);
      this.submitPaper(true);
    }
  }, 1000);
},


  beforeDestroy() {
  if (this.tick) clearInterval(this.tick);
},

  computed: {
    current() { return this.questions[this.currentIndex] || {}; },

  // 依赖 now，每次 now 变化，remainMs/remainText 自动重算
  remainMs() {
    const durationMs = Number(this.exam.duration || 0) * 60 * 1000;
    const end = this.startAt + durationMs;
    return Math.max(0, end - this.now);
  },
  remainText() {
    // 向上取整，视觉更自然
    const s = Math.ceil(this.remainMs / 1000);
    const hh = String(Math.floor(s / 3600)).padStart(2, '0');
    const mm = String(Math.floor((s % 3600) / 60)).padStart(2, '0');
    const ss = String(s % 60).padStart(2, '0');
    return `${hh}:${mm}:${ss}`;
  },
    typeTip() {
      const t = this.current.type;
      if (t === 'single') return '单选题（每题1分）每小题只有一个正确答案，错选、多选或未选均不得分';
      if (t === 'multiple') return '多选题（每题2分）每小题有两个或两个以上正确答案，漏选、错选不得分';
      if (t === 'judge') return '判断题（每题1分）正确填“T”，错误填“F”';
      return '填空题（每题5分）';
    },
    groups() {
      return {
        single:  this.questions.filter(q => q.type === 'single').map(q => q.id),
        multiple:this.questions.filter(q => q.type === 'multiple').map(q => q.id),
        judge:   this.questions.filter(q => q.type === 'judge').map(q => q.id),
        fill:    this.questions.filter(q => q.type === 'fill').map(q => q.id)
      };
    }
  },
  methods: {
    buildPaper() {
      const qs = [];
      let id = 1;

      // 50 单选（每题 1 分）
      for (let i = 0; i < 50; i++) {
        const opts = ['A','B','C','D'].map((v, idx) => ({ value: v, text: ['高质量发展','城乡融合发展','对外开放','法治建设'][idx] || `选项${v}` }));
        qs.push({
          id: id++,
          type: 'single',
          stem: '（ ）是全面建设社会主义现代化国家的首要任务。',
          options: opts,
          answer: 'A',
          score: 1
        });
      }

      // 10 多选（每题 2 分）
      for (let i = 0; i < 10; i++) {
        const opts = ['A','B','C','D'].map(v => ({ value: v, text: `备选项 ${v}` }));
        qs.push({
          id: id++,
          type: 'multiple',
          stem: '下列哪些属于我国基本政治制度？（多选）',
          options: opts,
          answer: ['A', 'C'],
          score: 2
        });
      }

      // 5 判断（每题 1 分）
      for (let i = 0; i < 5; i++) {
        qs.push({
          id: id++,
          type: 'judge',
          stem: '历史唯物主义认为社会存在决定社会意识。',
          answer: 'T',
          score: 1
        });
      }

      // 5 填空（每题 5 分）
      const blanks = ['人民', '中国共产党', '马克思主义', '高质量发展', '从严治党'];
      for (let i = 0; i < 5; i++) {
        qs.push({
          id: id++,
          type: 'fill',
          stem: `请填写关键词：________`,
          answer: blanks[i],
          score: 5
        });
      }

      return qs;
    },

    isMarked(id) { return this.marks.indexOf(id) !== -1; },
    toggleMark(id) {
      const k = this.marks.indexOf(id);
      if (k === -1) this.marks.push(id);
      else this.marks.splice(k, 1);
    },

    next() { if (this.currentIndex < this.questions.length - 1) this.currentIndex++; },
    prev() { if (this.currentIndex > 0) this.currentIndex--; },

    indexOf(id) { return this.questions.findIndex(q => q.id === id); },
    jumpTo(id) { const idx = this.indexOf(id); if (idx >= 0) this.currentIndex = idx; },

    // 答题卡状态样式
    cellClass(id) {
      const q = this.questions.find(x => x.id === id);
      const answered =
        (q.type === 'single' || q.type === 'judge') ? !!this.answers[id] :
        (q.type === 'multiple') ? (this.answersMulti[id] && this.answersMulti[id].length > 0) :
        (this.answersFill[id] && this.answersFill[id].length > 0);
      return {
        done: answered,
        marked: this.isMarked(id)
      };
    },

    // 判分
    scoreOf(q) {
      if (q.type === 'single' || q.type === 'judge') {
        return this.answers[q.id] === q.answer ? q.score : 0;
      }
      if (q.type === 'multiple') {
        const a = this.answersMulti[q.id] || [];
        if (a.length !== q.answer.length) return 0;
        const same = q.answer.every(v => a.indexOf(v) !== -1);
        return same ? q.score : 0;
      }
      // 填空：忽略大小写和空格
      const v = (this.answersFill[q.id] || '').trim().toLowerCase();
      const ans = String(q.answer).trim().toLowerCase();
      return v && v === ans ? q.score : 0;
    },

    submitPaper(auto = false) {
        if (this.tick) { clearInterval(this.tick); this.tick = null; }
      if (!auto) {
        const ok = confirm('确认提交试卷？提交后将显示成绩。');
        if (!ok) return;
      }
      let total = 0;
      let got = 0;
      for (let i = 0; i < this.questions.length; i++) {
        const q = this.questions[i];
        total += q.score;
        got   += this.scoreOf(q);
      }
      alert(`提交成功！本次得分：${got}/${total}`);
      this.$router.replace({ name: 'ExamCenter' });
    },

    quit() {
      const ok = confirm('确定要退出考试吗？未提交的答题将丢失。');
      if (ok) this.$router.back();
    }
  }
};
</script>

<style scoped>
.top { display: flex; align-items: center; justify-content: space-between; margin-bottom: 10px; }
.title { margin: 0; font-size: 20px; }
.ops { display: flex; align-items: center; gap: 10px; }
.timer .time { color: #e53935; font-weight: 700; }

.grid { display: grid; grid-template-columns: 1fr 320px; grid-column-gap: 16px; }
.q-area { padding: 0; }
.q-type-tip { padding: 12px 16px; border-bottom: 1px solid var(--border); color: #666; }
.q { padding: 16px; }
.stem { font-size: 16px; margin-bottom: 10px; }
.num { margin-right: 6px; }
.mark { margin-left: 10px; border: 1px solid var(--border); background: #fff; border-radius: 4px; padding: 2px 8px; cursor: pointer; }
.mark.on { color: #e53935; border-color: #e53935; }

.opts { list-style: none; padding: 0; margin: 0 0 10px; }
.opts li { padding: 8px 0; }
.o-text { margin-left: 8px; }

.fill-input { width: 100%; height: 36px; border: 1px solid var(--border); border-radius: 6px; padding: 0 10px; }

.divider { height: 1px; background: var(--border); margin: 12px 0; }
.nav { display: flex; align-items: center; justify-content: center; gap: 12px; }
.progress { color: #666; }

.side { padding: 12px; }
.legend { display: flex; align-items: center; gap: 12px; color: #666; margin-bottom: 12px; }
.legend input { pointer-events: none; }
.flag { color: #e53935; font-weight: 600; margin-left: 6px; }

.group { margin-bottom: 16px; }
.g-title { font-weight: 600; margin-bottom: 6px; }
.cards { display: grid; grid-template-columns: repeat(10, 1fr); gap: 6px; }
.cell {
  display: inline-flex; align-items: center; justify-content: center;
  height: 28px; border: 1px solid var(--border); border-radius: 4px; cursor: pointer;
  user-select: none;
}
.cell.done { background: #f5faff; border-color: #90caf9; }
.cell.marked { outline: 2px solid #e53935; }

.btn-red { padding: 8px 14px; background: #e53935; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
.btn { padding: 8px 14px; background: #fff; border: 1px solid var(--border); border-radius: 6px; cursor: pointer; }
.btn-orange { padding: 8px 14px; background: #ff9800; color: #fff; border: none; border-radius: 6px; cursor: pointer; }
@media (max-width: 1000px) { .grid { grid-template-columns: 1fr; } }
</style>
