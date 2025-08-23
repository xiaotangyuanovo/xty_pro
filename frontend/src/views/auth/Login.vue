<template>
  <div class="login">
    <form @submit.prevent="onSubmit">
      <div>
        <label>用户名</label>
        <input v-model="form.username" placeholder="请输入用户名" />
      </div>
      <div>
        <label>密码</label>
        <input type="password" v-model="form.password" placeholder="请输入密码" />
      </div>
      <button type="submit">登录</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'Login',
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    ...mapActions('auth', ['login']),
    async onSubmit() {
      const res = await this.login(this.form);
      if (res && res.token) {
        this.$router.push('/');
      } else {
        // 可在此处理失败提示
      }
    }
  }
};
</script>

<style scoped>
.login {
  max-width: 320px;
  margin: 40px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
.login label {
  display: block;
  margin-bottom: 4px;
  font-weight: bold;
}
.login input {
  width: 100%;
  padding: 6px 8px;
  margin-bottom: 12px;
  box-sizing: border-box;
}
.login button {
  width: 100%;
  padding: 8px 0;
  background-color: #409eff;
  border: none;
  color: #fff;
  cursor: pointer;
}
</style>
