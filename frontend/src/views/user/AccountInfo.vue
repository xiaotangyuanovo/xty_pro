<template>
  <div class="info">
    <!-- 顶部页签放在根元素内部 -->
    <div class="tabs">
      <router-link to="/user/account/info"   class="tab" :class="{on:$route.path.endsWith('/info')}">基本信息</router-link>
      <router-link to="/user/account/avatar" class="tab" :class="{on:$route.path.endsWith('/avatar')}">头像修改</router-link>
    </div>

    <form @submit.prevent="save">
      <div class="row"><label>学号/工号：</label><span>{{ form.code }}</span></div>

      <div class="row">
        <label><i class="req">*</i> 姓名：</label>
        <input v-model.trim="form.name" type="text" class="ipt">
      </div>

      <div class="row">
        <label><i class="req">*</i> 性别：</label>
        <label class="radio"><input type="radio" value="男" v-model="form.gender"> 男</label>
        <label class="radio"><input type="radio" value="女" v-model="form.gender"> 女</label>
      </div>

      <div class="row">
        <label><i class="req">*</i> 上传个人照片：</label>
        <span>图片</span>
        <input type="file" @change="pickPhoto" accept="image/*">
        <img v-if="form.photoUrl" :src="form.photoUrl" class="photo">
        <a v-if="form.photoUrl" href="javascript:;" @click="removePhoto">删除</a>
      </div>

      <div class="row">
        <label><i class="req">*</i> 民族：</label>
        <select v-model="form.nation" class="ipt">
          <option>汉族</option>
          <option>回族</option>
          <option>藏族</option>
          <option>苗族</option>
          <option>维吾尔族</option>
        </select>
      </div>

      <div class="row"><label>所属党组织：</label><span>{{ form.org }}</span></div>
      <div class="row"><label>所属院系：</label><span>{{ form.college }}</span></div>

      <div class="row">
        <label>手机号：</label>
        <input v-model.trim="form.phone" class="ipt" maxlength="11">
      </div>

      <div class="row">
        <label>邮箱：</label>
        <input v-model.trim="form.email" class="ipt">
      </div>

      <div class="actions">
        <button class="btn-save">保存</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AccountInfo',
  data(){
    return {
      form: {
        code: 'SA24225304',
        name: '陶尧',
        gender: '男',
        photoUrl: '',
        nation: '汉族',
        org: '软件学院党委',
        college: '软件学院',
        phone: '19855132730',
        email: '1242889734@qq.com'
      }
    };
  },
  methods: {
    pickPhoto(e){
      const f = e.target.files[0];
      if (!f) return;
      if (f.size > 2 * 1024 * 1024) { alert('图片请小于 2MB'); return; }
      const reader = new FileReader();
      reader.onload = ev => { this.form.photoUrl = ev.target.result; };
      reader.readAsDataURL(f);
    },
    removePhoto(){ this.form.photoUrl = ''; },
    save(){ alert('保存成功（示例）'); }
  }
};
</script>

<style scoped>
.info {
  max-width: 960px;
  background:#fff;
  border:1px solid var(--border);
  border-radius:8px;
  padding:18px 22px;   /* 统一内边距 */
}

/* 页签保持不变 … */


/* 页签样式 */
.tabs{display:flex;border-bottom:1px solid var(--border);margin-bottom:12px}
.tab{padding:10px 18px;text-decoration:none;color:#333}
.tab.on{color:#e53935;border-bottom:2px solid #e53935;font-weight:600}

.row { display:flex; align-items:center; margin:14px 0; }
.row > label { width: 140px; color:#666; }
.ipt { width: 360px; height: 34px; border:1px solid var(--border); border-radius:6px; padding:0 10px; }
.radio { margin-right:18px; }
.photo { width: 64px; height:64px; border-radius:50%; object-fit:cover; margin:0 10px; border:1px solid var(--border); }
.req { color:#e53935; margin-right:4px; }
.actions { margin-top:20px; }
.btn-save { width:120px; height:38px; background:#e53935; color:#fff; border:none; border-radius:6px; cursor:pointer; }
</style>
