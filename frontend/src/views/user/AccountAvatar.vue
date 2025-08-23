<template>
  <div class="avatar">
    <div class="preview">
      <img :src="preview || defaultAvatar" alt="avatar">
    </div>
    <div class="ops">
      <input type="file" accept="image/*" @change="select">
      <button class="btn" :disabled="!preview" @click="save">保存头像</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AccountAvatar',
  data(){
    return {
      preview: '',
      defaultAvatar: require('@/assets/avatar/default.png')
    };
  },
  methods: {
    select(e){
      const f = e.target.files[0];
      if (!f) return;
      if (!/image\//.test(f.type)) { alert('请选择图片'); return; }
      const reader = new FileReader();
      reader.onload = ev => { this.preview = ev.target.result; };
      reader.readAsDataURL(f);
    },
    save(){
      // 可改为真实上传接口
      try {
        localStorage.setItem('user.avatar', this.preview);
      } catch (e) {
        void e; // 占位以满足 eslint(no-empty) 且不输出日志
      }
      alert('头像已保存（示例）');
    }
  }
};
</script>

<style scoped>
.avatar {
  max-width: 960px;
  background:#fff;
  border:1px solid var(--border);
  border-radius:8px;
  padding:18px 22px;
}

.tabs{display:flex;border-bottom:1px solid var(--border);margin-bottom:12px}
.tab{padding:10px 18px;text-decoration:none;color:#333}
.tab.on{color:#e53935;border-bottom:2px solid #e53935;font-weight:600}

.content { display:flex; align-items:center; gap:20px; }
.preview img { width:120px; height:120px; border-radius:50%; border:1px solid var(--border); object-fit:cover; }
.btn { height:36px; padding:0 12px; border:1px solid var(--border); background:#fff; border-radius:6px; cursor:pointer; }

</style>
