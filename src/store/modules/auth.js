import { login as loginApi } from '@/api/auth';

const state = () => ({
  token: null
});

const mutations = {
  setToken(state, token) {
    state.token = token;
  }
};

const actions = {
  async login({ commit }, payload) {
    const data = await loginApi(payload);
    if (data && data.token) {
      commit('setToken', data.token);
    }
    return data;
  }
};

export default { namespaced: true, state, mutations, actions };
